
package AI;

import AI.Client.MockAIClient;
import AI.Prompts.AIPromptManager;
import AI.Collector.FailureReportReader;

import AI.Reports.AIReportWriter;
public class TestAIAnalysis {

    public static void main(String[] args) {

        try {

            // Step 1: Read actual Selenium failure report
            FailureReportReader reader = new FailureReportReader();

            String failureDetails = reader.readLatestFailure();

            System.out.println("===== ACTUAL FAILURE REPORT =====");
            System.out.println(failureDetails);

            // Step 2: Prepare AI prompt
            String prompt =
                    AIPromptManager.createFailurePrompt(failureDetails);

            // Step 3: Analyze using mock AI client
            MockAIClient aiClient = new MockAIClient();

            String analysis = aiClient.analyzeFailure(prompt);

            // Step 4: Save AI analysis into a report
            AIReportWriter reportWriter = new AIReportWriter();
            reportWriter.writeReport(analysis);

           // Step 5: Display analysis
            System.out.println("\n===== AI FAILURE ANALYSIS =====");
            System.out.println(analysis);


        } catch (Exception e) {

            System.out.println("AI Analysis Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
