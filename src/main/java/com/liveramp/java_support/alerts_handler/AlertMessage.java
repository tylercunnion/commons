package com.liveramp.java_support.alerts_handler;

import com.liveramp.java_support.alerts_handler.configs.AlertMessageConfig;
import com.liveramp.java_support.alerts_handler.configs.OverrideAlertMessageConfig;

public interface AlertMessage {
  String getSubject(AlertMessageConfig alertMessageConfig);

  String getMessage(AlertMessageConfig alertMessageConfig);

  OverrideAlertMessageConfig getConfigOverrides();
}
