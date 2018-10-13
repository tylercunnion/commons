package com.liveramp.java_support.alerts_handler.recipients;

import com.google.common.base.Optional;

public class AddRecipientContext {
  private final Optional<AlertSeverity> alertSeverityOverride;

  public AddRecipientContext(Optional<AlertSeverity> alertSeverityOverride) {
    this.alertSeverityOverride = alertSeverityOverride;
  }

  public Optional<AlertSeverity> getAlertSeverityOverride() {
    return alertSeverityOverride;
  }
}
