package ui.browser.browsertypes.chrome;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeWithOutProxy extends Chrome {
     ChromeOptions getOptions() {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.setCapability("platform", (Platform) null);

        return options;
    }
}
