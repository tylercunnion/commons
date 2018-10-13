package com.liveramp.java_support.alerts_handler;

public interface AlertMessage {
  String getSubject(AlertMessageConfig alertMessageConfig);

  String getMessage(AlertMessageConfig alertMessageConfig);

  OverrideAlertMessageConfig getConfigOverrides();
}
