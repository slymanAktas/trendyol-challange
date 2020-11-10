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


public class HomePage extends Page {
    private static final String PAGE_URL = "https://www.trendyol.com/";
    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    public static By getBoutiques() {
        return boutiques;
    }

    private static By boutiques = By.cssSelector("#navigation-wrapper nav ul.main-nav li.tab-link");

    HomePage(Browser browser) {
        this.url = PAGE_URL;
        this.browser = browser;
    }

    public static Matcher<Page> shouldHaveAllImagesForBoutiques() {
        return new BaseMatcher<Page>() {
            List<String> unLoadedImagesList = new ArrayList<>();

            @Override
            public boolean matches(Object item) {
                HomePage homePage = (HomePage) item;
                unLoadedImagesList = homePage.getUnLoadedImagesListFor(boutiques);
                LOGGER.warn("But images logged as " + unLoadedImagesList + " couldn't loaded!");
                return unLoadedImagesList.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Images under all boutiques should be listed as successfully...");
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendValue("But images logged as " + unLoadedImagesList + " couldn't loaded!");
            }
        };
    }

    public BoutiqueDetailPage goRandomBoutique() {
        List<WebElement> boutiquesList = browser.findElements(boutiques);
        WebElement randomBoutique = boutiquesList.get((new Random().nextInt(boutiquesList.size())));
        browser.clickTo(randomBoutique);

        return new BoutiqueDetailPage(this.browser);
    }
}
