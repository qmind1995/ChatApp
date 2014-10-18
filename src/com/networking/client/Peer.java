package com.networking.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import com.networking.data.DataPeer;
import com.networking.tags.DeCode;
import com.networking.tags.EnCode;
import com.networking.tags.Tags;

public class Peer {

	//private long timeRequest = 0;
	public static ArrayList<DataPeer> peer = null;
	private PeerServer server;
	private InetAddress ipServer;
	private int portServer = 8080;
	private String nameUser = "";

	private static int portPeer = 10000;
	private Socket socketClient;
	private ObjectInputStream serverInputStream;
	private ObjectOutputStream serverOutputStream;

	public Peer(String arg, int arg1, String name, String dataUser)
			throws Exception {
		ipServer = InetAddress.getByName(arg);
		nameUser = name;
		portPeer = arg1;
		peer = DeCode.getAllUser(dataUser);
		new Thread(new Runnable() {

			@Override
			public void run() {
				updateFiend();
			}
		}).start();
		server = new PeerServer(nameUser);
		(new Request()).start();
	}

	public static int getPort() {
		return portPeer;
	}

	public void request() throws Exception {
		socketClient = new Socket();
		SocketAddress addressServer = new InetSocketAddress(ipServer,
				portServer);
		socketClient.connect(addressServer);
		String msg = EnCode.sendRequest(nameUser, Tags.SERVER_ONLINE);
		serverOutputStream = new ObjectOutputStream(
				socketClient.getOutputStream());
		serverOutputStream.writeObject(msg);
		serverOutputStream.flush();
		serverInputStream = new ObjectInputStream(socketClient.getInputStream());
		msg = (String) serverInputStream.readObject();
		serverInputStream.close();
		peer = DeCode.getAllUser(msg);
		new Thread(new Runnable() {

			@Override
			public void run() {
				updateFiend();
			}
		}).start();
	}

	public class Request extends Thread {
		@Override
		public void run() {
			super.run();
			// timeRequest = System.currentTimeMillis();
			while (!server.getStop()) {
				try {
					Thread.sleep(15000);
					request();
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*
				 * long time = System.currentTimeMillis(); int getTime = ((int)
				 * (time - timeRequest) / 1000); if (getTime >= 15) try {
				 * request(); timeRequest = time; } catch (Exception e) {
				 * e.printStackTrace(); }
				 */
			}
		}
	}

	public void requestChat(String IP, int host, String guest) throws Exception {
		final Socket connPeer = new Socket(InetAddress.getByName(IP), host);
		ObjectOutputStream sendrequestChat = new ObjectOutputStream(
				connPeer.getOutputStream());
		sendrequestChat.writeObject(EnCode.sendRequestChat(nameUser));
		sendrequestChat.flush();
		ObjectInputStream receivedChat = new ObjectInputStream(
				connPeer.getInputStream());
		String msg = (String) receivedChat.readObject();
		if (msg.equals(Tags.CHAT_DENY_TAG)) {
			ContractApp.request("My Friend Busy", false);
			connPeer.close();
			return;
		} else {
			new ChatApp(nameUser, guest, connPeer, portPeer);
		}
		// TO DO SOMETHING
	}

	public void updateFiend() {
		int size = peer.size();
		ContractApp.clearAll();
		for (int i = 0; i < size; i++)
			if (!peer.get(i).getName().equals(nameUser))
				ContractApp.updateFiend(peer.get(i).getName());
	}
}