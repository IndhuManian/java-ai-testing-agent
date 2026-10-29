
package AI.Listener;

import AI.Collector.FailureReportReader;
import AI.Client.MockAIClient;
import AI.Prompts.AIPromptManager;
import AI.Reports.AIReportWriter;

import org.testng.IExecutionListener;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class AIAfterTestListener implements IExecutionListener {

    private static final Path FAILURE_REPORT_DIRECTORY =
            Paths.get("target/ai-failure-reports");

    private void clearPreviousFailureReports() throws IOException {

        if (!Files.isDirectory(FAILURE_REPORT_DIRECTORY)) {
            return;
        }

        try (DirectoryStream<Path> reports =
                     Files.newDirectoryStream(
                             FAILURE_REPORT_DIRECTORY, "*.txt")) {

            for (Path report : reports) {
                Files.deleteIfExists(report);
            }
        }
    }
    @Override
    public void onExecutionStart() {

        System.out.println("AI Test Analysis: Execution started.");

        try {
            clearPreviousFailureReports();
        } catch (IOException e) {
            System.err.println(
                    "Could not clear previous failure reports: "
                            + e.getMessage());
        }
    }

    @Override
    public void onExecutionFinish() {

        System.out.println("\n===== STARTING POST-EXECUTION AI ANALYSIS =====");

        try {
            FailureReportReader reader = new FailureReportReader();

            String failureDetails = reader.readLatestFailure();

            String prompt =
                    AIPromptManager.createFailurePrompt(failureDetails);

            MockAIClient aiClient = new MockAIClient();

            String analysis = aiClient.analyzeFailure(prompt);

            AIReportWriter reportWriter = new AIReportWriter();

            reportWriter.writeReport(analysis);

            System.out.println("\n===== AI FAILURE ANALYSIS =====");
            System.out.println(analysis);

        } catch (Exception e) {
            System.out.println("Post-execution AI analysis: "
                    + e.getMessage());
        }
    }
}
