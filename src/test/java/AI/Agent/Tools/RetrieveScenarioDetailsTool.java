
package AI.Agent.Tools;

import AI.Context.FailureContext;
import java.util.List;

public class RetrieveScenarioDetailsTool {

    public String execute(List<FailureContext> failures, String scenarioName) {

        System.out.println("AI Agent Tool: Retrieving scenario details...");

        for (FailureContext failure : failures) {

            if (failure.getScenarioName() != null
                    && failure.getScenarioName().equalsIgnoreCase(scenarioName)) {

                StringBuilder details = new StringBuilder();

                details.append("\n===== SCENARIO FAILURE DETAILS =====");
                details.append("\nScenario Name: ").append(failure.getScenarioName());
                details.append("\nException Type: ").append(failure.getExceptionType());
                details.append("\nFailed Step: ").append(failure.getFailedStep());
                details.append("\nError Message: ").append(failure.getErrorMessage());
                details.append("\nStack Trace: ").append(failure.getStackTrace());

                System.out.println("AI Agent Tool: Scenario details retrieved successfully.");

                return details.toString();
            }
        }

        return "Scenario not found: " + scenarioName;
    }
}
