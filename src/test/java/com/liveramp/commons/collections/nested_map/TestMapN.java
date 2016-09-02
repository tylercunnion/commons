package com.liveramp.commons.collections.nested_map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestMapN {

  @Test
  public void testIt() {

    //  test 4 - index maps

    FourNestedMap<Integer, Integer, Byte, String, Long> fourMap = new FourNestedMap<Integer, Integer, Byte, String, Long>();

    fourMap.put(1, 3, (byte)1, "A", 7l);
    fourMap.put(2, 2, (byte)1, "A", 8l);
    fourMap.put(1, 2, (byte)1, "A", 7l);
    fourMap.put(1, 3, (byte)2, "A", 7l);

    Iterator<FourNestedMap.Entry<Integer, Integer, Byte, String, Long>> entries4 = fourMap.iterator();
    Set<FourNestedMap.Entry<Integer, Integer, Byte, String, Long>> set = Sets.newHashSet(entries4);

    System.out.println(set);
    System.out.println(new FourNestedMap.Entry<>(2, 2, (byte)1, "A", 8l));

    assertEquals(Sets.newHashSet(new FourNestedMap.Entry<>(2, 2, (byte)1, "A", 8l),
        new FourNestedMap.Entry<>(1, 2, (byte)1, "A", 7l),
        new FourNestedMap.Entry<>(1, 3, (byte)1, "A", 7l),
        new FourNestedMap.Entry<>(1, 3, (byte)2, "A", 7l)), set);

    //  test 3 - index maps

    ThreeNestedMap<String, Long, String, Integer> map = new ThreeNestedMap<>();

    map.put("b", 2l, "1", 34);
    map.put("a", 1l, "1", 35);
    map.put("b", 3l, "3", 34);
    map.put("d", 3l, "1", 33);
    map.put("e", 3l, "2", 31);
    map.put("e", 3l, "1", 30);

    Iterator<ThreeNestedMap.Entry<String, Long, String, Integer>> entries = map.iterator();

    Set<ThreeNestedMap.Entry<String, Long, String, Integer>> expected = Sets.newHashSet(new ThreeNestedMap.Entry<>("a", 1l, "1", 35),
        new ThreeNestedMap.Entry<>("b", 2l, "1", 34),
        new ThreeNestedMap.Entry<>("b", 3l, "3", 34),
        new ThreeNestedMap.Entry<>("d", 3l, "1", 33),
        new ThreeNestedMap.Entry<>("e", 3l, "1", 30),
        new ThreeNestedMap.Entry<>("e", 3l, "2", 31));
    assertEquals(expected, Sets.newHashSet(entries));

    ThreeNestedMap<String, Long, String, Integer> map2 = new ThreeNestedMap<String, Long, String, Integer>();
    assertFalse(map2.iterator().hasNext());

    for (ThreeNestedMap.Entry<String, Long, String, Integer> entry : map) {
      System.out.println(entry);
    }

    //  test 2 - index maps

    TwoNestedMap<String, String, Integer> map3 = new TwoNestedMap<String, String, Integer>();
    map3.put("b", "1", 34);
    map3.put("a", "1", 35);

    Iterator<TwoNestedMap.Entry<String, String, Integer>> entries2 = map3.iterator();
    Set<TwoNestedMap.Entry<String, String, Integer>> expected2 = Sets.newHashSet(
        new TwoNestedMap.Entry<>("a", "1", 35),
        new TwoNestedMap.Entry<>("b", "1", 34));

    assertEquals(expected2, Sets.newHashSet(entries2));

    for (TwoNestedMap.Entry<String, String, Integer> entry : map3) {
      System.out.println(entry);
    }

    TwoNestedMap<String, String, Integer> map4 = new TwoNestedMap<>();
    assertFalse(map4.iterator().hasNext());
  }

  @Test
  public void testDefault() {
    TwoNestedMap<String, String, Integer> map3 = new TwoNestedMap<>();
    map3.put("b", "1", 34);
    map3.put("a", "1", 35);

    assertNull(map3.get("a", "2"));
    assertEquals(new HashMap<>(), map3.get("c"));
  }

  @Test
  public void testDefaultDeprecatedReturnsReference() {
    final String stale = "a";
    final String fresh = "b";
    final TwoNestedMap<String, String, RefType> map = new TwoNestedMap<>(new RefType(stale));
    map.get("a", "b").setThing(fresh);
    assertEquals(fresh, map.get("b", "a").getThing());
  }

  @Test
  public void testDefaultReturnsNew() {
    final String stale = "a";
    final String fresh = "b";

    final TwoNestedMap<Object, Object, RefType> map = new TwoNestedMap<>(new Supplier<RefType>() {
      @Override
      public RefType get() {
        return new RefType(stale);
      }
    });

    map.get("a", "b").setThing(fresh);
    assertEquals(stale, map.get("b", "a").getThing());
  }

  @Test
  public void testDefaultEmptyNested() throws Exception {

    ThreeNestedMap<String, String, String, Long> map = new ThreeNestedMap<>(0l);
    TwoNestedMap<String, String, Long> sub = map.get("a");

    assertEquals(0l, (long)sub.get("some", "value"));

  }

  private static class RefType {
    private String thing;

    public RefType(String thing) {
      this.thing = thing;
    }

    public String getThing() {
      return thing;
    }

    public void setThing(String thing) {
      this.thing = thing;
    }
  }
}
