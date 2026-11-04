package AI.Agent.Decision;

public class AgentFeedbackHandler {

    public String evaluate(String toolResult) {

        System.out.println("\nAI Agent: Evaluating tool execution result...");

        if (toolResult == null || toolResult.trim().isEmpty()) {
            return "Tool returned an empty result. Further investigation is required.";
        }

        if (toolResult.contains("Locator information not found")) {
            return "Locator details are unavailable. Retrieve full scenario details.";
        }

        if (toolResult.contains("Scenario not found")) {
            return "Scenario could not be located. Verify the scenario information.";
        }

        if (toolResult.contains("No error message available")) {
            return "Error message is unavailable. Retrieve other failure details.";
        }

        if (toolResult.contains("LOCATOR DETAILS")) {
            return "Locator information retrieved successfully. Locator investigation can proceed.";
        }

        if (toolResult.contains("SCENARIO FAILURE DETAILS")) {
            return "Scenario failure details retrieved successfully. Further analysis can proceed.";
        }

        return "Tool execution completed. Review the returned information for further action.";
    }
}