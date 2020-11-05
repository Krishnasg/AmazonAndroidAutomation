package com.amazon.testnglisteners;

import com.amazon.util.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


/**
 * Extent test report is initialized here
 *
 */
public class TestListener implements ITestListener {
    ExtentReports reports = new ExtentReports(Constants.EXTENT_REPORT_FILE);
    ExtentTest test;

    final static Logger logger =  Logger.getLogger(TestListener.class);
    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        test.log(LogStatus.INFO, "finished");
        reports.endTest(test);
        reports.flush();
    }

    public void onTestStart(ITestResult result) {
        logger.info(String.format("------------ %s START ------------", result.getName()));
        test = reports.startTest(result.getName());
        test.log(LogStatus.INFO, "test started");
    }

    public void onTestSuccess(ITestResult result) {
        logger.info(String.format("------------ %s END [PASS] ------------", result.getName()));
        test.log(LogStatus.PASS, "test passed");
    }

    public void onTestFailure(ITestResult result) {
        logger.info(String.format("------------ %s END [FAIL] ------------", result.getName()), result.getThrowable());
        test.log(LogStatus.ERROR, result.getThrowable());
        test.log(LogStatus.FAIL, "test failed");
    }

    public void onTestSkipped(ITestResult result) {
        logger.info(String.format("------------ %s END [SKIP] ------------", result.getName()), result.getThrowable());
        test.log(LogStatus.SKIP, "test skipped");
    }
}
