package AI.Agent.Tools;
public class ReadFailureReportToolTest {

    public static void main(String[] args) {

        ReadFailureReportTool tool = new ReadFailureReportTool();

        try {
            String result = tool.execute();

            System.out.println("\nRetrieved Failure Report:");
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

