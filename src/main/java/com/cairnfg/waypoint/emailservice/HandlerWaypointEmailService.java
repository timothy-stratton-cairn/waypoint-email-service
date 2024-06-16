package com.cairnfg.waypoint.emailservice;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

public class HandlerWaypointEmailService implements RequestHandler<SQSEvent, Integer>{

  @Override
  public Integer handleRequest(SQSEvent sqsEvent, Context context) {
    for (SQSMessage msg : sqsEvent.getRecords()) {
      processMessage(msg, context);
    }
    context.getLogger().log("done");
    return null;
  }

  private void processMessage(SQSMessage msg, Context context) {
    try {
      context.getLogger().log("Processed message " + msg.getBody());

      // TODO: Do interesting work based on the new message

    } catch (Exception e) {
      context.getLogger().log("An error occurred");
      throw e;
    }

  }
}

record IntegerRecord(int x, int y, String message) {
}