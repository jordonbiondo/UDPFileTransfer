package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;

public class UFTServerSpeaker implements UFTPacketSpeaker {

    /*
     * Parent server
     */
    UDPFileTransferServer server;


    /*
     * Server socket
     */
    DatagramSocket serverSocket;




    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * UFT Packet Listener
     */
    public UFTServerSpeaker(UDPFileTransferServer server) {
	this.server = server;
    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////


    /*
     * Is there Data to send?
     */
    public boolean hasData() {
	// true if the servers reactionqueu has content
	return ! server.getSendQueue().isEmpty();
    }


    /*
     * Try to send a UFTPacket
     */
    public void sendPacket(UFTPacket packet, int port, InetAddress address) throws IOException{
	this.server.prepareSendSocket(port);
	// ensure checksum
	packet.prepareForSend();
	//create datagram packet
	byte[] packetData = packet.toBytes();
	DatagramPacket sendPacket = new DatagramPacket(packetData, packetData.length, address, port);
	// send it
	Debug.pln("sending packet: "+ packet);
	this.server.sendSocket.send(sendPacket);
    }



    /*
     * Main Run loop
     */
    public void run() {
	while(this.hasData()) {
	    UFTPacket next = server.getSendQueue().poll();
	    try {
		this.sendPacket(next, server.getSendPort(), server.getFriendAddress());
	    } catch (IOException se) {
		Debug.err("Failed to send packet, requeueing");
		this.server.enqueueForSend(next);
	    }
	}


    }


}
