package ui.browser.browsertypes;

import org.openqa.selenium.WebDriver;

public interface Initialize {
    WebDriver initInLocal();
    void initInGrid();
}
