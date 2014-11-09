package szolucha.manipulator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import artnet4j.ArtNet;
import artnet4j.ArtNetException;
import artnet4j.ArtNetNode;
import artnet4j.events.ArtNetDiscoveryListener;
import artnet4j.packets.ArtPollReplyPacket;

public class RecognizeNetworkActivity extends Activity implements ArtNetDiscoveryListener {
	
	private static final int PROTOCOL = 14;
	private static final String BROADCAST_ADDRESS = "255.255.255.255";
	private static final int DEFAULT_PORT = 0x1936;
	private ArtNet artnet = new ArtNet();
	private List<ArtNetNode> nodes;
	private ArrayAdapter<ArtNetNode> adapter;
	
	private Vibrator vibrator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recognize_network);
		
//		adapter = new ArrayAdapter<ArtNetNode>(this, android.R.layout.simple_list_item_1, nodes);
//		setListAdapter(adapter);
		
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		
		Button recognize = (Button) findViewById(R.id.startButton);
		recognize.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					recognizeNetwork();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	
	public void plus1 (View view) {
		nodes.add(new ArtNetNode());
	}
	
//	public void recognizeNetwork(View view) {
//		try {
//			artnet.start();
//			artnet.getNodeDiscovery().addListener(this);
//			artnet.startNodeDiscovery();
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} catch (ArtNetException e) {
//			e.printStackTrace();
//		}
//	}
	
	class InsideThread implements Runnable {

	private static final long ART_POLL_REPLY_TIMEOUT = 3000;

	@Override
	public void run() {
		
		try {
			int index = 0;
			DatagramPacket packet = null;
			DatagramSocket socket = new DatagramSocket(0x1936);
			socket.setBroadcast(true);
//				packet = generatePacket(index);
				packet = generateArtPoll();
				socket.send(packet);
				index++;
				System.out.print("sent!");
				Thread.sleep(ART_POLL_REPLY_TIMEOUT);
				socket.receive(packet);
				byte[] data = packet.getData();
				if (data[8] == LByte(0x2000)) {
					vibrator.vibrate(100);
					ArtPollReplyPacket reply = new ArtPollReplyPacket(data);
					//TODO
				}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	}
	
	private void recognizeNetwork() throws UnknownHostException {
		new Thread(new InsideThread()).start();
		
	}
	
	private DatagramPacket generateArtPoll() throws UnknownHostException {
		byte[] temp = new byte[14];
		//header
		byte[] header = new String("Art-Net\0").getBytes();
		System.arraycopy(header, 0, temp, 0, header.length);
		//op code
		temp[9] = HByte(0x2000); temp[8] = LByte(0x2000);
		//protocol
		temp[10] = HByte(PROTOCOL); temp[11] = LByte(PROTOCOL);
		//talk to me
		temp[12] = 0;
		//priotiry
		temp[13] = 0;
		DatagramPacket packet;
		packet = new DatagramPacket(temp, temp.length, InetAddress.getByName(BROADCAST_ADDRESS), DEFAULT_PORT);
		return packet;
	}

	
	private void updateNodeList(List<ArtNetNode> nodes) {
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_recognize_network, menu);
		return true;
	}

	@Override
	public void discoveredNewNode(ArtNetNode node) {
		nodes.add(node);
	}

	@Override
	public void discoveredNodeDisconnected(ArtNetNode node) {
		Toast.makeText(getApplicationContext(), node + " node disconected!", Toast.LENGTH_SHORT).show();
		updateNodeList(nodes);
	}

	@Override
	public void discoveryCompleted(List<ArtNetNode> nodes) {
		Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
		updateNodeList(nodes);
	}

	@Override
	public void discoveryFailed(Throwable t) {
		Toast.makeText(getApplicationContext(), "Failed :-(", Toast.LENGTH_SHORT).show();
	}
	
	public List<ArtNetNode> getNodes() {
		return nodes;
	}
	
	private static byte HByte (int num)
	{
		return Integer.valueOf(num >> 8 & 0xff).byteValue();
	}
	
	private static byte LByte (int num)
	{
		return Integer.valueOf(num & 0xff).byteValue();
	}
	
//	private class NodeList extends AdapterView {
//		
//	}
}
