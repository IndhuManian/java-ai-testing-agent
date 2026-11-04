package AI.Agent.Tests;

import AI.Agent.Decision.AgentFeedbackHandler;
import AI.Agent.Decision.AgentFunctionCall;
import AI.Agent.Decision.SimulatedAIDecisionMaker;
import AI.Agent.Tools.AgentToolExecutor;
import AI.Context.FailureContext;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AgentFeedbackSimulationTest {

    @Test
    public void testMissingLocatorFeedback() {

        System.out.println("\n===== SCENARIO B: LOCATOR NOT FOUND =====");

        // Step 1: Create simulated failure context
        FailureContext failure = new FailureContext();

        failure.setScenarioName(
                "TC02_Verify update pincode with already login user");

        failure.setExceptionType("NoSuchElementException");

        failure.setErrorMessage(
                "Locator information not found in error message.");

        // Step 2: Simulate the first tool result
        String simulatedToolResult =
                "Locator information not found in error message.";

        System.out.println("\nSimulated Tool Result:");
        System.out.println(simulatedToolResult);

        // Step 3: Evaluate feedback
        AgentFeedbackHandler feedbackHandler =
                new AgentFeedbackHandler();

        String feedback =
                feedbackHandler.evaluate(simulatedToolResult);

        System.out.println("\nAgent Feedback:");
        System.out.println(feedback);

        // Step 4: Ask simulated decision-maker for next action
        SimulatedAIDecisionMaker decisionMaker =
                new SimulatedAIDecisionMaker();

        AgentFunctionCall nextDecision =
                decisionMaker.decideNext(
                        failure,
                        simulatedToolResult,
                        feedback
                );

        if (nextDecision == null) {
            System.out.println("No further tool execution required.");
            return;
        }

        System.out.println("\nNext Tool Selected: "
                + nextDecision.getToolName());

        System.out.println("Reasoning: "
                + nextDecision.getReasoning());

        // Step 5: Execute the selected second tool
        AgentToolExecutor toolExecutor =
                new AgentToolExecutor();

        String secondToolResult =
                toolExecutor.execute(
                        nextDecision,
                        Collections.singletonList(failure)
                );

        System.out.println("\n===== SECOND TOOL RESULT =====");
        System.out.println(secondToolResult);
    }
}