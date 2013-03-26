import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
	DatagramSocket clientSocket = new DatagramSocket();
	Scanner input = new Scanner(System.in);
	String message = input.next();
	byte[] sendData = new byte[1024];
	sendData = message.getBytes();
	InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9898);
	clientSocket.send(sendPacket);
	byte[] recvData = new byte[1024];
	DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
	clientSocket.receive(recvPacket);
	String newMessage = new String(recvData);
	System.out.println("=> " + newMessage);
    }
}
