
package AI.Agent.Decision;

import AI.Context.FailureContext;

public class MockDecisionMaker {

    public AgentDecision decide(FailureContext failure) {

        System.out.println("Mock Decision Maker: Analysing failure...");

        String exceptionType = failure.getExceptionType();
        String scenarioName = failure.getScenarioName();

        if (exceptionType != null
                && exceptionType.contains("NoSuchElementException")) {

            return new AgentDecision(
                    "InspectLocatorTool",
                    "NoSuchElementException requires locator investigation.",
                    scenarioName
            );
        }

        if (exceptionType != null
                && exceptionType.contains("TimeoutException")) {

            return new AgentDecision(
                    "RetrieveScenarioDetailsTool",
                    "TimeoutException requires detailed scenario investigation.",
                    scenarioName
            );
        }

        return new AgentDecision(
                "RetrieveScenarioDetailsTool",
                "General failure requires detailed scenario investigation.",
                scenarioName
        );
    }
}
