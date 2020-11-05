package com.amazon.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.Map;

/**
 * Cart page, contains the details of the products added to the cart
 *
 * removing product, incrementing, decrementing quantity and so on
 * TODO: implement add a new address
 *
 */
public class CartPage extends BasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    String proceedToBuyBtnText = "Proceed to Buy";

    public CartPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    /**
     * Click on proceed to buy
     *
     * @return DeliverAddressPage object
     */
    public DeliverAddressPage clickOnProceedToBuy() {
        textExact(proceedToBuyBtnText).click();
        return new DeliverAddressPage(driver, testData);
    }

    public void removeProductsExcept(Product product) {
        // TODO: remove all products in the cart page except the product which is passed, this ensures any product which
        // is present when test started
    }
}
