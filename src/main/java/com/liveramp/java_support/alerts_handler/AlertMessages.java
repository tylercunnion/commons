package com.liveramp.java_support.alerts_handler;

import java.util.List;

import com.google.common.base.Optional;

import com.liveramp.java_support.alerts_handler.configs.DefaultOverrideAlertMessageConfig;
import com.liveramp.java_support.alerts_handler.configs.OverrideAlertMessageConfig;

public class AlertMessages {
  public static class Builder {
    private final String subject;

    private Optional<String> body;
    private Optional<Throwable> throwable;
    private DefaultOverrideAlertMessageConfig.Builder configOverrideBuilder;

    private Builder(String subject) {
      this.subject = subject;
      this.configOverrideBuilder = new DefaultOverrideAlertMessageConfig.Builder();
      this.body = Optional.absent();
      this.throwable = Optional.absent();
    }

    public Builder setBody(String body) {
      this.body = Optional.of(body);
      return this;
    }

    public Builder setThrowable(Throwable throwable) {
      this.throwable = Optional.of(throwable);
      return this;
    }

    public Builder overrideIsHtml(boolean isHtml) {
      configOverrideBuilder.setIsAllowHtml(isHtml);
      return this;
    }

    public Builder overrideDefaultTags(List<String> tags) {
      configOverrideBuilder.setTagsToSet(tags);
      return this;
    }

    public Builder overrideDefaultTags(String firstTag, String... otherTags) {
      configOverrideBuilder.setTagsToSet(AlertHelpers.atLeastOneToList(firstTag, otherTags));
      return this;
    }

    public Builder addToDefaultTags(String firstTag, String... otherTags) {
      configOverrideBuilder.addTagsToAdd(AlertHelpers.atLeastOneToList(firstTag, otherTags));
      return this;
    }

    public Builder addToDefaultTags(List<String> tags) {
      configOverrideBuilder.addTagsToAdd(tags);
      return this;
    }

    public AlertMessage build() {
      return AlertMessages.build(subject, body, throwable, Optional.of(configOverrideBuilder.build()));
    }
  }

  public static AlertMessage build(String subject, String body) {
    return build(subject, Optional.of(body), Optional.absent(), Optional.absent());
  }

  public static AlertMessage build(String subject, Throwable t) {
    return build(subject, Optional.absent(), Optional.fromNullable(t), Optional.absent());
  }

  public static Builder builder(String subject) {
    return new Builder(subject);
  }

  public static AlertMessage build(final String subject, final Optional<String> body, final Optional<Throwable> throwable, final Optional<OverrideAlertMessageConfig> alertMessageConfig) {
    return new EmailAlertMessage(subject, body, throwable, alertMessageConfig);
  }
}