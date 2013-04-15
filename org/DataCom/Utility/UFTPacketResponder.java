package org.DataCom.Utility;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;


/**
 * Packet Responder prototype
 */
public abstract class UFTPacketResponder implements Runnable {

    /**
     * master node
     */
    public UDPFileTransferNode master;

    /**
     * Create a new Responder
     */
    public UFTPacketResponder(UDPFileTransferNode master) {
	this.master = master;
    }


    /**
     * is there more data?
     */
    public boolean hasData() {
	return ! master.getReceivedQueue().isEmpty();
    }


    /**
     * Get the next packet
     */
    public UFTPacket getNext() {
	return master.getReceivedQueue().poll();
    }


    /**
     * Respond to a packet
     */
    public abstract void respondTo(UFTPacket packet);

    /**
     * Run loop
     */
    public void run() {
	while(hasData()) {
	    UFTPacket next = this.getNext();
	    this.respondTo(next);
	}
    }
}
