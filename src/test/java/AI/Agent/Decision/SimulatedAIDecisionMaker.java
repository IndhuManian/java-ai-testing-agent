
package AI.Agent.Decision;

import AI.Context.FailureContext;
import java.util.HashMap;
import java.util.Map;

public class SimulatedAIDecisionMaker {

    public AgentFunctionCall decide(FailureContext failure) {

        System.out.println("Simulated AI: Analysing failure context...");

        String exceptionType = failure.getExceptionType();
        String scenarioName = failure.getScenarioName();

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("scenarioName", scenarioName);

        if (exceptionType != null &&
                exceptionType.contains("NoSuchElementException")) {

            return new AgentFunctionCall(
                    "InspectLocatorTool",
                    arguments,
                    "The element could not be identified. Locator inspection is required."
            );
        }

        if (exceptionType != null &&
                exceptionType.contains("TimeoutException")) {

            return new AgentFunctionCall(
                    "RetrieveScenarioDetailsTool",
                    arguments,
                    "The operation exceeded the expected wait time. Detailed scenario investigation is required."
            );
        }

        return new AgentFunctionCall(
                "RetrieveScenarioDetailsTool",
                arguments,
                "The failure requires further investigation using the scenario details."
        );
    }

    public AgentFunctionCall decideNext(
            FailureContext failure,
            String previousToolResult,
            String feedback) {

        System.out.println(
                "\nSimulated AI: Evaluating previous tool feedback...");

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("scenarioName", failure.getScenarioName());

        if (feedback != null &&
                feedback.contains("Retrieve full scenario details")) {

            return new AgentFunctionCall(
                    "RetrieveScenarioDetailsTool",
                    arguments,
                    "Locator details were unavailable. Retrieving complete scenario failure details."
            );
        }

        return null;
    }
}
