package com.liveramp.commons;

import java.util.Collections;
import java.util.HashSet;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
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
    Assert.assertEquals(1L, Accessors.first(Lists.newArrayList(1L, 2L)).longValue());
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
    Assert.assertEquals(2L, Accessors.second(Lists.newArrayList(1L, 2L)).longValue());
  }

  @Test(expected = NullPointerException.class)
  public void testOnlyOrAbsentOnNull() throws Exception {
    Accessors.onlyOrAbsent(null);
  }

  @Test
  public void testOnlyOrAbsentOnEmpty() throws Exception {
    Assert.assertEquals(Optional.absent(), Accessors.onlyOrAbsent(Collections.emptySet()));
  }

  @Test
  public void testOnlyOrAbsentOnOne() throws Exception {
    Assert.assertEquals(1L, Accessors.onlyOrAbsent(Lists.newArrayList(1L)).get().longValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOnlyOrAbsentOnTwo() throws Exception {
    Accessors.onlyOrAbsent(Lists.newArrayList(1L, 2L));
  }

  @Test
  public void testFirstOrEmptyReturnsFirstElement() {
    Assert.assertEquals(java.util.Optional.of(1L), Accessors.firstOrEmpty(Lists.newArrayList(1L, 2L)));
  }

  @Test
  public void testFirstOrEmptyReturnsEmptyForEmptyIterable() {
    Assert.assertEquals(java.util.Optional.empty(), Accessors.firstOrEmpty(Collections.emptyList()));
  }

  @Test(expected = NullPointerException.class)
  public void testFirstOrEmptyThrowsNpeForNull() {
    Assert.assertEquals(java.util.Optional.empty(), Accessors.firstOrEmpty(null));
  }

}
