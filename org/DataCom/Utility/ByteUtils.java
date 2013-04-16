package org.DataCom.Utility;

import java.math.*;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * Byte Utils
 */
public class ByteUtils {

    /**
     * Get int value fo 4 bytes
     */
    public static int intVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 4) {
	    throw new ByteTranslationException("int", 4, bytes.length);
	}
	return new BigInteger(bytes).intValue();
    }


    public static byte[] byteVal(int num) {
	ByteBuffer buffer = ByteBuffer.allocate(4);
	buffer.putInt(num);
	return buffer.array();

    }


    /**
     * Get long value of 8 bytes
     */
    public static long longVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 8) {
	    throw new ByteTranslationException("long", 8, bytes.length);
	}
	ByteBuffer buffer = ByteBuffer.wrap(bytes);
	return buffer.getLong();
    }



    /**
     * Get the crc32 checksum of an array of bytes
     */
    public static long computeCRCChecksum(final byte[] data) {
	// mmmm look at that double brace badassery
	return new CRC32() {{
	    update(data);
	}}.getValue();
    }

}

