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
	    throw new ByteTranslationException("int", 4, bytes.length);
	}
	return new BigInteger(bytes).intValue();
    }

    public static long longVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 8) {
	    throw new ByteTranslationException("long", 8, bytes.length);
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

    public ByteTranslationException(String name, int expected, int given) {
	super("Error in "+name+" translation: needed "+expected+" bytes, given: "+given);
    }
}
