package AI.Agent.Tools;

import Hooks.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class DOMInspector {

    public String inspectDOM() {

        System.out.println("DOM Inspector: Inspecting current webpage...");

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized.");
        }

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        String pageDOM = (String) js.executeScript(
                "return document.documentElement.outerHTML;"
        );

        System.out.println("DOM inspection completed.");

        return pageDOM;
    }
}