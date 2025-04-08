# 📧 EmailHandler - AWS Lambda Function with SendGrid Integration

The **EmailHandler** class is an AWS Lambda function written in Java that processes incoming Amazon SNS (Simple Notification Service) events and sends email notifications using the **SendGrid API**. The function uses environment variables to configure the SendGrid API key and sender domain.

---

## 🧩 Functionality

The Lambda function listens for **SNS events**, extracts the message payload, deserializes it into an `EmailRequest` object, and sends an email using **SendGrid**.

---

## 🔑 Key Components

- **SendGrid Java Library**: Used to send transactional emails via the SendGrid API.  
- **SNS Event Handling**: Processes records from SNS events and logs incoming messages.  
- **ObjectMapper**: Deserializes JSON message from SNS into an `EmailRequest` object.  
- **Environment Variables**:
  - `SENDGRID_API_KEY`: API key for authenticating with SendGrid.
  - `EMAIL_FROM`: Sender email address configured and verified in SendGrid.
  - `SENDGRID_DOMAIN_NAME`:The domain name registered with SendGrid

---

## ✅ Prerequisites

Ensure the following **environment variables** are set in your AWS Lambda function configuration:

- `SENDGRID_DOMAIN_NAME`
- `SENDGRID_API_KEY`

These are required for the Lambda to authenticate and send emails through SendGrid.

---

## 🛠️ Installation & Deployment

### Clone the repository

```bash
git clone https://github.com/Shreyas-Kothari/serverless.git](https://github.com/nirawadea/serverless-fork.git
cd serverless-fork/ServerlessEmailLambda
