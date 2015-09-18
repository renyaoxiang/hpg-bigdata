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

package org.opencb.hpg.bigdata.app.cli.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.ga4gh.models.ReadAlignment;
import org.opencb.hpg.bigdata.app.cli.CommandExecutor;
import org.opencb.hpg.bigdata.tools.converters.mr.Bam2AvroMR;
import org.opencb.hpg.bigdata.tools.io.parquet.ParquetMR;
import org.opencb.hpg.bigdata.tools.stats.alignment.mr.ReadAlignment2FqMR;
import org.opencb.hpg.bigdata.tools.stats.alignment.mr.ReadAlignmentDepthMR;
import org.opencb.hpg.bigdata.tools.stats.alignment.mr.ReadAlignmentStatsMR;

import java.io.IOException;
import java.util.Date;

/**
 * Created by imedina on 16/03/15.
 */
public class AlignmentCommandExecutor extends CommandExecutor {

    private CliOptionsParser.AlignmentCommandOptions alignmentCommandOptions;

    public AlignmentCommandExecutor(CliOptionsParser.AlignmentCommandOptions alignmentCommandOptions) {
        this.alignmentCommandOptions = alignmentCommandOptions;
    }

    /**
     * Parse specific 'alignment' command options
     */
    public void execute() throws Exception {
        String subCommand = alignmentCommandOptions.getParsedSubCommand();

        switch (subCommand) {
            case "convert":
                init(alignmentCommandOptions.convertAlignmentCommandOptions.commonOptions.logLevel,
                        alignmentCommandOptions.convertAlignmentCommandOptions.commonOptions.verbose,
                        alignmentCommandOptions.convertAlignmentCommandOptions.commonOptions.conf);
                convert();
                break;
            case "stats":
                init(alignmentCommandOptions.statsAlignmentCommandOptions.commonOptions.logLevel,
                        alignmentCommandOptions.statsAlignmentCommandOptions.commonOptions.verbose,
                        alignmentCommandOptions.statsAlignmentCommandOptions.commonOptions.conf);
                stats();
                break;
            case "depth":
                init(alignmentCommandOptions.depthAlignmentCommandOptions.commonOptions.logLevel,
                        alignmentCommandOptions.depthAlignmentCommandOptions.commonOptions.verbose,
                        alignmentCommandOptions.depthAlignmentCommandOptions.commonOptions.conf);
                depth();
                break;
            case "bam2fq":
                init(alignmentCommandOptions.bam2fqAlignmentCommandOptions.commonOptions.logLevel,
                        alignmentCommandOptions.bam2fqAlignmentCommandOptions.commonOptions.verbose,
                        alignmentCommandOptions.bam2fqAlignmentCommandOptions.commonOptions.conf);
                bam2fq();
                break;
            case "align":
                System.out.println("Sub-command 'align': Not yet implemented for the command 'alignment' !");
                break;
            default:
                break;
        }
    }

    private void convert() throws Exception {
        String input = alignmentCommandOptions.convertAlignmentCommandOptions.input;
        String output = alignmentCommandOptions.convertAlignmentCommandOptions.output;
        String compressionCodecName = alignmentCommandOptions.convertAlignmentCommandOptions.compression;

        // sanity check
        if (compressionCodecName.equals("null")) {
            compressionCodecName = null;
        }

        // run MapReduce job to convert to GA4GH/Avro/Parquet model
        try {
            Bam2AvroMR.run(input, output, compressionCodecName, alignmentCommandOptions.convertAlignmentCommandOptions.adjustQuality);
            // Parquet runs from Avro file
            if (alignmentCommandOptions.convertAlignmentCommandOptions.toParquet) {
                new ParquetMR(ReadAlignment.getClassSchema()).run(output, output + ".parquet", compressionCodecName);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void stats() throws Exception {
        String input = alignmentCommandOptions.statsAlignmentCommandOptions.input;
        String output = alignmentCommandOptions.statsAlignmentCommandOptions.output;

        // prepare the HDFS output folder
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        String outHdfsDirname = Long.toString(new Date().getTime());

        // run MapReduce job to compute stats
        ReadAlignmentStatsMR.run(input, outHdfsDirname);

        // post-processing
        Path outFile = new Path(outHdfsDirname + "/part-r-00000");

        try {
            if (!fs.exists(outFile)) {
                logger.error("Stats results file not found: {}", outFile.getName());
            } else {
                String outRawFileName =  output + "/stats.json";
                fs.copyToLocalFile(outFile, new Path(outRawFileName));

                //Utils.parseStatsFile(outRawFileName, out);
            }
            fs.delete(new Path(outHdfsDirname), true);
        } catch (IOException e) {
            throw e;
        }
    }

    private void depth() throws Exception {
        String input = alignmentCommandOptions.depthAlignmentCommandOptions.input;
        String output = alignmentCommandOptions.depthAlignmentCommandOptions.output;

        // prepare the HDFS output folder
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        // run MapReduce job to compute stats
        ReadAlignmentDepthMR.run(input, output);

//        String tmpOutDirName = output + "_" + Long.toString(new Date().getTime());
//
//        // run MapReduce job to compute stats
//        ReadAlignmentDepthMR.run(input, tmpOutDirName);
//
//        // post-processing
//        Path depthOutFile = new Path(tmpOutDirName + "/part-r-00000");
//
//        try {
//            if (!fs.exists(depthOutFile)) {
//                logger.error("Stats results file not found: {}", depthOutFile.getName());
//            } else {
//                String outRawFileName =  output + ".depth.txt";
//                fs.copyToLocalFile(depthOutFile, new Path(outRawFileName));
//
//                //Utils.parseStatsFile(outRawFileName, out);
//            }
//            fs.delete(new Path(tmpOutDirName), true);
//        } catch (IOException e) {
//            throw e;
//        }
    }

    private void bam2fq() throws Exception {
        String input = alignmentCommandOptions.bam2fqAlignmentCommandOptions.input;
        String output = alignmentCommandOptions.bam2fqAlignmentCommandOptions.output;
        String compression = alignmentCommandOptions.bam2fqAlignmentCommandOptions.compression;

        // prepare the HDFS output folder
//        Configuration conf = new Configuration();
//        FileSystem fs = FileSystem.get(conf);

        // run MapReduce job to compute stats
        ReadAlignment2FqMR.run(input, output, compression);

//        String tmpOutDirName = output + "_" + Long.toString(new Date().getTime());
//
//        // run MapReduce job to compute stats
//        ReadAlignmentDepthMR.run(input, tmpOutDirName);
//
//        // post-processing
//        Path depthOutFile = new Path(tmpOutDirName + "/part-r-00000");
//
//        try {
//            if (!fs.exists(depthOutFile)) {
//                logger.error("Stats results file not found: {}", depthOutFile.getName());
//            } else {
//                String outRawFileName =  output + ".depth.txt";
//                fs.copyToLocalFile(depthOutFile, new Path(outRawFileName));
//
//                //Utils.parseStatsFile(outRawFileName, out);
//            }
//            fs.delete(new Path(tmpOutDirName), true);
//        } catch (IOException e) {
//            throw e;
//        }
    }

}
