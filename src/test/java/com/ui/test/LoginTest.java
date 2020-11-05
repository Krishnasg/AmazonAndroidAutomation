package com.ui.test;

import com.amazon.dataprovider.DataP;
import com.amazon.pages.LoginPage;
import com.amazon.pages.HomePage;
import com.amazon.util.CustomSoftAlert;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "login", "regression"}, alwaysRun = true, description = "Login",
            dataProvider = "data-provider", dataProviderClass = DataP.class)
    public void ValidateLoginSuccess(Map<String, String> testData) throws IOException {
        LoginPage loginPage = new LoginPage(driver, testData);
        loginPage.clickOnAlreadyCustomer();

        loginPage.clickOnLoginOption();
        loginPage.enterValidEmail();
        loginPage.clickOnContinueBtn();

        loginPage.enterValidPassword();
        HomePage homePage = loginPage.singIn();

        Assert.assertTrue(homePage.isLoggedIn(), "Login failed, Expected after successful login home page to be loaded");
        takeScreenshot(getTestMethodName());
    }

   @Test(groups = {"login-ui", "regression"}, alwaysRun = true, description = "Validate login ui elements and text content",
           dataProvider = "data-provider", dataProviderClass = DataP.class)
   public void ValidatedLoginUIElements(Map<String, String> testData) {
       CustomSoftAlert softAssert = new CustomSoftAlert(driver, getTestMethodName());
       LoginPage loginPage = new LoginPage(driver, testData);
       loginPage.validateUiElements(softAssert);
       softAssert.assertAll();
   }

   @Test(groups = {"smoke", "invalid-login", "regression"}, alwaysRun = true, description = "Validate invalid credentials and error message",
           dataProvider = "data-provider", dataProviderClass = DataP.class)
   public void ValidateInvalidLoginCredentials(Map<String, String> testData) {
       LoginPage loginPage = new LoginPage(driver, testData);
       loginPage.clickOnAlreadyCustomer();

       loginPage.clickOnLoginOption();

       // enter invalid credentials
       loginPage.enterInvalidEmail();
       loginPage.clickOnContinueBtn();
       CustomSoftAlert softAssert = new CustomSoftAlert(driver, getTestMethodName());
       softAssert.assertTrue(loginPage.getEmailErrorMessage() != null && loginPage.getEmailErrorMessage().equals(testData.get("InvalidEmailMessage")), "Invalid email error message error. Expected error message "+testData.get("InvalidEmailMessage"));

       // enter valid email, so that it goes to the next screen
       loginPage.enterValidEmail();
       loginPage.clickOnContinueBtn();

       // enter invalid password
       loginPage.enterInvalidPassword();
       softAssert.assertTrue(loginPage.getPasswordErrorMessage() != null && loginPage.getPasswordErrorMessage().equals(testData.get("InvalidPasswordMessage")), "Invalid email error message error. Expected error message "+testData.get("InvalidEmailMessage"));
       softAssert.assertAll();
   }

   @Test(groups = {"smoke", "validate-links", "regression"}, alwaysRun = true, description = "Validate privacy notes and conditions of use links",
           dataProvider = "data-provider", dataProviderClass = DataP.class)
   public void ValidateLinks(Map<String, String> testData) {
       LoginPage loginPage = new LoginPage(driver, testData);
       loginPage.clickOnAlreadyCustomer();
       CustomSoftAlert softAssert = new CustomSoftAlert(driver, getTestMethodName());
       loginPage.validateLinks(softAssert);
       softAssert.assertAll();
   }
}
