
package AI.Collector;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.TestCaseFinished;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FailureCollector implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestCaseFinished.class,
                this::captureFailure
        );
    }

    private void captureFailure(TestCaseFinished event) {

        Throwable error = event.getResult().getError();

        if (error == null) {
            return;
        }

        try {
            String scenarioName = event.getTestCase().getName();

            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));

            String report =
                    "Scenario: " + scenarioName +
                            "\nStatus: FAILED" +
                            "\nError: " + error.getMessage() +
                            "\nStack Trace:\n" + sw;

            Path directory =
                    Paths.get("target/ai-failure-reports");

            Files.createDirectories(directory);

            String fileName = "failure_"
                    + System.currentTimeMillis() + ".txt";

            Files.write(
                    directory.resolve(fileName),
                    report.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("AI Failure Data Captured!");
            System.out.println(report);

        } catch (Exception e) {
            System.out.println(
                    "Failure collection error: " + e.getMessage()
            );
        }
    }
}
