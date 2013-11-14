package com.liveramp.commons.collections;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestIntervalMap {

  @Test
  public void checkCombineRanges(){
    IntervalMap<String> range = TreeIntervalMap.create();

    range.put(0, 0, "B");
    range.put(1, 1, "A");
    range.put(4, 5, "A");
    range.put(2, 3, "A");
    range.put(6, 8, "A");
    range.put(9, 11, "B");

    Iterator<IntervalMap.Interval<String>> iter = range.iterator();
    assertEquals(range(0, 0, "B"), iter.next());
    assertEquals(range(1, 8, "A"), iter.next());
    assertEquals(range(9, 11, "B"), iter.next());
  }

  @Test
  public void checkSimpleInsert(){
    IntervalMap<String> range = TreeIntervalMap.create();

    assertEquals(null, range.get(1));

    range.put(2, 3, "B");
    range.put(1, 1, "A");
    range.put(4, 10, "C");

    assertEquals("A", range.get(1));
    assertEquals("B", range.get(2));
    assertEquals("B", range.get(3));
    assertEquals("C", range.get(4));
    assertEquals("C", range.get(6));
    assertEquals("C", range.get(10));

    assertEquals(null, range.get(15));
    range.put(13, 20, "D");
    assertEquals("D", range.get(15));

    Iterator<IntervalMap.Interval<String>> iter = range.iterator();
    assertEquals(range(1, 1, "A"), iter.next());
    assertEquals(range(2, 3, "B"), iter.next());
    assertEquals(range(4, 10, "C"), iter.next());
    assertEquals(range(13, 20, "D"), iter.next());

  }

  public IntervalMap.Interval<String> range(long min, long max, String value){
    return new IntervalMap.Interval<String>(min, max, value);
  }

  @Test
  public void checkFailInsert(){
    IntervalMap<String> range = TreeIntervalMap.create();

    range.put(4, 4, "A");

    try{
      range.put(4, "B");
      fail("Should have thrown exception!");
    }catch(RuntimeException e){
      assertEquals("Trying to insert value: B from 4 to 4 but values already exists for that range!", e.getMessage());
    }

    range.put(6, 8, "C");

    try{
      range.put(6, "D");
      fail("Should have thrown exception!");
    }catch(RuntimeException e){
      assertEquals("Trying to insert value: D from 6 to 6 but values already exists for that range!", e.getMessage());
    }

    try{
      range.put(7, "D");
      fail("Should have thrown exception!");
    }catch(RuntimeException e){
      assertEquals("Trying to insert value: D from 7 to 7 but values already exists for that range!", e.getMessage());
    }

    try{
      range.put(8, "D");
      fail("Should have thrown exception!");
    }catch(RuntimeException e){
      assertEquals("Trying to insert value: D from 8 to 8 but values already exists for that range!", e.getMessage());
    }

    //  fine
    range.put(9, "E");
  }

}
