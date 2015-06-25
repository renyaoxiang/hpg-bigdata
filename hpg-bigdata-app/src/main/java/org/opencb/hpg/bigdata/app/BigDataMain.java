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

package org.opencb.hpg.bigdata.app;

import com.beust.jcommander.ParameterException;
import org.opencb.hpg.bigdata.app.cli.CommandExecutor;
import org.opencb.hpg.bigdata.app.cli.hadoop.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by imedina on 15/03/15.
 */
public class BigDataMain {

    public static void main(String[] args) {

        CliOptionsParser cliOptionsParser = new CliOptionsParser(true);
        
        if (args == null || args.length == 0) {
        	cliOptionsParser.printUsage();
        }

        try {
        	cliOptionsParser.parse(args);
        } catch(ParameterException e) {
        	System.out.println(e.getMessage());
        	cliOptionsParser.printUsage();
            System.exit(-1);
        }

        String parsedCommand = cliOptionsParser.getCommand();
        if (parsedCommand == null || parsedCommand.isEmpty()) {
            if (cliOptionsParser.getGeneralOptions().help) {
                cliOptionsParser.printUsage();
                System.exit(0);
            }
            if (cliOptionsParser.getGeneralOptions().version) {
                printVersion();
            }
        } else {
            CommandExecutor commandExecutor = null;
            // Check if any command -h option is present
            if(cliOptionsParser.isHelp()) { //cliOptionsParser.getCommonCommandOptions().help
                System.out.println("aaaaaaaa");
                cliOptionsParser.printUsage();
            } else {
                String subCommand = cliOptionsParser.getSubCommand();
                System.out.println("subCommand = " + subCommand);
                switch (parsedCommand) {
                    case "fastq":
                        if (cliOptionsParser.getFastqCommandOptions().help) {
                            cliOptionsParser.printUsage();
                        } else {
                            commandExecutor = new FastqCommandExecutor(cliOptionsParser.getFastqCommandOptions());
                        }
                        break;
                    case "bam":
                        if (cliOptionsParser.getAlignmentCommandOptions().help) {
                            cliOptionsParser.printUsage();
                        } else {
                            commandExecutor = new BamCommandExecutor(cliOptionsParser.getAlignmentCommandOptions());
                        }
                        break;
                    case "convert":
                        if (cliOptionsParser.getConvertCommandOptions().commonOptions.help) {
                            cliOptionsParser.printUsage();
                        } else {
                            commandExecutor = new ConvertCommandExecutor(cliOptionsParser.getConvertCommandOptions());
                        }
                        break;
                    case "align":
                        if (cliOptionsParser.getAlignmentCommandOptions().help) {
                            cliOptionsParser.printUsage();
                        } else {
                            commandExecutor = new AlignmentCommandExecutor(cliOptionsParser.getAlignmentCommandOptions());
                        }
                        break;
                    default:
                        System.out.printf("ERROR: not valid command passed: '" + parsedCommand + "'");
                        break;
                }
            }

            if (commandExecutor != null) {
                try {
                    commandExecutor.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    public static void printVersion() {
        Properties properties = new Properties();
        try {
            properties.load(BigDataMain.class.getClassLoader().getResourceAsStream("conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String version = properties.getProperty("HPG.BIGDATA.VERSION", "<unkwnown>");
        System.out.println("HPG BigData v" + version);
        System.out.println("");
        System.out.println("Read more on https://github.com/opencb/hpg-bigdata");
    }
}
