package com.amazon.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Map;

public class AccountBasePage extends BasePage {

    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    // Text content Locators
    protected By signInToYourAccountTextLocator = By.id("com.amazon.mShop.android.shopping:id/signin_to_yourAccount");
    protected By wishListViewTextLocator = By.id("com.amazon.mShop.android.shopping:id/view_your_wish_list");
    protected By findAndReorderPurchaseTextLocator = By.id("com.amazon.mShop.android.shopping:id/Find_purchase");
    protected By trackYourPackageTextLocator = By.id("com.amazon.mShop.android.shopping:id/track_your_packages");

    // Locators
    protected By userDifferentAccountLocator = By.id("com.amazon.mShop.android.shopping:id/sso_use_different_account");
    protected By alreadyCustomerSignInLocator = By.id("com.amazon.mShop.android.shopping:id/sign_in_button");
    protected By newUserLocator = By.id("com.amazon.mShop.android.shopping:id/new_user");
    protected By skipSignInLocator = By.id("com.amazon.mShop.android.shopping:id/skip_sign_in_button");
    protected By continueBtnLocator = By.id("com.amazon.mShop.android.shopping:id/sso_continue");
    protected By conditionOfUseLinkLocator = By.id("com.amazon.mShop.android.shopping:id/sso_conditions_of_use");
    protected By privacyNoticeLinkLocator = By.id("com.amazon.mShop.android.shopping:id/sso_privacy_notice");

    final static Logger logger =  Logger.getLogger(AccountBasePage.class);
    public AccountBasePage(AppiumDriver<MobileElement> driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    public LoginPage clickOnAlreadyCustomer() {
        logger.info("Click on already customer? Sign in button");
        findMobileElement(alreadyCustomerSignInLocator).click();
        return new LoginPage(driver, testData);
    }

    public void clickOnContinueBtn() {
        findMobileElement(continueBtnLocator).click();
    }

    public void clickOnSkipBtn() {
        findMobileElement(skipSignInLocator).click();
    }

}
