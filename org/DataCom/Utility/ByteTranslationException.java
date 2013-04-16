package org.DataCom.Utility;

/**
 * Because new exceptions are fun
 */
public class ByteTranslationException extends Exception {

    /**
     * New exception with message
     */
    public ByteTranslationException(String s) {
	super(s);
    }


    /**
     * Formatted message for bad byte count;
     */
    public ByteTranslationException(String name, int expected, int given) {
	super("Error in "+name+" translation: needed "+expected+" bytes, given: "+given);
    }
}
