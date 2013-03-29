package org.DataCom.Client;

import org.DataCom.Utility.*;

public class UDPFileTransferClient {


    public static void main(String[] args) {
	UFTHeader h = new UFTHeader(11, 22, UFTHeaderType.ACK, 44, 55, 33);
	for (byte b : h.toBytes()) {
	    System.out.print(b+",");
	}
	System.out.println();

    }
}
