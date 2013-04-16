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

    public static final int BYTE_SIZE = 1024 - UFTHeader.BYTE_SIZE;
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

	    // Get the data from the rest of the bytes,
	    //
	    // cuts off the zeros that datagrampacket is adding on for god knows why
	    //
	    byte[] packetData = Arrays.copyOfRange(entireData, UFTHeader.BYTE_SIZE, UFTHeader.BYTE_SIZE + dataSize);

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


    public void prepareForSend() {
	this.header.computeChecksum(this.data);
    }


    /**
     * Return the packet header.
     */
    public UFTHeader getHeader() {
	return this.header;
    }


    /**
     * Return the packets data bytes.
     */
    public byte[] getData() {
	return this.data;
    }


    /**
     * Return the string representation of the data byte array.
     */
    public String getDataAsString() {
	return new String(data);
    }


    /**
     * Return the byte representation of the packet
     */
    public byte[] toBytes() {

	byte[] packetBytes = new byte[UFTHeader.BYTE_SIZE + this.data.length];
	byte[] headerBytes = this.getHeader().toBytes();

	for (int i = 0; i < UFTHeader.BYTE_SIZE; i++) {
	    packetBytes[i] = headerBytes[i];
	}
	for (int i = UFTHeader.BYTE_SIZE; i < packetBytes.length; i++) {
	    packetBytes[i] = data[i-UFTHeader.BYTE_SIZE];
	}
	return packetBytes;
    }


    /**
     * Return true if a recompuation of the checksum comes out the same as it's current value
     */
    public static boolean checksumIsValid(UFTPacket packet) {
	return packet.getHeader().getChecksum() == ByteUtils.computeCRCChecksum(packet.getData());
    }


    public static UFTPacket createACKPacket(UFTPacket datPacket) {
	UFTHeader header = new UFTHeader(datPacket.getHeader().destPort, datPacket.getHeader().sourcePort,
					 UFTHeaderType.ACK,1, 1, 4);
	byte[] seqenceNumData = ByteUtils.byteVal(datPacket.getHeader().packetNumber);
	UFTPacket ackPacket = new UFTPacket(header, seqenceNumData);
	ackPacket.prepareForSend();
	return ackPacket;
    }


    /**
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
