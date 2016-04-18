package com.liveramp.commons.state;

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
        '}';
  }
}
