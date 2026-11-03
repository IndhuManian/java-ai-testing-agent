package AI.Agent.Decision;

import AI.Agent.Decision.AgentDecision;
import AI.Agent.Decision.SimulatedAIDecisionMaker;
import AI.Agent.Tools.ReadAllFailuresTool;
import AI.Context.FailureContext;

import java.io.IOException;
import java.util.List;

public class SimulatedAIDecisionMakerTest {

    public static void main(String[] args) throws IOException {

        ReadAllFailuresTool readAllFailuresTool =
                new ReadAllFailuresTool();

        List<FailureContext> failures =
                readAllFailuresTool.execute();

        if (failures.isEmpty()) {
            System.out.println("No failure reports found.");
            return;
        }

        FailureContext failure = failures.get(0);

        SimulatedAIDecisionMaker decisionMaker =
                new SimulatedAIDecisionMaker();

        AgentDecision decision = decisionMaker.decide(failure);

        System.out.println("\n===== SIMULATED AI DECISION =====");
        System.out.println("Scenario: " + decision.getScenarioName());
        System.out.println("Selected Tool: " + decision.getToolName());
        System.out.println("Reason: " + decision.getReason());
    }
}