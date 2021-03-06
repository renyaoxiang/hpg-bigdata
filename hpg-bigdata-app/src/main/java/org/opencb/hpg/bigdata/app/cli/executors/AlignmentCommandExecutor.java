/*
 * Copyright 2015 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.hpg.bigdata.app.cli.executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import htsjdk.samtools.SAMFileHeader;
import htsjdk.samtools.SAMSequenceRecord;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;
import org.ga4gh.models.ReadAlignment;
import org.opencb.biodata.models.alignment.RegionCoverage;
import org.opencb.biodata.models.core.Region;
import org.opencb.biodata.tools.alignment.AlignmentOptions;
import org.opencb.biodata.tools.alignment.BamManager;
import org.opencb.biodata.tools.alignment.BamUtils;
import org.opencb.biodata.tools.alignment.stats.AlignmentGlobalStats;
import org.opencb.commons.utils.FileUtils;
import org.opencb.hpg.bigdata.app.cli.options.AlignmentCommandOptions;
import org.opencb.hpg.bigdata.core.avro.AlignmentAvroSerializer;
import org.opencb.hpg.bigdata.core.lib.AlignmentDataset;
import org.opencb.hpg.bigdata.core.lib.SparkConfCreator;
import org.opencb.hpg.bigdata.core.parquet.AlignmentParquetConverter;
import org.opencb.hpg.bigdata.core.utils.ReadAlignmentUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * Created by imedina on 16/03/15.
 */
public class AlignmentCommandExecutor extends CommandExecutor {

    private AlignmentCommandOptions alignmentCommandOptions;

    public static final String BAM_HEADER_SUFFIX = ".header";

    public AlignmentCommandExecutor(AlignmentCommandOptions alignmentCommandOptions) {
        super(alignmentCommandOptions.commonCommandOptions);
        this.alignmentCommandOptions = alignmentCommandOptions;
    }

    /*
     * Parse specific 'alignment' command options
     */
    public void execute() throws Exception {
        String subCommandString = getParsedSubCommand(alignmentCommandOptions.jCommander);

        // init
        init(alignmentCommandOptions.commonCommandOptions.logLevel,
                alignmentCommandOptions.commonCommandOptions.verbose,
                alignmentCommandOptions.commonCommandOptions.conf);

        switch (subCommandString) {
            case "convert":
                convert();
                break;
            case "sort":
                sort();
                break;
            case "stats":
                stats();
                break;
            case "coverage":
                coverage();
                break;
            case "query":
                query();
                break;
            case "view":
                view();
                break;
            /*
            case "align":
                System.out.println("Sub-command 'align': Not yet implemented for the command 'alignment' !");
                break;
*/
            default:
                logger.error("Alignment subcommand '" + subCommandString + "' not valid");
                break;
        }
    }

