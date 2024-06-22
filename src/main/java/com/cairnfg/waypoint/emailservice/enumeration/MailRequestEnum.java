package com.cairnfg.waypoint.emailservice.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailRequestEnum {
  ONBOARDING, PASSWORD_RESET, DB_BACKUP;

  private String recipient;
  private List<String> attachments;
  private Map<String, String> links;
  private Map<String, String> parameters;
  private String subject;
  private String body;

  MailRequestEnum() {
  }

  @JsonCreator
  public static MailRequestEnum forValues(@JsonProperty("requestType") String requestType,
      @JsonProperty("recipient") String recipient,
      @JsonProperty("attachments") String[] attachments,
      @JsonProperty("links") Map<String, String> links,
      @JsonProperty("parameters") Map<String, String> parameters,
      @JsonProperty("subject") String subject,
      @JsonProperty("body") String body) {

    MailRequestEnum request = MailRequestEnum.valueOf(requestType.toUpperCase().replace(" ", "_"));

    request.recipient = recipient;
    request.attachments = Arrays.asList(attachments);
    request.links = links;
    request.parameters = parameters;
    request.subject = subject;
    request.body = body;

    return request;
  }
}
