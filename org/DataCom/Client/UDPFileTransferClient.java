package org.DataCom.Client;


import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UDPFileTransferClient extends UDPFileTransferNode {

    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////

    private String currentRequest;

    /*
     * New Client
     */
    public UDPFileTransferClient(int listenPort, int sendPort, InetAddress serverAddress) {
	super();
	try {
	    // args
	    this.listenPort = listenPort;
	    this.sendPort = sendPort;
	    this.friendAddress = serverAddress;


	    this.sendSocket = new DatagramSocket();
	    // threads
	    this.packetListener = new Thread(new UFTClientListener(this));
	    this.packetSender = new Thread(new UFTClientSpeaker(this));

	} catch (SocketException e) {
	    Debug.err("Cannot start client on port " + sendPort);
	    e.printStackTrace();
	}

    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////


    /*
     * Request a file from the server
     */
    public void requestAFile() {
	System.out.print("Enter file name: ");
	String input = new Scanner(System.in).next();
	this.currentRequest = input;
	UFTHeader header = new UFTHeader(this.listenPort, this.sendPort, UFTHeaderType.GET, 1, 1, input.getBytes().length);
	UFTPacket packet = new UFTPacket(header, input.getBytes());
	enqueueForSend(packet);
	notifySpeaker();
    }


    /*
     * Start up the speaker thread if it's dead
     */
    public void notifySpeaker() {
	if (packetSender.getState() == Thread.State.TERMINATED) {
	    packetSender = new Thread(new UFTClientSpeaker(this));
	    packetSender.start();
	}
    }

    // /////////////////////////////////////////////////////////////////
    //   Client main
    // /////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
	try {

	    UDPFileTransferClient client = new UDPFileTransferClient(9292, 9898, InetAddress.getByName("127.0.0.1"));
	    client.start();
	    client.requestAFile();
	} catch (UnknownHostException uhe) {
	    Debug.err(uhe.getMessage());
	}

    }



}
