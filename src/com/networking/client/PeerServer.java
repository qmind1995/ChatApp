package com.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.networking.tags.DeCode;
import com.networking.tags.Tags;

public class PeerServer {

	private String userName = "";
	private ServerSocket serverPeer;
	private int port;
	private static boolean isStop = false;

	public void stopServerPeer() {
		isStop = true;
	}

	public boolean getStop() {
		return isStop;
	}

	public PeerServer(String name) throws Exception {
		userName = name;
		port = Peer.getPort();
		serverPeer = new ServerSocket(port);
		(new WaitPeerConnect()).start();
	}

	class WaitPeerConnect extends Thread {

		Socket connection;
		ObjectInputStream getRequest;

		@Override
		public void run() {
			super.run();
			while (!isStop) {
				try {
					connection = serverPeer.accept();
					getRequest = new ObjectInputStream(
							connection.getInputStream());
					String msg = (String) getRequest.readObject();
					String name = DeCode.getNameRequestChat(msg);
					int result = ContractApp.request(name, true);
					ObjectOutputStream send = new ObjectOutputStream(
							connection.getOutputStream());
					if (result == 0) {
						send.writeObject(Tags.CHAT_ACCEPT_TAG);
						new ChatApp(userName, name, connection, port);
					} else if (result == 1) {
						send.writeObject(Tags.CHAT_DENY_TAG);
					}
					send.flush();
				} catch (Exception e) {
					break;
				}
			}
			try {
				serverPeer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
