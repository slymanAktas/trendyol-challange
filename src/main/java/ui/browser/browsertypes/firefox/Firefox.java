package ui.browser.browsertypes.firefox;

import config.Config;
import org.openqa.selenium.WebDriver;
import ui.browser.Browser;
import ui.browser.browsertypes.Initialize;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class Firefox extends Browser implements Initialize {
    public WebDriver initInLocal() {
        FirefoxDriverManager.getInstance().setup();
        driver = new FirefoxDriver(getOptions());
        driver.get(Config.TRENDYOL);
        driver.manage().window().maximize();

        return driver;
    }

    public void initInGrid() {
    }

    private static FirefoxOptions getOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(FirefoxDriver.MARIONETTE, true);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.PLATFORM_NAME, (Platform) null);

        return options;
    }
}
