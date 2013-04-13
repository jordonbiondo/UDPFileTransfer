package org.DataCom.Utility;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;

public interface UFTPacketSpeaker extends Runnable {

    public boolean hasData();

    public void sendPacket(UFTPacket packet, int port, InetAddress address) throws IOException;


}
