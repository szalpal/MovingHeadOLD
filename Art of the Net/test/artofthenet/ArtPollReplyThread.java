package test.artofthenet;

import java.io.IOException;
import java.net.*;
import java.util.Date;

import artnet.ArtNet;

public class ArtPollReplyThread implements Runnable {
	
	private static final int PORT = 0x1936;
	private static final String ADDRESS = new String("2.255.255.255"); 

	@Override
	public void run() {
		try {
			System.out.println("Hello!");
			DatagramSocket socket = new DatagramSocket(PORT);
			while (true)
			{
				byte[] data = new byte[15];
				DatagramPacket packet = new DatagramPacket(data, 0);
				System.out.print("Packet pending... ");
				socket.receive(packet);
				System.out.println("received!");
				data = packet.getData();
				if (data[8]==ArtNet.lByte(0x2000) && data[9]==ArtNet.hByte(0x2000))
				{
					DatagramPacket reply = new DatagramPacket(generateReply(), 0, InetAddress.getByName(ADDRESS), PORT);
					socket.send(reply);
					System.out.println("reply sent! "+ new Date().toString());
				}			
				else
				{
					
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private byte[] generateReply()
	{
		byte[] data = new byte[419];
		
		return data;
	}

}
