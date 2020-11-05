package com.amazon.pages;

import com.amazon.GlobalResource;
import com.amazon.util.ScrollAndSwipe;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is the BasePage class which should be extended by all other pages
 * Contains helper functions for finding elements
 *
 */
public class BasePage {
    private AppiumDriver<MobileElement> driver;
    private WebDriverWait driverWait;
    protected ScrollAndSwipe scrollAndSwipe;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        int timeoutInSeconds = Integer.parseInt(GlobalResource.getInstance().getProperties().getProperty("wait.explicit"));
        // must wait at least 60 seconds for running on Sauce.
        // waiting for 30 seconds works locally however it fails on Sauce.
        driverWait = new WebDriverWait(driver, timeoutInSeconds);
        scrollAndSwipe = new ScrollAndSwipe(driver);
    }

    /**
     * Press the back button *
     */
    protected void back() {
        driver.navigate().back();
    }

    /**
     * Return a static text element that contains text *
     */
    protected MobileElement findMobileElement(By locator) {
        return wait(locator);
    }

    /**
     * Return a static text element that contains text *
     */
    protected MobileElement scrollAndFind(By locator) {
        int counter = 10;
        while (counter-- > 0) {
            if (driver.findElements(locator).size() > 0) {
                return driver.findElements(locator).get(0);
            }
            scrollAndSwipe.scroll();
        }
        throw new NoSuchElementException(locator.toString()+ "could not find the element");
    }

    /**
     * Return a static text element that contains text *
     */
    protected MobileElement scrollAndFindByText(String text) {
        return scrollAndFind(By.xpath("//*[@text='" + text + "']"));
    }

    /**
     * Return a static text element that contains text *
     */
    protected List<MobileElement> findMobileElements(By locator) {
        return waitAll(locator);
    }

    /**
     * Return a static text element by exact text *
     */
    protected MobileElement textExact(String text) {
        return wait(By.xpath("//*[@text='" + text + "']"));
    }


    /**
     * Wait 30 seconds for locator to find an element *
     */
    protected void waitForElementNotVisible(By locator) {
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait 30 seconds for locator to find an element *
     */
    protected MobileElement wait(By locator) {
        return element(driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    /**
     * Wait 30 seconds for locator to find an element *
     */
    public MobileElement isVisible(By locator) {
        return element(driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    /**
     * Wait 30 seconds for locator to find an element *
     */
    protected MobileElement presenceOf(By locator) {
        return element(driverWait.until(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    /**
     * Wait 60 seconds for locator to find all elements *
     */
    protected List<MobileElement> waitAll(By locator) {
        return elements(driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
    }

    private List<MobileElement> elements(List<WebElement> elements) {
        List<MobileElement> mobileElements = new ArrayList<>();
        for(WebElement element : elements) {
            mobileElements.add((MobileElement)element);
        }
        return mobileElements;
    }

    private MobileElement element(WebElement element) {
        return (MobileElement) element;
    }
}
