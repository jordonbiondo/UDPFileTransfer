package org.DataCom.Utility;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;

/**
 * Packet speaker
 */
public interface UFTPacketSpeaker extends Runnable {

    /**
     * Does the thread have data?
     */
    public boolean hasData();

    /**
     * Send a packet
     */
    public void sendPacket(UFTPacket packet, int port, InetAddress address) throws IOException;


}
