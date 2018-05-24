package com.liveramp.commons.guava_support;

import java.util.function.Function;
import java.util.stream.IntStream;

import com.google.common.collect.Multimap;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMultimapCollector {

  @Test
  public void testSetMultimapAvoidsDupes() {
    Multimap<Integer, Integer> res = IntStream.range(0, 100)
        .boxed()
        .collect(MultimapCollector.toSetMultimap(
            i -> i % 2,
            i -> i % 8
        ));

    assertEquals(4, res.get(0).size());
  }

  @Test
  public void testListMultimapRetainsDupes() {
     Multimap<Integer, Integer> res = IntStream.range(0, 100)
        .boxed()
        .collect(MultimapCollector.toListMultimap(
            i -> i % 2,
            i -> i % 8
        ));

    assertEquals(50, res.get(0).size());
  }
}
