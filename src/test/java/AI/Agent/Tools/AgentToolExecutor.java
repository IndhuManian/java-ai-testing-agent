
package AI.Agent.Tools;

import AI.Agent.Decision.AgentFunctionCall;
import AI.Context.FailureContext;

import java.util.List;

public class AgentToolExecutor {

    private final InspectLocatorTool inspectLocatorTool;
    private final RetrieveScenarioDetailsTool retrieveScenarioDetailsTool;

    public AgentToolExecutor() {

        this.inspectLocatorTool = new InspectLocatorTool();
        this.retrieveScenarioDetailsTool = new RetrieveScenarioDetailsTool();
    }

    public String execute(AgentFunctionCall functionCall,
                          List<FailureContext> failures) {

        System.out.println("\nAI Agent: Executing selected tool...");
        System.out.println("Requested Tool: " + functionCall.getToolName());

        String scenarioName = (String) functionCall.getArguments()
                .get("scenarioName");

        switch (functionCall.getToolName()) {

            case "InspectLocatorTool":

                return inspectLocatorTool.execute(
                        failures, scenarioName);

            case "RetrieveScenarioDetailsTool":

                return retrieveScenarioDetailsTool.execute(
                        failures, scenarioName);

            default:

                return "Unknown agent tool requested: "
                        + functionCall.getToolName();
        }
    }
}
