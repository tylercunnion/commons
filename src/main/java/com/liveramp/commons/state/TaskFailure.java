package com.liveramp.commons.state;

public class TaskFailure {

  private final String taskAttemptID;
  private final String error;

  public TaskFailure(String taskAttemptID,
                     String error) {
    this.taskAttemptID = taskAttemptID;
    this.error = error;
  }

  public String getTaskAttemptID() {
    return taskAttemptID;
  }

  public String getError() {
    return error;
  }


  @Override
  public String toString() {
    return "TaskError{" +
        "taskAttemptID=" + taskAttemptID +
        ", error=" + error +
        "}";
  }
}
