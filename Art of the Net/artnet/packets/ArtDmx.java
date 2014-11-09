package artnet.packets;

import artnet.*;

public class ArtDmx extends ArtNetPacket {
	
	//TODO parameterize OpCode by enum (OpCode class)
	private Integer opCode = 0x5000;
	
	public ArtDmx(String address)
	{
		super(address);
//		dataArray = new byte[530];
		setOpCode();
		//setting default Sequence = 0
		setSequence(0);
		//setting default Physical = 0
		setPhysical(0);
		//setting default SubUni = 0
		setData((byte) 0, 14);
		//setting default Net = 0
		setData((byte) 0, 15);
		//setting default Length = 512
		setLength(512);
	}
	
	public ArtDmx()
	{
		//warning: broadcasting ArtDmx
		this(BROADCAST_ADDRESS);
	}
	
	private void setOpCode()
	{
		setData(ArtNet.hByte(opCode), 9);
		setData(ArtNet.lByte(opCode), 8);
	}
	
	public void setSequence(int sequence)
	{
		setData((byte) sequence, 12);
	}
	
	public void setPhysical(int physical)
	{
		setData((byte) 0, 13);
	}
	
	public void setPortAddress(byte[] portAddress)
	{
		setData(portAddress, 14);
	}
	
	public void setLength(int length)
	{
		setData(ArtNet.hByte(512), 16);
		setData(ArtNet.lByte(512), 17);
	}
	
	public void setDmxData(byte[] dmxData)
	{
		setData(dmxData, 18);
	}

}
