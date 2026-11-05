
package AI.Agent.Tools;

import Hooks.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LocatorValidator {

    public By validateCandidates(List<By> candidates) {

        System.out.println();
        System.out.println("Locator Validator: Validating candidates...");

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized.");
        }

        for (By candidate : candidates) {

            try {

                System.out.println(
                        "Checking candidate: " + candidate);

                List<WebElement> elements =
                        driver.findElements(candidate);

                // Locator must identify exactly one element
                if (elements.size() != 1) {

                    System.out.println(
                            "Rejected: Element count = "
                                    + elements.size());

                    continue;
                }

                WebElement element = elements.get(0);

                // Check visibility
                if (!element.isDisplayed()) {

                    System.out.println(
                            "Rejected: Element is not visible.");

                    continue;
                }

                // Check enabled status
                if (!element.isEnabled()) {

                    System.out.println(
                            "Rejected: Element is disabled.");

                    continue;
                }

                System.out.println(
                        "Valid candidate found: " + candidate);

                return candidate;

            } catch (Exception e) {

                System.out.println(
                        "Candidate validation failed: "
                                + e.getMessage());
            }
        }

        System.out.println(
                "No valid candidate locator found.");

        return null;
    }
}
