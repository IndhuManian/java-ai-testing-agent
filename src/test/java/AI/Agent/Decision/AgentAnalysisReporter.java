package AI.Agent.Decision;

import AI.Context.FailureContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AgentAnalysisReporter {

    public String generateReport(
            FailureContext failure,
            String toolResult,
            String feedback) {

        StringBuilder report = new StringBuilder();

        report.append("\n========== AI AGENT QA ANALYSIS REPORT ==========");

        report.append("\n\n1. FAILURE SUMMARY");
        report.append("\nScenario: ")
                .append(failure.getScenarioName());

        report.append("\nException: ")
                .append(getValue(failure.getExceptionType()));

        report.append("\nFailed Step: ")
                .append(getValue(failure.getFailedStep()));

        report.append("\n\n2. INVESTIGATION DETAILS");
        report.append("\n")
                .append(getValue(toolResult));

        report.append("\n\n3. AGENT OBSERVATION");
        report.append("\n")
                .append(getValue(feedback));

        report.append("\n\n4. RECOMMENDED ACTION");

        if (toolResult != null &&
                toolResult.contains("LOCATOR DETAILS")) {

            report.append("\nReview the identified locator against the current application DOM.");
            report.append("\nVerify whether the element attributes and page state have changed.");

        } else if (toolResult != null &&
                toolResult.contains("SCENARIO FAILURE DETAILS")) {

            report.append("\nReview the complete scenario failure information.");
            report.append("\nInvestigate the failed step, application state and exception details.");

        } else {

            report.append("\nInsufficient information for a specific recommendation.");
            report.append("\nReview the failure report and collect additional evidence.");
        }

        report.append("\n\n5. ANALYSIS STATUS");
        report.append("\nTechnical investigation completed.");
        report.append("\nRecommended action requires QA engineer review.");

        report.append("\n\n================================================");

        return report.toString();
    }

    private String getValue(String value) {

        if (value == null || value.trim().isEmpty()) {
            return "Not available";
        }

        return value;
    }

    public void saveReport(String report) throws IOException {

        Path directory = Paths.get("target/ai-failure-reports");

        Files.createDirectories(directory);

        Path reportFile = directory.resolve(
                "AI-Agent-QA-Analysis-Report.txt"
        );

        Files.write(
                reportFile,
                report.getBytes(StandardCharsets.UTF_8)
        );

        System.out.println(
                "\nFinal QA report saved at: " +
                        reportFile.toAbsolutePath()
        );
    }
}