    private void convert() throws IOException {

        // sanity check: paremeter 'to'
        String to = alignmentCommandOptions.convertAlignmentCommandOptions.to.toLowerCase();
        if (!to.equals("avro") && !to.equals("parquet")) {
            throw new IllegalArgumentException("Unknown serialization format: " + to + ". Valid values: avro, parquet");
        }
        boolean toParquet = to.equals("parquet");

        String from = alignmentCommandOptions.convertAlignmentCommandOptions.from.toLowerCase();
        if (!from.equals("bam") && !from.equals("avro")) {
            throw new IllegalArgumentException("Unknown input format: " + from + ". Valid values: bam, avro");
        }
        boolean fromAvro = from.equals("avro");

        // sanity check: parameter 'compression'
        String compressionCodecName = alignmentCommandOptions.convertAlignmentCommandOptions.compression.toLowerCase();
        if (!compressionCodecName.equals("gzip")
                && !compressionCodecName.equals("snappy")) {
            throw new IllegalArgumentException("Unknown compression method: " + compressionCodecName
                    + ". Valid values: gzip, snappy");
        }

        // sanity check: input file
        Path inputPath = Paths.get(alignmentCommandOptions.convertAlignmentCommandOptions.input);
        FileUtils.checkFile(inputPath);

        // sanity check: output file
        String output = CliUtils.getOutputFilename(alignmentCommandOptions.convertAlignmentCommandOptions.input,
                alignmentCommandOptions.convertAlignmentCommandOptions.output, to);

        long startTime, elapsedTime;
        boolean binQualities = alignmentCommandOptions.convertAlignmentCommandOptions.binQualities;

        // convert to parquet if required
        if (toParquet) {
            // sanity check: rowGroupSize and pageSize for parquet conversion
            int rowGroupSize = alignmentCommandOptions.convertAlignmentCommandOptions.blockSize;
            if (rowGroupSize <= 0) {
                throw new IllegalArgumentException("Invalid block size: " + rowGroupSize
                        + ". It must be greater than 0");
            }
            int pageSize = alignmentCommandOptions.convertAlignmentCommandOptions.pageSize;
            if (pageSize <= 0) {
                throw new IllegalArgumentException("Invalid page size: " + pageSize
                        + ". It must be greater than 0");
            }

            // create the Parquet writer and add the necessary filters
            AlignmentParquetConverter parquetConverter = new AlignmentParquetConverter(
                    CompressionCodecName.fromConf(compressionCodecName), binQualities, rowGroupSize, pageSize);

            // set minimum mapping quality filter
            if (alignmentCommandOptions.convertAlignmentCommandOptions.minMapQ > 0) {
                parquetConverter.addMinMapQFilter(alignmentCommandOptions.convertAlignmentCommandOptions.minMapQ);
            }

            // region filter management,
            // we use the same region list to store all regions from both parameter --regions and
            // parameter --region-file
            List<Region> regions = CliUtils.getRegionList(alignmentCommandOptions.convertAlignmentCommandOptions.regions,
                    alignmentCommandOptions.convertAlignmentCommandOptions.regionFilename);
            if (regions != null && regions.size() > 0) {
                parquetConverter.addRegionFilter(regions, false);
            }

            if (fromAvro) {
                // convert to AVRO -> PARQUET
                InputStream inputStream = new FileInputStream(inputPath.toString());
                System.out.println("\n\nStarting AVRO->PARQUET conversion...\n");
                startTime = System.currentTimeMillis();
                parquetConverter.toParquetFromAvro(inputStream, output);
                elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("\n\nFinish AVRO->PARQUET conversion in " + (elapsedTime / 1000F) + " sec\n");

                // header file management
                File headerFile = new File(inputPath.toString() + ".header");
                if (headerFile.exists()) {
                    File outHeaderFile = new File(output + ".header");
                    Files.copy(headerFile.toPath(), new FileOutputStream(outHeaderFile));
                }
            } else {
                // convert to BAM -> PARQUET
                System.out.println("\n\nStarting BAM->PARQUET conversion...\n");
                startTime = System.currentTimeMillis();
                parquetConverter.toParquetFromBam(inputPath.toString(), output);
                elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("\n\nFinish BAM->PARQUET conversion in " + (elapsedTime / 1000F) + " sec\n");
            }
        } else {
            // convert to BAM -> AVRO

            AlignmentAvroSerializer avroSerializer;
            avroSerializer = new AlignmentAvroSerializer(compressionCodecName, binQualities);

            // set minimum mapping quality filter
            if (alignmentCommandOptions.convertAlignmentCommandOptions.minMapQ > 0) {
                avroSerializer.addMinMapQFilter(alignmentCommandOptions.convertAlignmentCommandOptions.minMapQ);
            }

            // region filter management,
            // we use the same region list to store all regions from both parameter --regions and
            // parameter --region-file
            List<Region> regions = CliUtils.getRegionList(alignmentCommandOptions.convertAlignmentCommandOptions.regions,
                    alignmentCommandOptions.convertAlignmentCommandOptions.regionFilename);
            if (regions != null && regions.size() > 0) {
                avroSerializer.addRegionFilter(regions, false);
            }

            System.out.println("\n\nStarting BAM->AVRO conversion...\n");
            startTime = System.currentTimeMillis();
            avroSerializer.toAvro(inputPath.toString(), output, false);
            elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("\n\nFinish BAM->AVRO conversion in " + (elapsedTime / 1000F) + " sec\n");
        }



//        if (alignmentCommandOptions.convertAlignmentCommandOptions.toBam) {
//            // conversion: GA4GH/Avro model -> BAM
//
//            // header management: read it from a separate file
//            File file = new File(input + BAM_HEADER_SUFFIX);
//            FileInputStream fis = new FileInputStream(file);
//            byte[] data = new byte[(int) file.length()];
//            fis.read(data);
//            fis.close();
//
//            InputStream is = new FileInputStream(input);
//
//            String textHeader = new String(data);
//
//            LineReader lineReader = new StringLineReader(textHeader);
//            SAMFileHeader header = new SAMTextHeaderCodec().decode(lineReader, textHeader);
//
//            // reader
//            DataFileStream<ReadAlignment> reader = new DataFileStream<ReadAlignment>(is, new SpecificDatumReader<>(ReadAlignment.class));
//
//            // writer
//            OutputStream os = new FileOutputStream(new File(output));
//            SAMFileWriter writer = new SAMFileWriterFactory().makeBAMWriter(header, false, new File(output));
//
//            // main loop
//            int reads = 0;
//            SAMRecord samRecord;
//            SAMRecord2ReadAlignmentConverter converter = new SAMRecord2ReadAlignmentConverter();
//            for (ReadAlignment readAlignment : reader) {
//                samRecord = converter.backward(readAlignment);
//                samRecord.setHeader(header);
//                writer.addAlignment(samRecord);
//                if (++reads % 100_000 == 0) {
//                    System.out.println("Converted " + reads + " reads");
//                }
//            }
//
//            // close
//            reader.close();
//            writer.close();
//            os.close();
//            is.close();
//
//        } else {
//
//            // conversion: BAM -> GA4GH/Avro model
///*            System.out.println("Loading library hpgbigdata...");
//            System.out.println("\tjava.libary.path = " + System.getProperty("java.library.path"));
//            System.loadLibrary("hpgbigdata");
//            System.out.println("...done!");
//            new NativeSupport().bam2ga(input, output, compressionCodecName == null
//                    ? "snappy"
//                    : compressionCodecName, alignmentCommandOptions.convertAlignmentCommandOptions.adjustQuality);
//
//            try {
//                // header management: saved it in a separate file
//                SamReader reader = SamReaderFactory.makeDefault().open(new File(input));
//                SAMFileHeader header = reader.getFileHeader();
//                PrintWriter pwriter = null;
//                pwriter = new PrintWriter(new FileWriter(output + BAM_HEADER_SUFFIX));
//                pwriter.write(header.getTextHeader());
//                pwriter.close();
//            } catch (IOException e) {
//                throw e;
//            }
//*/
//
//            boolean adjustQuality = alignmentCommandOptions.convertAlignmentCommandOptions.adjustQuality;
//            AlignmentAvroSerializer avroSerializer = new AlignmentAvroSerializer(compressionCodecName);
//            avroSerializer.toAvro(input, output);
//
//        }
    }

