package com.cairnfg.waypoint.emailservice.service.email.impl;

import com.cairnfg.waypoint.emailservice.enumeration.MailRequestEnum;

@SuppressWarnings("FieldCanBeLocal")
public class DBBackupMailService extends BaseMailService {

  private final String SUBJECT = "Waypoints Database Backup";
  private final String HTML_BODY = """
      <html>
        <head></head>
        <body>
          <h1>Database Backup for <code>{databaseName}</code></h1>
          <p>Please download the database backup at the following link: <a href="{downloadDbBackupLink}">Database Backup</a></p>
        </body>
      </html>
      """;

  public DBBackupMailService() {
    super(MailRequestEnum.DB_BACKUP);
  }

  @Override
  public void sendEmail(MailRequestEnum request) {
    sendSimpleEmail(request.getRecipient(),
        SUBJECT,
        HTML_BODY
            .replace("{databaseName}", request.getParameters().get("databaseName"))
            .replace("{downloadDbBackupLink}", request.getLinks().get("downloadDbBackupLink")));
  }
}
