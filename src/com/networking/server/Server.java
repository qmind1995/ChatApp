package com.networking.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.netwoking.tags.Tags;

public class Server {

	private static String ACCOUNT = "ACCOUNT";
	// private static String WORKINK_DIR = System.getProperty("user.dir");
	JSONObject accountClient;
	ServerSocket server;
	ArrayList<ObjectOutputStream> objectOut;
	ArrayList<Socket> serverSocket;// serverSocket for 1 client connect

	public Server(int port) throws Exception {
		server = new ServerSocket(port);
		serverSocket = new ArrayList<Socket>();
		objectOut = new ArrayList<ObjectOutputStream>();
		accountClient = new JSONObject();
	}

	@SuppressWarnings("unused")
	private void sendSessionAccept(int position, String userName)
			throws Exception {
		String msg = Tags.SESSION_ACCEPT_OPEN_TAG;
		boolean isFound = false;
		JSONArray arrayAccount = accountClient.getJSONArray(ACCOUNT);
		int size = arrayAccount.length();
		for (int i = 0; i < size; i++) {
			msg += Tags.PEER_OPEN_TAG;
			JSONObject account = arrayAccount.getJSONObject(i);
			if (account.getString(Tags.PEER_NAME_OPEN_TAG).equals(userName)) {
				isFound = true;
				break;
			}
			msg += Tags.PEER_NAME_OPEN_TAG;
			msg += account.getString(Tags.PEER_NAME_OPEN_TAG);
			msg += Tags.PEER_NAME_CLOSE_TAG;
			msg += Tags.IP_OPEN_TAG;
			msg += account.getString(Tags.IP_OPEN_TAG);
			msg += Tags.IP_CLOSE_TAG;
			msg += Tags.PORT_OPEN_TAG;
			msg += account.getString(Tags.PORT_OPEN_TAG);
			msg += Tags.PORT_CLOSE_TAG;
			msg += Tags.PEER_CLOSE_TAG;
		}
		msg += Tags.SESSION_ACCEPT_CLOSE_TAG;
		ObjectOutputStream out = new ObjectOutputStream(serverSocket.get(
				position).getOutputStream());
		if (isFound) {
			out.writeObject(msg);
			objectOut.add(out);
		} else {
			msg = Tags.SESSION_DENY_TAG;
			out.writeObject(msg);
			serverSocket.get(position).close();
			out.flush();
			serverSocket.remove(position);
		}
	}

	@SuppressWarnings("unused")
	private void waitForConnection() throws Exception {
		Socket connection = server.accept();
		ObjectInputStream input = new ObjectInputStream(
				connection.getInputStream());
		String msg = (String) input.readObject();
		
	}
}
