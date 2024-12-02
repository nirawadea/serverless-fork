import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class MockLambdaLogger implements LambdaLogger {
    @Override
    public void log(String message) {
        System.out.println("Lambda Log: " + message);
    }

    @Override
    public void log(byte[] message) {
        System.out.println("Lambda Log: " + new String(message));
    }
}
