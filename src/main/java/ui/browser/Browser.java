package ui.browser;

import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.ScreenshotException;
import io.qameta.allure.Attachment;

import org.openqa.selenium.support.ui.WebDriverWait;
import config.Config;
import ui.browser.browsertypes.chrome.Chrome;
import ui.browser.browsertypes.firefox.Firefox;
import ui.pages.Page;

import java.util.ArrayList;
import java.util.List;

import static config.Config.TIMEOUT;
import static org.openqa.selenium.OutputType.BYTES;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static ui.browser.browsertypes.chrome.ChromeWithProxy.getProxy;
import static utils.FileUtils.writeFile;


public abstract class Browser extends BrowserUtils {

    protected Browser launchBrowser(String chromeType) {
        if (Config.DEFAULT_BROWSER_NAME.equalsIgnoreCase("firefox")) {
            this.driver = new Firefox().initInLocal();
        } else if (Config.DEFAULT_BROWSER_NAME.equalsIgnoreCase("chrome")) {
            this.driver = ((Chrome) Chrome.get(chromeType)).initInLocal();
        }
        return this;
    }

    protected Browser launchBrowser() {
        return launchBrowser("withoutProxy");
    }


    public void closeBrowser() {
        this.driver.close();
    }

    public <P extends Page> Page goTo(P page) {
        driver.get(page.url);
        waitForDialog();
        return new Page();
    }


    public void clickToBy(By by) {
        clickTo(visibilityWait(TIMEOUT, by));
    }

    public void clickTo(WebElement element) {
        waitForEnableOf(TIMEOUT, element);
        waitForClickableOf(TIMEOUT, element);
        highlightElement(element);
        element.click();
        waitForDialog();
    }

    public void type(By by, String text, boolean clear) {
        WebElement element = visibilityWait(TIMEOUT, by);
        waitForEnableOf(TIMEOUT, element);
        waitForClickableOf(TIMEOUT, element);
        highlightElement(element);
        scrollToElement(element);
        if (clear) {
            element.clear();
            waitForDialog();
        }

        element.sendKeys(text);
        waitForDialog();
    }

    private WebElement findElement(By by, WebElement element) {
        List<WebElement> elements;
        if (element != null) {
            elements = element.findElements(by);
        } else {
            elements = driver.findElements(by);
        }
        return elements.isEmpty() ? null : elements.get(0);
    }

    public WebElement findElement(By by) {
        return findElement(by, null);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    private void waitForDialog() {
        int i = 0;
        while (i < 15) {
            waitForAjax();
            i++;
        }
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, arguments[0]);", element);
    }

    private void highlightElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.background = 'yellow';", element);
    }

    public void waitForUrlHasNavigatedTo(int seconds, String urlToBe) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(urlToBe(urlToBe));
        waitForDialog();
    }


    @Attachment(value = "Fail screenshot", type = "image/png")
    public byte[] takeScreenshot(String scrFilename) {
        byte[] byteArray = new byte[0];
        try {
            byteArray = ((TakesScreenshot) driver).getScreenshotAs(BYTES);
            writeFile(scrFilename, byteArray);
        } catch (ScreenshotException sse) {
            System.out.println("Taking screenshot has been failed, " + sse);
        }
        return byteArray;
    }

    public BrowserMobProxy proxy() {
        return getProxy();
    }
}
