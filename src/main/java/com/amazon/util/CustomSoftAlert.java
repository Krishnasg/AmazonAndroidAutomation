package com.amazon.util;

import com.amazon.GlobalResource;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * When an assertion fails, don't throw an exception but record the failure.
 * Calling {@code assertAll()} will cause an exception to be thrown if at least
 * one assertion failed.
 */
public class CustomSoftAlert extends Assertion {

    // LinkedHashMap to preserve the order
    private AppiumDriver<MobileElement> driver;
    private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
    private String assertMessage = null;
    int counter = 1;
    String testName;

    final static Logger logger =  Logger.getLogger(CustomSoftAlert.class);
    public CustomSoftAlert(AppiumDriver<MobileElement> driver, String testName) {
        this.driver = driver;
        this.testName = testName;
    }

    @Override
    protected void doAssert(IAssert<?> a) {
        onBeforeAssert(a);
        try {
            assertMessage = a.getMessage();
            a.doAssert();
            onAssertSuccess(a);
        } catch (AssertionError ex) {
            onAssertFailure(a, ex);
            m_errors.put(ex, a);
            try {
                saveScreenshot();
            } catch (IOException e) {
                logger.error(String.format("Error taking screenshot for method %s ", testName), e);
            }
        } finally {
            onAfterAssert(a);
        }
    }

    public void assertAll() {
        if (!m_errors.isEmpty()) {
            StringBuilder sb = new StringBuilder("The following asserts failed:");
            boolean first = true;
            for (Map.Entry<AssertionError, IAssert<?>> ae : m_errors.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }
                sb.append("\n\t");
                sb.append(ae.getKey().getMessage());
            }
            throw new AssertionError(sb.toString());
        }
    }

    public void saveScreenshot() throws IOException {
        TakesScreenshot screenshot = driver;
        String filePath = Constants.ROOT_DIR + GlobalResource.getInstance().getProperties().getProperty("result.screenshots") +
                File.separator + testName+"_"+counter + ".png";
        File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(filePath);
        FileUtils.copyFile(SrcFile, DestFile);
    }
}
