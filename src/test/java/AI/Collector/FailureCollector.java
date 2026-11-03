
package AI.Collector;

import AI.Context.FailureContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FailureCollector implements EventListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String failedStep;

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestStepFinished.class,
                this::captureFailedStep
        );

        publisher.registerHandlerFor(
                TestCaseFinished.class,
                this::captureFailure
        );
    }

    private void captureFailedStep(TestStepFinished event) {

        if (event.getTestStep() instanceof PickleStepTestStep
                && event.getResult().getStatus() == Status.FAILED) {

            PickleStepTestStep step =
                    (PickleStepTestStep) event.getTestStep();

            failedStep = step.getStep().getText();
        }
    }

    private void captureFailure(TestCaseFinished event) {

        Throwable error = event.getResult().getError();

        if (error == null) {
            failedStep = null;
            return;
        }

        String scenarioName = event.getTestCase().getName();

        String exceptionType =
                error.getClass().getSimpleName();

        String errorMessage = error.getMessage();

        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));

        String stackTrace = sw.toString();

        FailureContext context = new FailureContext(
                scenarioName,
                failedStep,
                "FAILED",
                exceptionType,
                errorMessage,
                stackTrace
        );

        try {

            Path directory =
                    Paths.get("target/ai-failure-reports");

            Files.createDirectories(directory);

            String fileName = "failure_"
                    + System.currentTimeMillis();

            String report =
                    "Scenario: " + scenarioName +
                            "\nFailed Step: " + failedStep +
                            "\nStatus: FAILED" +
                            "\nException Type: " + exceptionType +
                            "\nError: " + errorMessage +
                            "\nStack Trace:\n" + stackTrace;

            Files.write(
                    directory.resolve(fileName + ".txt"),
                    report.getBytes(StandardCharsets.UTF_8)
            );

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                            directory.resolve(fileName + ".json").toFile(),
                            context
                    );

            System.out.println(
                    "Failure Context JSON Generated Successfully!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Failure collection error: " + e.getMessage()
            );

        } finally {
            failedStep = null;
        }
    }
}
