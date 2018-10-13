package com.liveramp.java_support.alerts_handler.recipients;

import com.liveramp.java_support.alerts_handler.configs.AlertsHandlerConfig;

public interface AlertRecipient {
  public void addRecipient(RecipientListBuilder recipientListBuilder, AlertsHandlerConfig alertsHandlerConfig, AddRecipientContext context);
}
