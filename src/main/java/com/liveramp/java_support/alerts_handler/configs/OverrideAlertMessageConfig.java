package com.liveramp.java_support.alerts_handler.configs;

import com.liveramp.java_support.alerts_handler.configs.AlertMessageConfig;

public interface OverrideAlertMessageConfig {
  AlertMessageConfig overrideConfig(AlertMessageConfig alertMessageConfig);
}
