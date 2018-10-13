package com.liveramp.java_support.alerts_handler.configs;

import java.util.List;

import com.google.common.collect.ImmutableList;

import com.liveramp.java_support.alerts_handler.AlertMessageConfig;

public class DefaultAlertMessageConfig implements AlertMessageConfig {
  private final boolean isAllowHtml;
  private final List<String> tags;

  public DefaultAlertMessageConfig(boolean isAllowHtml, List<String> tags) {
    this.isAllowHtml = isAllowHtml;
    this.tags = tags;
  }

  @Override
  public boolean isAllowHtml() {
    return isAllowHtml;
  }

  @Override
  public List<String> getTags() {
    return ImmutableList.copyOf(tags);
  }
}
