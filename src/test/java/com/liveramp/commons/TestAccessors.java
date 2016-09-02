package com.liveramp.commons;

import java.util.HashSet;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

public class TestAccessors {

  @Test(expected = NullPointerException.class)
  public void testFirstOnNull() throws Exception {
    Accessors.first(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFirstOnEmpty() throws Exception {
    Accessors.first(new HashSet<>());
  }

  @Test
  public void testReturnFirst() throws Exception {
    Assert.assertEquals(1L, Accessors.first(Sets.newHashSet(1L, 2L)).longValue());
  }

  @Test(expected = NullPointerException.class)
  public void testSecondOnNull() throws Exception {
    Accessors.second(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondOnEmpty() throws Exception {
    Accessors.second(new HashSet<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondOnTooSmall() throws Exception {
    Accessors.second(Sets.newHashSet(1L));
  }

  @Test
  public void testReturnSecond() throws Exception {
    Assert.assertEquals(2L, Accessors.second(Sets.newHashSet(1L, 2L)).longValue());
  }
}
