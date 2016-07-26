package com.liveramp.commons.collections.properties;

import java.util.Map;

import com.google.common.collect.Maps;

public class NestedProperties implements OverridableProperties{

  @Override
  public String toString() {
    return "NestedProperties{" +
        "properties=" + properties +
        '}';
  }

  public static interface IBuilder {

    public IBuilder setProperty(String name, Object value, boolean isFinal);

    public IBuilder setProperty(String name, Object value);

  }

  public static class Builder implements IBuilder{
    private Map<Object, OverridableProperties.Property> properties;

    public Builder() {
      this.properties = Maps.newHashMap();
    }

    public Builder setProperty(String name, Object value, boolean isFinal) {
      properties.put(name, new OverridableProperties.Property(value, isFinal));
      return this;
    }

    public Builder setProperty(String name, Object value) {
      return setProperty(name, value, false);
    }

    public NestedProperties build() {
      return new NestedProperties(properties);
    }
  }

  private final Map<Object, OverridableProperties.Property> properties;

  public NestedProperties() {
    this.properties = Maps.newHashMap();
  }

  public NestedProperties(Map<Object, Object> propertiesMap, boolean areFinal) {
    this.properties = Maps.newHashMap();
    for (Map.Entry<Object, Object> entry : propertiesMap.entrySet()) {
      properties.put((String)entry.getKey(), new OverridableProperties.Property(entry.getValue(), areFinal));
    }
  }

  @Override
  public Map<Object, Object> getPropertiesMap() {
    Map<Object, Object> propertiesMap = Maps.newHashMap();
    for (Map.Entry<Object, OverridableProperties.Property> entry : properties.entrySet()) {
      propertiesMap.put(entry.getKey(), entry.getValue().value);
    }
    return propertiesMap;
  }

  @Override
  public Map<Object, Property> getMap() {
    return properties;
  }

  public boolean containsKey(String key){
    return properties.containsKey(key);
  }

  public Object get(String key){
    return properties.get(key).value;
  }

  protected NestedProperties(Map<Object, OverridableProperties.Property> properties) {
    this.properties = properties;
  }

  protected NestedProperties(NestedProperties other) {
    this.properties = Maps.newHashMap(other.properties);
  }

  protected boolean canOverride(OverridableProperties parentProperties) {

    Map<Object, Property> properties = parentProperties.getMap();

    for (Object parentPropertyName : properties.keySet()) {
      OverridableProperties.Property parentProperty = properties.get(parentPropertyName);
      if (this.properties.containsKey(parentPropertyName) && parentProperty.isFinal) {
        return false;
      }
    }
    return true;
  }

  @Override
  public NestedProperties override(OverridableProperties parentProperties) {
    if (!canOverride(parentProperties)) {
      throw new RuntimeException("Failed to override parent properties " + parentProperties);
    }

    Map<Object, OverridableProperties.Property> overridden = Maps.newHashMap(parentProperties.getMap());
    overridden.putAll(properties);

    return new NestedProperties(overridden);
  }

  protected boolean isSetProperty(String name) {
    return properties.containsKey(name);
  }

  protected Object getProperty(String name) {
    if (!isSetProperty(name)) {
      throw new RuntimeException("Property " + name + " is not set in " + this);
    }
    return properties.get(name).value;
  }

}
