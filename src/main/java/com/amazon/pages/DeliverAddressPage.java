package com.amazon.pages;

import com.amazon.util.HelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

/**
 * This is the Address page of the amazon application
 * Contains all the operations which can be performed on the address page
 *
 * Adding a new address, selecting an existing address
 * TODO: implement add a new address
 *
 */
public class DeliverAddressPage extends BasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    private String addAnewAddressText = "Add a New Address";
    private String deliverToThisAddressText = "Deliver to this address";
    private String continueText = "Continue";
    private By fullNameLocator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-enterAddressFullName']");
    private By mobileNoLocator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-enterAddressPhoneNumber']");
    private By postalLocator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-enterAddressPostalCode']");
    private By addressLine1Locator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-enterAddressLine1']");
    private By addressLine2Locator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-enterAddressLine2']");
    private By addressLandmarkLocator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-landmark']");
    private By cityLocator = By.xpath("//android.widget.EditText[@resource-id='address-ui-widgets-enterAddressCity']");
    private By selectStateLocator = By.xpath("//android.view.View[@resource-id='address-ui-widgets-enterAddressStateOrRegion']");
    private String stateStr = "KARNATAKA";
    private By addAddressBtnLocator = By.xpath("//android.view.View[@resource-id='address-ui-widgets-form-submit-button']");

    private By addressListLocator = By.xpath("//android.view.View[@resource-id='address-select']//android.widget.ListView");

    public DeliverAddressPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    /**
     * Selects a random address form the list and clicks on it
     *
     */
    public void selectRandomAddressAndProceed() {
        List<MobileElement> locations = findMobileElements(addressListLocator);
        locations.get(HelperUtil.getRandomInt(locations.size()-1)).click();
        textExact(deliverToThisAddressText).click();
    }

    /**
     * Clicks on continue
     *
     * @return PaymentPage object
     */
    public PaymentPage clickOnContinue() {
        textExact(continueText).click();
        return new PaymentPage(driver, testData);
    }
}
