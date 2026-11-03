
package AI.Agent.Tools;

import AI.Context.FailureContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadAllFailuresTool {

    private static final String REPORT_FILE =
            "target/ai-failure-reports/all-failures.json";

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public List<FailureContext> execute() throws IOException {

        System.out.println(
                "AI Agent Tool: Reading all failure reports...");

        File file = new File(REPORT_FILE);

        if (!file.exists()) {
            throw new IOException(
                    "Consolidated failure report not found: "
                            + REPORT_FILE);
        }

        List<FailureContext> failures =
                objectMapper.readValue(
                        file,
                        new TypeReference<List<FailureContext>>() {}
                );

        System.out.println(
                "AI Agent Tool: Total failures retrieved: "
                        + failures.size());

        return failures;
    }
}
