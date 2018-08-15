package com.liveramp.commons.collections.nested_map;

import java.util.*;
import java.io.Serializable;

import com.google.common.collect.Lists;

public class TwoNestedMap<K1, K2, V> implements Iterable<TwoNestedMap.Entry<K1, K2, V>>, Serializable {
  protected final Map<K1, Map<K2, V>> data = new HashMap<K1, Map<K2, V>>();
  private final com.google.common.base.Supplier<V> defaultValueSupplier;

  public TwoNestedMap(com.google.common.base.Supplier<V> defaultValueSupplier) {
    this.defaultValueSupplier = defaultValueSupplier;
  }

  /**
  * @deprecated stores instance, so every default value modified is the same
  */
  @Deprecated
  public TwoNestedMap(V defaultValue) {
    this(com.google.common.base.Suppliers.ofInstance(defaultValue));
  }

  public TwoNestedMap() {
    this((V)null);
  }

  public TwoNestedMap(TwoNestedMap<K1, K2, V> other){
    this.defaultValueSupplier = other.defaultValueSupplier;
    this.putAll(other);
  }
  public Set<K1> key1Set() {
    if(data.keySet() != null) {
      return data.keySet();
    }
    else return Collections.emptySet();
  }
  public Set<K2> key2Set(K1 k1) {
    if(data.get(k1) != null) {
      return data.get(k1).keySet();
    } else {
    return Collections.emptySet();
    }
  }
  public Set<TwoKeyTuple<K1, K2>> key12Set() {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    Set<TwoKeyTuple<K1, K2>> tuples = new HashSet<TwoKeyTuple<K1, K2>>();
    for(K1 k1 : data.keySet()) {
      Map<K2, V> map1 = data.get(k1);
      for(K2 k2: map1.keySet()) {
        tuples.add(new TwoKeyTuple<K1, K2>(k1, k2));
      }
    }
    return tuples;
  }
  public Map<K2, V> get(K1 k1) {
    return (data.get(k1) == null) ? new HashMap<K2, V>() : data.get(k1);
  }
  public V get(K1 k1, K2 k2) {
    return (data.get(k1) == null || data.get(k1).get(k2) == null) ? defaultValueSupplier.get() : data.get(k1).get(k2);
  }

  public V get(TwoKeyTuple tuple) {
    return data.get(tuple.head()).get(tuple.tail());
  }
  public List<K1> key1Sort(Comparator<K1> sort) {
    List<K1> list = Lists.newArrayList(key1Set());
    Collections.sort(list, sort);
    return list;
  }

  public void put(K1 k1, K2 k2, V v) {
    if(data.get(k1) == null) {
      data.put(k1, new HashMap<K2, V>());
    }
    data.get(k1).put(k2, v);
  }

  public void clear() {
    data.clear();
  }

  public Collection<V> values() {
    Iterator<Entry<K1, K2, V>> iterator = iterator();
    List<V> values = new ArrayList();
    while(iterator.hasNext()) {
      values.add(iterator.next().getValue());
    }
    return values;
  }

  public boolean containsKey(K1 k1, K2 k2) {
    return data.containsKey(k1) && data.get(k1).containsKey(k2);
  }

  public boolean containsKey(TwoKeyTuple key) {
    return data.containsKey(key.getK1()) && data.get(key.getK1()).containsKey(key.tail());
  }

  public boolean containsValue(V v) {
    return values().contains(v);
  }

  public boolean isEmpty() {
    return data.isEmpty();
  }

  public int size() {
    return entrySet().size();
  }

  public Set<Entry<K1, K2, V>> entrySet() {
    Set<TwoKeyTuple<K1, K2>> tuples = key12Set();
    Iterator<TwoKeyTuple<K1, K2>> i = tuples.iterator();
    Set<Entry<K1, K2, V>> entries = new HashSet<Entry<K1, K2, V>>();
    while(i.hasNext()) {
      TwoKeyTuple<K1, K2> next = i.next();
      entries.add(new Entry<K1, K2, V>(next, get(next)));
    }
    return entries;
  }

  public void putAll(TwoNestedMap<K1, K2, V> map){
    for(K1 key : map.key1Set()){
      Map<K2, V> currentSubMap = this.get(key);
      currentSubMap.putAll(map.get(key));
      this.put(key, currentSubMap);
    }
  }

  public void put(K1 key, Map<K2, V> map){
    data.put(key, map);
  }

  public Iterator<Entry<K1, K2, V>> iterator() {
    return entrySet().iterator();
  }

  @Override
  public String toString() {
    return "TwoNestedMap{" +
        "data=" + data +
        ", defaultValueSupplier=" + defaultValueSupplier +
        '}';
  }

  public static class Entry<K1, K2, V>  {
    private final TwoKeyTuple<K1, K2> keyTuple;
    private final V value;

    public Entry(K1 k1, K2 k2, V v) {
      keyTuple = new TwoKeyTuple<K1, K2>(k1, k2);
      value = v;
    }

    public Entry(TwoKeyTuple<K1, K2> tuple, V v) {
      keyTuple = tuple;
      value = v;
    }

    public TwoKeyTuple<K1, K2> getKeyTuple() {
      return keyTuple;
    }

    public TwoKeyTuple<K1, K2> getKey() {
      return keyTuple;
    }
    public K1 getK1() {
      return keyTuple.getK1();
    }
    public K2 getK2() {
      return keyTuple.getK2();
    }


    public V getValue() {
      return value;
    }

    public int hashCode() {
      return keyTuple.hashCode() * 31 + value.hashCode();
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if(o == null || getClass() != o.getClass()) {
        return false;
      }
      Entry entry = (Entry) o;
      return (getKey().equals(entry.getKey()) && getValue().equals(entry.getValue()));
    }

    @Override
    public String toString() {
      return String.format("%s -> %s", keyTuple.toSet(), value);
    }

  }
}
