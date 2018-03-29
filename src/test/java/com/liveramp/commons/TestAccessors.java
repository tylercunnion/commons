package com.liveramp.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    assertEquals(1L, Accessors.first(Lists.newArrayList(1L, 2L)).longValue());
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
    assertEquals(2L, Accessors.second(Lists.newArrayList(1L, 2L)).longValue());
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_arrayShouldCheckNull() {
    Integer integer;

    integer = Accessors.only((Integer[])null);
    assertNull(integer);  // not reached
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_arrayShouldCheckEmpty() {
    Integer[] empty = new Integer[0];
    Integer integer;

    integer = Accessors.only(empty);
    assertNull(integer);  // not reached
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_arrayShouldCheckTwo() {
    Integer[] array = new Integer[2];
    Integer integer;

    integer = Accessors.only(array);
    assertNull(integer);  // not reached
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_arrayShouldCheckThree() {
    Integer[] array = new Integer[3];
    Integer integer;

    integer = Accessors.only(array);
    assertNull(integer);  // not reached
  }

  @Test
  public void testOnly_arrayShouldGetNullEntry() {
    Integer[] array = new Integer[1];
    Integer integer;

    integer = Accessors.only(array);
    assertNull(integer);
  }

  @Test
  public void testOnly_arrayShouldGetValue() {
    Integer[] array = new Integer[] {314};
    Integer integer;

    integer = Accessors.only(array);
    assertEquals(new Integer(314), integer);
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_iterableShouldCheckNull() {
    Integer integer;

    integer = Accessors.only((Iterable<Integer>)null);
    assertNull(integer);  // not reached
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_iterableShouldCheckEmpty() {
    Set<Integer> emptySet = new HashSet<>();
    Integer integer;

    integer = Accessors.only(emptySet);
    assertNull(integer);  // not reached
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_iterableShouldCheckTwo() {
    Set<Integer> set = new HashSet<>();
    Integer integer;

    set.add(314);
    set.add(315);
    integer = Accessors.only(set);
    assertNull(integer);  // not reached
  }

  @Test(expected = RuntimeException.class)
  public void testOnly_iterableShouldCheckThree() {
    Set<Integer> set = new HashSet<>();
    Integer integer;

    set.add(314);
    set.add(315);
    set.add(316);
    integer = Accessors.only(set);
    assertNull(integer);  // not reached
  }

  @Test
  public void testOnly_iterableShouldGetNullEntry() {
    Set<Integer> set = new HashSet<>();
    Integer integer;

    set.add(null);
    integer = Accessors.only(set);
    assertNull(integer);

    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(null);
    integer = Accessors.only(arrayList);
    assertNull(integer);
  }

  @Test
  public void testOnly_iterableShouldGetValue() {
    Set<Integer> set = new HashSet<>();
    Integer integer;

    set.add(314);
    integer = Accessors.only(set);
    assertEquals(new Integer(314), integer);
  }

  @Test(expected = NullPointerException.class)
  public void testOnlyOrAbsentOnNull() throws Exception {
    Accessors.onlyOrAbsent(null);
  }

  @Test
  public void testOnlyOrAbsentOnEmpty() throws Exception {
    assertEquals(Optional.absent(), Accessors.onlyOrAbsent(Collections.emptySet()));
  }

  @Test
  public void testOnlyOrAbsentOnOne() throws Exception {
    assertEquals(1L, Accessors.onlyOrAbsent(Lists.newArrayList(1L)).get().longValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOnlyOrAbsentOnTwo() throws Exception {
    Accessors.onlyOrAbsent(Lists.newArrayList(1L, 2L));
  }

  @Test
  public void testFirstOrEmptyReturnsFirstElement() {
    assertEquals(java.util.Optional.of(1L), Accessors.firstOrEmpty(Lists.newArrayList(1L, 2L)));
  }

  @Test
  public void testFirstOrEmptyReturnsEmptyForEmptyIterable() {
    assertEquals(java.util.Optional.empty(), Accessors.firstOrEmpty(Collections.emptyList()));
  }

  @Test(expected = NullPointerException.class)
  public void testFirstOrEmptyThrowsNpeForNull() {
    assertEquals(java.util.Optional.empty(), Accessors.firstOrEmpty(null));
  }

}
