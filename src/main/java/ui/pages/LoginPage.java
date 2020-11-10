package ui.pages;

import org.openqa.selenium.By;
import ui.browser.Browser;

public class LoginPage extends Page {
    private static final String PAGE_URL = "https://www.trendyol.com/giris";
    private By emailTextBox = By.id("login-email");
    private By passwordTextBox = By.id("login-password-input");
    private By loginButton = By.cssSelector("form[novalidate='novalidate'] button");
    private By notificationPopup = By.className("notification-popup-container");
    private By closePopupBtn = By.className("modal-close");

    public LoginPage(Browser browser) {
        this.url = PAGE_URL;
        this.browser = browser;
    }

    public HomePage login(String email, String password) {
        browser.goTo(this);
        browser.findElement(emailTextBox).sendKeys(email);
        browser.findElement(passwordTextBox).sendKeys(password);
        browser.clickToBy(loginButton);

        HomePage homePage = new HomePage(this.browser);
        browser.waitForUrlHasNavigatedTo(15, homePage.url);

        if(browser.isElementPresent(5, notificationPopup)){
            browser.clickToBy(closePopupBtn);
        }

        return homePage;
    }

}
