package com.liveramp.commons.collections.map;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class NestedMultimap<K1, K2, V> {

  Map<K1, Multimap<K2, V>> map = Maps.newHashMap();

  public void put(K1 k1, K2 k2, V value) {
    if (!map.containsKey(k1)) {
      map.put(k1, HashMultimap.<K2, V>create());
    }
    map.get(k1).put(k2, value);
  }

  public Multimap<K2, V> get(K1 k1) {
    if (!map.containsKey(k1)) {
      return HashMultimap.create();
    }
    return map.get(k1);
  }

  public Set<K1> k1Set() {
    return map.keySet();
  }

}
