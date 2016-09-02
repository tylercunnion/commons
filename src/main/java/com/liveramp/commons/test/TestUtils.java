package com.liveramp.commons.test;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import static org.junit.Assert.fail;

public class TestUtils {

  public static <T> void assertCollectionEquivalent(Collection<T> expectedCollection,
                                                    Collection<T> collection) {
    assertCollectionEquivalent("Collections not equivalent.", expectedCollection, collection);
  }

  public static <T> void assertCollectionEquivalent(String message, Collection<T> expectedCollection,
                                                    Collection<T> collection) {
    if (!CollectionUtils.isEqualCollection(expectedCollection, collection)) {
      System.out.println("Expected:");
      printCollection(expectedCollection);
      System.out.println("Actual: ");
      printCollection(collection);

      System.out.println("Detailed diff:");

      if (expectedCollection.size() != collection.size()) {
        System.out.println("  Sizes of collections differ. Expected size: " + expectedCollection.size() + ", actual size: " + collection.size());
      }

      List<String> inExpectedButNotActual = Lists.newArrayList();
      for (T t : expectedCollection) {
        if (!collection.contains(t)) {
          inExpectedButNotActual.add(getStringRepresentation(t));
        }
      }
      if (!inExpectedButNotActual.isEmpty()) {
        Collections.sort(inExpectedButNotActual);
        System.out.println("  Actual collection does not contain the following elements (which are contained in the expected collection): ");
        for (String t : inExpectedButNotActual) {
          System.out.println("    " + t);
        }
      }

      List<String> inActualButNotExpected = Lists.newArrayList();
      for (T t : collection) {
        if (!expectedCollection.contains(t)) {
          inActualButNotExpected.add(getStringRepresentation(t));
        }
      }
      if (!inActualButNotExpected.isEmpty()) {
        Collections.sort(inActualButNotExpected);
        System.out.println("  Expected collection does not contain the following elements (which are contained in the actual collection): ");
        for (String t : inActualButNotExpected) {
          System.out.println("    " + t);
        }
      }

      fail(message);
    }
  }

  public static <K, V> void assertMapsEquivalent(Map<K, V> expectedMap, Map<K, V> actualMap) {
    assertMapsEquivalent("Maps not equivalent.", expectedMap, actualMap);
  }

  public static <K, V> void assertMapsEquivalent(String message, Map<K, V> expectedMap, Map<K, V> actualMap) {
    assertCollectionEquivalent(message, expectedMap.entrySet(), actualMap.entrySet());
  }

  public static <T> String getStringRepresentation(T t) {

    // Copy BytesWritable toString
    if (ByteBuffer.class.isAssignableFrom(t.getClass())) {
      byte[] bytes = ((ByteBuffer)t).array();
      int size = bytes.length;
      StringBuffer sb = new StringBuffer(3 * size);
      for (int idx = 0; idx < size; idx++) {
        // if not the first, put a blank separator in
        if (idx != 0) {
          sb.append(' ');
        }
        String num = Integer.toHexString(0xff & bytes[idx]);
        // if it is only one digit, add a leading 0.
        if (num.length() < 2) {
          sb.append('0');
        }
        sb.append(num);
      }
      return sb.toString();
    }

    return t.toString();
  }


  public static <T> void printCollection(Collection<T> collection) {
    printCollection(null, collection);
  }

  public static <T> void printCollection(String header, Collection<T> collection) {
    if (header != null) {
      System.out.println(header);
    }
    for (T item : collection) {
      System.out.println(item);
    }
  }
}
