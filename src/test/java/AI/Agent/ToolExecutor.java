
package AI.Agent;

import AI.Agent.Decision.AgentDecision;
import AI.Agent.Tools.ReadAllFailuresTool;
import AI.Agent.Tools.InspectLocatorTool;
import AI.Agent.Tools.RetrieveScenarioDetailsTool;
import AI.Context.FailureContext;

import java.io.IOException;
import java.util.List;

public class ToolExecutor {

    private final ReadAllFailuresTool readAllFailuresTool =
            new ReadAllFailuresTool();

    private final InspectLocatorTool inspectLocatorTool =
            new InspectLocatorTool();

    private final RetrieveScenarioDetailsTool retrieveScenarioDetailsTool =
            new RetrieveScenarioDetailsTool();

    public String execute(AgentDecision decision) throws IOException {

        System.out.println("Tool Executor: Executing selected tool...");
        System.out.println("Selected Tool: " + decision.getToolName());

        List<FailureContext> failures = readAllFailuresTool.execute();

        switch (decision.getToolName()) {

            case "InspectLocatorTool":
                return inspectLocatorTool.execute(
                        failures,
                        decision.getScenarioName()
                );

            case "RetrieveScenarioDetailsTool":
                return retrieveScenarioDetailsTool.execute(
                        failures,
                        decision.getScenarioName()
                );

            default:
                return "Unknown tool selected: " + decision.getToolName();
        }
    }
}