    public void sort() throws Exception {
        // check mandatory parameter 'input file'
        Path inputPath = Paths.get(alignmentCommandOptions.sortAlignmentCommandOptions.input);
        FileUtils.checkFile(inputPath);

        // TODO: to take the spark home from somewhere else
        SparkConf sparkConf = SparkConfCreator.getConf("variant query", "local", 1,
                true, "/home/jtarraga/soft/spark-2.0.0/");
        System.out.println("sparkConf = " + sparkConf.toDebugString());
        SparkSession sparkSession = new SparkSession(new SparkContext(sparkConf));

//        SparkConf sparkConf = SparkConfCreator.getConf("MyTest", "local", 1, true, "/home/jtarraga/soft/spark-2.0.0/");
//        SparkSession sparkSession = new SparkSession(new SparkContext(sparkConf));

        AlignmentDataset ad = new AlignmentDataset(sparkSession);

        ad.load(alignmentCommandOptions.sortAlignmentCommandOptions.input); //, sparkSession);

        // sort
        ad.orderBy("alignment.position.referenceName", "alignment.position.position");

        // save the dataset
        logger.warn("The current query implementation saves the resulting dataset in Avro format.");
        CliUtils.saveDatasetAsOneFile(ad, "avro", alignmentCommandOptions.sortAlignmentCommandOptions.output, logger);
    }

