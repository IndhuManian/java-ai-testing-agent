
package AI.Agent;

import AI.Agent.Decision.AgentDecision;
import AI.Agent.Decision.MockDecisionMaker;
import AI.Context.FailureContext;

import java.io.IOException;

public class AIAgent {

    private final MockDecisionMaker decisionMaker =
            new MockDecisionMaker();

    private final ToolExecutor toolExecutor =
            new ToolExecutor();

    public String analyze(FailureContext failure) throws IOException {

        System.out.println("\n========== AI AGENT STARTED ==========");

        // Step 1: Receive failure context
        System.out.println("AI Agent: Received failure context.");
        System.out.println("Scenario: " + failure.getScenarioName());
        System.out.println("Exception: " + failure.getExceptionType());

        // Step 2: Decide which tool to use
        AgentDecision decision = decisionMaker.decide(failure);

        System.out.println("\nAI Agent: Decision generated.");
        System.out.println("Selected Tool: " + decision.getToolName());
        System.out.println("Reason: " + decision.getReason());

        // Step 3: Execute the selected tool
        String toolResult = toolExecutor.execute(decision);

        System.out.println("\nAI Agent: Tool execution completed.");

        // Step 4: Return the tool result
        System.out.println("\n========== AI AGENT COMPLETED ==========");

        return toolResult;
    }
}
