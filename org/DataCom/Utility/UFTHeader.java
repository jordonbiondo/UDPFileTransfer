package org.DataCom.Utility;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.ByteBuffer;



public class UFTHeader {


    /*
     * The size in byte of a UFT Header
     * Subject to change
     */
    public static final int BYTE_SIZE = 32;


    UFTHeaderType type;


    public final int sourcePort;


    public final int destPort;


    public final int packetNumber;


    public final int totalPackets;


    public final int  dataSize;


    private long checksum = 0;



    /*
     * UFTHeader
     */
    public UFTHeader(int sourcePort, int destPort, UFTHeaderType type,
		     int packetNumber, int totalPackets, int dataSize) {
	this.type = type;
	this.sourcePort = sourcePort;
	this.destPort = destPort;
	this.packetNumber = packetNumber;
	this.totalPackets = totalPackets;
	this.dataSize = dataSize;
    }


    public void computeChecksum(byte[] data) {
	// set the checksum based on the data given
	//
	//
	//
	//            Do this.
	//
	//
	this.checksum = 0;
    }


    /*
     * Get Header checksum
     */
    public long getChecksum() {
	return this.checksum;
    }


    /*
     * Get Header as Byte Array.
     */
    public byte[] toBytes() {
	ByteBuffer buff = ByteBuffer.allocate(UFTHeader.BYTE_SIZE);
	buff.putInt(type.ordinal());
	buff.putInt(sourcePort);
	buff.putInt(destPort);
	buff.putInt(dataSize);
	buff.putLong(checksum);
	buff.putInt(packetNumber);
	buff.putInt(totalPackets);
	return buff.array();
    }

}
