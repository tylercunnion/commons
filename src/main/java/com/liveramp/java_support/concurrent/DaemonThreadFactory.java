package com.liveramp.java_support.concurrent;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {
  private final ThreadFactory factory;

  public DaemonThreadFactory() {
    this.factory = null;
  }

  public DaemonThreadFactory(ThreadFactory factory) {
    this.factory = factory;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread thread;
    if (factory == null) {
      thread = new Thread(r);
    } else {
      thread = factory.newThread(r);
    }

    thread.setDaemon(true);

    return thread;
  }
}
