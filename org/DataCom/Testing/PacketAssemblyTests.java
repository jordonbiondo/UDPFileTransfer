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

	DatagramPacket packet = assembler.assemble(UFTHeaderType.ACK, 3, 10, testData);

	for (byte b : packet.getData()) {
	    System.out.print(b+" ");
	}
	System.out.print("\b\n");
	byte[] expected = {0, 0, 0, 1, 0, 0, 35, 40, 0, 0, 35, 41,
			   0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0,
			   0, 0, 0, 0, 3, 0, 0, 0, 10, 1, 2, 3, 4, 0, 4, 3, 2, 1 };
	System.out.println(Arrays.equals(packet.getData(), expected) ? "pass" : "fail");

    }

}

