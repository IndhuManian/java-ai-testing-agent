
package AI.Client;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class AIClient {

    private final OpenAIClient client;

    public AIClient() {
        client = OpenAIOkHttpClient.fromEnv();
    }

    public String analyzeFailure(String failureDetails) {

        ResponseCreateParams params =
                ResponseCreateParams.builder()
                        .model(ChatModel.GPT_5_2)
                        .input(failureDetails)
                        .build();

        Response response = client.responses().create(params);

        return response.output().toString();
    }
}
