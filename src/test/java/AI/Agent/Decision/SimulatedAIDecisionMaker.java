package AI.Agent.Decision;

import AI.Context.FailureContext;

public class SimulatedAIDecisionMaker {

    public AgentDecision decide(FailureContext failure) {

        System.out.println("Simulated AI: Analysing failure context...");

        String exceptionType = failure.getExceptionType();
        String scenarioName = failure.getScenarioName();

        if (exceptionType != null &&
                exceptionType.contains("NoSuchElementException")) {

            return new AgentDecision(
                    "InspectLocatorTool",
                    "The element could not be identified. Locator inspection is required.",
                    scenarioName
            );
        }

        if (exceptionType != null &&
                exceptionType.contains("TimeoutException")) {

            return new AgentDecision(
                    "RetrieveScenarioDetailsTool",
                    "The operation exceeded the expected wait time. Detailed scenario investigation is required.",
                    scenarioName
            );
        }

        return new AgentDecision(
                "RetrieveScenarioDetailsTool",
                "The failure requires further investigation using the scenario details.",
                scenarioName
        );
    }
}