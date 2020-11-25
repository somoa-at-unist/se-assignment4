package kr.unist.cse.fl;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FuzzStatement;
import org.junit.runners.model.Statement;
import org.junit.runners.model.FrameworkMethod;
import edu.berkeley.cs.jqf.fuzz.Fuzz;

public class FL extends BlockJUnit4ClassRunner {
    public FL(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public Statement methodBlock(FrameworkMethod method) {
        return super.methodBlock(method);
    }
}
