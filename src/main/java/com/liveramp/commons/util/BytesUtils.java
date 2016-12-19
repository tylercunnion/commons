/**
 * Copyright 2011 LiveRamp
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liveramp.commons.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Comparator;

import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedBytes;
import org.apache.commons.codec.binary.Hex;

public class BytesUtils {
  private static final Comparator<byte[]> BYTES_COMPARATOR = UnsignedBytes.lexicographicalComparator();
  private static final String CHARSET = "utf-8";

  public static int compareBytesUnsigned(byte[] a, int aOff, byte[] b, int bOff, int len) {
    if (len > a.length - aOff || len > b.length - bOff) {
      throw new RuntimeException("Not enough bytes left to compare!");
    }
    for (int i = 0; i < len; i++) {
      // we want our comparison to be unsigned. if we just compare the bytes,
      // it will be a signed comparison. to drop the sign, we convert the byte
      // to an int, then mask off all the upper bits. if we don't do the
      // masking, then the signed byte will just get sign-extended and remain
      // negative.
      final int ab = a[aOff + i] & 0xff;
      final int bb = b[bOff + i] & 0xff;
      if (ab > bb) {
        return 1;
      } else if (ab < bb) {
        return -1;
      }
    }
    return 0;
  }

  public static int compareBytesUnsigned(ByteBuffer a, ByteBuffer b) {
    if (a.remaining() != b.remaining()) {
      throw new RuntimeException("Cannot compare ByteBuffers that have a different number of remaining elements.");
    }
    return compareBytesUnsigned(a.array(), a.arrayOffset() + a.position(),
        b.array(), b.arrayOffset() + b.position(),
        a.remaining());
  }

  public static byte[] intToBytes(int value) {
    return new byte[]{
        (byte)(value >>> 24),
        (byte)(value >>> 16),
        (byte)(value >>> 8),
        (byte)value};
  }

  public static int bytesToInt(byte[] b) {
    return (b[0] << 24)
        + ((b[1] & 0xFF) << 16)
        + ((b[2] & 0xFF) << 8)
        + (b[3] & 0xFF);
  }

  /**
   * Or just use {@link com.google.common.primitives.Longs#toByteArray(long)}
   */
  public static byte[] longToBytes(long l) {
    return Longs.toByteArray(l);
  }

  /**
   * Or just use {@link com.google.common.primitives.Longs#fromByteArray(byte[])}
   */
  public static long bytesToLong(byte[] b) {
    return Longs.fromByteArray(b);
  }

  public static String bytesToString(byte[] b) {
    try {
      return new String(b, CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] stringToBytes(String s) {
    try {
      return s.getBytes(CHARSET);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  // Make a deep copy of the remaining bytes of the given ByteBuffer.
  // The new buffer's capacity is trimmed down to the required size (remaining bytes).
  public static ByteBuffer byteBufferDeepCopy(ByteBuffer src) {
    ByteBuffer copy = ByteBuffer.allocate(src.remaining()).put(src.slice());
    copy.flip();
    return copy;
  }

  // Does a deep copy of src into dst. Allocation is performed only if necessary.
  // Return of this function should be assigned to dst.
  public static ByteBuffer byteBufferDeepCopy(ByteBuffer src, ByteBuffer dst) {
    if (dst == null || dst.capacity() < src.remaining()) {
      dst = byteBufferDeepCopy(src);
    } else {
      dst.rewind();
      dst.limit(src.remaining());
      dst.put(src.slice());
      dst.flip();
    }
    return dst;
  }

  // Each byte is converted to its hexadecimal 2 character string
  // representation. Bytes are separated by spaces in the output.
  public static String bytesToHexString(ByteBuffer b) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < b.remaining(); ++i) {
      final int v = b.array()[b.arrayOffset() + b.position() + i] & 0xff;
      if (i > 0) {
        result.append(" ");
      }
      if (v < 16) {
        result.append("0");
      }
      result.append(Integer.toString(v, 16));
    }
    return result.toString();
  }

  // Each sequence of 2 characters is considered to be the hexadecimal
  // representation of a byte. Blanks are ignored.
  public static ByteBuffer hexStringToBytes(String hexString) {
    hexString = hexString.replaceAll("\\W+", "");
    if (hexString.length() % 2 != 0) {
      throw new RuntimeException("Input string's size must be even.");
    }
    byte[] result = new byte[hexString.length() / 2];
    for (int i = 0; i < hexString.length(); i += 2) {
      result[i / 2] = (byte)Integer.valueOf(hexString.substring(i, i + 2), 16).intValue();
    }
    return ByteBuffer.wrap(result);
  }

  /**
   * If the given ByteBuffer wraps completely its underlying byte array, return the underlying
   * byte array (no copy). Otherwise, return a deep copy of the range represented by the given
   * ByteBuffer.
   *
   * @param byteBuffer
   */
  public static byte[] byteBufferToByteArray(ByteBuffer byteBuffer) {
    if (wrapsFullArray(byteBuffer)) {
      return byteBuffer.array();
    }
    byte[] target = new byte[byteBuffer.remaining()];
    byteBufferToByteArray(byteBuffer, target, 0);
    return target;
  }

  public static int byteBufferToByteArray(ByteBuffer byteBuffer, byte[] target, int offset) {
    int remaining = byteBuffer.remaining();
    System.arraycopy(byteBuffer.array(),
        byteBuffer.arrayOffset() + byteBuffer.position(),
        target,
        offset,
        remaining);
    return remaining;
  }

  public static byte[] deepCopyByteBufferToByteArray(ByteBuffer byteBuffer) {
    byte[] result = new byte[byteBuffer.remaining()];
    System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), result, 0, byteBuffer.remaining());
    return result;
  }

  public static boolean wrapsFullArray(ByteBuffer byteBuffer) {
    return byteBuffer.hasArray()
        && byteBuffer.position() == 0
        && byteBuffer.arrayOffset() == 0
        && byteBuffer.remaining() == byteBuffer.capacity();
  }

  public static int compareBytes(byte[] b1, byte[] b2) {
    return BYTES_COMPARATOR.compare(b1, b2);
  }

  public static String encodeHex(byte[] bytes) {
    return String.valueOf(Hex.encodeHex(bytes));
  }
}
