
package AI.Collector;

import AI.Agent.Decision.AgentLoopController;
import AI.Agent.Tools.ReadAllFailuresTool;
import AI.Context.FailureContext;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;

import java.io.IOException;
import java.util.List;

public class AIAgentExecutionListener implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestRunFinished.class,
                this::executeAIAgent
        );
    }

    private void executeAIAgent(TestRunFinished event) {

        System.out.println(
                "\n===== STARTING POST-EXECUTION AI ANALYSIS ====="
        );

        try {

            // Step 1: Aggregate individual failure JSON files
            FailureContextAggregator aggregator =
                    new FailureContextAggregator();

            aggregator.aggregateFailures();

            // Step 2: Read consolidated failure information
            ReadAllFailuresTool reader =
                    new ReadAllFailuresTool();

            List<FailureContext> failures =
                    reader.execute();

            // Step 3: Execute AI Agent
            if (failures != null && !failures.isEmpty()) {

                AgentLoopController agent =
                        new AgentLoopController();

                agent.run(failures);

            } else {

                System.out.println(
                        "No failed scenarios found. AI analysis skipped."
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "AI Agent execution error: " + e.getMessage()
            );

            e.printStackTrace();
        }

        System.out.println(
                "\n===== POST-EXECUTION AI ANALYSIS COMPLETED ====="
        );
    }
}
