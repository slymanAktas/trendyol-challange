package ui.browser.browsertypes.chrome;

import config.Config;
import org.openqa.selenium.WebDriver;
import ui.browser.Browser;
import ui.browser.browsertypes.Initialize;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class Chrome extends Browser implements Initialize {

    public static Browser get(String chromeType) {
        return chromeType.equalsIgnoreCase("withProxy") ? new ChromeWithProxy() : new ChromeWithOutProxy();
    }

    @Override
    public WebDriver initInLocal() {
//        ChromeDriverManager.getInstance().setup();
        ChromeDriverManager.getInstance().version("86.0.4240.22").setup();
        driver = new ChromeDriver(getOptions());
        driver.get(Config.TRENDYOL);
        driver.manage().window().maximize();

        return driver;
    }

    @Override
    public void initInGrid() {
    }

    abstract ChromeOptions getOptions();
}
