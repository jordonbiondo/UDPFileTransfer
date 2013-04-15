package org.DataCom.Utility;

import java.math.*;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/*
 * Byte Utils
 */
public class ByteUtils {

    /*
     * Get int value fo 4 bytes
     */
    public static int intVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 4) {
	    throw new ByteTranslationException("int", 4, bytes.length);
	}
	return new BigInteger(bytes).intValue();
    }


    /*
     * Get long value of 8 bytes
     */
    public static long longVal(byte[] bytes) throws ByteTranslationException {
	if (bytes.length != 8) {
	    throw new ByteTranslationException("long", 8, bytes.length);
	}
	ByteBuffer buffer = ByteBuffer.wrap(bytes);
	return buffer.getLong();
    }



    /*
     * Get the crc32 checksum of an array of bytes
     */
    public static long computeCRCChecksum(final byte[] data) {
	// mmmm look at that double brace badassery
	return new CRC32() {{
	    update(data);
	}}.getValue();
    }

}


/*
 * Because new exceptions are fun
 */
class ByteTranslationException extends Exception {

    /*
     * New exception with message
     */
    public ByteTranslationException(String s) {
	super(s);
    }


    /*
     * Formatted message for bad byte count;
     */
    public ByteTranslationException(String name, int expected, int given) {
	super("Error in "+name+" translation: needed "+expected+" bytes, given: "+given);
    }
}
