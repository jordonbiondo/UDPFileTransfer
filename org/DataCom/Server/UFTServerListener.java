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
    public UFTServerListener(UDPFileTransferServer server) throws SocketException{
	this.server = server;
	this.serverSocket = new DatagramSocket(server.getListenPort());
    }


    /*
     * Run the thread
     */
    public void run() {
	byte[] recveivedData;
	while(server.shouldListen) {
	    recveivedData = new byte[UFTPacket.BYTE_SIZE];
	    try {
		System.out.println("receiving......");
		//make a data packet
		DatagramPacket dataPacket = new DatagramPacket(recveivedData, recveivedData.length);
		// try to fill it
		serverSocket.receive(dataPacket);
		onPacketReceive(dataPacket);
		System.out.println("received");
	    } catch(Exception e) {
		e.printStackTrace();
	    }

	}
    }


    public void onPacketReceive(DatagramPacket dataPacket) {
	// fill packet wrapper
	try {
	    UFTPacket packet = new UFTPacket(dataPacket);
	    server.enqueueReaction(packet);
	} catch(MalformedPacketException mpe) {
	    onPacketError(dataPacket, mpe);
	}
    }


    public void onPacketError(DatagramPacket packet, Exception e) {
	System.out.println("Shit, packet error");
	e.printStackTrace();
    }


}
