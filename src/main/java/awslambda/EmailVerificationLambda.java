package awslambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.util.Map;

public class EmailVerificationLambda implements RequestHandler<SNSEvent, String> {

    private static final long EXPIRATION_TIME = 2 * 60 * 1000; // 2 minutes in milliseconds
    private final String sendgridApiKey = "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";

    @Override
    public String handleRequest(SNSEvent event, Context context) {
        context.getLogger().log("Lambda function invoked with SNS event");

        // Log environment variables for debugging (remove in production)
        context.getLogger().log("sendgridApiKey: " + sendgridApiKey);

        // Deserialize the SNS message
        SNSEvent.SNSRecord record = event.getRecords().get(0);
        String message = record.getSNS().getMessage();
        context.getLogger().log("Received SNS message: " + message);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, String> payload = objectMapper.readValue(message, Map.class);
            String email = payload.get("email");
            String userId = payload.get("userId"); // Extract any additional fields as necessary

            // Generate a unique token and verification link
//            String tokenId = UUID.randomUUID().toString();
            String verificationLink = generateVerificationLink(email, userId);
            context.getLogger().log("Generated verification link: " + verificationLink);

            // Send verification email
            sendEmail(email, verificationLink, context);

            return "Email sent successfully!";
        } catch (Exception e) {
            context.getLogger().log("Error processing message: " + e.getMessage());
            e.printStackTrace();
            return "Error processing message: " + e.getMessage();
        }
    }

    private String generateVerificationLink(String email, String userId) {
        long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
        return "http://dev.aayukacloudnative.me/v1/user/verify?token=" + userId + "&email=" + email + "&expires=" + expirationTime;
    }

    private void sendEmail(String email, String verificationLink, Context context) throws Exception {
        Email from = new Email("nirawade.a@northeastern.edu");
        String subject = "Email Verification";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Please verify your email by clicking the link: " + verificationLink);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        context.getLogger().log("Sending email through SendGrid...");
        Response response = sg.api(request);
        context.getLogger().log("SendGrid response: Status code - " + response.getStatusCode());

        if (response.getStatusCode() != 202) {
            context.getLogger().log("Failed to send email: " + response.getBody());
            throw new Exception("Failed to send email: " + response.getBody());
        }
    }
}

