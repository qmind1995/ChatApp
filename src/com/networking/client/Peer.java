package com.networking.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Peer {
	private PeerServer server;
	private Socket socketClient;
	private Socket socketPeer;
	ObjectInputStream serverInputStream;
	ObjectOutputStream peerOutputStream;

	public Peer(String arg, String arg1) throws Exception {
		socketClient = new Socket(InetAddress.getByName(arg),
				Integer.parseInt(arg1));
		/*
		 * serverOutputStream = new ObjectOutputStream(
		 * socketClient.getOutputStream()); serverOutputStream.writeObject(arg +
		 * "\t Connected"); serverOutputStream.flush();
		 */
		serverInputStream = new ObjectInputStream(socketClient.getInputStream());
		int port = (Integer) serverInputStream.readObject();
		server = new PeerServer(port);
		System.out.println("Connected");
	}

	public void connectPeer(String arg, String arg1, String msg)
			throws Exception {
		socketPeer = new Socket(InetAddress.getByName(arg),
				Integer.parseInt(arg1));
		peerOutputStream = new ObjectOutputStream(socketPeer.getOutputStream());
		peerOutputStream.writeObject(msg);
		peerOutputStream.flush();
	}
}
