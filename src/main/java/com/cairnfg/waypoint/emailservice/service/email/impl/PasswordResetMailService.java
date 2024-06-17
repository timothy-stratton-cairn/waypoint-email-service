package com.cairnfg.waypoint.emailservice.service.email.impl;

import com.cairnfg.waypoint.emailservice.enumeration.MailRequestEnum;

@SuppressWarnings("FieldCanBeLocal")
public class PasswordResetMailService extends BaseMailService {
  private final String SUBJECT = "Forgot Password";
  private final String HTML_BODY = """
      <html>
        <head></head>
        <body>
          <h1>Forgot Password</h1>
          <p>Please reset your password at the following link: <a href="{resetPassword}">Forgot Password</a></p>
        </body>
      </html>
      """;

  public PasswordResetMailService() {
    super(MailRequestEnum.PASSWORD_RESET);
  }

  @Override
  public void sendEmail(MailRequestEnum request) {
    sendSimpleEmail(request.getRecipient(), SUBJECT,
        HTML_BODY.replace("{resetPassword}", request.getLinks().get("resetPasswordLink")));
  }
}
