/**
 *  Copyright 2013 LiveRamp
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.liveramp.commons.collections;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.liveramp.commons.util.MemoryUsageEstimator;

public class MemoryBoundLruHashMap<K, V> implements Iterable<Map.Entry<K, V>> {

  public static final long UNLIMITED_MEMORY_CAPACITY = -1;
  public static final int UNLIMITED_ITEM_CAPACITY = -1;

  private long numManagedBytes = 0;
  private final long numBytesCapacity;
  private MemoryUsageEstimator<K> keyEstimator;
  private MemoryUsageEstimator<V> valueEstimator;
  private final LruHashMap<K, V> map;

  public MemoryBoundLruHashMap(long numBytesCapacity, MemoryUsageEstimator<K> keyEstimator, MemoryUsageEstimator<V> valueEstimator) {
    this(-1, numBytesCapacity, keyEstimator, valueEstimator);
  }

  // Negative capacity disables the corresponding check
  public MemoryBoundLruHashMap(int numItemsCapacity, long numBytesCapacity, MemoryUsageEstimator<K> keyEstimator, MemoryUsageEstimator<V> valueEstimator) {
    this.numBytesCapacity = numBytesCapacity;
    this.keyEstimator = keyEstimator;
    this.valueEstimator = valueEstimator;
    if (numBytesCapacity >= 0 && (keyEstimator == null || valueEstimator == null)) {
      throw new IllegalArgumentException("Key and value estimators must be provided when using a memory based limit.");
    }
    map = new LruHashMap<K, V>(numItemsCapacity, 0);
  }

  /**
   * @param key
   * @param value
   * @return a List of all evicted Map Entries. Will return an empty list if nothing was evicted.
   */
  public List<Map.Entry<K, V>> putAndEvict(K key, V value) {

    // First, unmanage memory usage of existing value since it is about to be replaced
    // We get() instead of remove() in order to keep the same instance of 'key' in the map
    if (map.containsKey(key)) {
      V oldValue = map.get(key);
      unmanage(key, oldValue);
    }

    // Add to map
    map.put(key, value);
    manage(key, value);
    return evictIfNecessary();
  }

  public List<Map.Entry<K, V>> evictIfNecessary() {
    List<Map.Entry<K, V>> evicted = null;

    // If an eldest element was removed, update byte count
    Map.Entry<K, V> eldestRemoved = map.getAndClearEldestRemoved();
    if (eldestRemoved != null) {
      unmanage(eldestRemoved);
      evicted = new LinkedList<Map.Entry<K, V>>();
      evicted.add(eldestRemoved);
    }

    // Now remove elements until byte count is under the threshold
    if (isMemoryBound()) {
      Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
      while (numManagedBytes > numBytesCapacity && map.size() > 0) {
        Map.Entry<K, V> eldest = iterator.next();
        unmanage(eldest);
        if (evicted == null) {
          evicted = new LinkedList<Map.Entry<K, V>>();
        }
        evicted.add(eldest);
        iterator.remove();
      }
    }
    if (evicted == null) {
      evicted = Collections.emptyList();
    }
    return evicted;
  }

  public V get(K key) {
    return map.get(key);
  }

  public V remove(K key) {
    if (map.containsKey(key)) {
      V value = map.remove(key);
      unmanage(key, value);
      return value;
    } else {
      return null;
    }
  }

  public int size() {
    return map.size();
  }

  public boolean isMemoryBound() {
    return numBytesCapacity >= 0;
  }

  public long getNumManagedBytes() {
    return numManagedBytes;
  }

  public long estimateKeyMemorySize(K key) {
    return keyEstimator.estimateMemorySize(key);
  }

  public long estimateValueMemorySize(V value) {
    return valueEstimator.estimateMemorySize(value);
  }

  public void adjustNumManagedBytes(long numBytes) {
    numManagedBytes += numBytes;
  }

  private void manage(K key, V value) {
    if (isMemoryBound()) {
      numManagedBytes += keyEstimator.estimateMemorySize(key) + valueEstimator.estimateMemorySize(value);
    }
  }

  private void unmanage(K key, V value) {
    if (isMemoryBound()) {
      numManagedBytes -= keyEstimator.estimateMemorySize(key) + valueEstimator.estimateMemorySize(value);
    }
  }

  private void unmanage(Map.Entry<K, V> entry) {
    if (isMemoryBound()) {
      numManagedBytes -= keyEstimator.estimateMemorySize(entry.getKey()) + valueEstimator.estimateMemorySize(entry.getValue());
    }
  }

  public long getMaxNumManagedBytes() {
    return numBytesCapacity;
  }

  public int getMaxNumItems() {
    return map.getMaxCapacity();
  }

  @Override
  public Iterator<Map.Entry<K, V>> iterator() {
    return new EntryIterator();
  }

  private class EntryIterator implements Iterator<Map.Entry<K, V>> {

    private Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
    private Map.Entry<K, V> currentItem;

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public Map.Entry<K, V> next() {
      currentItem = iterator.next();
      return currentItem;
    }

    @Override
    public void remove() {
      if (currentItem == null) {
        throw new IllegalStateException();
      } else {
        unmanage(currentItem);
      }
      iterator.remove();
    }
  }
}
