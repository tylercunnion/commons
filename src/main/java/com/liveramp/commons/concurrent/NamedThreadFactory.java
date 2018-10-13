package com.liveramp.commons.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
  private String name;
  private AtomicInteger id;

  public NamedThreadFactory(String name) {
    this.name = name;
    this.id = new AtomicInteger(0);
  }

  @Override
  public Thread newThread(Runnable r) {
    return new Thread(r, String.format("%s-%d", name, id.getAndIncrement()));
  }

}
