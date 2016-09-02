/**
 * Copyright 2013 Liveramp
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liveramp.commons.collections.lightweight_trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class TestImmutableStringRadixTreeMap {
  @Test
  public void testSize() throws Exception {
    StringRadixTreeMap<Integer> mmap = new StringRadixTreeMap<>();
    mmap.put("blah", 1);

    ImmutableStringRadixTreeMap<Integer> map = new ImmutableStringRadixTreeMap<>(mmap);
    assertEquals(1, map.size());
    assertFalse(map.isEmpty());
  }

  @Test
  public void testGetPut() {
    StringRadixTreeMap<Integer> mmap = new StringRadixTreeMap<>();
    mmap.put("blah", 1);

    ImmutableStringRadixTreeMap<Integer> map = new ImmutableStringRadixTreeMap<>(mmap);
    assertEquals(Integer.valueOf(1), map.get("blah"));
  }

  @Test
  public void testLotsOfElements() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      map.put(sb.toString(), i);
    }

    ImmutableStringRadixTreeMap<Integer> imm = new ImmutableStringRadixTreeMap<>(map);

    sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("a");
      assertEquals(Integer.valueOf(i), imm.get(sb.toString()));
    }
  }

  @Test
  public void testEntrySet() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<>(map);

    Map<String, Integer> otherMap = new HashMap<>();
    for (Map.Entry<String, Integer> entry : imap.entrySet()) {
      otherMap.put(entry.getKey(), entry.getValue());
    }

    Map<String, Integer> expectedMap = new HashMap<>();
    expectedMap.put("blah", 1);
    expectedMap.put("blah2", 2);
    expectedMap.put("foo", 7);
    expectedMap.put("bar", 15);
    expectedMap.put("LONGGGG one", 250);

    assertEquals(expectedMap, otherMap);
  }

  @Test
  public void testKeySet() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<>(map);

    Set<String> keys = new HashSet<>();
    for (String k : imap.keySet()) {
      keys.add(k);
    }

    assertEquals(new HashSet<>(Arrays.asList("blah", "blah2", "foo", "bar", "LONGGGG one")), keys);
  }

  @Test
  public void testValues() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("blah", 1);
    map.put("blah2", 2);
    map.put("foo", 7);
    map.put("bar", 15);
    map.put("LONGGGG one", 250);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<>(map);

    assertEquals(new HashSet<>(Arrays.asList(1, 2, 7, 15, 250)), new HashSet<>(imap.values()));
  }

  @Test
  public void testGetPartialMatches() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("a", 1);
    map.put("ab", 2);
    map.put("abcd", 3);
    map.put("abcde", 4);
    map.put("ac", 5);
    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<>(map);

    assertEquals(new HashSet<>(Arrays.asList("a", "ab", "abcd", "abcde")), new HashSet<>(imap.getPartialMatches("abcdef")));
    assertEquals(new HashSet<>(Arrays.asList("a")), new HashSet<>(imap.getPartialMatches("a")));
    assertEquals(new HashSet<>(Arrays.asList("a")), new HashSet<>(imap.getPartialMatches("azzzzzzzzz")));
    assertEquals(new HashSet<>(Arrays.asList("a", "ac")), new HashSet<>(imap.getPartialMatches("accc")));
    assertEquals(new HashSet<>(Arrays.asList("a", "ab")), new HashSet<>(imap.getPartialMatches("abc")));
  }

  @Test
  public void testNullQuery() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("a", 1);
    map.put("b", 1);

    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<>(map);

    assertEquals(1, (int)imap.get("b"));
    assertEquals(null, imap.get("c"));
    assertEquals(null, imap.get(""));
  }

  @Test
  public void testNullQuey2() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("a", 1);

    ImmutableStringRadixTreeMap<Integer> imap = new ImmutableStringRadixTreeMap<>(map);

    assertEquals(1, (int)imap.get("a"));
    assertEquals(null, imap.get(""));
  }

  @Test
  public void testAssertNoEmpty() {
    StringRadixTreeMap<Integer> map = new StringRadixTreeMap<>();
    map.put("b", 3);
    map.put("a", 2);

    try {
      map.put("", 1);
      fail("should throw an exception if empty key inserted");
    } catch (IllegalArgumentException e) {
      //  fine
    }

    try {
      map.put(null, 1);
      fail("should throw an exception if null key inserted");
    } catch (IllegalArgumentException e) {
      //  fine
    }

    assertEquals(2, (int)map.get("a"));
    assertEquals(3, (int)map.get("b"));
  }
}
