package com.liveramp.java_support.alerts_handler;

import java.util.List;

public interface AlertMessageConfig {
  boolean isAllowHtml();

  List<String> getTags();
}
