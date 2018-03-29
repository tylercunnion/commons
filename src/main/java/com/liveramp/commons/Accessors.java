package com.liveramp.commons;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class Accessors {

  /**
   * Use {@link #firstOrEmpty(Iterable)}
   */
  @Deprecated
  public static <T> Optional<T> firstOrAbsent(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iter = c.iterator();

    if (!iter.hasNext()) {
      return Optional.absent();
    } else {
      return Optional.of(first(c));
    }
  }

  public static <T> java.util.Optional<T> firstOrEmpty(Iterable<T> c) {
    return java.util.Optional.ofNullable(firstOrAbsent(c).orNull());
  }

  public static <T> T first(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iter = c.iterator();
    Preconditions.checkArgument(iter.hasNext(), "Empty collection");

    return iter.next();
  }

  public static <T> T second(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iter = c.iterator();
    Preconditions.checkArgument(iter.hasNext(), "Empty iterable");

    iter.next();
    Preconditions.checkArgument(iter.hasNext(), "Not enough elements");

    return iter.next();
  }


  /**
   * Verifies an iterable object contains a single value and returns that value.
   * The provided iterable object is expected to contain one and only one item.
   * Any violation results in an exception.
   *
   * @param c The iterable object.
   * @param <T> The type of objects in {@code c}.
   * @return
   * The value of the item in {@code c}, of type {@code <T>}.
   * This value is allowed to be {@code null}.
   *
   * @throws RuntimeException
   * If {@code c} is null, is empty, or has more than one item.
   * Note that {@link RuntimeException} is a non-checked exception and thus does not
   * require a try/catch in the caller.
   */
  public static <T> T only(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iterator = c.iterator();
    Preconditions.checkArgument(iterator.hasNext(), "Empty iterable");

    T val = iterator.next();

    if (iterator.hasNext()) {
      throw new RuntimeException(
          String.format(
              "Iterable has more than one element. First element: %s, Second element: %s, Has third element: %s",
              val,
              iterator.next(),
              iterator.hasNext()
          )
      );
    }

    return val;
  }


  /**
   * Verifies an array contains a single value and returns that value.
   * The provided array is expected to contain one and only one entry.
   * Any violation results in an exception.
   *
   * @param ts The array object.
   * @param <T> The type of objects in {@code ts}.
   * @return
   * The value in {@code ts[0]} of type {@code <T>}.
   * This value is allowed to be {@code null}.
   *
   * @throws RuntimeException
   * If {@code ts} is null or does not have length one.
   * Note that {@link RuntimeException} is a non-checked exception and thus does not
   * require a try/catch in the caller.
   */

  public static <T> T only(T[] ts) {
    Preconditions.checkNotNull(ts, "Array is null");
    Preconditions.checkArgument(ts.length == 1, String.format("Array has %d elements instead of just 1", ts.length));

    return ts[0];
  }

  public static <T> Optional<T> onlyOrAbsent(Iterable<T> c) {
    Preconditions.checkNotNull(c, "Null iterable");

    Iterator<T> iterator = c.iterator();

    if (!iterator.hasNext()) {
      return Optional.absent();
    }

    T val = iterator.next();

    if (iterator.hasNext()) {
      throw new IllegalArgumentException(
          String.format(
              "Iterable has more than one element. First element: %s, Second element: %s, Has third element: %s",
              val,
              iterator.next(),
              iterator.hasNext()
          )
      );
    }

    return Optional.of(val);
  }

  public static <T> java.util.Optional<T> onlyOrEmpty(Iterable<T> c) {
    final Optional<T> maybeOnly = onlyOrAbsent(c);
    if (maybeOnly.isPresent()) {
      return java.util.Optional.of(maybeOnly.get());
    } else {
      return java.util.Optional.empty();
    }
  }

  public static <T> T last(List<T> c) {
    Preconditions.checkNotNull(c, "Null collection");
    Preconditions.checkArgument(!c.isEmpty(), "Empty collection");
    return c.get(c.size() - 1);
  }

}
