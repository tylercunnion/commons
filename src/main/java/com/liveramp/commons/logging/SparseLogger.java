package com.liveramp.commons.logging;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;

/**
 * Wrapper around a traditional logger not to repeat INFO logs too much.
 *
 * Error and Warning logs should not be logged through this class, since you shouldn't
 * ignore any of those logs.
 * Debug logs should not either, because you shouldn't care if your logs explode in
 * debug mode.
 */
public class SparseLogger {
  public static final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(3);

  private final Logger logger;
  private final Duration minDurationBetweenLogs;

  public SparseLogger(Logger logger, Duration minDurationBetweenLogs) {
    this.logger = logger;
    this.minDurationBetweenLogs = minDurationBetweenLogs;
  }

  public SparseLogger(Logger logger) {
    this(logger, DEFAULT_WAIT_TIME);
  }

  private Instant lastLogTime = Instant.EPOCH;

  public void info(String msg) {
    Instant now = Instant.now();
    if (enoughTimeHasPassed(now)) {
      logger.info(msg);
      lastLogTime = now;
    }
  }

  private boolean enoughTimeHasPassed(Instant now) {
    return now.isAfter(lastLogTime.plus(minDurationBetweenLogs));
  }
}
