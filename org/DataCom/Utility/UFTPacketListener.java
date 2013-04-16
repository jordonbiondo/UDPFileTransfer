package org.DataCom.Utility;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;


/**
 * Packet listener
 */
public interface UFTPacketListener extends Runnable {

    /**
     * On packet receive
     */
    public void onPacketReceive(DatagramPacket packet);


    /**
     * On Packet error
     */
    public void onPacketError(DatagramPacket packet, Exception e);
}
