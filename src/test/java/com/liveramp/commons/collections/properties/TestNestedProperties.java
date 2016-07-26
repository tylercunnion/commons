package com.liveramp.commons.collections.properties;

import java.util.Map;

import org.junit.Test;

import com.liveramp.commons.CommonsTestCase;
import com.liveramp.commons.collections.map.MapBuilder;

import static com.liveramp.commons.test.TestUtils.assertCollectionEquivalent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestNestedProperties extends CommonsTestCase {
  private static final String PROPERTY_1 = "mapreduce.job.queuename";
  private static final String VALUE_1A = "identity_resolver";
  private static final String VALUE_1B = "esp_summer";
  private static final String PROPERTY_2 = "some.other.property";
  private static final Object VALUE_2A = 599;
  private static final Object VALUE_2B = 1599;
  private static final String PROPERTY_3 = "mapred.job.priority";
  private static final String VALUE_3 = "VERY_HIGH";

  @Test
  public void testGetPropertiesMap() {
    NestedProperties.Builder builder = new NestedProperties.Builder();
    builder.setProperty(PROPERTY_1, VALUE_1A);
    builder.setProperty(PROPERTY_2, VALUE_2A);
    Map<Object, Object> expected = MapBuilder.<Object, Object>of(PROPERTY_1, VALUE_1A).put(PROPERTY_2, VALUE_2A).get();
    assertEquals(expected, builder.build().getPropertiesMap());
  }

  @Test
  public void testIsSetAndGet() {
    NestedProperties.Builder builder = new NestedProperties.Builder();
    builder.setProperty(PROPERTY_1, VALUE_1A);
    builder.setProperty(PROPERTY_2, VALUE_2A);
    final NestedProperties properties = builder.build();
    assert (properties.isSetProperty(PROPERTY_1));
    assert (properties.isSetProperty(PROPERTY_2));
    assert (!properties.isSetProperty(PROPERTY_3));
    assertEquals(properties.getProperty(PROPERTY_1), VALUE_1A);
    assertEquals(properties.getProperty(PROPERTY_2), VALUE_2A);

    try{
      properties.getProperty(PROPERTY_3);
      fail();
    }catch(Exception e){
      assertEquals("Property " + PROPERTY_3 + " is not set in " + properties, e.getMessage());
    }

  }

  @Test
  public void testCanOverrideAndOverride() {
    NestedProperties.Builder builder1 = new NestedProperties.Builder();
    builder1.setProperty(PROPERTY_1, VALUE_1A);
    builder1.setProperty(PROPERTY_2, VALUE_2A, true);
    NestedProperties properties1 = builder1.build();

    NestedProperties.Builder builder2 = new NestedProperties.Builder();
    builder2.setProperty(PROPERTY_2, VALUE_2B);
    builder2.setProperty(PROPERTY_3, VALUE_3);
    NestedProperties properties2 = builder2.build();

    assert (!properties2.canOverride(properties1));

    NestedProperties.Builder builder3 = new NestedProperties.Builder();
    builder3.setProperty(PROPERTY_1, VALUE_1B);
    builder3.setProperty(PROPERTY_3, VALUE_3);
    NestedProperties properties3 = builder3.build();

    assert (properties3.canOverride(properties1));

    NestedProperties.Builder expectedBuilder = new NestedProperties.Builder();
    expectedBuilder.setProperty(PROPERTY_2, VALUE_2A);
    expectedBuilder.setProperty(PROPERTY_1, VALUE_1B);
    expectedBuilder.setProperty(PROPERTY_3, VALUE_3);
    NestedProperties expectedProperties = expectedBuilder.build();

    assertCollectionEquivalent(
        expectedProperties.getPropertiesMap().entrySet(),
        properties3.override(properties1).getPropertiesMap().entrySet()
    );
  }

  @Test
  public void testFromPropertiesMap() {
    Map<Object, Object> propertiesMap = MapBuilder.<Object, Object>of(PROPERTY_1, VALUE_1A).put(PROPERTY_2, VALUE_2A).get();
    assertEquals(propertiesMap, new NestedProperties(propertiesMap, false).getPropertiesMap());
  }

}