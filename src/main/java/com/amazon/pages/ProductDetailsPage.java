package com.amazon.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.Map;

/**
 * This is Product details page
 *
 */
public class ProductDetailsPage extends BasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    By productDetailsTitleLocator = By.xpath("//android.view.View[@resource-id='titleExpanderContent']");
    By productBrandInfoLocator = By.xpath("//android.view.View[@resource-id='bylineInfo']");
    By productReviewLocator = By.xpath("//android.view.View[@resource-id='acrCustomerReviewLink']");

    By productPriceLocator = By.xpath("//android.view.View[@resource-id='atfRedesign_priceblock_priceToPay']");
    By addToCartLocator = By.xpath("//android.widget.Button[@resource-id='add-to-cart-button']");

    By proceedToCheckOut = By.xpath("//android.widget.Button[@resource-id='a-autoid-2-announce']");
    By cartLocator = By.xpath("//android.widget.Button[@resource-id='a-autoid-1-announce']");

    String doneTextLocator = "DONE";

    By cartCounterLocator = By.id("com.amazon.mShop.android.shopping:id/action_bar_cart_count");

    public ProductDetailsPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    /**
     * the product details are parsed and creates an object Product
     *
     * @return Product object
     */
    public Product getProductDetails() {
        String title = findMobileElement(productDetailsTitleLocator).getText();
        String brand = findMobileElement(productBrandInfoLocator).getText();
        String review = findMobileElement(productReviewLocator).getText();
        String price = scrollAndFind(productPriceLocator).getText();
        return new Product(title, brand, review, price);
    }

    public void addToCart() {
        scrollAndFind(addToCartLocator).click();
    }

    public void proceedToCheckout() {
        findMobileElement(proceedToCheckOut).click();
    }

    public int getCartCounterValue() {
        return Integer.parseInt(findMobileElement(cartCounterLocator).getText().trim());
    }

    public CartPage goToCart() {
        findMobileElement(cartLocator).click();
        return new CartPage(driver, testData);
    }
}
