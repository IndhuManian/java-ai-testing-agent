package AI.Agent.Decision;

import AI.Agent.Tools.ReadAllFailuresTool;
import AI.Agent.Decision.AgentLoopController;
import AI.Context.FailureContext;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class SimulatedAIDecisionMakerTest {

    @Test
    public void testSimulatedAIAgent() throws IOException {

        System.out.println(
                "\n===== STARTING SIMULATED AI AGENT TEST =====");

        // Step 1: Read structured failure information
        ReadAllFailuresTool readAllFailuresTool =
                new ReadAllFailuresTool();

        List<FailureContext> failures =
                readAllFailuresTool.execute();

        // Step 2: Validate failure information
        if (failures == null || failures.isEmpty()) {
            System.out.println("No failure reports available.");
            return;
        }

        // Step 3: Start the agent workflow
        AgentLoopController agentLoopController =
                new AgentLoopController();

        agentLoopController.run(failures);

        System.out.println(
                "\n===== SIMULATED AI AGENT TEST COMPLETED =====");
    }
}