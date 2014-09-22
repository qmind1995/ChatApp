package com.networking.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import com.netwoking.tags.Tags;

public class Server {

	private static String ACCOUNT = "ACCOUNT";
	private JSONObject accountClient;
	private ServerSocket server;
	Socket connection;
	public boolean isStop = false;

	// ArrayList<Socket> serverSocket;// serverSocket for 1 client connect

	public Server(int port) throws Exception {
		server = new ServerSocket(port);
		accountClient = new JSONObject();
		(new WaitForConnect()).start();
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
		/*
		 * ObjectOutputStream out = new ObjectOutputStream(
		 * connection.getOutputStream()); if (isFound) { out.writeObject(msg);
		 * objectOut.add(out); } else { msg = Tags.SESSION_DENY_TAG;
		 * out.writeObject(msg); // serverSocket.get(position).close();
		 * out.flush(); // serverSocket.remove(position); }
		 */
	}

	public void stopServer() throws Exception {
		isStop = true;
		server.close();
		connection.close();
	}

	private void waitForConnection() throws Exception {
		connection = server.accept();
		ServerApp.updateNumberClient();
	}

	@SuppressWarnings("unused")
	private String getUserName(String msg) {
		return null;
	}

	public class WaitForConnect extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				while (!isStop) {
					waitForConnection();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

// private static String WORKINK_DIR = System.getProperty("user.dir");
