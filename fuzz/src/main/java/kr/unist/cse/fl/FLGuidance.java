package kr.unist.cse.fl;

import edu.berkeley.cs.jqf.fuzz.guidance.Guidance;
import edu.berkeley.cs.jqf.fuzz.guidance.GuidanceException;
import edu.berkeley.cs.jqf.fuzz.guidance.Result;
import edu.berkeley.cs.jqf.fuzz.util.IOUtils;
import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.function.Consumer;

public class FLGuidance implements Guidance {
    /** The name of the test for display purposes. */
    protected final String testName;

    /** Timeout for an individual run. */
    protected long singleRunTimeoutMillis;

    public FLGuidance(String testName) throws IOException {
        this.testName = testName;

        // Try to parse the single-run timeout
        String timeout = System.getProperty("jqf.ei.TIMEOUT");
        if (timeout != null && !timeout.isEmpty()) {
            try {
                // Interpret the timeout as milliseconds (just like `afl-fuzz -t`)
                this.singleRunTimeoutMillis = Long.parseLong(timeout);
            } catch (NumberFormatException e1) {
                throw new IllegalArgumentException("Invalid timeout duration: " + timeout);
            }
        }
    }

    @Override
    public InputStream getInput() throws IllegalStateException, GuidanceException {
        return null;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void handleResult(Result result, Throwable error) throws GuidanceException {

    }

    @Override
    public Consumer<TraceEvent> generateCallBack(Thread thread) {
        return this::handleEvent;
    }

    /**
     * Handles a trace event generated during test execution.
     *
     * @param e the trace event to be handled
     */
    protected void handleEvent(TraceEvent e) {
        // do nothing
    }
}
