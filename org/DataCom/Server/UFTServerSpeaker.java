package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;

public class UFTServerSpeaker implements UFTPacketSpeaker {

    /*
     * Parent server
     */
    UDPFileTransferServer server;


    /*
     * Server socket
     */
    DatagramSocket serverSocket;




    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * UFT Packet Listener
     */
    public UFTServerSpeaker(UDPFileTransferServer server) throws SocketException{
	this.server = server;
    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////


    /*
     * Is there Data to send?
     */
    public boolean hasData() {
	// true if the servers reactionqueu has content
	return ! server.getQueu().isEmpty();
    }


    /*
     * Try to send a UFTPacket
     */
    public boolean sendPacket(UFTPacket packet) {
	return false;
    }


    /*
     * Tell the thread to stop sending
     */
    public void markAsFinished() {
	this.server.shouldSend = false;
    }


    /*
     * Main Run loop
     */
    public void run() {
	while (this.server.shouldSend) {

	}

    }


}
