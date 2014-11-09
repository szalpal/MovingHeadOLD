package artnet.packets;

import artnet.ArtNet;

public class ArtPoll extends ArtNetPacket {

	private Integer opCode = 0x2000;
	
	public ArtPoll()
	{
		setOpCode();
		setTalkToMe(0,0,0); //default
		setPriority(0x80);
	}
	
	private void setOpCode()
	{
		setData(ArtNet.hByte(opCode), 9);
		setData(ArtNet.lByte(opCode), 8);
	}
	
	/*
	 * bit 0 -> deprecated
	 * bit 1 -> 0 = Only send ArtPollReply in response to an ArtPoll or ArtAddress.
	 * bit 1 -> 1 = Send ArtPollReply whenever Node conditions change.
	 * 				This selection allows the Controller to be informed of changes without the need to continuously poll.
	 * bit 2 -> 0 = Do not send me diagnostics messages.
	 * bit 2 -> 1 = Send me diagnostics messages.
	 * bit 3 -> 0 = Diagnostics messages are broadcast.
	 * bit 3 -> 1 = Diagnostics messages are unicast.
	 * bits 4-7 -> unused, transmit as 0
	 */
	public void setTalkToMe (int bit1, int bit2, int bit3)
	{
		if (!(bit1==0 || bit2==0 || bit3==0 || bit1==1 || bit2==1 || bit3==1))
		{
			//TODO exception
			System.out.println("Talk to me error");
		}
			
		byte z = (byte) (bit3 << 3 | bit2 << 2 | bit1);
		setData(z, 12);
	}
	
	public void setPriority(int priority)
	{
		if (priority==0x10 || priority==0x40 || priority==0x80 || priority==0xe0 || priority==0xff)
			setData((byte)0x80, 13);
		else
			//TODO exception
			System.out.println("Priority Error");
	}
}
