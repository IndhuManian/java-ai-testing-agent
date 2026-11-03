package AI.Collector;

import AI.Context.FailureContext;
import java.nio.file.Paths;

public class FailureContextReaderTest {

    public static void main(String[] args) throws Exception {

        FailureContextReader reader =
                new FailureContextReader();

        FailureContext context =
                reader.readFailureContext(
                        Paths.get("target/ai-failure-reports/failure_1793624869717.json")
                );

        System.out.println("Scenario: "
                + context.getScenarioName());

        System.out.println("Status: "
                + context.getStatus());

        System.out.println("Exception: "
                + context.getExceptionType());

        System.out.println("Error: "
                + context.getErrorMessage());
    }
}