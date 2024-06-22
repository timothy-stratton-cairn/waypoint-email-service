package com.cairnfg.waypoint.emailservice.service.email.impl;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.cairnfg.waypoint.emailservice.configuration.SESConfiguration;
import com.cairnfg.waypoint.emailservice.enumeration.MailRequestEnum;
import com.cairnfg.waypoint.emailservice.service.email.IMailService;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

public abstract class BaseMailService implements IMailService {

  protected static final SesClient sesClient = SESConfiguration.getClient();
  protected static final String sender = "tim@cairnfg.com";
  protected static LambdaLogger logger;
  private final MailRequestEnum consumesRequestType;
  private IMailService next;

  public BaseMailService(final MailRequestEnum consumesRequestType) {
    this.consumesRequestType = consumesRequestType;
  }

  public static BaseMailService setupProcessorMailChain(BaseMailService firstLink,
      BaseMailService... chain) {
    BaseMailService head = firstLink;

    for (BaseMailService next : chain) {
      head.next = next;
      head = next;
    }

    return firstLink;
  }

  @Override
  public boolean canHandle(MailRequestEnum requestType) {
    return requestType == this.consumesRequestType;
  }

  @Override
  public void handle(MailRequestEnum requestType) {
    if (canHandle(requestType)) {
      logger.log("Handling request: " + requestType.name());
      this.sendEmail(requestType);
    } else {
      this.next.handle(requestType);
    }
  }

  protected void sendSimpleEmail(String recipient, String emailSubject, String emailBody) {
    Destination destination = Destination.builder()
        .toAddresses(recipient)
        .build();

    Content content = Content.builder()
        .data(emailBody)
        .build();

    Content sub = Content.builder()
        .data(emailSubject)
        .build();

    Body body = Body.builder()
        .html(content)
        .build();

    Message msg = Message.builder()
        .subject(sub)
        .body(body)
        .build();

    SendEmailRequest emailRequest = SendEmailRequest.builder()
        .destination(destination)
        .message(msg)
        .source(sender)
        .build();

    try {
      System.out.println(
          "Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
      sesClient.sendEmail(emailRequest);

    } catch (SesException e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
  }

  @Override
  public void setLogger(LambdaLogger logger) {
    BaseMailService.logger = logger;
  }

}
