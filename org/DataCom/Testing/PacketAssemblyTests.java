package org.DataCom.Testing;

import java.util.*;
import java.io.*;
import java.net.*;
import org.DataCom.Utility.*;
import java.math.BigInteger;

public class PacketAssemblyTests {

    public static void main(String[] args) {

	UFTPacketAssembler assembler = new UFTPacketAssembler(9000, 9001);
	byte[] testData = {1, 2, 3, 4, 0, 4, 3, 2, 1};
	byte[] testData2 = {1, 2, 3, 4, 0, 4, 3, 2, 2};
	DatagramPacket packet = assembler.assemble(UFTHeaderType.ACK, 3, 10, testData);
	DatagramPacket packet2 = assembler.assemble(UFTHeaderType.GET, 2, 11, testData2);

	try {
	    UFTPacket uftPacket = new UFTPacket(packet);
	    UFTPacket uftPacket2 = new UFTPacket(packet);
	    UFTPacket uftPacket3 = new UFTPacket(packet2);
	    System.out.println(uftPacket.getHeader().getChecksum() !=
			       uftPacket3.getHeader().getChecksum() ? "pass" : "fail");
	    System.out.println(uftPacket.getHeader().getChecksum() ==
			       uftPacket2.getHeader().getChecksum() ? "pass" : "fail");
	} catch(Exception e) {
	    e.printStackTrace();
	}



    }

}

