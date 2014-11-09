package artnet;

import java.io.IOException;
import java.net.*;

import artnet.packets.*;

public class ArtNet {

	private final Integer PORT = 0x1936;
	
	private DatagramSocket socket;
	
	public ArtNet()
	{
		try {
			socket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
		
	public void unicastPacket(ArtNetPacket artNetPacket)
	{
		byte[] data = artNetPacket.getData();
		DatagramPacket packet = new DatagramPacket(data, data.length, artNetPacket.getAddress(), PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void multicastPacket(ArtNetPacket packet)
	{
		
	}
		
	/*
	 * destination - where to fill data
	 * offset, length - of the destination
	 * data - byte[] from where data is copied
	 */
	public static void fillByteArray (byte[] data, byte[] destination, int offset, int length)
	{
		System.arraycopy(data, 0, destination, offset, length);
	}
	
	public static byte hByte(Integer i)
	{
		return new Integer(i >> 8 & 0xff).byteValue();
	}
	
	public static byte lByte(Integer i)
	{
		return new Integer(i & 0xff).byteValue();
	}
	
	//TODO finalize method to close the socket
	
//	public finally()
//	{
//		socket.close();
//	}
}
