package com.amazon.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;

import java.util.Map;

/**
 * Home page of the amazon application
 *
 */
public class HomePage extends BasePage {
    AndroidDriver<MobileElement> driver;
    Map<String, String> testData;

    By searchFieldLocator = By.id("com.amazon.mShop.android.shopping:id/rs_search_src_text");
    By loadingLocator = By.xpath("//*[@resource-id='gwm-CardLoadingIndicator']");

    public HomePage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = (AndroidDriver)driver;
        this.testData = testData;
    }

    public void waitForLoad() {
        waitForElementNotVisible(loadingLocator);
    }

    /**
     * Search a product and enter
     *
     */
    public void searchProduct() {
        findMobileElement(searchFieldLocator).click();
        findMobileElement(searchFieldLocator).sendKeys(testData.get("product"));
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    /**
     * Use this method to check if loggedin/home page
     *
     */
    public boolean isLoggedIn() {
        return isHomePage();
    }

    /**
     * Checks if the current application is on home page
     *
     */
    public boolean isHomePage() {
        try {
            isVisible(searchFieldLocator);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
