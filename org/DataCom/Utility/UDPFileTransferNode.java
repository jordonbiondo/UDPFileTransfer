package org.DataCom.Utility;



import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public abstract class UDPFileTransferNode {

    /**
     * Listen Port
     */
    protected int listenPort;

    /**
     * Send port
     */
    protected int sendPort;


    
    /**
     * Firend Node's address
     */
    protected InetAddress friendAddress;


    /**
     * Shoule the node listen for packets?
     */
    public boolean shouldListen;


    /**
     * Should the node be trying to send packets
     */
    public boolean shouldSend;


    /**
     * Socket to send packets on
     */
    public  DatagramSocket sendSocket;


    /**
     * Socket to listen for packets on
     */
    public DatagramSocket listenSocket;


    /**
     * Packets received that the node needs to respond to
     */
    protected ConcurrentLinkedQueue<UFTPacket> reactionQueue;


    /**
     * Packets waiting to be sent by the node
     */
    protected  ConcurrentLinkedQueue<UFTPacket> sendQueue;



    /**
     * Thread that listens for packets
     */
    protected Thread packetListener;


    /**
     * Thread that sends packets
     */
    protected Thread packetSender;


    /**
     * Constructor
     */
    public UDPFileTransferNode() {
	this.reactionQueue = new ConcurrentLinkedQueue<UFTPacket>();
	this.sendQueue = new ConcurrentLinkedQueue<UFTPacket>();
	this.shouldListen = true;
	this.shouldSend = true;
    }



    /**
     * Set listen port
     */
    public void setListenPort(int port) {
	this.listenPort = port;
    }


    /**
     * Get listen port
     */
    public int getListenPort() {
	return this.listenPort;
    }


    /**
     * Get send port
     */
    public int getSendPort() {
	return this.sendPort;
    }


    /**
     * Queue a packet for responding
     */
    public void enqueueReaction(UFTPacket p) {
	reactionQueue.add(p);
    }


    /**
     * Get friend nodes address
     */
    public InetAddress getFriendAddress() {
	return this.friendAddress;
    }


    /**
     * Queue a packet for sending
     * Must call notifySpeaker in order for sending to happen
     */
    public void enqueueForSend(UFTPacket p) {
	this.sendQueue.add(p);
    }



    /**
     * Get the reaction queue
     */
    public ConcurrentLinkedQueue<UFTPacket> getReceivedQueue() {
	return this.reactionQueue;
    }


    /**
     * Get the send queu
     */
    public ConcurrentLinkedQueue<UFTPacket> getSendQueue() {
	return this.sendQueue;
    }


    /**
     * Try waking up the server speaker
     */
    public abstract void notifySpeaker();



    /**
     * Start the server
     */
    public void start() {
	packetListener.start();
	packetSender.start();
    }

}
