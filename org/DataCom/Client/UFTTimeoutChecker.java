package org.DataCom.Client;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;


public class UFTTimeoutChecker implements Runnable {
    public UDPFileTransferClient client;
    public UFTTimeoutChecker(UDPFileTransferClient client) {
	this.client = client;
    }
    public void run() {
	while(true) {
	    try{
	    Thread.sleep(1000);
	    } catch(Exception e) {
		
	    }
	    if ((System.currentTimeMillis()/1000) - client.lastPacketTime > 15) {
		System.out.println("Timeout, no repsonse from server");
		System.exit(-1);
	    }
	}
    }

}
