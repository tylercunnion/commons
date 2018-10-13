package com.liveramp.java_support.alerts_handler.recipients;

import com.liveramp.java_support.alerts_handler.AlertsHandlerConfig;
import com.liveramp.java_support.alerts_handler.RecipientListBuilder;
import com.liveramp.java_support.alerts_handler.recipients.AddRecipientContext;

public interface AlertRecipient {
  public void addRecipient(RecipientListBuilder recipientListBuilder, AlertsHandlerConfig alertsHandlerConfig, AddRecipientContext context);
}
