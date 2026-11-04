
package AI.Agent.Decision;

import AI.Agent.Tools.AgentToolExecutor;
import AI.Context.FailureContext;

import java.io.IOException;
import java.util.List;

public class AgentLoopController {

    private static final int MAX_TOOL_EXECUTIONS = 2;

    private final SimulatedAIDecisionMaker decisionMaker;
    private final AgentToolExecutor toolExecutor;
    private final AgentFeedbackHandler feedbackHandler;
    private final AgentAnalysisReporter analysisReporter;

    public AgentLoopController() {
        this.decisionMaker = new SimulatedAIDecisionMaker();
        this.toolExecutor = new AgentToolExecutor();
        this.feedbackHandler = new AgentFeedbackHandler();
        this.analysisReporter = new AgentAnalysisReporter();
    }

    public void run(List<FailureContext> failures) {

        System.out.println("\n===== AI AGENT STARTED =====");

        if (failures == null || failures.isEmpty()) {
            System.out.println("No failure information available.");
            return;
        }

        FailureContext failure = failures.get(0);

        AgentFunctionCall decision = decisionMaker.decide(failure);

        String lastToolResult = null;
        String lastFeedback = null;

        for (int step = 1;
             step <= MAX_TOOL_EXECUTIONS && decision != null;
             step++) {

            System.out.println("\n===== AGENT EXECUTION STEP "
                    + step + " =====");

            lastToolResult =
                    toolExecutor.execute(decision, failures);

            System.out.println("\nTool Result:");
            System.out.println(lastToolResult);

            lastFeedback =
                    feedbackHandler.evaluate(lastToolResult);

            System.out.println("\nAgent Feedback:");
            System.out.println(lastFeedback);

            if (step < MAX_TOOL_EXECUTIONS) {

                decision = decisionMaker.decideNext(
                        failure,
                        lastToolResult,
                        lastFeedback
                );

                if (decision == null) {
                    System.out.println(
                            "\nAI Agent: No further tool execution required."
                    );
                }

            } else {
                System.out.println(
                        "\nAI Agent: Maximum tool execution limit reached."
                );
            }
        }

        // Generate and save final QA analysis report
        if (lastToolResult != null) {

            String finalReport =
                    analysisReporter.generateReport(
                            failure,
                            lastToolResult,
                            lastFeedback
                    );

            System.out.println(finalReport);

            try {
                analysisReporter.saveReport(finalReport);

            } catch (IOException e) {
                System.out.println(
                        "Unable to save final QA report: "
                                + e.getMessage()
                );
            }
        }

        System.out.println("\n===== AI AGENT COMPLETED =====");
    }
}
