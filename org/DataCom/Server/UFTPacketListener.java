package org.DataCom.Server;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;


public class UFTPacketListener implements Runnable {

    UDPFileTransferServer server;

    public UFTPacketListener(UDPFileTransferServer server) {
	this.server = server;
    }

    public void run() {
	
    }
}
