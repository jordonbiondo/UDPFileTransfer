package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;




/***
 * Server listener
 */
public class UFTServerListener implements UFTPacketListener {

    /**
     * Parent server
     */
    UDPFileTransferServer server;




    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /**
     * UFT Packet Listener
     */
    public UFTServerListener(UDPFileTransferServer server) throws SocketException{
	this.server = server;
	this.server.listenSocket = new DatagramSocket(server.getListenPort());
    }


    /**
     * Run the thread
     */
    public void run() {
	byte[] recveivedData;
	while(server.shouldListen) {
	    recveivedData = new byte[UFTPacket.BYTE_SIZE];
	    try {
		//make a data packet
		DatagramPacket dataPacket = new DatagramPacket(recveivedData, recveivedData.length);
		// try to fill it
		this.server.listenSocket.receive(dataPacket);
		onPacketReceive(dataPacket);
	    } catch(Exception e) {
		e.printStackTrace();
	    }
	}
    }


    /**
     * When a packet is received, do...
     */
    public void onPacketReceive(DatagramPacket dataPacket) {
	try {
	    UFTPacket packet = new UFTPacket(dataPacket);
	    //Debug.pln("Received packet, checksum: "+packet.getHeader().getChecksum());
	    Debug.pln("Received packet: "+packet.getHeader().getChecksum()+ " | " + packet.getHeader().type.name());
	    //Debug.pln("Checksum is "+ (UFTPacket.checksumIsValid(packet) ? "valid" : "INCORRECT"));
	    //Debug.pln("Data is "+ packet.getDataAsString());
	    System.out.println("Received: "+packet.simpleString());
	    if (UFTPacket.checksumIsValid(packet)) {
		this.server.setFriendAddress(dataPacket.getAddress());
		this.server.enqueueReaction(packet);
		this.server.notifyResponder();
	    } else {
	    	throw new MalformedPacketException("Checksum validation failure!");
	    }
	} catch(MalformedPacketException mpe) {
	    onPacketError(dataPacket, mpe);
	}
    }


    /**
     * When a packet contains an error do...
     */
    public void onPacketError(DatagramPacket packet, Exception e) {
	Debug.err(e.getMessage());
	e.printStackTrace();
    }


}
