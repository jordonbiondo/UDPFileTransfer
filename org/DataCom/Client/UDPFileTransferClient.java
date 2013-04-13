package org.DataCom.Client;


import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UDPFileTransferClient {

    private int sendPort;

    private InetAddress serverAddress;

    private int listenPort;

    public boolean shouldListen;

    public boolean shouldSend;

    public  DatagramSocket sendSocket;

    public DatagramSocket listenSocket;

    public Thread packetSender;

    Thread packetListener;

    /*
     * Received packet queue
     */
    private ConcurrentLinkedQueue<UFTPacket> reactionQueue;


    /*
     * Packets waiting to be sent
     */
    private ConcurrentLinkedQueue<UFTPacket> sendQueue;


    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    public UDPFileTransferClient(int port, InetAddress serverAddress) {
	try {
	    // args
	    this.sendPort = port;
	    this.serverAddress = serverAddress;

	    // packet queues
	    this.reactionQueue = new ConcurrentLinkedQueue<UFTPacket>();
	    this.sendQueue = new ConcurrentLinkedQueue<UFTPacket>();
	    //this.listenSocket = new DatagramSocket(this.listenPort);
	    this.sendSocket = new DatagramSocket();

	    // threads
	    this.packetListener = new Thread(new UFTClientListener(this));
	    this.packetSender = new Thread(new UFTClientSpeaker(this));

	    // states
	    this.shouldSend = true;
	    this.shouldListen = true;
	    this.start();

	} catch (SocketException e) {
	    System.out.println("Cannot start client on port " + port);
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
	System.out.println("Requesting \"" + input + "\"");

	UFTHeader header = new UFTHeader(this.listenPort, this.sendPort, UFTHeaderType.GET, 1, 1, input.getBytes().length);
	UFTPacket packet = new UFTPacket(header, input.getBytes());
	enqueueForSend(packet);
	notifySpeaker();

    }


    public void notifySpeaker() {
	if (packetSender.getState() == Thread.State.TERMINATED) {
	    packetSender = new Thread(new UFTClientSpeaker(this));
	    packetSender.start();
	}
    }

    /*
     * Get Send port
     */
    public int getSendPort() {
	return sendPort;
    }


    /*
     * Set listen port
     */
    public void setListenPort(int port) {
	this.listenPort = port;
    }


    /*
     * Get listen port
     */
    public int getListenPort() {
	return this.listenPort;
    }

    public InetAddress getServerAddress() {
	return this.serverAddress;
    }

    public void enqueueForReaction(UFTPacket p) {
	this.reactionQueue.add(p);
    }

    public void enqueueForSend(UFTPacket p) {
	this.sendQueue.add(p);
    }



    public ConcurrentLinkedQueue<UFTPacket> getReceivedQueue() {
	return this.reactionQueue;
    }


    public ConcurrentLinkedQueue<UFTPacket> getSendQueue() {
	return this.sendQueue;
    }



    /*
     * Start the client
     */
    public void start() {
	packetListener.start();
	packetSender.start();
    }


    // /////////////////////////////////////////////////////////////////
    //   Client main
    // /////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
	try {
	    UDPFileTransferClient client = new UDPFileTransferClient(9090, InetAddress.getByName("127.0.0.1"));
	    client.requestAFile();
	} catch (UnknownHostException uhe) {
	    System.out.println(uhe.getMessage());
	}

    }



}
