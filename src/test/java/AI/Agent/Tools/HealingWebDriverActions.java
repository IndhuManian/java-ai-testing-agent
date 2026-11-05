
package AI.Agent.Tools;

import AI.Context.HealingResult;
import Hooks.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HealingWebDriverActions {

    private final SelfHealingLocatorTool healingTool =
            new SelfHealingLocatorTool();

    public void click(
            By originalLocator,
            String scenarioName,
            String expectedText,
            String tagName) {

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized.");
        }

        try {

            System.out.println(
                    "Attempting original locator: " + originalLocator);

            List<WebElement> elements =
                    driver.findElements(originalLocator);

            if (elements.size() == 1
                    && elements.get(0).isDisplayed()
                    && elements.get(0).isEnabled()) {

                elements.get(0).click();

                System.out.println("Original locator succeeded.");
                return;
            }

            System.out.println(
                    "Original locator failed. Invoking self-healing...");

        } catch (Exception e) {

            System.out.println(
                    "Original action failed: " + e.getMessage());
        }

        // Invoke self-healing
        HealingResult result = healingTool.execute(
                scenarioName,
                originalLocator,
                expectedText,
                tagName
        );

        if (!result.isValidated()
                || result.getHealedBy() == null) {

            throw new RuntimeException(
                    "Locator validation unsuccessful: " + result.getReason());
        }

        // Retry using healed locator
        System.out.println(
                "Retrying action with healed locator: "
                        + result.getHealedBy());

        WebElement healedElement =
                driver.findElement(result.getHealedBy());




        healedElement.click();

        System.out.println(
                "Self-healing successful. Action retried.");
    }

    public WebElement type(
            By originalLocator,
            String scenarioName,
            String expectedText,
            String tagName,
            String textToType) {

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized.");
        }

        try {
            System.out.println(
                    "Attempting original locator: " + originalLocator);

            List<WebElement> elements =
                    driver.findElements(originalLocator);

            if (elements.size() == 1
                    && elements.get(0).isDisplayed()
                    && elements.get(0).isEnabled()) {

                WebElement input = elements.get(0);

                input.clear();
                input.sendKeys(textToType);

                System.out.println("Original locator succeeded.");

                return input;
            }

            System.out.println(
                    "Original locator failed. Invoking self-healing...");

        } catch (Exception e) {
            System.out.println(
                    "Original typing failed: " + e.getMessage());
        }

        HealingResult result = healingTool.execute(
                scenarioName,
                originalLocator,
                expectedText,
                tagName
        );

        if (!result.isValidated()
                || result.getHealedBy() == null) {

            throw new RuntimeException(
                    "Locator validation unsuccessful: "
                            + result.getReason());
        }

        System.out.println(
                "Retrying typing with healed locator: "
                        + result.getHealedBy());

        List<WebElement> healedElements =
                driver.findElements(result.getHealedBy());

        if (healedElements.size() != 1
                || !healedElements.get(0).isDisplayed()
                || !healedElements.get(0).isEnabled()) {

            throw new RuntimeException(
                    "Healed input is not unique, visible and enabled.");
        }

        WebElement healedInput = healedElements.get(0);

        healedInput.clear();
        healedInput.sendKeys(textToType);

        System.out.println(
                "Self-healing successful. Text entered.");

        return healedInput;
    }
}
