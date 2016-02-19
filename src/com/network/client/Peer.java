package com.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import com.networking.data.DataPeer;
import com.networking.tags.DeCode;
import com.networking.tags.enCode;
import com.networking.tags.Tags;

public class Peer {

	public static ArrayList<DataPeer> peer = null;
	private PeerServer server;
	private InetAddress ipServer;
	private int portServer = 8080;
	private String nameUser = "";
	private boolean isStop = false;
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
		String msg = enCode.sendRequest(nameUser);
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
			while (!isStop) {
				try {
					Thread.sleep(15000);
					request();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void requestChat(String IP, int host, String guest) throws Exception {
		final Socket connPeer = new Socket(InetAddress.getByName(IP), host);
		ObjectOutputStream sendrequestChat = new ObjectOutputStream(
				connPeer.getOutputStream());
		sendrequestChat.writeObject(enCode.sendRequestChat(nameUser));
		sendrequestChat.flush();
		ObjectInputStream receivedChat = new ObjectInputStream(
				connPeer.getInputStream());
		String msg = (String) receivedChat.readObject();
		if (msg.equals(Tags.CHAT_DENY_TAG)) {
			ContractApp.request("May be your friend busy!", false);
			connPeer.close();
			return;
		} else {
			new ChatApp(nameUser, guest, connPeer, portPeer);
		}
		// TO DO SOMETHING
	}

	public void exit() throws IOException, ClassNotFoundException {
		isStop = true;
		socketClient = new Socket();
		SocketAddress addressServer = new InetSocketAddress(ipServer,
				portServer);
		socketClient.connect(addressServer);
		String msg = enCode.exit(nameUser);
		serverOutputStream = new ObjectOutputStream(
				socketClient.getOutputStream());
		serverOutputStream.writeObject(msg);
		serverOutputStream.flush();
		serverOutputStream.close();
		server.exit();
	}

	public void updateFiend() {
		int size = peer.size();
		ContractApp.clearAll();
		for (int i = 0; i < size; i++)
			if (!peer.get(i).getName().equals(nameUser))
				ContractApp.updateFiend(peer.get(i).getName());
	}
        public void set_datapeer(ArrayList<DataPeer> peern){
            Peer.peer = peern;
        }
}