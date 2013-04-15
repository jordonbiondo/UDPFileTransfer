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

    public UFTPacket[] fileDataPackets = new UFTPacket[1];

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
	    this.packetResponder = new Thread(new UFTClientResponder(this));

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


    /*
     * Start the responder thrad if it's dead
     */
    public void notifyResponder() {
	if (packetResponder.getState() == Thread.State.TERMINATED) {
	    packetResponder = new Thread(new UFTClientResponder(this));
	    packetResponder.start();
       	}
    }


    @Override
    public void start() {
	packetSender.start();
	packetListener.start();
	packetResponder.start();
    }


    /*
     * Write the received data to a file
     */
    public void writeFileAndEnd() {

	Debug.pln("Ending client, writing file");

	endConnection();

	try {
	    File output = new File(currentRequest+"OUT");
	    FileOutputStream stream = new FileOutputStream(output);
	    for (UFTPacket packet : this.fileDataPackets) {
		stream.write(packet.getData());
	    }
	    stream.close();

	} catch (FileNotFoundException fnfe) {
	    Debug.err("file not found " + fnfe.getMessage());
	    System.exit(1);
	} catch(IOException ioe) {
	    Debug.err("Could not write file " + ioe.getMessage());
	    System.exit(1);
	} finally {
	    System.exit(0);
	}
    }


    private void endConnection() {
	UFTHeader header = new UFTHeader(this.listenPort, this.sendPort, UFTHeaderType.END, 1, 1, 1);
	UFTPacket packet = new UFTPacket(header, new byte[1]);
	this.sendQueue.clear();
	enqueueForSend(packet);
	//
	notifySpeaker();
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
