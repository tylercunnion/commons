package com.liveramp.commons.test;

public class WaitUntil {

  public static void condition(Condition condition) throws InterruptedException {
    ExponentialBackoff exponentialBackoff = new ExponentialBackoff();
    while (!condition.test()) {
      exponentialBackoff.backoff();
    }
  }

  public static boolean orReturn(Condition condition) throws InterruptedException {
    return orReturn(condition, ExponentialBackoff.DEFAULT_MAXIMUM_BACKOFF_MS);
  }

  public static boolean orReturn(Condition condition, long maximumBackoffMs) throws InterruptedException {
    ExponentialBackoff exponentialBackoff = new ExponentialBackoff(ExponentialBackoff.DEFAULT_INITIAL_BACKOFF_MS, maximumBackoffMs);
    while (!condition.test()) {
      if (exponentialBackoff.isMaxedOut()) {
        return false;
      }
      exponentialBackoff.backoff();
    }
    return true;
  }

  public static void orDie(Condition condition) throws InterruptedException {
    orDie(condition, ExponentialBackoff.DEFAULT_MAXIMUM_BACKOFF_MS);
  }

  public static void orDie(Condition condition, long maximumBackoffMs) throws InterruptedException {
    ExponentialBackoff exponentialBackoff = new ExponentialBackoff(ExponentialBackoff.DEFAULT_INITIAL_BACKOFF_MS, maximumBackoffMs);
    while (!condition.test()) {
      if (exponentialBackoff.isMaxedOut()) {
        throw new RuntimeException("Timed out while waiting for condition to test true: " + condition);
      }
      exponentialBackoff.backoff();
    }
  }
}
