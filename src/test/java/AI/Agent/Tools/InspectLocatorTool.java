
package AI.Agent.Tools;

import AI.Context.FailureContext;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InspectLocatorTool {

    public String execute(List<FailureContext> failures,
                          String scenarioName) {

        System.out.println(
                "AI Agent Tool: Inspecting locator...");

        for (FailureContext failure : failures) {

            if (failure.getScenarioName() != null
                    && failure.getScenarioName()
                    .equalsIgnoreCase(scenarioName)) {

                String errorMessage = failure.getErrorMessage();

                if (errorMessage == null) {
                    return "No error message available for scenario: "
                            + scenarioName;
                }

                Pattern pattern = Pattern.compile(
                        "\"method\":\"([^\"]+)\"\\s*,\\s*"
                                + "\"selector\":\"([^\"]+)\"");

                Matcher matcher = pattern.matcher(errorMessage);

                if (matcher.find()) {

                    String locatorType = matcher.group(1);
                    String locatorValue = matcher.group(2);

                    return "\n===== LOCATOR DETAILS ====="
                            + "\nScenario: " + failure.getScenarioName()
                            + "\nException: " + failure.getExceptionType()
                            + "\nFailed Step: " + failure.getFailedStep()
                            + "\nLocator Strategy: " + locatorType
                            + "\nLocator Value: " + locatorValue;
                }

                return "Locator information not found in error message.";
            }
        }

        return "Scenario not found: " + scenarioName;
    }
}