    private void stats() throws Exception {
        // get input parameters
        String input = alignmentCommandOptions.statsAlignmentCommandOptions.input;
        String output = alignmentCommandOptions.statsAlignmentCommandOptions.output;

        try {
            // compute stats using the BamManager
            BamManager alignmentManager = new BamManager(Paths.get(input));
            AlignmentGlobalStats stats = alignmentManager.stats();
            alignmentManager.close();

            // write results
            PrintWriter writer = new PrintWriter(new File(output + "/stats.json"));
            writer.write(stats.toJSON());
            writer.close();

        } catch (Exception e) {
            throw e;
        }
    }

    private void coverage() throws IOException {
        final int chunkSize = 10000;

        // get input parameters
        String input = alignmentCommandOptions.coverageAlignmentCommandOptions.input;
        String output = alignmentCommandOptions.coverageAlignmentCommandOptions.output;

        Path filePath = Paths.get(input);

        // writer
        PrintWriter writer = new PrintWriter(new File(output + "/" + filePath.getFileName() + ".coverage"));

        SAMFileHeader fileHeader = BamUtils.getFileHeader(filePath);

        AlignmentOptions options = new AlignmentOptions();
        options.setContained(false);

        float[] values;

        BamManager alignmentManager = new BamManager(filePath);
        Iterator<SAMSequenceRecord> iterator = fileHeader.getSequenceDictionary().getSequences().iterator();
        while (iterator.hasNext()) {
            SAMSequenceRecord next = iterator.next();
            for (int i = 0; i < next.getSequenceLength(); i += chunkSize) {
                Region region = new Region(next.getSequenceName(), i + 1,
                        Math.min(i + chunkSize, next.getSequenceLength()));
                RegionCoverage regionCoverage = alignmentManager.coverage(region, null, options);

                // write coverages to file (only values greater than 0)
                values = regionCoverage.getValues();
                for (int j=0, start = region.getStart(); j < values.length; j++, start++) {
                    if (values[j] > 0) {
                        writer.write(next.getSequenceName() + "\t" + start + "\t" + values[j] + "\n");
                    }
                }
            }
        }

        // close
        writer.close();
    }

