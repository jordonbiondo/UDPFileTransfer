package org.DataCom.Utility;


/*
 * Exception thrown while parsing packets
 */
public class MalformedPacketException extends Exception {

    /*
     * New exception with message
     */
    public MalformedPacketException(String s) {
	super(s);
    }
}
