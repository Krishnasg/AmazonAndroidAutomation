package com.amazon.pages;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class Product {

    // Locator in the product list page
    private By productTitleLocator = By.id("com.amazon.mShop.android.shopping:id/item_title");
    private By productBrandLocator = By.id("com.amazon.mShop.android.shopping:id/rs_item_styled_byline");
    private By productRatingLocator = By.id("com.amazon.mShop.android.shopping:id/rs_results_ratings");
    private By productPriceLocator = By.id("com.amazon.mShop.android.shopping:id/rs_results_styled_price_v2");

    String title;
    String brand;
    String rating;
    String price;

    public Product(String title, String brand, String rating, String price) {
        this.title = title;
        this.brand = brand;
        this.rating = rating;
        this.price = price;
    }

    public Product() {
    }

    // This constructor can be called in ProductList page
    public Product(MobileElement element) {
        if (element == null) {
            throw new NullPointerException("Product element should not be null");
        }
        title = element.findElement(productTitleLocator).getText();
        brand = element.findElement(productBrandLocator).getText();
        rating = element.findElement(productRatingLocator).getText();
        price = element.findElement(productPriceLocator).getText();
    }

    @Override
    public String toString() {
        return String.format("Product details -> name %s, brand %s, rating %s, price %s ", title, brand, rating, price);
    }

    @Override
    public boolean equals(Object o) {
        Product product = (Product) o;
        return product.brand.equals(brand) && product.price.equals(price) && product.title.equals(title);
    }
}
