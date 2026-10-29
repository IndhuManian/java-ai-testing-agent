
package AI.Collector;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class FailureReportReader {

    private static final String REPORT_DIRECTORY =
            "target/ai-failure-reports";

    public String readLatestFailure() throws IOException {

        Path directory = Paths.get(REPORT_DIRECTORY);

        if (!Files.exists(directory)) {
            throw new IOException("Failure report folder not found!");
        }

        try (Stream<Path> files = Files.list(directory)) {

            Optional<Path> latestFile = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .max(Comparator.comparingLong(
                            path -> path.toFile().lastModified()));

            if (!latestFile.isPresent()) {
                throw new IOException("No failure reports found!");
            }

            return new String(
                    Files.readAllBytes(latestFile.get()),
                    StandardCharsets.UTF_8);
        }
    }
}
