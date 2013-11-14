package com.liveramp.commons.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TreeIntervalMap<V> implements IntervalMap<V> {

  //  omit max internally since it is already the key
  private class InternalInterval {
    private final long min;
    private final V value;
    private InternalInterval(long min, V value) {
      this.min = min;
      this.value = value;
    }

    @Override
    public String toString() {
      return "InternalInterval{" +
          "min=" + min +
          ", value=" + value +
          '}';
    }
  }

  private TreeMap<Long, InternalInterval> maxToValue = new TreeMap<Long, InternalInterval>();

  public static <V> TreeIntervalMap<V> create(){
    return new TreeIntervalMap<V>();
  }

  @Override
  public Iterator<Interval<V>> iterator(){
    final Iterator<Map.Entry<Long, InternalInterval>> internalIter = maxToValue.entrySet().iterator();

    return new Iterator<Interval<V>>() {
      @Override
      public boolean hasNext() {
        return internalIter.hasNext();
      }

      @Override
      public Interval<V> next() {
        Map.Entry<Long, InternalInterval> internal = internalIter.next();
        return new Interval<V>(internal.getValue().min, internal.getKey(), internal.getValue().value);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  private void failInsert(long min, long max, V value){
    throw new RuntimeException("Trying to insert value: "+value+" from "+min+" to "+max+" but values already exists for that range!");
  }

  @Override
  public void put(long key, V value){
    put(key, key, value);
  }

  @Override
  public void put(long min, long max, V value){

    if(min > max){
      throw new RuntimeException("cannot insert interval with min "+min+" and max "+max+"!");
    }

    //  check consistency
    Map<Long, InternalInterval> betweenMinMax = maxToValue.subMap(min, true, max, true);
    if(!betweenMinMax.isEmpty()){
      failInsert(min, max, value);
    }

    Map.Entry<Long, InternalInterval> existingEntry = getEntry(max);
    if(existingEntry != null){
      failInsert(min, max, value);
    }

    // can we combine it with an existing range?  try both upper and lower

    long insertMin = min;
    long insertMax = max;

    Map.Entry<Long, InternalInterval> lowerNeighbbor = getEntry(min-1);
    if(lowerNeighbbor != null && lowerNeighbbor.getValue().value.equals(value)){
      maxToValue.remove(lowerNeighbbor.getKey());
      insertMin = lowerNeighbbor.getValue().min;
    }

    Map.Entry<Long, InternalInterval> upperNeighbbor = getEntry(max+1);
    if(upperNeighbbor != null && upperNeighbbor.getValue().value.equals(value)){
      maxToValue.remove(upperNeighbbor.getKey());
      insertMax = upperNeighbbor.getKey();
    }

    maxToValue.put(insertMax, new InternalInterval(insertMin, value));
  }

  @Override
  public V get(long value){
    Map.Entry<Long, InternalInterval> entry = getEntry(value);
    if(entry != null){
      return entry.getValue().value;
    }

    return null;
  }

  protected Map.Entry<Long, InternalInterval> getEntry(long value){
    Map.Entry<Long, InternalInterval> rightAbove = maxToValue.ceilingEntry(value);
    if(rightAbove != null){
      if(rightAbove.getValue().min <= value){
        return rightAbove;
      }
    }
    return null;
  }
}
