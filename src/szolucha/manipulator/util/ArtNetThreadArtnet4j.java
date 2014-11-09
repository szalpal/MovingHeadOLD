package szolucha.manipulator.util;

import java.net.InetAddress;
import java.net.SocketException;

import artnet4j.ArtNet;
import artnet4j.ArtNetException;
import artnet4j.ArtNetNode;
import artnet4j.packets.ArtDmxPacket;

public class ArtNetThreadArtnet4j implements Runnable {
	
	ArtNet artnet;
	ArtDmxPacket packet;
	
	private byte[] dmxData;
	private int subnetID, universeID;
	private ArtNetNode node;
	private String BROADCAST_ADDRESS = "2.255.255.255";
	
	public ArtNetThreadArtnet4j(ArtNet artnet, byte[] dmxData, ArtNetNode node)
	{
		this.artnet = artnet;
		this.node = node;
		packet = new ArtDmxPacket();
		packet.setDMX(dmxData, dmxData.length);
		packet.setUniverse(node.getSubNet(), node.getDmxOuts()[0]);
		
	}
	
	public ArtNetThreadArtnet4j(ArtNet artnet, byte[] dmxData) {
		this.artnet = artnet;
		this.node = null;
		packet = new ArtDmxPacket();
		packet.setUniverse(0, 0);
		packet.setDMX(dmxData, dmxData.length);
	}
	
//	public ArtNetThread(byte[] data) {
//		node = null;
//		this.dmxData = data;
//		this.subnetID = 0;
//		this.universeID = 0;
//	}
//	
//	public ArtNetThread(byte[] data, ArtNetNode node) {
//		this.node = node;
//		this.dmxData = data;
//		this.subnetID = node.getSubNet();
//		this.universeID = node.getDmxOuts()[0];
//		
//	}

	@Override
	public void run() {
		if (node == null)
			artnet.broadcastPacket(packet);
		else
			artnet.unicastPacket(packet, node);
		System.out.println("sent");
	}
	
	public void setDMX(byte[] data) {
		this.dmxData = data;
	}
	
	public void setSubnetID(int id) {
		this.subnetID = id;
	}
	
	public void setUniverseID(int id) {
		this.universeID = id;
	}
	
	public void setNode (ArtNetNode node) {
		this.subnetID = node.getSubNet();
		this.universeID = node.getDmxOuts()[0];
	}

}
