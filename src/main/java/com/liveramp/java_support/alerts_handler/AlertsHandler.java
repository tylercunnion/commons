package com.liveramp.java_support.alerts_handler;

import java.util.List;

import com.liveramp.java_support.alerts_handler.recipients.AlertRecipient;

public interface AlertsHandler {

  public void sendAlert(AlertMessage contents, AlertRecipient recipient, AlertRecipient... ad);

  public void sendAlert(String subject, String body, AlertRecipient recipient, AlertRecipient... additionalRecipients);

  public void sendAlert(String subject, Throwable t, AlertRecipient recipient, AlertRecipient... additionalRecipients);

  public void sendAlert(String subject, String body, Throwable t, AlertRecipient recipient, AlertRecipient... additionalRecipients);


  public RecipientListBuilder resolveRecipients(List<AlertRecipient> recipients);

}
