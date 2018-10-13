package com.liveramp.java_support.alerts_handler.configs;

import com.liveramp.java_support.alerts_handler.configs.AlertMessageConfig;
import com.liveramp.java_support.alerts_handler.recipients.AlertRecipient;

public interface AlertsHandlerConfig {
  String getFromAddress();

  AlertRecipient getEngineeringRecipient();

  AlertMessageConfig getDefaultMessageConfig();
}
