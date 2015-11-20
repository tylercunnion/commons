package com.liveramp.commons.collections.map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class MultimapBuilder<K, V> {

  private Multimap<K, V> map;

  public MultimapBuilder(Multimap<K, V> map) {
    this.map = map;
  }

  public MultimapBuilder() {
    this.map = HashMultimap.create();
  }

  public MultimapBuilder<K, V> put(K key, V value) {
    map.put(key, value);
    return this;
  }

  public MultimapBuilder<K, V> putAll(Multimap<? extends K, ? extends V> other) {
    map.putAll(other);
    return this;
  }

  public MultimapBuilder<K, V> putAll(K key, Iterable<V> values){
    map.putAll(key, values);
    return this;
  }

  public Multimap<K, V> get() {
    return map;
  }

  public Multimap<K, V> asUnmodifiableMap() {
    return Multimaps.unmodifiableMultimap(map);
  }

  public static <K, V> MultimapBuilder<K, V> of(K key, V value) {
    return new MultimapBuilder<K, V>().put(key, value);
  }
}
