package com.ui.test;

import com.amazon.dataprovider.DataP;
import com.amazon.pages.CartPage;
import com.amazon.pages.CheckoutPage;
import com.amazon.pages.DeliverAddressPage;
import com.amazon.pages.PaymentPage;
import com.amazon.pages.HomePage;
import com.amazon.pages.Product;
import com.amazon.pages.ProductDetailsPage;
import com.amazon.pages.ProductListPage;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class PurchaseTest extends BaseTest {


    @Test(groups = {"smoke", "checkout", "regression"}, alwaysRun = true, description = "Search for a product and verify product buy",
            dataProvider = "data-provider", dataProviderClass = DataP.class)
    public void verifyPlaceOrderTest(Map<String, String> testData) throws InterruptedException {
        HomePage homePage = new HomePage(driver, testData);
        Assert.assertTrue("Application is not logged in, Expected logged in state", homePage.isLoggedIn());

        // Search a product
        homePage.searchProduct();

        // Create an object product list page
        ProductListPage productListPage = new ProductListPage(driver, testData);
        Assert.assertTrue( "No results found for product "+testData.get("product"), productListPage.getSearchResultCount() > 0);

        // Click on random product from the list
        Product product = productListPage.clickRandomProductFromTheList();
        Assert.assertNotNull( "Clicked product details object is null, search for "+testData.get("product"), product);

        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver, testData);
        Product productInTheDetailsPage = productDetailsPage.getProductDetails();

        // Assert the product clicked from the list and the product details page product data is matching
        // price, name, brand
        Assert.assertEquals( "Clicked product and the product in the details page is not matching. clicked product -> "+
                product.toString() + "Product in the details page "+productInTheDetailsPage.toString(),  productInTheDetailsPage.equals(product));

        // Get the cart counter value before adding product to cart
        int actionBarCounterValueBeforeAddToCart = productDetailsPage.getCartCounterValue();
        productDetailsPage.addToCart();
        // Get the cart counter value after adding product to cart
        int actionBarCounterValueAfterAddToCart = productDetailsPage.getCartCounterValue();
        // Assert if the counter is increased by one
        Assert.assertTrue( "Cart counter is incremented after product added to cart. count before "+ actionBarCounterValueBeforeAddToCart + " and count after adding the product "+actionBarCounterValueAfterAddToCart,
                (actionBarCounterValueBeforeAddToCart+1) == actionBarCounterValueAfterAddToCart);

        // Cart to address page
        CartPage cartPage = productDetailsPage.goToCart();
        DeliverAddressPage deliverAddressPage = cartPage.clickOnProceedToBuy();
        deliverAddressPage.selectRandomAddressAndProceed();

        // Address to payment page
        PaymentPage paymentPage = deliverAddressPage.clickOnContinue();
        paymentPage.clickPayOnDeliver();
        CheckoutPage checkoutPage = paymentPage.clickOnContinue();

        // Checkout page
        checkoutPage.clickOnPlaceYourOrder();
        Assert.assertTrue(testData.get("product")+" order placement failed ", checkoutPage.isOrderPlaced());
    }
}
