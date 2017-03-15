package com.liveramp.commons.collections.list;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

//  I hate hate hate generics varargs warnings, so I'll make this
public class ListBuilder<T> {

  private final List<T> list;

  public ListBuilder() {
    list = Lists.newArrayList();
  }

  public ListBuilder<T> add(T item) {
    list.add(item);
    return this;
  }

  public ListBuilder<T> addAll(Collection<T> item) {
    list.addAll(item);
    return this;
  }

  public ListBuilder<T> addAll(Iterable<T> item) {
    return addAll(Lists.newArrayList(item));
  }

  public ListBuilder<T> addAll(T[] item) {
    return addAll(Lists.newArrayList(item));
  }

  public List<T> get() {
    return list;
  }

  public ImmutableList<T> asImmutableList() {
    return ImmutableList.copyOf(list);
  }
}
