
package AI.Agent.Decision;

public class AgentDecision {

    private String toolName;
    private String reason;
    private String scenarioName;

    public AgentDecision(String toolName, String reason, String scenarioName) {
        this.toolName = toolName;
        this.reason = reason;
        this.scenarioName = scenarioName;
    }

    public String getToolName() {
        return toolName;
    }

    public String getReason() {
        return reason;
    }

    public String getScenarioName() {
        return scenarioName;
    }
}
