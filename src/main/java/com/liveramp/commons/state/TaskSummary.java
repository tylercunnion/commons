package com.liveramp.commons.state;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class TaskSummary {

  private final Long avgMapDuration;
  private final Long medianMapDuration;
  private final Long maxMapDuration;
  private final Long minMapDuration;
  private final Long mapDurationStDev;

  private final Long avgReduceDuration;
  private final Long medianReduceDuration;
  private final Long maxReduceDuration;
  private final Long minReduceDuration;
  private final Long reduceDurationStDev;

  private final Integer numTasksSampled;
  private final Integer numTasksFailed;

  private final List<TaskFailure> taskFailures;

  public TaskSummary(Long avgMapDuration,
                     Long medianMapDuration,
                     Long maxMapDuration,
                     Long minMapDuration,
                     Long mapDurationStDev,
                     Long avgReduceDuration,
                     Long medianReduceDuration,
                     Long maxReduceDuration,
                     Long minReduceDuration,
                     Long reduceDurationStDev,
                     Integer numTasksSampled,
                     Integer numTasksFailed,
                     List<TaskFailure> taskFailures) {
    this.avgMapDuration = avgMapDuration;
    this.medianMapDuration = medianMapDuration;
    this.maxMapDuration = maxMapDuration;
    this.minMapDuration = minMapDuration;
    this.mapDurationStDev = mapDurationStDev;
    this.avgReduceDuration = avgReduceDuration;
    this.medianReduceDuration = medianReduceDuration;
    this.maxReduceDuration = maxReduceDuration;
    this.minReduceDuration = minReduceDuration;
    this.reduceDurationStDev = reduceDurationStDev;
    this.numTasksSampled = numTasksSampled;
    this.numTasksFailed = numTasksFailed;
    this.taskFailures = taskFailures;
  }

  public TaskSummary(Long avgMapDuration,
                     Long medianMapDuration,
                     Long maxMapDuration,
                     Long minMapDuration,
                     Long mapDurationStDev,
                     Long avgReduceDuration,
                     Long medianReduceDuration,
                     Long maxReduceDuration,
                     Long minReduceDuration,
                     Long reduceDurationStDev,
                     List<TaskFailure> taskFailures) {

    this(avgMapDuration,
        medianMapDuration,
        maxMapDuration,
        minMapDuration,
        mapDurationStDev,
        avgReduceDuration,
        medianReduceDuration,
        maxReduceDuration,
        minReduceDuration,
        reduceDurationStDev,
        null,
        null,
        taskFailures);
  }

  public TaskSummary(Long avgMapDuration,
                     Long medianMapDuration,
                     Long maxMapDuration,
                     Long minMapDuration,
                     Long mapDurationStDev,
                     Long avgReduceDuration,
                     Long medianReduceDuration,
                     Long maxReduceDuration,
                     Long minReduceDuration,
                     Long reduceDurationStDev) {

    this(avgMapDuration,
        medianMapDuration,
        maxMapDuration,
        minMapDuration,
        mapDurationStDev,
        avgReduceDuration,
        medianReduceDuration,
        maxReduceDuration,
        minReduceDuration,
        reduceDurationStDev,
        new ArrayList<TaskFailure>());
  }

  public Long getAvgMapDuration() {
    return avgMapDuration;
  }

  public Long getMedianMapDuration() {
    return medianMapDuration;
  }

  public Long getMaxMapDuration() {
    return maxMapDuration;
  }

  public Long getMinMapDuration() {
    return minMapDuration;
  }

  public Long getMapDurationStDev() {
    return mapDurationStDev;
  }

  public Long getAvgReduceDuration() {
    return avgReduceDuration;
  }

  public Long getMedianReduceDuration() {
    return medianReduceDuration;
  }

  public Long getMaxReduceDuration() {
    return maxReduceDuration;
  }

  public Long getMinReduceDuration() {
    return minReduceDuration;
  }

  public Long getReduceDurationStDev() {
    return reduceDurationStDev;
  }

  public Integer getNumTasksSampled() {
    return numTasksSampled;
  }

  public Integer getNumFailed() {
    return numTasksFailed;
  }

  public List<TaskFailure> getTaskFailures() {
    return taskFailures;
  }

  @Override
  public String toString() {
    return "TaskSummary{" +
        "avgMapDuration=" + avgMapDuration +
        ", medianMapDuration=" + medianMapDuration +
        ", maxMapDuration=" + maxMapDuration +
        ", minMapDuration=" + minMapDuration +
        ", mapDurationStDev=" + mapDurationStDev +
        ", avgReduceDuration=" + avgReduceDuration +
        ", medianReduceDuration=" + medianReduceDuration +
        ", maxReduceDuration=" + maxReduceDuration +
        ", minReduceDuration=" + minReduceDuration +
        ", reduceDurationStDev=" + reduceDurationStDev +
        ", numTasksSampled=" + safeNullString(numTasksSampled) +
        ", numTasksFailed=" + safeNullString(numTasksFailed) +
        ", taskFailures={" + Joiner.on(",").skipNulls().join(taskFailures) +
        "}}";
  }

  private String safeNullString(Object o) {
    if (o == null) {
      return "null";
    } else {
      return o.toString();
    }
  }
}
