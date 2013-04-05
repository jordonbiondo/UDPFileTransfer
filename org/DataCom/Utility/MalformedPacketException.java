package org.DataCom.Utility;


/*
 * Exception thrown while parsing packets
 */
public class MalformedPacketException extends Exception {
    
    public MalformedPacketException(String s) {
	super(s);
    }
}
