package org.DataCom.Utility;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

/*
 * UFT Packet.
 */
public class UFTPacket {


    /*
     * Header
     */
    private UFTHeader header;

    /*
     * Packet data
     */
    private byte[] data;



    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////

    /*
     * Create a UFTPacket given a DatagramPacket containing UFT data
     */
    public UFTPacket(DatagramPacket packet) throws MalformedPacketException {
	// throw exception is the packet is too short
	if (packet.getData().length < UFTHeader.BYTE_SIZE) {
	    throw new MalformedPacketException("packet didn't have enough data for a header");
	}

	// get all the bytes
	byte[] entireData = packet.getData();

	// header bytes are from 0 - header byte size
	byte[] headerData = Arrays.copyOfRange(entireData, 0, UFTHeader.BYTE_SIZE);

	// the rest of the bytes is the data
	byte[] packetData = Arrays.copyOfRange(entireData, UFTHeader.BYTE_SIZE, entireData.length);

	// get the packet's type
	UFTHeaderType packetType = UFTHeaderType.fromBytes(Arrays.copyOfRange(headerData, 0, 4));

	try {
	    // get the source port
	    int sourcePort = ByteUtils.intVal(Arrays.copyOfRange(headerData, 4, 8));

	    // get the destination port
	    int destinationPort = ByteUtils.intVal(Arrays.copyOfRange(headerData, 8, 12));

	    // get the length of the data
	    int dataSize = ByteUtils.intVal(Arrays.copyOfRange(headerData, 12, 16));

	    // get the chucksum
	    long checksum = ByteUtils.longVal(Arrays.copyOfRange(headerData, 16, 24));

	    // get the sequence number
	    int packetNumber = ByteUtils.intVal(Arrays.copyOfRange(headerData, 24, 28));

	    // get the total number of packets
	    int totalPackets = ByteUtils.intVal(Arrays.copyOfRange(headerData, 28, UFTHeader.BYTE_SIZE));
	    // build the header with the extracted info
	    UFTHeader header = new UFTHeader(sourcePort, destinationPort, packetType,
					     packetNumber, totalPackets, dataSize);
	    header.overrideChecksum(checksum);
	    // set this instances header and data
	    this.header = header;
	    this.data = packetData;
	    
	} catch (ByteTranslationException bte) {
	    throw new MalformedPacketException("Error in byte translation: " + bte.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }


    /*
     * Create a UFTPacket from a header and data.
     */
    public UFTPacket(UFTHeader header, byte[] data) {
	this.header = header;
	this.data = data;

    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////


    /*
     * Return the packet header.
     */
    public UFTHeader getHeader() {
	return this.header;
    }


    /*
     * Return the packets data bytes.
     */
    public byte[] getData() {
	return this.data;
    }


    /*
     * Return the string representation of the data byte array.
     */
    public String getDataAsString() {
	return new String(data);
    }


    /*
     * To String.
     */
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("Header-----------------------\n");
	buffer.append(this.header.toString());
	buffer.append("Data-------------------------\n");
	for (byte b : this.data) {
	    buffer.append(b + " ");
	}
	buffer.append("\n");
	return buffer.toString();
    }
}
