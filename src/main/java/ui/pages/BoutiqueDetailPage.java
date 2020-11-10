package ui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.browser.Browser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoutiqueDetailPage extends Page {
    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);
    private static By bigBoutiqueImages = By.cssSelector(".component-big-list article.component-item");
    private static By products = By.cssSelector("div.products div.boutique-product");

    BoutiqueDetailPage(Browser browser) {
        this.browser = browser;
    }

    public By getRandomBoutiqueDetailCss() {
        int boutiqueDetailsSize = browser.findElements(bigBoutiqueImages).size();
        int randomIndex = (new Random().nextInt(boutiqueDetailsSize)) + 1;
        return By.cssSelector(".component-big-list article.component-item:nth-child(" + randomIndex + ")");
    }

    public ProductDetailPage getRandomProductDetail() {
        List<WebElement> productList = browser.findElements(products);
        int randomIndex = (new Random().nextInt(productList.size()));
        WebElement randomProduct = productList.get(randomIndex);
        browser.clickTo(randomProduct);

        return new ProductDetailPage(this.browser);
    }

    public static Matcher<Page> shouldHaveAllImagesForBoutiqueDetail() {
        return new BaseMatcher<Page>() {
            List<String> unLoadedImagesList = new ArrayList<>();

            @Override
            public boolean matches(Object item) {
                BoutiqueDetailPage boutiqueDetailPage = (BoutiqueDetailPage) item;
                unLoadedImagesList = boutiqueDetailPage.getUnLoadedImagesListFor(boutiqueDetailPage.getRandomBoutiqueDetailCss());
                LOGGER.warn("But images logged as " + unLoadedImagesList + " couldn't loaded!");
                return unLoadedImagesList.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("All images should be listed as successfully...");
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendValue("But images logged as " + unLoadedImagesList + " couldn't loaded!");
            }
        };
    }
}
