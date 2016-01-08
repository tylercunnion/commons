package com.liveramp.commons.state;

public class LaunchedJob {

  private final String jobId;
  private final String jobName;
  private final String trackingURL;

  public LaunchedJob(String jobId, String jobName, String trackingURL) {
    this.jobId = jobId;
    this.jobName = jobName;
    this.trackingURL = trackingURL;
  }

  public String getJobId() {
    return jobId;
  }

  public String getJobName() {
    return jobName;
  }

  public String getTrackingURL() {
    return trackingURL;
  }
}
