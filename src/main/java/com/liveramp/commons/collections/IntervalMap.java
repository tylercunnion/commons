package com.liveramp.commons.collections;

import java.util.Iterator;

public interface IntervalMap<V> {

  public void put(long key, V value);

  public void put(long min, long max, V value);

  public V get(long value);

  public Iterator<Interval<V>> iterator();

  public static class Interval<V> {

    private final long min;
    private final long max;
    private final V value;

    public Interval(long min, long max, V value) {
      this.min = min;
      this.max = max;
      this.value = value;
    }

    @Override
    public String toString() {
      return "Interval{" +
          "min=" + min +
          ", max=" + max +
          ", value=" + value +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Interval)) return false;

      Interval interval = (Interval) o;

      if (max != interval.max) return false;
      if (min != interval.min) return false;
      if (value != null ? !value.equals(interval.value) : interval.value != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = (int) (min ^ (min >>> 32));
      result = 31 * result + (int) (max ^ (max >>> 32));
      result = 31 * result + (value != null ? value.hashCode() : 0);
      return result;
    }
  }
}
