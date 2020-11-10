package ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ui.pages.*;

import java.util.List;

import static config.ConfigKeyword.EMAIL;
import static config.ConfigKeyword.PASSWORD;

import static org.hamcrest.MatcherAssert.assertThat;
import static ui.pages.ProductDetailPage.shouldShowHasAddedToBasket;

public class BoutiqueTest extends BaseUITest {
    private static final Logger LOGGER = LogManager.getLogger(BoutiqueTest.class);


    @Test
    public void shouldLoadImagesThenAddBasketSuccesfully() {
        LoginPage loginPage = new LoginPage(browser);
        HomePage homePage = loginPage.login(EMAIL, PASSWORD);
        List<String> unLoadedImagesList = homePage.getUnLoadedImagesListFor(HomePage.getBoutiques());
        LOGGER.warn("Images under all boutiques logged as " + unLoadedImagesList + " couldn't loaded!");

        BoutiqueDetailPage boutiqueDetailPage = homePage.goRandomBoutique();
        unLoadedImagesList = boutiqueDetailPage.getUnLoadedImagesListFor(boutiqueDetailPage.getRandomBoutiqueDetailCss());
        LOGGER.warn("Product şmages under random boutique logged as " + unLoadedImagesList + " couldn't loaded!");

        ProductDetailPage productDetailPage = boutiqueDetailPage
                .getRandomProductDetail()
                .addProductToBasket();

        assertThat("When buyer add product to basket", productDetailPage, shouldShowHasAddedToBasket());
    }
}
