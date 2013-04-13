package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UDPFileTransferServer {

    /*
     * Port to listen for packets on
     */
    private int listenPort;


    /*
     * The port to send packets to
     */
    private int clientPort = -1;



    /*
     * Shoule the server listen for packets
     */
    public boolean shouldListen;


    /*
     * Should the server be sending packets
     */
    public boolean shouldSend;


    /*
     * File splitter
     */
    private UFTFileSplitter fileSplitter;


    /*
     * Received packet queue
     */
    private ConcurrentLinkedQueue<UFTPacket> reactionQueue;


    /*
     * Packets waiting to be sent
     */
    private ConcurrentLinkedQueue<UFTPacket> sendQueue;


    /*
     * Packet Listener thread
     */
    Thread packetListener;


    /*
     * Packet Send Thread
     */
    Thread packetSender;


    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * Constructor
     */
    public UDPFileTransferServer(int port) {
	try {
	    this.reactionQueue = new ConcurrentLinkedQueue<UFTPacket>();
	    this.listenPort = port;
	    this.packetListener = new Thread(new UFTServerListener(this));
	    this.packetSender = new Thread(new UFTServerSpeaker(this));
	    this.shouldListen = true;
	} catch (SocketException e) {
	    System.out.println("Cannot start server on port " + port);
	    e.printStackTrace();
	}
    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////


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


    public void enqueueReaction(UFTPacket p) {
	reactionQueue.add(p);
    }

    public ConcurrentLinkedQueue getReceivedQueue() {
	return this.reactionQueue;
    }

    public ConcurrentLinkedQueue getSendQueue() {
	return this.sendQueue;
    }

    /*
     * Start the server
     */
    public void start() {
	packetListener.start();
	packetSender.start();
    }


    public ArrayList<UFTPacket> prepareFileTransmission(File file) {
	// ensure port has been received
	if (this.clientPort < 0) return null;

	try {
	    fileSplitter = new UFTFileSplitter(file);

	    ArrayList<byte[]> chunks = fileSplitter.getChunks();

	    ArrayList<UFTPacket> dataPackets = new ArrayList<UFTPacket>();

	    int sequenceNumber = 0;
	    for (byte[] chunk : chunks) {

		UFTHeader header = new UFTHeader(this.listenPort, this.clientPort, UFTHeaderType.DAT,
						 sequenceNumber, chunks.size(), chunk.length);
		UFTPacket packet = new UFTPacket(header, chunk);
		dataPackets.add(packet);

		sequenceNumber++;
	    }
	    return dataPackets;

	} catch (IOException ioe) {
	    System.out.println("Could not prepare \"" + file.getName() + "\" for transmission");
	    System.out.println(ioe.getMessage());
	}
	return null;
    }

    /*
     * Returns true if fileRequest is a valid file to send to the client
     */
    public boolean acceptsRequest(String fileRequest) {
	File file = new File(fileRequest);
	// file cannot request paths to files, only a single filename
	return file.isFile() && file.getName().equals(fileRequest);
    }



    // /////////////////////////////////////////////////////////////////
    //   APPLICATION MAIN
    // /////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
	UDPFileTransferServer server = new UDPFileTransferServer(9898);
	server.start();
    }
}
