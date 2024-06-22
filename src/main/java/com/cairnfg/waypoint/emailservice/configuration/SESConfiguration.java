package com.cairnfg.waypoint.emailservice.configuration;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

public class SESConfiguration {

  public static SesClient getClient() {
    Region region = Region.US_EAST_1;
    return SesClient.builder()
        .credentialsProvider(DefaultCredentialsProvider.create())
        .region(region)
        .httpClient(ApacheHttpClient.builder().build())
        .build();
  }
}
