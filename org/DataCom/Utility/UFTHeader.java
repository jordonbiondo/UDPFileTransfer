package org.DataCom.Utility;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.ByteBuffer;

public class UFTHeader {


    UFTHeaderType type; 


    public final int sourcePort;


    public final int destPort;


    public final int packetNumber;


    public final int totalPackets;


    public final long dataSize;


    private long checksum = 0;



    /*
     * UFTHeader
     */
    public UFTHeader(int sourcePort, int destPort, UFTHeaderType type,
		     int packetNumber, int totalPackets, long dataSize) {
	this.type = type;
	this.sourcePort = sourcePort;
	this.destPort = destPort;
	this.packetNumber = packetNumber;
	this.totalPackets = totalPackets;
	this.dataSize = dataSize;
    }


    public void computeChecksum() {
	// Do some computations here.
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
	ByteBuffer buff = ByteBuffer.allocate(36);
	buff.putInt(type.ordinal());
	buff.putInt(sourcePort);
	buff.putInt(destPort);
	buff.putLong(dataSize);
	buff.putLong(checksum);
	buff.putInt(packetNumber);
	buff.putInt(totalPackets);
	return buff.array();
	// byte[] bytes = new byte[buff.position()];
	// for (int i = 0; i < bytes.length; i++) {
	//     bytes[i] = buff.get(i);
	// }
	// return bytes;
    }

}
