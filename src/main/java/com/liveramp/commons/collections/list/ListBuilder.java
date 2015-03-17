package com.liveramp.commons.collections.list;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

//  I hate hate hate generics varargs warnings, so I'll make this
public class ListBuilder<T> {

  private final List<T> list;

  public ListBuilder(){
    list = Lists.newArrayList();
  }

  public ListBuilder<T> add(T item){
    list.add(item);
    return this;
  }

  public List<T> get() {
    return list;
  }

  public ImmutableList<T> asImmutableList() {
    return ImmutableList.copyOf(list);
  }
}
