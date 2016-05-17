package com.liveramp.commons.collections.nested_map;

public class TwoNestedCountingMap <K1, K2> extends TwoNestedMap<K1, K2, Long>{

  public TwoNestedCountingMap(Long defaultValue){
    super(defaultValue);
  }

  public Long incrementAndGet(K1 k1, K2 k2, Long amount){
    Long previous = get(k1, k2);
    long newV = previous.longValue() + amount.longValue();
    put(k1, k2, newV);
    return newV;
  }

  public void incrementAll(TwoNestedMap<K1, K2, Long> other){
    for (Entry<K1, K2, Long> entry : other) {
      incrementAndGet(entry.getK1(), entry.getK2(), entry.getValue());
    }
  }

}
