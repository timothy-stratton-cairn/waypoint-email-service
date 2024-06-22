package com.cairnfg.waypoint.emailservice.service.email.impl;

import com.cairnfg.waypoint.emailservice.enumeration.MailRequestEnum;

@SuppressWarnings("FieldCanBeLocal")
public class OnboardingMailService extends BaseMailService {

  private final String SUBJECT = "Welcome to Waypoints";
  private final String HTML_BODY = """
      <html>
        <head></head>
        <body>
          <h1>Welcome to Waypoints</h1>
          <p>Please setup your account at the following link: <a href="{accountSetupLink}">Setup Account</a></p>
        </body>
      </html>
      """;

  public OnboardingMailService() {
    super(MailRequestEnum.ONBOARDING);
  }

  @Override
  public void sendEmail(MailRequestEnum request) {
    sendSimpleEmail(request.getRecipient(), SUBJECT,
        HTML_BODY.replace("{accountSetupLink}", request.getLinks().get("accountSetupLink")));
  }
}
