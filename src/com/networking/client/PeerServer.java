package com.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.networking.tags.DeCode;

public class PeerServer {

	private ServerSocket serverPeer;
	private int port;
	private static boolean isStop = false;

	public void stopServerPeer() {
		isStop = true;
	}

	public boolean getStop() {
		return isStop;
	}

	public PeerServer() throws Exception {
		port = Peer.getPort();
		serverPeer = new ServerSocket(port);
		(new WaitPeerConnect()).start();
	}

	class WaitPeerConnect extends Thread {

		Socket connection;
		ObjectInputStream getData;

		@Override
		public void run() {
			super.run();
			while (!isStop) {
				try {
					connection = serverPeer.accept();
					getData = new ObjectInputStream(connection.getInputStream());
					String msg = (String) getData.readObject();
					String message = DeCode.getMessage(msg);
					PeerMessageApp.updateChat(Integer.toString(connection
							.getPort()) + "\t" + message);
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
