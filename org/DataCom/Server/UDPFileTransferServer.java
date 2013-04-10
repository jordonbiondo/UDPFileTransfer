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

    public boolean shouldListen;


    private ConcurrentLinkedQueue<UFTPacket> reactionQueue;

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
	    this.shouldListen = true;
	    packetListener.start();
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


    /*
     * Start the server
     */
    public void start() {

    }


    // /////////////////////////////////////////////////////////////////
    //   APPLICATION MAIN
    // /////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
	new UDPFileTransferServer(9898);
    }
}
