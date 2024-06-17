package com.cairnfg.waypoint.emailservice;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;
import com.cairnfg.waypoint.emailservice.enumeration.MailRequestEnum;
import com.cairnfg.waypoint.emailservice.service.email.IMailService;
import com.cairnfg.waypoint.emailservice.service.email.impl.BaseMailService;
import com.cairnfg.waypoint.emailservice.service.email.impl.OnboardingMailService;
import com.cairnfg.waypoint.emailservice.service.email.impl.PasswordResetMailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public class HandlerWaypointEmailService implements RequestHandler<SQSEvent, Void>{

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final IMailService mailService;

  static {
    mailService = BaseMailService.setupProcessorMailChain(
        new OnboardingMailService(),
        new PasswordResetMailService()
    );
  }

  @Override
  public Void handleRequest(SQSEvent sqsEvent, Context context) {
    mailService.setLogger(context.getLogger());

    try {
      for (SQSMessage msg : sqsEvent.getRecords()) {
        processMessage(msg, context);
      }
      context.getLogger().log("done");
      return null;
    } catch (Exception e) {
      context.getLogger().log("An error occurred: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException(e);
    }
  }

  private void processMessage(SQSMessage msg, Context context) throws JsonProcessingException {
    try {
      context.getLogger().log("Processed message " + msg.getBody());

      MailRequestEnum request = mapper.readValue(msg.getBody(), MailRequestEnum.class);

      mailService.handle(request);

    } catch (Exception e) {
      context.getLogger().log("An error occurred", LogLevel.ERROR);
      throw e;
    }

  }
}