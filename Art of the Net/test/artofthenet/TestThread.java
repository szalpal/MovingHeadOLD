package test.artofthenet;

import artnet.ArtNet;
import artnet.packets.ArtDmx;
import artnet.packets.ArtPoll;

public class TestThread implements Runnable {

	public void run()
	{
		ArtNet artNet = new ArtNet();
		ArtDmx dmx = new ArtDmx(new String("2.255.255.255"));
		ArtPoll poll = new ArtPoll();
		int sequence = 1;
		while (sequence <= 10000)
		{
			artNet.unicastPacket(poll);
			byte[] dmxData = new byte[512];
			for (int i=0; i<dmxData.length; i++)
				dmxData[i] = (byte) ((i + sequence) % 255);
			sequence++;
			
			dmx.setDmxData(dmxData);
			artNet.unicastPacket(dmx);
//			System.out.println("sent! "+ new Date().toString());
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
}
