
package AI.Collector;

import AI.Context.FailureContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class FailureContextReader {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public FailureContext readFailureContext(Path jsonFile)
            throws IOException {

        if (!jsonFile.toFile().exists()) {
            throw new IOException(
                    "Failure context JSON file not found: "
                            + jsonFile
            );
        }

        return objectMapper.readValue(
                jsonFile.toFile(),
                FailureContext.class
        );
    }
}
