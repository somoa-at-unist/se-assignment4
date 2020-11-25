/*
 * Copyright (c) 2017-2018 The Regents of the University of California
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package edu.berkeley.cs.jqf.fuzz.ei;

import edu.berkeley.cs.jqf.instrument.InstrumentingClassLoader;
import kr.unist.cse.fl.*;
import kr.unist.cse.fl.formula.Formula;
import kr.unist.cse.fl.formula.Tarantula;
import org.junit.runner.Request;
import org.junit.runner.Result;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * CLI for Zest based guidance.
 *
 * @author Yevgeny Pats
 */
@CommandLine.Command(name = "LocalizerCLI", mixinStandardHelpOptions = true, version = "1.3")
public class LocalizerCLI implements Runnable{

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..2")
    Dependent dependent;

    static class Dependent {
        @Option(names = { "-e", "--exit-on-crash" },
                description = "Exit fuzzer on first crash (default: false)")
        boolean exitOnCrash = false;

        @Option(names = { "--exact-crash-path" },
                description = "exact path for the crash")
        String exactCrashPath;
    }

    @Option(names = { "-l", "--libfuzzer-compat-output" },
            description = "Use libFuzzer compat output instead of AFL like stats screen (default: false)")
    private boolean libFuzzerCompatOutput = false;

    @Option(names = { "-i", "--input" },
            description = "Input directory containing seed test cases (default: none)")
    private File inputDirectory;

    @Option(names = { "-o", "--output" },
            description = "Output Directory containing results (default: fuzz_results)")
    private File outputDirectory = new File("fuzz-results");

    @Option(names = { "-d", "--duration" },
            description = "Total fuzz duration (e.g. PT5s or 5s)")
    private Duration duration;

    @Option(names = { "-b", "--blind" },
            description = "Blind fuzzing: do not use coverage feedback (default: false)")
    private boolean blindFuzzing;

    @Option(names = { "-f", "--formula" },
            description = "Formula to get score(default: Tarantula)")
    private String formula;

    @Parameters(index = "0", paramLabel = "CLASSPATH", description = "CLASSPATH")
    private String classPath;

    @Parameters(index="1", paramLabel = "TEST_CLASS:TEST_METHOD",
            arity = "1..*",
            description = "TEST_CLASS:TEST_METHOD")
    private List<String> testDescList;

    private File[] readSeedFiles() {
        if (this.inputDirectory == null) {
            return new File[0];
        }

        ArrayList<File> seedFilesArray = new ArrayList<>();
        File[] allFiles = this.inputDirectory.listFiles();
        if (allFiles == null) {
            // this means the directory doesn't exist
            return new File[0];
        }
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].isFile()) {
                seedFilesArray.add(allFiles[i]);
            }
        }
        File[] seedFiles = seedFilesArray.toArray(new File[seedFilesArray.size()]);
        return seedFiles;
    }

    public void run() {

        File[] seedFiles = readSeedFiles();

        if (this.dependent != null) {
            if (this.dependent.exitOnCrash) {
                System.setProperty("jqf.ei.EXIT_ON_CRASH", "true");
            }

            if (this.dependent.exactCrashPath != null) {
                System.setProperty("jqf.ei.EXACT_CRASH_PATH", this.dependent.exactCrashPath);
            }
        }

        if (this.libFuzzerCompatOutput) {
            System.setProperty("jqf.ei.LIBFUZZER_COMPAT_OUTPUT", "true");
        }

        try {
            ClassLoader loader = new InstrumentingClassLoader(
                    this.classPath.split(File.pathSeparator),
                    LocalizerCLI.class.getClassLoader());

            for (String testDesc : this.testDescList) {
                String[] items = testDesc.split(":");
                String testClassName = items[0];
                String testMethodName;
                List<String> testMethodNameList = new ArrayList<>();

                // prepare a list of test methods
                if(items.length == 1) {
                    // when method is not given
                    Class<?> testClass =
                            java.lang.Class.forName(testClassName, true, loader);
                    Method[] methods = testClass.getMethods();

                    Request testRequest = Request.aClass(testClass);
                    // TODO: collect all methods avialble in testRequest
                    for (Method method: methods) {

                        if(method.getName().contains("test")) {
                            testMethodNameList.add(method.getName());

                        }
                    }
                    // testMethodNameList.add(...);
                }
                else {
                    testMethodName = items[1];
                    testMethodNameList.add(testMethodName);
                }

                for (String methodName : testMethodNameList) {
                    // Load the guidance
                    String title = testClassName + "#" + methodName;
                    System.out.println(title);
                    FLGuidance guidance = new FLGuidance(title);
                    // Run the Junit test
                    Result res = TestRunner.runMethod(testClassName, methodName, loader, guidance, System.out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new LocalizerCLI())
                .registerConverter(Duration.class, v -> {
                    try {
                        return Duration.parse(v);
                    } catch (DateTimeParseException e) {
                        return Duration.parse("PT" + v);
                    }
                })
                .execute(args);
        //System.exit(exitCode);
    }
}
