package ui.pages;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.HarEntry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.browser.Browser;

import java.util.ArrayList;
import java.util.List;

import static ui.browser.browsertypes.chrome.ChromeWithProxy.getProxy;

public class Page extends Browser {
    Browser browser;

    public String url;

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public Browser browser() {
        return this.browser;
    }

    private List<HarEntry> getNetworkActivities(WebElement element) {
        browser.proxy().newHar();
        browser.clickTo(element);
        return browser.proxy().getHar().getLog().getEntries();
    }

    public List<String> getUnLoadedImagesListFor(By elementList) {
        List<String> unLoadedImagesList = new ArrayList<>();
        int elementListSize = browser.findElements(elementList).size();

        for (int i = 0; i < elementListSize; i++) {
            WebElement eachElement = browser.findElements(elementList).get(i); //In case of stale element exception
            List<HarEntry> entries = getNetworkActivities(eachElement);

            for (HarEntry entry : entries) {
                String eachUrl = entry.getRequest().getUrl();
                int eachResponseCode = entry.getResponse().getStatus();

                if (eachUrl.endsWith(".jpg")) {
                    if (eachResponseCode != 200) {
                        unLoadedImagesList.add(eachUrl);
                    }
                }
            }
        }

        return unLoadedImagesList;
    }
}
