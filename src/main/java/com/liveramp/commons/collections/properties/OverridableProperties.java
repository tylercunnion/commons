package com.liveramp.commons.collections.properties;

import java.util.Map;

public interface OverridableProperties {

  public static class Property {
    public Object value;
    public boolean isFinal;

    public Property(Object value, boolean isFinal) {
      this.value = value;
      this.isFinal = isFinal;
    }

    @Override
    public String toString() {
      return "Property{" +
          "value='" + value + '\'' +
          ", isFinal=" + isFinal +
          '}';
    }
  }


  public OverridableProperties override(OverridableProperties parentProperties);

  public Map<Object, Object> getPropertiesMap();

  public Map<Object, Property> getMap();

}

