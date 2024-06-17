package com.cairnfg.waypoint.emailservice.service.email;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.cairnfg.waypoint.emailservice.enumeration.MailRequestEnum;

public interface IMailService {
  boolean canHandle(MailRequestEnum requestType);
  void handle(MailRequestEnum requestType);
  void sendEmail(MailRequestEnum requestType);

  void setLogger(LambdaLogger logger);
}
