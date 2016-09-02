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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestMultiChildNode {
  @Test
  public void testIt() throws Exception {
    Object value1 = new Object();
    LeafNode<Object> l1 = new LeafNode<>("ab".toCharArray(), value1);
    Object value2 = new Object();
    LeafNode<Object> l2 = new LeafNode<>("cd".toCharArray(), value2);
    MultiChildNode<Object> n = new MultiChildNode<>("".toCharArray(), null, l1, l2);

    assertNull(n.get("a".toCharArray(), 0));
    assertNull(n.get("abc".toCharArray(), 0));
    assertNull(n.get("fff".toCharArray(), 0));
    assertEquals(value1, n.get("ab".toCharArray(), 0));
    assertEquals(value2, n.get("cd".toCharArray(), 0));
  }
}
