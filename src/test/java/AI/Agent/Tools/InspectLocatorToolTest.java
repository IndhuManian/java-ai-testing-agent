
package AI.Agent.Tools;

import AI.Context.FailureContext;

import java.util.List;

public class InspectLocatorToolTest {

    public static void main(String[] args) throws Exception {

        ReadAllFailuresTool reader =
                new ReadAllFailuresTool();

        List<FailureContext> failures = reader.execute();

        InspectLocatorTool tool =
                new InspectLocatorTool();

        String result = tool.execute(
                failures,
                "TC02_Verify update pincode with already login user"
        );

        System.out.println(result);
    }
}
