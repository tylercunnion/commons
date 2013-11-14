/**
 *  Copyright 2013 Liveramp
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
package com.liveramp.commons.collections.lightweight_trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestStringRadixTreeMap extends TestCase {
  public void testSize() throws Exception {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    assertEquals(0, map.size());
    Assert.assertTrue(map.isEmpty());
    map.put("blah", 1);
    assertEquals(1, map.size());
    Assert.assertFalse(map.isEmpty());

    // TODO:
//    map.put("blah", 2);
//    assertEquals(1, map.size());
  }

  public void testGetPut() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    Assert.assertFalse(map.containsKey("blah"));
    Assert.assertNull(map.get("blah"));
    map.put("blah", 1);
    Assert.assertTrue(map.containsKey("blah"));
    assertEquals(Integer.valueOf(1), map.get("blah"));
  }

  public void testClear() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);

    map.clear();
    Assert.assertNull(map.get("blah"));
    Assert.assertNull(map.get("blah2"));
    Assert.assertTrue(map.isEmpty());
  }

  public void testPutAll() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.putAll(new HashMap<String, Integer>(){{put("blah1", 1); put("blah2", 2);}});
    assertEquals(2, map.size());
    assertEquals(Integer.valueOf(1), map.get("blah1"));
    assertEquals(Integer.valueOf(2), map.get("blah2"));
  }

  public void testLotsOfElements() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      map.put(sb.toString(), i);
    }

    sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      assertEquals(Integer.valueOf(i), map.get(sb.toString()));
    }
  }

  public void testReplaceValue() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    Assert.assertFalse(map.containsKey("blah"));
    Assert.assertNull(map.get("blah"));
    map.put("blah", 1);
    Assert.assertTrue(map.containsKey("blah"));
    assertEquals(Integer.valueOf(1), map.get("blah"));
    map.put("blah", 2);
    assertEquals(Integer.valueOf(2), map.get("blah"));
  }

  public void testEntrySet() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);

    Map<String, Integer> otherMap = new HashMap<String, Integer>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      otherMap.put(entry.getKey(), entry.getValue());
    }

    Map<String, Integer> expectedMap = new HashMap<String, Integer>(){{
      put("blah", 1);
      put("blah2", 2);
      put("foo", 7);
      put("bar", 15);
      put("LONGGGG one", 250);
    }};

    Assert.assertEquals(expectedMap, otherMap);
  }

  public void testKeySet() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);

    Set<String> keys = new HashSet<String>();
    for (String k : map.keySet()) {
      keys.add(k);
    }

    Assert.assertEquals(new HashSet<String>(Arrays.asList("blah", "blah2", "foo", "bar", "LONGGGG one")), keys);
  }
  
  public void testValues() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<Integer>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);

    Assert.assertEquals(new HashSet<Integer>(Arrays.asList(1, 2, 7, 15, 250)), new HashSet<Integer>(map.values()));
  }
}
