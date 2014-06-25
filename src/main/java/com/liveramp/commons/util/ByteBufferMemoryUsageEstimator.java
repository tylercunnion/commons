package com.liveramp.commons.util;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class ByteBufferMemoryUsageEstimator implements MemoryUsageEstimator<ByteBuffer>, Serializable {
  @Override
  public long estimateMemorySize(ByteBuffer item) {
    // 8 bytes for ByteBufferManagedBytes itself
    // 40 bytes for the corresponding ByteBuffer
    // Plus the capacity of the underlying byte array
    return 8 + 40 + item.capacity();
  }
}
