package org.DataCom.Utility;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.ByteBuffer;


public class UFTPacket {

    private UFTHeader header;

    private byte[] data;


    /*
     * Create a UFTPacket given a DatagramPacket containing UFT data
     */
    public UFTPacket(DatagramPacket packet) throws MalformedPacketException {
	
    }


    /*
     * Create a UFTPacket from a header and data
     */
    public UFTPacket(UFTHeader header, byte[] data) {

	this.header = header;
	this.data = data;

    }

    /*
     * Return the packet header
     */
    public UFTHeader getHeader() {
	return this.header;
    }

    /*
     * Return the packets data bytes
     */
    public byte[] getData() {
	return this.data;
    }

    /*
     * Return the string representation of the data byte array
     */
    public String getDataAsString() {
	return new String(data);
    }



}
