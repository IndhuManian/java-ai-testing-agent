public class OpenAIKeyTest {

    public static void main(String[] args) {

        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey != null && !apiKey.isEmpty()) {
            System.out.println("OpenAI API key loaded successfully.");
        } else {
            System.out.println("OpenAI API key not found.");
        }
    }
}