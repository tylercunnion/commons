package com.liveramp.commons.collections.nested_map;

public class ThreeNestedCountingDoubleMap <K1, K2, K3> extends ThreeNestedMap<K1, K2, K3, Double>{

  public ThreeNestedCountingDoubleMap(Double defaultValue){
    super(defaultValue);
  }

  public Double incrementAndGet(K1 k1, K2 k2, K3 k3, Double amount){
    Double previous = get(k1, k2, k3);
    double newV = previous + amount;
    put(k1, k2, k3, newV);
    return newV;
  }
}
