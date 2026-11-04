package AI.Agent.Decision;

import java.util.Map;

public class AgentFunctionCall {

    private String toolName;
    private Map<String, Object> arguments;
    private String reasoning;

    public AgentFunctionCall(String toolName,
                             Map<String, Object> arguments,
                             String reasoning) {

        this.toolName = toolName;
        this.arguments = arguments;
        this.reasoning = reasoning;
    }

    public String getToolName() {
        return toolName;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public String getReasoning() {
        return reasoning;
    }
}