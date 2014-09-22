package com.networking.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

	Socket socketClient;
	ObjectInputStream serverInputStream;
	ObjectOutputStream serverOutputStream;
	ArrayList<ObjectInputStream> peerInputStream;
	ArrayList<ObjectOutputStream> peerOutStream;

	public Client(String arg, String arg1) throws Exception {
		InetAddress address = InetAddress.getByName(arg);
		Socket socket = new Socket(address, Integer.parseInt(arg1));
		// serverInputStream = new ObjectInputStream(socket.getInputStream());
		serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
		serverOutputStream.writeObject(arg + "\t Connected");
		serverOutputStream.flush();
		System.out.println("Connected");
	}

	/*
	 * public void getIpAndPortClient() {
	 * 
	 * }
	 */
}
