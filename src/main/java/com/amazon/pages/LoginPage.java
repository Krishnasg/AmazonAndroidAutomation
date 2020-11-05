package com.amazon.pages;

import com.amazon.util.CustomSoftAlert;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Map;

public class LoginPage extends AccountBasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    private String loginOptionText = "Sign-In. Already a customer?";
    private String conditionsOfUseText = "Conditions of Use";
    private String privacyNoticeText = "Privacy Notice";
    By registerOptionLocator = By.id("//*[@resource-id='register_accordion_header']");
    By continueBtnLocator = By.xpath("//*[@resource-id='continue']");
    By emailFieldLocator = By.xpath("//android.widget.EditText[@resource-id='ap_email_login']");
    By passwordFieldLocator = By.xpath("//android.widget.EditText[@resource-id='ap_password']");
    By signInSubmitLocator = By.xpath("//android.widget.Button[@resource-id='signInSubmit']");
    By forgotPasswordLinkLocator = By.xpath("//android.view.View[@resource-id='auth-fpp-link-bottom']");
    By passwordContainerLocator = By.xpath("//android.view.View[@resource-id='auth-password-container']");

    By changeEmailIDLocator = By.xpath("//android.view.View[@resource-id='ap_change_login_claim']");
    By showPasswordCheckBox = By.xpath("//android.widget.CheckBox[@resource-id='auth-signin-show-password-checkbox']");

    // Error message locator
    By emailMissingMessageLocator = By.id("//android.view.View[@resource-id='auth-email-missing-alert']");
    By passwordIncorrectErrorMessage = By.xpath("//android.view.View[@resource-id='auth-error-message-box']");


    final static Logger logger =  Logger.getLogger(LoginPage.class);
    public LoginPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver, testData);
        this.driver = driver;
        this.testData = testData;
    }

    public void clickOnLoginOption() {
        logger.info("Click on login option");
        if (!textExact(loginOptionText).isSelected()) {
            textExact(loginOptionText).click();
        }
    }

    public void enterValidEmail() {
        logger.info("Enter valid user emails");
        enterEmailValue(estData.get("username"));
    }

    public void enterValidPassword() {
        logger.info("Enter valid user password");
        enterPasswordValue(testData.get("password"));
    }

    public void enterInvalidEmail() {
        logger.info("Enter Invalid user email");
        enterEmailValue(testData.get("invalidusername"));
    }

    public void enterInvalidPassword() {
        logger.info("Enter Invalid user password");
        enterPasswordValue(testData.get("invalidpassword"));
    }

    public HomePage singIn() {
        logger.info("Click on sign in button");
        findMobileElement(signInSubmitLocator).click();
        return new HomePage(driver, testData);
    }

    public void clickOnContinueBtn() {
        logger.info("Click on continue button");
        findMobileElement(continueBtnLocator).click();
    }

    public void validateUiElements(CustomSoftAlert softAssert) {
        logger.info("Validate UI elements");
        softAssert.assertNotNull(findMobileElement(super.continueBtnLocator), "Already a customer sign in button is not present in the screen");
        softAssert.assertNotNull(findMobileElement(newUserLocator), "New to Amazon.com? Create an account button is not present in the screen");
        softAssert.assertNotNull(findMobileElement(skipSignInLocator), "Skip sign in button is not present in the screen");
        // TODO: can add all other elements. stopping it here
    }

    public String getEmailErrorMessage() {
        if (findMobileElements(emailMissingMessageLocator).size() > 0) {
            logger.info("wrong password error message --> "+findMobileElements(emailMissingMessageLocator).get(0).getText());
            return findMobileElements(emailMissingMessageLocator).get(0).getText();
        }
        logger.info("email error message is not present");
        return null;
    }

    public String getPasswordErrorMessage() {
        if (findMobileElements(passwordIncorrectErrorMessage).size() > 0) {
            logger.info("wrong password error message --> "+findMobileElements(passwordIncorrectErrorMessage).get(0).getText());
            return findMobileElements(passwordIncorrectErrorMessage).get(0).getText();
        }
        logger.info("password error message is not present");
        return null;
    }

    public void validateLinks(CustomSoftAlert softAlert) {
        logger.info("validating conditions of use and privacy notice links ");
        findMobileElement(conditionOfUseLinkLocator).click();
        softAlert.assertTrue(!driver.getContext().equalsIgnoreCase("NATIVE_APP"), "not redirected to web browser after clicking on condition of use link locator");
        back();
        findMobileElement(privacyNoticeLinkLocator).click();
        softAlert.assertTrue(!driver.getContext().equalsIgnoreCase("NATIVE_APP"), "not redirected to web browser after clicking privacy notice link locator");
    }

    private void enterEmailValue(String email) {
        logger.info("Entering user email "+email);
        findMobileElement(emailFieldLocator).clear();
        findMobileElement(emailFieldLocator).click();
        findMobileElement(emailFieldLocator).sendKeys(email);
    }

    private void enterPasswordValue(String password) {
        logger.info("Entering user password "+password);
        findMobileElement(passwordFieldLocator).clear();
        findMobileElement(passwordFieldLocator).click();
        findMobileElement(passwordFieldLocator).sendKeys(password);
    }
}