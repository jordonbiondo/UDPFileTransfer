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
	this.clientSocket = new DatagramSocket(client.getListenPort());
    }


    /*
     * Run the thread
     */
    public void run() {
	byte[] recveivedData;
	while(client.shouldListen) {
	    recveivedData = new byte[UFTPacket.BYTE_SIZE];
	    try {
		DatagramPacket dataPacket = new DatagramPacket(recveivedData, recveivedData.length);
		
		onPacketReceive(dataPacket);
		System.out.println("Listener: received");
	    } catch(Exception e) {
		e.printStackTrace();
	    }

	}
    }


    public void onPacketReceive(DatagramPacket dataPacket) {
	try {

	    UFTPacket packet = new UFTPacket(dataPacket);
	    System.out.println("Success receiving:");
	    System.out.println(packet);
	} catch (MalformedPacketException mpe) {
	    System.out.println(mpe.getMessage());

	}

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
