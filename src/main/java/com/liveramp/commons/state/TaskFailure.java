package com.liveramp.commons.state;

public class TaskFailure {

  private final String taskAttemptID;
  private final String error;
  private final String hosturl;

  public TaskFailure(String taskAttemptID,
                     String hosturl,
                     String error) {
    this.taskAttemptID = taskAttemptID;
    this.error = error;
    this.hosturl = hosturl;
  }

  public String getTaskAttemptID() {
    return taskAttemptID;
  }

  public String getError() {
    return error;
  }

  public String getHosturl() {
    return hosturl;
  }


  @Override
  public String toString() {
    return "TaskError{" +
        "taskAttemptID=" + taskAttemptID +
        ", error=" + error +
        ", hosturl=" + hosturl +
        "}";
  }
}
