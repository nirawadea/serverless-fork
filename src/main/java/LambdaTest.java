import awslambda.EmailVerificationLambda;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LambdaTest {
    public static void main(String[] args) throws Exception {
        // Initialize the Lambda function
        EmailVerificationLambda lambda = new EmailVerificationLambda();

        // Load and parse the SNS event
        ObjectMapper objectMapper = new ObjectMapper();
        String snsEventJson = new String(Files.readAllBytes(
                Paths.get(LambdaTest.class.getClassLoader().getResource("sns-event.json").toURI())
        ));
        SNSEvent snsEvent = objectMapper.readValue(snsEventJson, SNSEvent.class);

        // Create a mock context
        Context mockContext = new MockContext();

        // Invoke the Lambda function
        String result = lambda.handleRequest(snsEvent, mockContext);

        // Print the result
        System.out.println("Lambda result: " + result);
    }
}
