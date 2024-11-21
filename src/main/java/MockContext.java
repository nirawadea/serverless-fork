import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class MockContext implements Context {
    @Override
    public String getAwsRequestId() {
        return "mock-request-id";
    }

    @Override
    public String getLogGroupName() {
        return "mock-log-group";
    }

    @Override
    public String getLogStreamName() {
        return "mock-log-stream";
    }

    @Override
    public String getFunctionName() {
        return "mock-function-name";
    }

    @Override
    public String getFunctionVersion() {
        return "1.0";
    }

    @Override
    public String getInvokedFunctionArn() {
        return "mock-arn";
    }

    @Override
    public CognitoIdentity getIdentity() {
        return null;
    }

    @Override
    public ClientContext getClientContext() {
        return null;
    }

    @Override
    public int getRemainingTimeInMillis() {
        return 300000;
    }

    @Override
    public int getMemoryLimitInMB() {
        return 512;
    }

    @Override
    public LambdaLogger getLogger() {
        return new MockLambdaLogger();
    }
}
