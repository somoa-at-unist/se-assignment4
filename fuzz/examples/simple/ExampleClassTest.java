package examples.simple;

import kr.unist.cse.fl.FL;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import org.junit.runner.RunWith;

//@Ignore
@RunWith(FL.class)
//@RunWith(JQF.class)
public class ExampleClassTest {

    @Test
    public void test1() {
        System.out.println("test1 is running");
        ExampleClass exClass = new ExampleClass(3, 5);
        exClass.halfY();
        //exClass.bar();
        int result = exClass.x-exClass.y;
        System.out.println("test1 is done");
        // Assert.assertTrue(result <= 0);
    }

    @Test
    public void test2() {
        System.out.println("test2 is running test2");
        int a = 3;
        int b = 5;
        ExampleClass exClass = new ExampleClass(a, b);
        int result = exClass.x-exClass.y;
        System.out.println("test2 is done");
        // Assert.assertTrue(result <= 0);
    }
}
