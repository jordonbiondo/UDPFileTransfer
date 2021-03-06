import java.io.*;
import java.net.*;
import java.util.*;

class UDPServer {
    public static void main(String[] args) throws Exception {
	DatagramSocket serverSocket = new DatagramSocket(9898);
	while(true) {
	    byte[] recvData = new byte[1024];
	    byte[] sendData = new byte[1024];
	    DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
	    serverSocket.receive(recvPacket);
	    String message = new String(recvPacket.getData()).trim();
	    String newMessage = "echoing: "+message;
	    sendData = newMessage.getBytes();
	    InetAddress clientIP = recvPacket.getAddress();
	    int port = recvPacket.getPort();
	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);
	    serverSocket.send(sendPacket);
	}

    }
}