    public void query() throws Exception {
        // check mandatory parameter 'input file'
        Path inputPath = Paths.get(alignmentCommandOptions.queryAlignmentCommandOptions.input);
        FileUtils.checkFile(inputPath);

        // TODO: to take the spark home from somewhere else
        SparkConf sparkConf = SparkConfCreator.getConf("variant query", "local", 1,
                true, "/home/jtarraga/soft/spark-2.0.0/");
        System.out.println("sparkConf = " + sparkConf.toDebugString());
        SparkSession sparkSession = new SparkSession(new SparkContext(sparkConf));

//        SparkConf sparkConf = SparkConfCreator.getConf("MyTest", "local", 1, true, "/home/jtarraga/soft/spark-2.0.0/");
//        SparkSession sparkSession = new SparkSession(new SparkContext(sparkConf));

        AlignmentDataset ad = new AlignmentDataset(sparkSession);

        ad.load(alignmentCommandOptions.queryAlignmentCommandOptions.input);
        ad.createOrReplaceTempView("alignment");

        // query for region
        List<Region> regions = CliUtils.getRegionList(alignmentCommandOptions.queryAlignmentCommandOptions.regions,
                alignmentCommandOptions.queryAlignmentCommandOptions.regionFile);
        if (regions != null && regions.size() > 0) {
            ad.regionFilter(regions);
        }

        // query for minimun mapping quality
        if (alignmentCommandOptions.queryAlignmentCommandOptions.minMapQ > 0) {
            ad.mappingQualityFilter(">=" + alignmentCommandOptions.queryAlignmentCommandOptions.minMapQ);
        }

        // query for flags
        if (alignmentCommandOptions.queryAlignmentCommandOptions.requireFlags != Integer.MAX_VALUE)  {
            ad.flagFilter("" + alignmentCommandOptions.queryAlignmentCommandOptions.requireFlags, false);
        }
        if (alignmentCommandOptions.queryAlignmentCommandOptions.filteringFlags != 0) {
            ad.flagFilter("" + alignmentCommandOptions.queryAlignmentCommandOptions.filteringFlags, true);
        }

        // query for template length
        if (alignmentCommandOptions.queryAlignmentCommandOptions.minTLen != 0) {
            ad.templateLengthFilter(">=" + alignmentCommandOptions.queryAlignmentCommandOptions.minTLen);
        }
        if (alignmentCommandOptions.queryAlignmentCommandOptions.maxTLen != Integer.MAX_VALUE) {
            ad.templateLengthFilter("<=" + alignmentCommandOptions.queryAlignmentCommandOptions.maxTLen);
        }

        // query for alignment length
        if (alignmentCommandOptions.queryAlignmentCommandOptions.minALen != 0) {
            ad.alignmentLengthFilter(">=" + alignmentCommandOptions.queryAlignmentCommandOptions.minALen);
        }
        if (alignmentCommandOptions.queryAlignmentCommandOptions.maxALen != Integer.MAX_VALUE) {
            ad.alignmentLengthFilter("<=" + alignmentCommandOptions.queryAlignmentCommandOptions.maxALen);
        }

        // apply previous filters
        ad.update();

        // save the dataset
        logger.warn("The current query implementation saves the resulting dataset in Avro format.");
        CliUtils.saveDatasetAsOneFile(ad, "avro", alignmentCommandOptions.queryAlignmentCommandOptions.output, logger);
    }

    public void view() throws Exception {
        Path input = Paths.get(alignmentCommandOptions.viewAlignmentCommandOptions.input);
        int head = alignmentCommandOptions.viewAlignmentCommandOptions.head;

        // open
        InputStream is = new FileInputStream(input.toFile());
        DataFileStream<ReadAlignment> reader = new DataFileStream<>(is,
                new SpecificDatumReader<>(ReadAlignment.class));

        long counter = 0;
        ObjectMapper mapper = new ObjectMapper();
        if (alignmentCommandOptions.viewAlignmentCommandOptions.sam) {
            // sam
            // first, header
            File headerFile = new File(input.toString() + ".header");
            if (headerFile.exists()) {
                System.out.println(org.apache.commons.io.FileUtils.readFileToString(headerFile).trim());
            }

            // and then, alignments
            for (ReadAlignment alignment : reader) {
                System.out.println(ReadAlignmentUtils.getSamString(alignment));
                counter++;
                if (head > 0 && counter == head) {
                    break;
                }
            }
        } else if (alignmentCommandOptions.viewAlignmentCommandOptions.schema) {
            // schema
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    mapper.readValue(reader.getSchema().toString(), Object.class)));
        } else {
            // main
            System.out.println("[");
            for (ReadAlignment alignment : reader) {
                // remove nucleotide sequences ?
                if (alignmentCommandOptions.viewAlignmentCommandOptions.excludeSequences) {
                    alignment.setAlignedSequence(null);
                }

                // remove quality sequences ?
                if (alignmentCommandOptions.viewAlignmentCommandOptions.excludeQualities) {
                    alignment.setAlignedQuality(null);
                }

                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        mapper.readValue(alignment.toString(), Object.class)));
                counter++;
                if (head > 0 && counter == head) {
                    break;
                }
                System.out.println(",");
            }
            System.out.println("]");
        }

        // close
        reader.close();
    }

}
