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
	this.client.listenSocket = new DatagramSocket(client.getListenPort());
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
		this.client.listenSocket.receive(dataPacket);
		onPacketReceive(dataPacket);
	    } catch(Exception e) {
		e.printStackTrace();
	    }
	}
    }


    public void onPacketReceive(DatagramPacket dataPacket) {
	try {
	    UFTPacket packet = new UFTPacket(dataPacket);
	    Debug.pln("received packet: "+packet.getHeader().getChecksum() + " | " + packet.getHeader().type.name());
	    //Debug.pln("Received packet, checksum: "+packet.getHeader().getChecksum() + "type: " + packet.getHeader().type.name());
	    //Debug.pln("Checksum is "+ (UFTPacket.checksumIsValid(packet) ? "valid" : "INCORRECT"));
	    //	    Debug.pln("Data is "+ packet.getDataAsString());
	    if (UFTPacket.checksumIsValid(packet)) {
		this.client.enqueueReaction(packet);
		this.client.notifyResponder();
	    } else {
	    	throw new MalformedPacketException("Checksum validation failure!");
	    }
	} catch (MalformedPacketException mpe) {
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
