package kr.unist.cse.fl;

import edu.berkeley.cs.jqf.fuzz.guidance.Guidance;
import edu.berkeley.cs.jqf.instrument.tracing.SingleSnoop;
import edu.berkeley.cs.jqf.instrument.tracing.TraceLogger;
import org.junit.internal.TextListener;
import org.junit.runner.*;

import java.io.PrintStream;

public class TestRunner {

    private static Guidance guidance;

    public synchronized static Result runMethod(String testClassName, String testMethod,
                                                ClassLoader loader,
                                                Guidance guidance, PrintStream out) throws ClassNotFoundException, IllegalStateException {
        Class<?> testClass =
                java.lang.Class.forName(testClassName, true, loader);

        return run(testClass, testMethod, guidance, out);
    }

    public synchronized static Result run(String testClassName, String testMethod,
                                          Guidance guidance, PrintStream out) throws ClassNotFoundException, IllegalStateException {

        // Run with the system class loader
        return runMethod(testClassName, testMethod, ClassLoader.getSystemClassLoader(), guidance, out);
    }

    public synchronized static Result run(Class<?> testClass, String testMethod,
                                          Guidance guidance, PrintStream out) throws IllegalStateException {

        // Ensure that the class uses the right test runner
        RunWith annotation = testClass.getAnnotation(RunWith.class);
        if (annotation == null || !annotation.value().equals(FL.class)) {
            System.out.println(testClass.getName() + " is not annotated with @RunWith(FL.class)");
            throw new IllegalArgumentException(testClass.getName() + " is not annotated with @RunWith(FL.class)");
        }

        try {
            // Set the static guidance instance
            setGuidance(guidance);

            // Register callback
            SingleSnoop.setCallbackGenerator(guidance::generateCallBack);

            // Create a JUnit Request
            //Request testRequest = Request.method(testClass, testMethod);
            Request testRequest = Request.method(testClass, testMethod);
            // Instantiate a runner (may return an error)
            Runner testRunner = testRequest.getRunner();

            // Start tracing for the test method
            //SingleSnoop.startSnooping(testClass.getName() + testMethod);
            SingleSnoop.startSnooping(testClass.getName() + "#" + testMethod);

            // Run the test
            JUnitCore junit = new JUnitCore();
            if (out != null) {
                junit.addListener(new TextListener(out));
            }

            Result result = junit.run(testRunner);
            boolean wasSuccessful = result.wasSuccessful();
            return result;
        } finally {
            // Make sure to de-register the guidance before returning
            unsetGuidance();
        }
    }

    private static void setGuidance(Guidance g) {
        if (guidance != null) {
            throw new IllegalStateException("Can only set guided once.");
        }
        guidance = g;
    }

    private static void unsetGuidance() {
        guidance = null;
        TraceLogger.get().remove();
    }

    public static void getRank() {

    }
}
