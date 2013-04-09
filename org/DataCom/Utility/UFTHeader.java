package org.DataCom.Utility;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.zip.CRC32;


/*
 * UFT Header.
 */
public class UFTHeader {

    /*
     * The size in byte of a UFT Header
     * Subject to change
     */
    public static final int BYTE_SIZE = 32;

    /*
     * Header type.
     */
    UFTHeaderType type;


    /*
     * Source Port.
     */
    public final int sourcePort;


    /*
     * Destination Port.
     */
    public final int destPort;


    /*
     * Sequence number
     */
    public final int packetNumber;


    /*
     * Total Packets
     */
    public final int totalPackets;


    /*
     * Data size
     */
    public final int  dataSize;


    /*
     * Checksum
     */
    private long checksum = 0;

    // /////////////////////////////////////////////////////////////////
    //  Constructors
    // /////////////////////////////////////////////////////////////////

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


    // /////////////////////////////////////////////////////////////////
    //  Methods
    // /////////////////////////////////////////////////////////////////


    /*
     * Computer the checksum with CRC32
     */
    public void computeChecksum(final byte[] data) {
	this.checksum = new CRC32() {{
	    update(data);
	}}.getValue();
    }


    /*
     * Get Header checksum
     */
    public long getChecksum() {
	return this.checksum;
    }


    /*
     * Override the current checksum
     */
    public void overrideChecksum(long checksum) {
	this.checksum = checksum;
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


    /*
     * To String.
     */
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("Type: ");
	buffer.append(this.type.name());
	buffer.append("\n");
	buffer.append("Source: ");
	buffer.append(this.sourcePort);
	buffer.append("\n");
	buffer.append("Dest: ");
	buffer.append(this.destPort);
	buffer.append("\n");
	buffer.append("Num: ");
	buffer.append(this.packetNumber);
	buffer.append("\n");
	buffer.append("Total: ");
	buffer.append(this.totalPackets);
	buffer.append("\n");
	buffer.append("Size: ");
	buffer.append(this.dataSize);
	buffer.append("\n");
	buffer.append("Checksum: ");
	buffer.append(this.getChecksum());
	buffer.append("\n");
	return buffer.toString();
    }
}
