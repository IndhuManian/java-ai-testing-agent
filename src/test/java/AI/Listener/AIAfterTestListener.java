
package AI.Listener;

import AI.Collector.FailureContextAggregator;
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


    private void clearPreviousFailureReports() {

        String[] reportDirectories = {
                "target/ai-failure-reports",
                "target/ai-analysis-reports"
        };

        for (String directoryPath : reportDirectories) {

            Path directory = Paths.get(directoryPath);

            if (!Files.exists(directory)) {
                continue;
            }

            try (DirectoryStream<Path> files =
                         Files.newDirectoryStream(directory)) {

                for (Path file : files) {
                    Files.deleteIfExists(file);
                }

                System.out.println(
                        "Previous reports cleared: " + directoryPath);

            } catch (IOException e) {

                System.out.println(
                        "Unable to clear reports: " + directoryPath);

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onExecutionStart() {

        System.out.println("AI Test Analysis: Execution started.");


            clearPreviousFailureReports();

    }

    @Override
    public void onExecutionFinish() {

        System.out.println("\n===== STARTING POST-EXECUTION AI ANALYSIS =====");

        try {
            FailureContextAggregator aggregator =
                    new FailureContextAggregator();

            aggregator.aggregateFailures();

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
