
package AI.Collector;

import AI.Context.FailureContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FailureContextAggregator {

    private final FailureContextReader reader =
            new FailureContextReader();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public void aggregateFailures() throws IOException {

        Path directory = Paths.get("target/ai-failure-reports");

        List<FailureContext> failures = new ArrayList<>();

        if (!Files.exists(directory)) {
            System.out.println("Failure report directory not found.");
            return;
        }

        try (Stream<Path> files = Files.list(directory)) {

            files.filter(path ->
                            path.toString().endsWith(".json")
                                    && !path.getFileName().toString()
                                    .equals("all-failures.json"))
                    .forEach(path -> {
                        try {
                            FailureContext context =
                                    reader.readFailureContext(path);

                            failures.add(context);

                        } catch (IOException e) {
                            System.out.println(
                                    "Unable to read: " + path);
                            throw new RuntimeException(e);
                        }
                    });
        }

        Path outputFile = directory.resolve("all-failures.json");

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(outputFile.toFile(), failures);

        System.out.println(
                "Total failures aggregated: " + failures.size());

        System.out.println(
                "Consolidated report generated: " + outputFile);
    }
}
