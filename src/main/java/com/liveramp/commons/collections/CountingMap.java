package com.liveramp.commons.collections;

import java.util.Map;

import com.google.common.collect.Maps;

public class CountingMap<T> {
  private final Map<T, Long> map;

  public CountingMap(){
    this(Maps.<T, Long>newHashMap());
  }

  public CountingMap(Map<T, Long> backing){
    this.map = backing;
  }

  public void increment(T key, Long amount){
    if(!map.containsKey(key)){
      map.put(key, 0L);
    }
    map.put(key, map.get(key)+amount);
  }

  public Map<T, Long> get(){
    return map;
  }

  public void incrementAll(Map<T, Long> map){
    for (Map.Entry<T, Long> entry : map.entrySet()) {
      increment(entry.getKey(), entry.getValue());
    }
  }

}
