package ui;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import ui.browser.Browser;


public class BaseUITest extends Browser {

    Browser browser;

    @Rule
    public TestRule listen = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            if(description.getMethodName().contains("Images")){
                browser = launchBrowser("withProxy");
            }else{
                browser = launchBrowser("withOutProxy");
            }

        }

        @Override
        protected void finished(Description description) {
            browser.proxy().stop();
            browser.closeBrowser();
        }

        @Override
        protected void failed(Throwable e, Description description) {
            takeScreenshot("/" + description.getMethodName() + "");
        }
    };
}
