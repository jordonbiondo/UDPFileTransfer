package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UDPFileTransferServer extends UDPFileTransferNode {


    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////


    /*
     * Constructor
     */
    public UDPFileTransferServer(int listenPort) {
	super();
	try {
	    this.listenPort = listenPort;
	    this.packetListener = new Thread(new UFTServerListener(this));
	    this.packetSender = new Thread(new UFTServerSpeaker(this));

	    this.sendSocket = new DatagramSocket();
	} catch (SocketException e) {
	    Debug.err("Cannot start server on port " + listenPort);
	    e.printStackTrace();
	}
    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////

    /*
     * Try waking up the server speaker
     */
    @Override
    public void notifySpeaker() {
	if (packetSender.getState() == Thread.State.TERMINATED) {
	    packetSender = new Thread(new UFTServerSpeaker(this));
	    packetSender.start();
	}
    }

    public boolean prepareSendSocket(int sendPort) {
	try {
	    this.setSendPort(sendPort);
	    if( ! this.sendSocket.isBound()) {
		this.sendSocket = new DatagramSocket(this.getSendPort());
	    }
	} catch (SocketException se) {
	    Debug.err("Couldn't start server sned socket on " + sendPort);
	    return false;
	}
	return true;
    }




    public ArrayList<UFTPacket> prepareFileTransmission(File file) {
	// ensure port has been received
	if (this.sendPort < 0) return null;

	try {
	    UFTFileSplitter fileSplitter = new UFTFileSplitter(file);

	    ArrayList<byte[]> chunks = fileSplitter.getChunks();

	    ArrayList<UFTPacket> dataPackets = new ArrayList<UFTPacket>();

	    int sequenceNumber = 0;
	    for (byte[] chunk : chunks) {

		UFTHeader header = new UFTHeader(this.listenPort, this.sendPort, UFTHeaderType.DAT,
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
