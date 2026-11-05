
package AI.Agent.Tools;

import AI.Context.HealingResult;
import Hooks.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelfHealingLocatorTool {

    private final DOMInspector domInspector =
            new DOMInspector();

    private final LocatorCandidateGenerator candidateGenerator =
            new LocatorCandidateGenerator();

    private final LocatorValidator locatorValidator =
            new LocatorValidator();

    public HealingResult execute(
            String scenarioName,
            By originalBy,
            String expectedText,
            String tagName) {


            System.out.println("\n==================================");
            System.out.println("AI AGENT: SELF HEALING LOCATOR");
            System.out.println("==================================");

            HealingResult result = new HealingResult();

            result.setScenarioName(scenarioName);
            result.setOriginalLocator(originalBy.toString());

            WebDriver driver = DriverFactory.getDriver();

            if (driver == null) {
                throw new IllegalStateException(
                        "WebDriver is not initialized.");
            }

            try {

                // STEP 1: Revisit application page
//
//            System.out.println("Step 1: Revisiting webpage...");
//
//            driver.get(pageUrl);

                // STEP 2: Check original locator


                List<WebElement> originalElements =
                        driver.findElements(originalBy);

                if (originalElements.size() == 1
                        && originalElements.get(0).isDisplayed()
                        && originalElements.get(0).isEnabled()) {

                    System.out.println(
                            "Original locator is still valid.");

                    result.setHealedLocator(originalBy.toString());
                    result.setHealedBy(originalBy);
                    result.setLocatorType("ORIGINAL");
                    result.setReason(
                            "Original locator is valid.");
                    result.setConfidence("HIGH");
                    result.setHealed(false);
                    result.setValidated(true);

                    return result;
                }

                // STEP 3: Inspect live DOM

                System.out.println("Step 2: Inspecting DOM...");

                String pageDOM = domInspector.inspectDOM();

                // STEP 4: Generate candidate locators

                System.out.println(
                        "Step 3: Generating candidate locators...");

                List<By> candidates =
                        candidateGenerator.generateCandidates(
                                pageDOM,
                                expectedText,
                                tagName
                        );

                if (candidates.isEmpty()) {

                    result.setReason(
                            "No candidate locators generated.");

                    result.setConfidence("LOW");
                    result.setHealed(false);
                    result.setValidated(false);

                    return result;
                }

                // STEP 5: Validate candidates

                System.out.println(
                        "Step 4: Validating candidate locators...");

                By healedBy =
                        locatorValidator.validateCandidates(candidates);

                if (healedBy == null) {

                    result.setReason(
                            "No valid replacement locator found.");

                    result.setConfidence("LOW");
                    result.setHealed(false);
                    result.setValidated(false);

                    return result;
                }

                // STEP 6: Return healing result

                System.out.println(
                        "Step 5: Candidate locator validated.");

                result.setHealedLocator(healedBy.toString());
                result.setHealedBy(healedBy);
                result.setLocatorType("SELENIUM_BY");
                result.setReason(
                        "Alternative locator identified and validated.");
                result.setConfidence("MEDIUM");
                result.setHealed(true);
                result.setValidated(true);

                System.out.println(
                        "Healed locator: " + healedBy);

            } catch (Exception e) {

                result.setReason(
                        "Self-healing failed: " + e.getMessage());

                result.setConfidence("LOW");
                result.setHealed(false);
                result.setValidated(false);

                e.printStackTrace();
            }

            return result;
        }


}
