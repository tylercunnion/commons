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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestLruHashMap {
  @Test
  public void testIt() throws Exception {
    LruHashMap<String, String> m = new LruHashMap<>(3);

    m.put("blah", "blah");
    assertEquals(1, m.size());
    assertTrue(m.containsKey("blah"));

    m.put("blah1", "blah");
    assertEquals(2, m.size());
    assertTrue(m.containsKey("blah"));
    assertTrue(m.containsKey("blah1"));

    m.put("blah2", "blah");
    assertEquals(3, m.size());
    assertTrue(m.containsKey("blah"));
    assertTrue(m.containsKey("blah1"));
    assertTrue(m.containsKey("blah2"));
    // this should make "blah" more recently used than "blah1"
    assertNotNull(m.get("blah"));

    m.put("blah3", "blah");
    assertEquals(3, m.size());
    assertTrue(m.containsKey("blah"));
    assertTrue(m.containsKey("blah2"));
    assertTrue(m.containsKey("blah3"));
  }
}
