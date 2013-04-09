package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UFTPacketListener implements Runnable {

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
    public UFTPacketListener(UDPFileTransferServer server) throws SocketException{
	this.server = server;
	this.serverSocket = new DatagramSocket(server.getListenPort());
    }


    /*
     * Run the thread
     */
    public void run() {
	byte[] recveivedData;
	while(server.shouldListen) {
	    recveivedData = new byte[1024];
	    try {
		//make a data packet
		DatagramPacket dataPacket = new DatagramPacket(recveivedData, recveivedData.length);
		// try to fill it
		serverSocket.receive(dataPacket);
		// fill packet wrapper
		UFTPacket packet = new UFTPacket(dataPacket);
		server.enqueueReaction(packet);
	    } catch(Exception e) {

	    }

	}
    }

}
