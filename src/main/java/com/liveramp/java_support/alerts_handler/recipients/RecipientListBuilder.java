package com.liveramp.java_support.alerts_handler.recipients;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class RecipientListBuilder {
  private List<String> emailRecipients = Lists.newArrayList();

  public RecipientListBuilder() { }

  public void addEmailRecipient(String recipient) {
    emailRecipients.add(recipient);
  }

  public List<String> getEmailRecipients() {
    return ImmutableList.copyOf(emailRecipients);
  }
}
