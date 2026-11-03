
package AI.Agent.Tools;

import AI.Collector.FailureReportReader;
import java.io.IOException;

public class ReadFailureReportTool {

    private final FailureReportReader reportReader;

    public ReadFailureReportTool() {
        this.reportReader = new FailureReportReader();
    }

    public String execute() throws IOException {

        System.out.println("AI Agent Tool: Reading latest failure report...");

        String failureReport = reportReader.readLatestFailure();

        System.out.println("AI Agent Tool: Failure report retrieved successfully.");

        return failureReport;
    }
}
