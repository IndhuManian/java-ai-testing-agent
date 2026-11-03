
package AI.Agent;

import AI.Agent.Tools.ReadAllFailuresTool;
import AI.Context.FailureContext;

import java.io.IOException;
import java.util.List;

public class AIAgentTest {

    public static void main(String[] args) throws IOException {

        // Step 1: Read all failure contexts
        ReadAllFailuresTool readAllFailuresTool =
                new ReadAllFailuresTool();

        List<FailureContext> failures =
                readAllFailuresTool.execute();

        // Step 2: Create the AI Agent
        AIAgent aiAgent = new AIAgent();

        // Step 3: Send each failure to the agent
        for (FailureContext failure : failures) {

            String result = aiAgent.analyze(failure);

            // Step 4: Display the result
            System.out.println("\n===== AGENT ANALYSIS RESULT =====");
            System.out.println(result);
        }
    }
}
