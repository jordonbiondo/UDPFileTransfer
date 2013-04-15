package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;




public class UFTServerListener implements UFTPacketListener {

    /*
     * Parent server
     */
    UDPFileTransferServer server;




    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * UFT Packet Listener
     */
    public UFTServerListener(UDPFileTransferServer server) throws SocketException{
	this.server = server;
	this.server.listenSocket = new DatagramSocket(server.getListenPort());
    }


    /*
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


    /*
     * When a packet is received, do...
     */
    public void onPacketReceive(DatagramPacket dataPacket) {
	try {
	    UFTPacket packet = new UFTPacket(dataPacket);

	    Debug.pln("Received packet, checksum: "+packet.getHeader().getChecksum());
	    Debug.pln("Checksum is "+ (UFTPacket.checksumIsValid(packet) ? "valid" : "INCORRECT"));
	    Debug.pln("Data is "+ packet.getDataAsString());
	    
	    this.server.setSendPort(packet.getHeader().sourcePort);
	    this.server.setFriendAddress(dataPacket.getAddress());

	    if (UFTPacket.checksumIsValid(packet)) {
		server.enqueueReaction(packet);
		if(server.acceptsRequest(packet.getDataAsString())) {
		    Debug.pln("Accepted");
		    File file = new File(packet.getDataAsString());
		    for (UFTPacket fPack : server.prepareFileTransmission(file)) {
			server.enqueueForSend(fPack);
		    }
		} else {
		    Debug.pln("not accepted");
		}
		server.notifySpeaker();
	    } else {
		throw new MalformedPacketException("Checksum validation failure!");
	    }
	} catch(MalformedPacketException mpe) {
	    onPacketError(dataPacket, mpe);
	}
    }


    /*
     * When a packet contains an error do...
     */
    public void onPacketError(DatagramPacket packet, Exception e) {
	Debug.err(e.getMessage());
	e.printStackTrace();
    }


}
