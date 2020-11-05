package com.amazon.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.Map;

/**
 * This is the Payment page of the amazon application
 * Contains all the operations which can be performed on the payment page
 *
 * Adding a new card, using upi, emi
 * TODO: implement above mentioned functionalities
 *
 */
public class PaymentPage extends BasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    String payOnDeliverText = "Pay on Delivery";
    String continueText = "Continue";

    public PaymentPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    /**
     * Scroll to 'Pay on Delivery' and click on it
     *
     */
    public void clickPayOnDeliver() {
        scrollAndFindByText(payOnDeliverText).click();
    }

    public CheckoutPage clickOnContinue() {
        scrollAndFindByText(continueText).click();
        return new CheckoutPage(driver, testData);
    }
}
