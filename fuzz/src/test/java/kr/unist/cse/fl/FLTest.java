package kr.unist.cse.fl;

import edu.berkeley.cs.jqf.fuzz.ei.LocalizerCLI;
import kr.unist.cse.fl.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


//@Ignore
public class FLTest {

    @Test
    public void basicTest() {
        String classPath = System.getProperty("user.dir");
        System.setProperty("janala.verbose", "true");
        System.setProperty("janala.instrumentationCacheDir", classPath);
        System.out.println(classPath);
        LocalizerCLI.main(new String[] {
                classPath,
                "--formula",
                "Tarantula",
                "examples.simple.ExampleClassTest:test1",
                "examples.simple.ExampleClassTest:test2"
                });
        System.exit(0);
    }

    @Test
    public void Math3Test() {
        // Attention: Give your project root dir
        String projectRoot = System.getProperty("user.dir");
        System.setProperty("janala.verbose", "true");
        // Comment out the following to save instrumented class files to the disk
        //System.setProperty("janala.instrumentationCacheDir", projectRoot+"");
        LocalizerCLI.main(new String[] {
                projectRoot + "/target/test-classes:" + projectRoot + "/target/classes",
                "--formula",
                "Tarantula", // formula
                "org.apache.commons.math3.util.MathArraysTest"
        });
        System.exit(0);
    }

}
