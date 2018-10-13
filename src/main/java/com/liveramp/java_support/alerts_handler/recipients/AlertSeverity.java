package com.liveramp.java_support.alerts_handler.recipients;

public enum AlertSeverity {
  INFO, ONCALL, ERROR;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
