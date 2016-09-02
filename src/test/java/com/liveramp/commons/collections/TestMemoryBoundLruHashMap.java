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

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.liveramp.commons.util.IntegerMemoryUsageEstimator;
import com.liveramp.commons.util.LongMemoryUsageEstimator;
import com.liveramp.commons.util.StringMemoryUsageEstimator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestMemoryBoundLruHashMap {
  @Test
  public void testNumBytesCapacity() {
    MemoryBoundLruHashMap<Integer, String> map = new MemoryBoundLruHashMap<>(128, new IntegerMemoryUsageEstimator(), new StringMemoryUsageEstimator());

    assertEquals(0, map.size());
    assertEquals(0, map.getNumManagedBytes());

    // Insert

    map.putAndEvict(1, "1"); // 40 byte string

    assertEquals(1, map.size());
    assertEquals(56, map.getNumManagedBytes());
    assertEquals("1", map.get(1));

    // Insert

    map.putAndEvict(2, "12"); // 48 byte string

    assertEquals(2, map.size());
    assertEquals(120, map.getNumManagedBytes());
    assertEquals("12", map.get(2));

    // Insert which goes over the size threshold

    map.putAndEvict(3, "1"); // 40 byte string

    assertEquals(2, map.size());
    assertEquals(120, map.getNumManagedBytes());
    assertEquals("1", map.get(3));

    // Insert which goes over the size threshold

    map.putAndEvict(4, "1234567890"); // 64 byte string

    assertEquals(1, map.size());
    assertEquals(80, map.getNumManagedBytes());
    assertEquals("1234567890", map.get(4));

    // Insert that replaces a value

    map.putAndEvict(4, "1");

    assertEquals(1, map.size());
    assertEquals(56, map.getNumManagedBytes());
    assertEquals("1", map.get(4));

    // Insert

    map.putAndEvict(2, "12");

    assertEquals(2, map.size());
    assertEquals(120, map.getNumManagedBytes());
    assertEquals("12", map.get(2));

    // Remove

    map.remove(4);

    assertEquals(1, map.size());
    assertEquals(64, map.getNumManagedBytes());
    assertEquals(null, map.get(4));
  }

  @Test
  public void testNumItemsCapacity() {
    MemoryBoundLruHashMap<Integer, String> map = new MemoryBoundLruHashMap<>(2, 1024, new IntegerMemoryUsageEstimator(), new StringMemoryUsageEstimator());

    assertEquals(0, map.size());
    assertEquals(0, map.getNumManagedBytes());

    // Insert

    map.putAndEvict(1, "a");

    assertEquals(1, map.size());
    assertEquals(56, map.getNumManagedBytes());
    assertEquals("a", map.get(1));

    // Insert

    map.putAndEvict(2, "ab");

    assertEquals(2, map.size());
    assertEquals(120, map.getNumManagedBytes());
    assertEquals("ab", map.get(2));

    // Insert which goes over the num items limit

    map.putAndEvict(3, "abc");

    assertEquals(2, map.size());
    assertEquals(128, map.getNumManagedBytes());
    // 1 should be gone
    assertEquals(null, map.get(1));
    assertEquals("ab", map.get(2));
    assertEquals("abc", map.get(3));

    // Get to change access ordering

    map.get(2);

    // Insert which goes over the num items limit

    map.putAndEvict(1, "a");

    assertEquals(2, map.size());
    assertEquals(120, map.getNumManagedBytes());
    // 3 should be gone
    assertEquals("a", map.get(1));
    assertEquals("ab", map.get(2));
    assertEquals(null, map.get(3));
  }

  @Test
  public void testIterator() {
    MemoryBoundLruHashMap<Long, Long> map = new MemoryBoundLruHashMap<>(10, 1024, new LongMemoryUsageEstimator(), new LongMemoryUsageEstimator());
    map.putAndEvict(1L, 1L);
    map.putAndEvict(2L, 2L);
    map.putAndEvict(3L, 3L);

    Iterator<Map.Entry<Long, Long>> itr = map.iterator();
    assertEquals(1L, (long)itr.next().getKey());
    assertEquals(2L, (long)itr.next().getKey());
    assertEquals(3, map.size());
    assertEquals(96, map.getNumManagedBytes());
    itr.remove();
    assertEquals(2, map.size());
    assertEquals(64, map.getNumManagedBytes());
    assertEquals(3L, (long)itr.next().getKey());

    assertFalse(itr.hasNext());
  }
}
