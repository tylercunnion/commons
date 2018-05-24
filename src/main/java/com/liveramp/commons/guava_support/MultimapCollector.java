package com.liveramp.commons.guava_support;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class MultimapCollector<T, K, V> implements Collector<T, Multimap<K, V>, Multimap<K, V>> {

  private Supplier<Multimap<K, V>> emptyMultimapSupplier;
  private final Function<T, K> keyMapper;
  private final Function<T, V> valueMapper;

  public MultimapCollector(Supplier<Multimap<K, V>> emptyMultimapSupplier,
                           Function<T, K> keyMapper,
                           Function<T, V> valueMapper) {
    this.emptyMultimapSupplier = emptyMultimapSupplier;
    this.keyMapper = keyMapper;
    this.valueMapper = valueMapper;
  }

  /**
   * Convenience method that accumulates to a {@link com.google.common.collect.SetMultimap}.
   *
   * Also see {@link #toCustomMultimap(Supplier, Function, Function)}
   *
   * @param keyMapper the function that produces the key. See {@link Function#identity()} for utility
   * @param valueMapper the function that produces the value. See {@link Function#identity()} for utility
   * @param <T> the type of the incoming value
   * @param <K> the type of the key stored
   * @param <V> the type of the value stored
   * @return a {@link MultimapCollector} that accumulates into a {@link com.google.common.collect.SetMultimap}
   */
  public static <T, K, V> MultimapCollector<T, K, V> toSetMultimap(
      Function<T, K> keyMapper,
      Function<T, V> valueMapper) {
    return toCustomMultimap(HashMultimap::create, keyMapper, valueMapper);
  }

  /**
   * Convenience method that accumulates to a {@link com.google.common.collect.ListMultimap}.
   *
   * Also see {@link #toCustomMultimap(Supplier, Function, Function)}
   *
   * @param keyMapper the function that produces the key. See {@link Function#identity()} for utility
   * @param valueMapper the function that produces the value. See {@link Function#identity()} for utility
   * @param <T> the type of the incoming value
   * @param <K> the type of the key stored
   * @param <V> the type of the value stored
   * @return a {@link MultimapCollector} that accumulates into a {@link com.google.common.collect.ListMultimap}
   */
  public static <T, K, V> MultimapCollector<T, K, V> toListMultimap(
      Function<T, K> keyMapper,
      Function<T, V> valueMapper) {
    return toCustomMultimap(ArrayListMultimap::create, keyMapper, valueMapper);
  }

  /**
   * @param multiMapSupplier a {@link Supplier} that produces the initial {@link Multimap}.
   *                         It must create a fresh instance each time.
   * @param keyMapper the function that produces the key. See {@link Function#identity()} for utility
   * @param valueMapper the function that produces the value. See {@link Function#identity()} for utility
   * @param <T> the type of the incoming value
   * @param <K> the type of the key stored
   * @param <V> the type of the value stored
   * @return a {@link MultimapCollector} that accumulates into a {@link com.google.common.collect.Multimap}
   */
  public static <T, K, V> MultimapCollector<T, K, V> toCustomMultimap(
      Supplier<Multimap<K, V>> multiMapSupplier,
      Function<T, K> keyMapper,
      Function<T, V> valueMapper) {
    return new MultimapCollector<>(multiMapSupplier, keyMapper, valueMapper) ;
  }


  @Override
  public Supplier<Multimap<K, V>> supplier() {
    return emptyMultimapSupplier;
  }

  @Override
  public BiConsumer<Multimap<K, V>, T> accumulator() {
    return (m, t) -> m.put(keyMapper.apply(t), valueMapper.apply(t));
  }

  @Override
  public BinaryOperator<Multimap<K, V>> combiner() {
    return (l, r) -> {
      l.putAll(r);
      return l;
    };
  }

  @Override
  public Function<Multimap<K, V>, Multimap<K, V>> finisher() {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics() { return Sets.newHashSet(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED); }
}
