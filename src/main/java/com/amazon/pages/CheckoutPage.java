package com.amazon.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.Map;

/**
 * This is the Checkout page of the amazon application
 * Contains all the info of the products buying and shipping details and so on
 *
 * Validating address, modifying the quantity and so on
 * TODO: implement add a new address
 *
 */
public class CheckoutPage extends BasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    By placeYourOrderBtnLocator = By.xpath("//android.view.View[@resource-id='placeYourOrder']");
    By shipmentAddressLocator = By.xpath("//android.view.View[@resource-id='widget-compactShipmentText-orderGroupID0']");
    String successMessage = "Thank you for shopping with us.";

    public CheckoutPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    public void clickOnPlaceYourOrder() {
        findMobileElement(placeYourOrderBtnLocator).click();
    }

    /**
     * Checks if the page/secreen contains 'Thank you for shopping with us.'
     * this is used to verify success of the order placed
     *
     * @return boolean
     */
    public boolean isOrderPlaced() {
        try {
            textExact(successMessage);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
