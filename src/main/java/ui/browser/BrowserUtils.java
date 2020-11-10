package ui.browser;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class BrowserUtils {

    protected WebDriver driver;

    public static WebElement fluentWait(final By locator, WebDriver driver) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return foo;
    }

    public WebElement visibilityWait(int timeoutInSeconds, By by) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, SECONDS)
                .pollingEvery(timeoutInSeconds, SECONDS).
                        ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        return wait.until(visibilityOfElementLocated(by));
    }

    public void waitForEnableOf(int timeOutInSeconds, WebElement elementLocator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(timeOutInSeconds, SECONDS).
                        ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        wait.until(enableOf(elementLocator));
    }

    public WebElement waitForClickableOf(int timeOutInSeconds, WebElement elementLocator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, SECONDS)
                .pollingEvery(timeOutInSeconds, SECONDS).
                        ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        return wait.until(elementToBeClickable(elementLocator));
    }

    private static ExpectedCondition<Boolean> enableOf(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver ignored) {
                try {
                    element.isEnabled();
                    return true;
                } catch (StaleElementReferenceException sere) {
                    return false;
                }
            }

            public String toString() {
                return String.format("element (%s) to become stale", element);
            }
        };
    }

    public void waitForAjax() {
        try {
            WebDriverWait myWait = new WebDriverWait(driver, 15);
            ExpectedCondition<Boolean> conditionToCheck = input -> {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                boolean stillRunningAjax = (Boolean) jsDriver
                        .executeScript("return (window.jQuery != undefined && ($(':animated').length != 0 || jQuery.active != 0)) || document.readyState != \"complete\"");
                return !stillRunningAjax;
            };

            myWait.until(conditionToCheck);
        } catch (RuntimeException rte) {
            System.out.println(rte);
        }

    }

    public boolean isElementPresent(long timeoutInSeconds, By by) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(timeoutInSeconds, SECONDS).
                        ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        try {
            wait.until(presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException toe) {
            return false;
        }
    }

    public WebElement presenceWait(int timeoutInSeconds, By by) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(timeoutInSeconds, SECONDS).
                        ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);
        return wait.until(presenceOfElementLocated(by));
    }

}
