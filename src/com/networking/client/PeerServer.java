package com.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer {

	private ServerSocket serverPeer;
	private int port;
	private static boolean isStop = false;

	public void stopServerPeer() {
		isStop = true;
	}

	public PeerServer(int portSocket) throws Exception {
		port = portSocket;
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
					PeerMessageApp.updateChat(Integer.toString(connection
							.getPort()) + "\t" + msg);
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
