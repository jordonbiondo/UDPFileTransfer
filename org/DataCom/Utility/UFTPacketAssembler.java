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
	UFTHeader packetHeader = new UFTHeader(this.sourcePort, this.destPort, type,
					   packetNumber, totalPackets, data.length);

	packetHeader.computeChecksum(data);

	byte[] packetBytes = new byte[data.length + UFTHeader.BYTE_SIZE];
	byte[] headerBytes = packetHeader.toBytes();

	for (int i = 0; i < UFTHeader.BYTE_SIZE; i++) {
	    packetBytes[i] = headerBytes[i];
	}
	for (int i = UFTHeader.BYTE_SIZE; i < packetBytes.length; i++) {
	    packetBytes[i] = data[i-UFTHeader.BYTE_SIZE];
	}

	return new DatagramPacket(packetBytes, packetBytes.length);
    }
}
