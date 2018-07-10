package com.liveramp.commons.test;

public class ExponentialBackoff {

  public static final long DEFAULT_INITIAL_BACKOFF_MS = 16;
  public static final long DEFAULT_MAXIMUM_BACKOFF_MS = 32000;

  private long backoffMs;
  private final long maximumBackoffMs;

  public ExponentialBackoff(long initialBackoffMs, long maximumBackoffMs) {
    this.backoffMs = initialBackoffMs;
    this.maximumBackoffMs = maximumBackoffMs;
  }

  public ExponentialBackoff() {
    this(DEFAULT_INITIAL_BACKOFF_MS, DEFAULT_MAXIMUM_BACKOFF_MS);
  }

  public void backoff() throws InterruptedException {
    Thread.sleep(backoffMs);
    backoffMs <<= 1;
    if (backoffMs > maximumBackoffMs) {
      backoffMs = maximumBackoffMs;
    }
  }

  public long getBackoffMs() {
    return backoffMs;
  }

  public boolean isMaxedOut() {
    return backoffMs >= maximumBackoffMs;
  }
}
