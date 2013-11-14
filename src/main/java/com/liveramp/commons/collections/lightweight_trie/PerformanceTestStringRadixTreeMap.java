/*
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PerformanceTestStringRadixTreeMap {

  public static void main(String[] args) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

    List<String> strings = new ArrayList<String>();

    while (true) {
      String l = r.readLine();
      if (l == null) {
        break;
      }
      strings.add(l);
    }

    System.out.println("Read " + strings.size() + " strings.");

    StringRadixTreeMap<Boolean> map = new StringRadixTreeMap<Boolean>();

    // HashMap<String, Boolean> map = new HashMap<String, Boolean>();

    long start = System.currentTimeMillis();
    for (int i = 0; i < strings.size(); i++) {
      map.put(strings.get(i), Boolean.TRUE);
    }
    long end = System.currentTimeMillis();
    System.out.println("Took " + (end - start) + "ms to populate map.");

    System.out.println(map.size());

    start = System.currentTimeMillis();
    ImmutableStringRadixTreeMap<Boolean> immutableStringRadixTreeMap = new ImmutableStringRadixTreeMap<Boolean>(map);
    end = System.currentTimeMillis();
    System.out.println(immutableStringRadixTreeMap.size());
    System.out.println("Took " + (end - start) + "ms to convert to immutable.");
  }
}
