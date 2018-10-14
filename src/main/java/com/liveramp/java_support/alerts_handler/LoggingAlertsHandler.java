package com.liveramp.java_support.alerts_handler;

import java.util.Collections;
import java.util.List;

import org.codehaus.plexus.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liveramp.java_support.alerts_handler.configs.AlertMessageConfig;
import com.liveramp.java_support.alerts_handler.configs.DefaultAlertMessageConfig;
import com.liveramp.java_support.alerts_handler.recipients.AlertRecipient;
import com.liveramp.java_support.alerts_handler.recipients.RecipientListBuilder;

public class LoggingAlertsHandler implements AlertsHandler {
  private static final Logger LOG = LoggerFactory.getLogger(LoggingAlertsHandler.class);

  @Override
  public void sendAlert(AlertMessage contents, AlertRecipient recipient, AlertRecipient... additionalRecipients) {
    final AlertMessageConfig config = contents.getConfigOverrides().overrideConfig(new DefaultAlertMessageConfig(false, Collections.<String>emptyList()));
    sendAlert(contents.getSubject(config), contents.getMessage(config), recipient, additionalRecipients);
  }

  @Override
  public void sendAlert(String subject, String body, AlertRecipient recipient, AlertRecipient... additionalRecipients) {
    logAlert(subject, body);
  }

  @Override
  public void sendAlert(String subject, Throwable t, AlertRecipient recipient, AlertRecipient... additionalRecipients) {
    sendAlert(subject, ExceptionUtils.getFullStackTrace(t), recipient, additionalRecipients);
  }

  @Override
  public void sendAlert(String subject, String body, Throwable t, AlertRecipient recipient, AlertRecipient... additionalRecipients) {
    sendAlert(subject, body + "\n\n" + ExceptionUtils.getFullStackTrace(t), recipient, additionalRecipients);
  }

  @Override
  public RecipientListBuilder resolveRecipients(List<AlertRecipient> recipients) {
    return new RecipientListBuilder();
  }

  private void logAlert(String subject, String body) {

    LOG.info("Alert:");
    LOG.info("Subject: " + subject);
    LOG.info("Body: " + body);

  }
}
