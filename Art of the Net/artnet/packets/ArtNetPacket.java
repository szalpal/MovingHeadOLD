package artnet.packets;

import java.net.*;

import artnet.ArtNet;

public class ArtNetPacket {
		
	protected static final Integer PORT = 0x1936;
	protected static final String ID = new String("Art-Net\0");
	protected static final Integer PROTOCOL = 14;
	protected static final String BROADCAST_ADDRESS = new String("2.255.255.255");
	
	private byte[] dataArray = new byte[530];
	private InetAddress address;	
	
	
	protected ArtNetPacket()
	{
		this(BROADCAST_ADDRESS);
	}
	
	protected ArtNetPacket(String address)
	{
		try {
			this.address = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		setHeader();
		setProtocol();
	}
	
	private void setHeader()
	{
		setData(ID.getBytes(), 0);
	}
	
	private void setProtocol()
	{
		setData(ArtNet.hByte(PROTOCOL), 10);
		setData(ArtNet.lByte(PROTOCOL), 11);
	}
	
	
	protected void setData(byte data, int offset)
	{
		dataArray[offset] = data;
	}
	
	protected void setData(byte[] data, int offset)
	{
		for (int i=0; i<data.length; i++)
			dataArray[offset+i] = data[i];
	}
	
	protected void setData(byte[] data, int offset, int length)
	{
		for (int i=0; i<length; i++)
			dataArray[offset+i] = data[i];
	}
	
	public byte[] getData()
	{
		return dataArray;
	}
	
	public InetAddress getAddress()
	{
		return address;
	}
}
