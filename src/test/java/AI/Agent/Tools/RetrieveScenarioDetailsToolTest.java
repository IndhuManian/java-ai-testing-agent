
package AI.Agent.Tools;

import AI.Context.FailureContext;
import java.io.IOException;
import java.util.List;

public class RetrieveScenarioDetailsToolTest {

    public static void main(String[] args) throws IOException {

        ReadAllFailuresTool readAllFailuresTool = new ReadAllFailuresTool();

        List<FailureContext> failures = readAllFailuresTool.execute();

        RetrieveScenarioDetailsTool retrieveTool =
                new RetrieveScenarioDetailsTool();

        String result = retrieveTool.execute(
                failures,
                "TC02_Verify update pincode with already login user"
        );

        System.out.println(result);
    }
}
