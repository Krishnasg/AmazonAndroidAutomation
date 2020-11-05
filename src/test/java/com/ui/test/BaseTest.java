package com.ui.test;

import com.amazon.GlobalResource;
import com.amazon.testnglisteners.RetryAnalyzer;
import com.amazon.util.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected AppiumDriver<MobileElement> driver = null;
    public static URL url;
    final static Logger logger =  Logger.getLogger(BaseTest.class);
    private static Vector<Integer> systemPorts = new Vector<>();

    @BeforeSuite
    public void beforeSuite(ITestContext iTestContext) {
        // remove log content and deleting screenshots folder if exists
        String logFilePath = Constants.ROOT_DIR+"log/logging.log";
        String screenshotDir = Constants.ROOT_DIR+"screenshots";
        if (new File(logFilePath).exists()) {
            try {
                FileUtils.write(new File(logFilePath), "");
            } catch (IOException e) {
                logger.error(String.format("Error removing %s log content", logFilePath), e);
            }
        }
        if (new File(screenshotDir).exists()) {
            logger.info(String.format("Removing %s screenshots directory", logFilePath));
            try {
                FileUtils.deleteDirectory(new File(screenshotDir));
            } catch (IOException e) {
                logger.error(String.format("Error removing %s screenshots directory", screenshotDir), e);
            }
        }
        logger.info(String.format("-------- suite START [%s] --------", iTestContext.getSuite().getName()));
        for(ITestNGMethod method : iTestContext.getAllTestMethods()) {
            // Add retry to all the tests
            method.setRetryAnalyzerClass(RetryAnalyzer.class);
        }
    }

    @AfterSuite
    public void afterSuite(ITestContext iTestContext) {
        logger.info(String.format("-------- suite END [%s] --------", iTestContext.getSuite().getName()));
    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        final String URL_STRING = GlobalResource.getInstance().getProperties().getProperty("appium.url");
        url = new URL(URL_STRING);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                GlobalResource.getInstance().getProperties().getProperty("platform.name.android"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
        GlobalResource.getInstance().getProperties().getProperty("platform.version.android"));
        capabilities.setCapability(MobileCapabilityType.UDID,
                GlobalResource.getInstance().getProperties().getProperty("device.udid"));
        capabilities.setCapability("appPackage",
        GlobalResource.getInstance().getProperties().getProperty("app.package.android"));
        capabilities.setCapability("appActivity",
        GlobalResource.getInstance().getProperties().getProperty("app.activity.android"));
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                GlobalResource.getInstance().getProperties().getProperty("automation.name.android"));
        capabilities.setCapability(MobileCapabilityType.APP,
                Constants.ROOT_DIR+GlobalResource.getInstance().getProperties().getProperty("app.android"));
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

        driver = new AndroidDriver<MobileElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(GlobalResource.getInstance().getProperties().getProperty("wait.implicit")),
                TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() != ITestResult.SUCCESS) {
                takeScreenshot(result.getName());
            }
        } catch (Exception ie) {
        } finally {
            driver.quit();
        }
    }

    protected void takeScreenshot(String testName) throws IOException {
        TakesScreenshot screenshot = driver;
        String filePath = Constants.ROOT_DIR + GlobalResource.getInstance().getProperties().getProperty("result.screenshots") +
                File.separator + testName + ".png";
        File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(filePath);
        FileUtils.copyFile(SrcFile, DestFile);
        logger.error("Test "+testName+" has failed. screenshot is captured. file -> "+filePath);
    }

    // When called from the test class method, it return the method name from which it called
    protected String getTestMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
