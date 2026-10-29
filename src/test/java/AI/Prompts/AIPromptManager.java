
package AI.Prompts;

public class AIPromptManager {

    public static String createFailurePrompt(String failureDetails) {

        return String.join("\n",
                "You are an expert QA automation engineer.",
                "Analyze the following automation test failure.",
                "",
                "Failure Details:",
                failureDetails,
                "",
                "Provide:",
                "1. Failure category",
                "2. Probable root cause",
                "3. Suggested solution",
                "4. Whether it appears to be an application or automation issue."
        );
    }
}
