package com.amazon.pages;

import com.amazon.util.HelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the product search list page
 * contains all the product which matches with the search item
 *
 */
public class ProductListPage extends BasePage {
    AppiumDriver<MobileElement> driver;
    Map<String, String> testData;

    By searchResultCountLocator = By.id("com.amazon.mShop.android.shopping:id/rs_results_count_text");
    By productListLocator = By.id("com.amazon.mShop.android.shopping:id/list_product_linear_layout");

    public ProductListPage(AppiumDriver driver, Map<String, String> testData) {
        super(driver);
        this.driver = driver;
        this.testData = testData;
    }

    public Integer getSearchResultCount() {
        return Integer.parseInt(findMobileElement(searchResultCountLocator).getText().replaceAll("[^0-9]", ""));
    }

    /**
     * Gets all the product list items
     *
     * @return product list items
     */
    public List<MobileElement> getProductList() {
        List<MobileElement> productLIst = findMobileElements(productListLocator);
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < productLIst.size()-1; i++) {
            products.add(new Product(productLIst.get(i)));
        }
        return productLIst;
    }

    /**
     * Clicks on a random product from the list
     *
     * @return returns clicked proudct
     */
    public Product clickRandomProductFromTheList() {
        List<MobileElement> productList = getProductList();
        int randomListNumber = HelperUtil.getRandomInt(productList.size()-1);
        MobileElement productElement = productList.get(randomListNumber);
        Product product = new Product(productElement);
        productElement.click();
        return product;
    }
}
