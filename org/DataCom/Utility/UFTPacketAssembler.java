package org.DataCom.Utility;

import java.util.*;
import java.io.*;
import java.net.*;


public class UFTPacketAssembler {

    /*
     * Packet Source Port
     */
    int sourcePort;


    /*
     * Packet Destination Port
     */
    int destPort;


    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////

    /*
     * Create a new Packet Assembler
     */
    public UFTPacketAssembler(int sourcePort, int destPort) {
	this.sourcePort = sourcePort;
	this.destPort = destPort;
    }

    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////

    /*
     * Create a Datagram Packet and fill it with a UFTHeader and the given data
     */
    public DatagramPacket assemble(UFTHeaderType type, int packetNumber, int totalPackets, byte[] data) {
	// Create the packet's header.
	UFTHeader packetHeader = new UFTHeader(this.sourcePort, this.destPort, type,
					   packetNumber, totalPackets, data.length);

	// compute the checksum for and place in header
	packetHeader.computeChecksum(data);

	// Create array for packet bytes sized to hold the data and a header
	byte[] packetBytes = new byte[data.length + UFTHeader.BYTE_SIZE];

	// Get headers bytes
	byte[] headerBytes = packetHeader.toBytes();
	// fill array beginning with the header bytes
	for (int i = 0; i < UFTHeader.BYTE_SIZE; i++) {
	    packetBytes[i] = headerBytes[i];
	}
	// fill the rest of the array with the data
	for (int i = UFTHeader.BYTE_SIZE; i < packetBytes.length; i++) {
	    packetBytes[i] = data[i-UFTHeader.BYTE_SIZE];
	}
	// create and return a datagram packet
	return new DatagramPacket(packetBytes, packetBytes.length);
    }

}
