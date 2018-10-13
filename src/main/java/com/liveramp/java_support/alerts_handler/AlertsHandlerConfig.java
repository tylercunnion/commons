package com.liveramp.java_support.alerts_handler;

import com.liveramp.java_support.alerts_handler.recipients.AlertRecipient;

public interface AlertsHandlerConfig {
  String getFromAddress();

  AlertRecipient getEngineeringRecipient();

  AlertMessageConfig getDefaultMessageConfig();
}
