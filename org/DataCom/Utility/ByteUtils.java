package org.DataCom.Utility;

import java.math.*;
import java.nio.ByteBuffer;

/*
 * Byte Utils
 */
public class ByteUtils {
    /*
     * Get in
     */
    public static int intVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 4) {
	    throw new ByteTranslationException("Integer translation must take 4 bytes");
	}
	return new BigInteger(bytes).intValue();
    }

    public static long longVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 8) {
	    throw new ByteTranslationException("Long translation: needs 8 bytes, given "
					       + bytes.length+" bytes");
	}
	ByteBuffer buffer = ByteBuffer.wrap(bytes);
	return buffer.getLong();
    }
}


/*
 * Because new exceptions are fun
 */
class ByteTranslationException extends Exception {
    public ByteTranslationException(String s) {
	super(s);
    }
}
