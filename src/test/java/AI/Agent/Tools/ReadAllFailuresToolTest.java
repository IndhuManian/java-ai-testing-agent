
package AI.Agent.Tools;

import AI.Context.FailureContext;

import java.util.List;

public class ReadAllFailuresToolTest {

    public static void main(String[] args) throws Exception {

        ReadAllFailuresTool tool =
                new ReadAllFailuresTool();

        List<FailureContext> failures = tool.execute();

        System.out.println("\n===== RETRIEVED FAILURES =====");

        for (FailureContext failure : failures) {

            System.out.println(
                    "Scenario: " + failure.getScenarioName());

            System.out.println(
                    "Status: " + failure.getStatus());

            System.out.println(
                    "Exception: " + failure.getExceptionType());

            System.out.println(
                    "Failed Step: " + failure.getFailedStep());

            System.out.println("-----------------------------");
        }
    }
}
