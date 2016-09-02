/**
 *  Copyright 2011 LiveRamp
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.liveramp.commons.util;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestBytesUtils {
  private static final byte[] A = {1, 2, 3};

  private static final byte[] B = {1, 1, 3};
  private static final byte[] C = {1, 3, 3};
  private static final byte[] D = {1, 2, 4};
  private static final byte[] E = {(byte) 0x80, 2, 4};

  @Test
  public void testCompareBytes() throws Exception {
    assertEquals(-1, BytesUtils.compareBytesUnsigned(B, 0, A, 0, 3));
    assertEquals(1, BytesUtils.compareBytesUnsigned(A, 0, B, 0, 3));
    assertEquals(-1, BytesUtils.compareBytesUnsigned(A, 0, C, 0, 3));
    assertEquals(-1, BytesUtils.compareBytesUnsigned(A, 1, C, 1, 2));
    assertEquals(0, BytesUtils.compareBytesUnsigned(A, 1, D, 1, 1));

    assertEquals(-1, BytesUtils.compareBytesUnsigned(A, 0, E, 0, 3));
    assertEquals(1, BytesUtils.compareBytesUnsigned(E, 0, A, 0, 3));
  }

  @Test
  public void testException() {
    try {
      BytesUtils.compareBytesUnsigned(A, 1, B, 0, 3);
      fail("Should fail with an exception");
    } catch (Exception e) {
    }
    try {
      BytesUtils.compareBytesUnsigned(A, 0, B, 1, 3);
      fail("Should fail with an exception");
    } catch (Exception e) {
    }
  }

  @Test
  public void testDeepCopy() {
    // Without allocation
    ByteBuffer copyA = BytesUtils.byteBufferDeepCopy(ByteBuffer.wrap(A));
    assertEquals(0, BytesUtils.compareBytesUnsigned(copyA, ByteBuffer.wrap(A)));

    // With allocation capable
    ByteBuffer copyB = null;

    byte[] v1 = {1};
    copyB = BytesUtils.byteBufferDeepCopy(ByteBuffer.wrap(v1), copyB);
    assertEquals(0, BytesUtils.compareBytesUnsigned(ByteBuffer.wrap(v1), copyB));

    byte[] v2 = {1, 2 , 3};
    copyB = BytesUtils.byteBufferDeepCopy(ByteBuffer.wrap(v2), copyB);
    assertEquals(0, BytesUtils.compareBytesUnsigned(ByteBuffer.wrap(v2), copyB));
  }

  @Test
  public void testBytesToHexString() {
    byte[] b = {0x0, 0x1, 0x8, 0x10, (byte)0xf0, (byte)0xaf};
    String s = BytesUtils.bytesToHexString(ByteBuffer.wrap(b));
    assertEquals("00 01 08 10 f0 af", s);
  }

  @Test
  public void testHexStringToBytes() {
    String s = "00 01 08 10 f0 af";
    ByteBuffer expectedB = ByteBuffer.wrap(new byte[] {0x0, 0x1, 0x8, 0x10, (byte)0xf0, (byte)0xaf});
    assertEquals(expectedB, BytesUtils.hexStringToBytes(s));
  }
}
