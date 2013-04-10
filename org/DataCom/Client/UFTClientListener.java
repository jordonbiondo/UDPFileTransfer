package org.DataCom.Client;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UFTClientListener implements UFTPacketListener {

    /*
     * Parent server
     */
    UDPFileTransferClient client;


    /*
     * Client socket
     */
    DatagramSocket clientSocket;


    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * UFT Packet Listener
     */
    public UFTClientListener(UDPFileTransferClient client) throws SocketException{
	this.client = client;
	this.clientSocket = new DatagramSocket(client.getSendPort());
    }


    /*
     * Run the thread
     */
    public void run() {
	byte[] recveivedData;
	while(client.shouldListen) {
	    recveivedData = new byte[UFTPacket.BYTE_SIZE];
	    try {
		System.out.println("receiving......");
		//make a data packet
		DatagramPacket dataPacket = new DatagramPacket(recveivedData, recveivedData.length);
		// try to fill it
		clientSocket.receive(dataPacket);
		onPacketReceive(dataPacket);
		System.out.println("received");
	    } catch(Exception e) {
		e.printStackTrace();
	    }

	}
    }


    public void onPacketReceive(DatagramPacket dataPacket) {
	// // fill packet wrapper
	// try {
	//     UFTPacket packet = new UFTPacket(dataPacket);
	//     client.enqueueReaction(packet);
	// } catch(MalformedPacketException mpe) {
	//     onPacketError(dataPacket, mpe);
	// }
    }


    public void onPacketError(DatagramPacket packet, Exception e) {
	System.out.println("Shit, packet error");
	e.printStackTrace();
    }


}
