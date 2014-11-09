package artnet.packets;

public class ArtPollReplyParser extends ArtNetPacket {
	
	/*
	 * This class has static methods that parse and translate ArtPollReply
	 * ArtPollReply is in every function argument.
	 */
	
	public static byte[] getIPAddress(byte[] artPollReply)
	{
		byte[] IP = new byte[4];
		for (int i=0; i<IP.length; i++)
			IP[i]=artPollReply[10+i];
		return IP;
	}
	
	/*
	 * PortAddress consists of:
	 * Bit 15	Bits 14-8	Bits 7-4	Bits 3-0
	 * 	 0			Net		 Sub-Net	Universe
	 * 
	 * For sake of ArtDmx, return of getPortAddres() is:
	 * 		byte[2] = {SubUni, Net}
	 */
	public static byte[] getPortAddress(byte[] artPollReply)
	{
		byte[] portAddress = new byte[2];
		//19 - sub; 186 - SwIn[0]; 190 - SwOut[0]
		portAddress[0] = (byte) ((artPollReply[19] & 15) >> 4 | (artPollReply[190] & 7));
		//18 - net
		portAddress[1] = (byte) (artPollReply[18] & 127);
		return portAddress;
	}
	
	public static byte[] getShortName(byte[] artPollReply)//26
	{
		byte[] shortName = new byte[18];
		for (int i=0; i<shortName.length; i++)
			shortName[i] = artPollReply[26+i];
		return shortName;
	}
	
	public static byte[] getLongName(byte[] artPollReply)
	{
		byte[] longName = new byte[64];
		for (int i=0; i<longName.length; i++)
			longName[i] = artPollReply[44+i];
		return longName;
	}

}
