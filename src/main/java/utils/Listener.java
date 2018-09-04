package utils;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author abelikov
 */
public class Listener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.printf("\033[32m%-90s PASSED\033\n", result.getTestClass().getName() + ":" + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.printf("\033[31m%-90s FAILED  !\033\n", result.getTestClass().getName() + ":" + result.getMethod().getMethodName());
    }
}
