package org.DataCom.Utility;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;


public interface UFTPacketListener extends Runnable {

    public void onPacketReceive(DatagramPacket packet);
    public void onPacketError(DatagramPacket packet, Exception e);
}
