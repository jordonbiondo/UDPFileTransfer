package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;


public class UFTServerResponder extends UFTPacketResponder {


    /**
     * Create a new Responder
     */
    public UFTServerResponder(UDPFileTransferNode master) {
	super(master);
    }


    /**
     * Respond to a packet
     */
    @Override
    public  void respondTo(UFTPacket packet) {
	Debug.pln("responding to: "+packet.getHeader().getChecksum());
	switch(packet.getHeader().type) {
	case GET: respondToGET(packet); break;
	case ERR: break;
	case ACK: respondToACK(packet);break;
	case END: respondToEND(packet); break;
	}
    }

    /**
     * Respond to ACK packet
     */
    public void respondToACK(UFTPacket packet) {
	UDPFileTransferServer server = (UDPFileTransferServer)master;
	try {
	    server.acknowledgements[ByteUtils.intVal(packet.getData()) -1] = true;
	} catch (ByteTranslationException bte) {
	    Debug.err(bte.getMessage() + " | couldn't understand ack pack");
	}
    }

    /**
     * Respond to end packet
     */
    public void respondToEND(UFTPacket packet) {
	Debug.pln("END received, resetting session...");
	UDPFileTransferServer server = (UDPFileTransferServer)master;
	server.endSession();

    }

    /**
     * Respon to get
     */
    public void respondToGET(UFTPacket packet) {
	UDPFileTransferServer server = (UDPFileTransferServer)master;
	if (server.currentRequest == null) {
	    this.master.setSendPort(packet.getHeader().sourcePort);
	    if(server.acceptsRequest(packet.getDataAsString())) {
		//		Debug.pln("Accepted");
		server.currentRequest = packet.getDataAsString();

		UFTHeader header = new UFTHeader(master.getListenPort(), master.getSendPort(), UFTHeaderType.ACK,
						 1, 1, packet.getData().length);
		UFTPacket ackPacket = new UFTPacket(header, packet.getData());
		ackPacket.prepareForSend();
		Debug.pln("queueing ack");
		master.enqueueForSend(ackPacket);


		File file = new File(packet.getDataAsString());
		ArrayList<UFTPacket> packets = server.prepareFileTransmission(file);
		server.acknowledgements = new boolean[packets.size()];
		for (UFTPacket fPack : packets) {
		    master.enqueueForSend(fPack);
		}
	    } else {
		String message = "File not available";
		UFTHeader header = new UFTHeader(master.getListenPort(), master.getSendPort(), UFTHeaderType.ERR,
						 1, 1, message.getBytes().length);
		UFTPacket errPacket = new UFTPacket(header, message.getBytes());
		errPacket.prepareForSend();
		Debug.pln("queueing err");
		master.enqueueForSend(errPacket);
	    }
	    server.notifySpeaker();
	} else {
	    Debug.pln("ignoring duplicate get");
	}
    }

}
