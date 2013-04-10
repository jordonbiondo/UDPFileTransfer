package org.DataCom.Client;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;

public class UFTClientSpeaker implements UFTPacketSpeaker {

    /*
     * Parent server
     */
    UDPFileTransferClient client;


    /*
     * Server socket
     */
    DatagramSocket serverSocket;


    /*
     * Should the thread be sending packets?
     */
    private boolean shouldBeSending = false;


    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * UFT Packet Listener
     */
    public UFTClientSpeaker(UDPFileTransferClient server) throws SocketException{
	this.client = client;
    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////


    /*
     * Is there Data to send?
     */
    public boolean hasData() {
	return false;
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
	this.shouldBeSending = false;
    }


    /*
     * Main Run loop
     */
    public void run() {
	while (shouldBeSending) {

	}

    }


}
