package ui.pages;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.browser.Browser;

public class ProductDetailPage extends Page {
    private static final By addBasketBBtn = By.cssSelector("button.add-to-bs");

    ProductDetailPage(Browser browser) {
        this.browser = browser;
    }

    public ProductDetailPage addProductToBasket(){
        browser.clickToBy(addBasketBBtn);

        return this;
    }

    public static Matcher<Page> shouldShowHasAddedToBasket(){
        return new BaseMatcher<Page>() {
            @Override
            public boolean matches(Object item) {
                ProductDetailPage productDetailPage = (ProductDetailPage) item;
                Browser browser = productDetailPage.browser;

                WebElement addBasketButton = browser.findElement(addBasketBBtn);
                return addBasketButton.getAttribute("class").contains("success");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Add basket button should be green for a multiple seconds...");
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendValue("But button still orange and background color never changed!");
            }
        };
    }
}
