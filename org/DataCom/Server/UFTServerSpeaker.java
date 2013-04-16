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
	if(packet.getHeader().type == UFTHeaderType.DAT){
	    if (this.server.acknowledgements[packet.getHeader().packetNumber -1]) return;
	}

	this.server.prepareSendSocket(port);
	// ensure checksum
	packet.prepareForSend();
	//create datagram packet
	byte[] packetData = packet.toBytes();
	DatagramPacket sendPacket = new DatagramPacket(packetData, packetData.length, address, port);
	// send it

	Random random = new Random();
	Debug.pln("sending packet: "+packet.getHeader().getChecksum()+ " | " + packet.getHeader().type.name());

	//if (random.nextInt(2) == 0 )
	    this.server.sendSocket.send(sendPacket);
	this.server.enqueueForSend(packet);

    }



    /*
     * Main Run loop
     */
    public void run() {
	while(this.hasData() && this.server.shouldSend) {
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
