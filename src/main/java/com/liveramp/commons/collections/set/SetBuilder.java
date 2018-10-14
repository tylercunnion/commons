package com.liveramp.commons.collections.set;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

public class SetBuilder<V> {

  private Set<V> set;

  public SetBuilder() {
    this(Sets.<V>newHashSet());
  }

  public SetBuilder(Set<V> set) {
    this.set = Sets.newHashSet(set);
  }

  public SetBuilder<V> add(V value) {
    set.add(value);
    return this;
  }

  public SetBuilder<V> add(V... values) {
    Collections.addAll(set, values);
    return this;
  }

  public SetBuilder<V> addAll(Collection<? extends V> other) {
    set.addAll(other);
    return this;
  }

  public Set<V> get() {
    return set;
  }

  public Set<V> asUnmodifiableSet() {
    return Collections.unmodifiableSet(set);
  }
}
