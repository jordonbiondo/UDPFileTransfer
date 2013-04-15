package org.DataCom.Client;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UFTClientResponder extends UFTPacketResponder {


    /**
     * Create a new Responder
     */
    public UFTClientResponder(UDPFileTransferNode master) {
	super(master);
    }


    /**
     * Respond to a packet
     */
    @Override
    public  void respondTo(UFTPacket packet) {
	switch(packet.getHeader().type) {
	case ERR: respondToERR(packet); break;
	case DAT: respondToDAT(packet); break;
	case ACK: respondToACK(packet); break;
	case END: break;
	}
    }


    /**
     * Respon to ACK
     */
    public void respondToACK(UFTPacket packet) {
	Debug.pln("got an ack");
	master.acknowledged.put(packet.getDataAsString(),new Boolean(true));
    }


    /**
     * Resond to dat
     */
    public void respondToDAT(UFTPacket packet) {
	UDPFileTransferClient client = (UDPFileTransferClient)master;
	if (client.fileDataPackets.length < packet.getHeader().totalPackets) {
	    client.fileDataPackets = new UFTPacket[packet.getHeader().totalPackets];
	}
	client.fileDataPackets[packet.getHeader().packetNumber-1] = packet;
	Debug.pln("putting packet: "+packet.getHeader().packetNumber+"/"+packet.getHeader().totalPackets);

	boolean done = true;
	for (UFTPacket pack : client.fileDataPackets) {
	    if (pack == null) done = false;
	}

	if (done) {
	    client.writeFileAndEnd();
	}
    }



    /**
     * Respond to ERR
     */
    public void respondToERR(UFTPacket packet) {
	System.out.println(packet.getDataAsString());
	System.exit(1);
    }


}
