package com.networking.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.networking.data.DataPeer;
import com.networking.tags.DeCode;
import com.networking.tags.Tags;

public class Server {

	private ArrayList<DataPeer> dataPeer = null;
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream obOutputClient;
	private ObjectInputStream obInputStream;
	public boolean isStop = false;

	public Server(int port) throws Exception {
		server = new ServerSocket(port);
		dataPeer = new ArrayList<DataPeer>();
		(new WaitForConnect()).start();
	}

	private String sendSessionAccept() throws Exception {
		String msg = Tags.SESSION_ACCEPT_OPEN_TAG;
		int size = dataPeer.size();
		for (int i = 0; i < size; i++) {
			DataPeer peer = dataPeer.get(i);
			msg += Tags.PEER_OPEN_TAG;
			msg += Tags.PEER_NAME_OPEN_TAG;
			msg += peer.getName();
			msg += Tags.PEER_NAME_CLOSE_TAG;
			msg += Tags.IP_OPEN_TAG;
			msg += peer.getHost();
			msg += Tags.IP_CLOSE_TAG;
			msg += Tags.PORT_OPEN_TAG;
			msg += peer.getPort();
			msg += Tags.PORT_CLOSE_TAG;
			msg += Tags.PEER_CLOSE_TAG;
		}
		msg += Tags.SESSION_ACCEPT_CLOSE_TAG;
		ServerApp.updateMessage("FeedBack : " + msg);
		return msg;
	}

	public void stopServer() throws Exception {
		isStop = true;
		server.close();
		connection.close();
	}

	private boolean waitForConnection() throws Exception {
		connection = server.accept();
		obInputStream = new ObjectInputStream(connection.getInputStream());
		String msg = (String) obInputStream.readObject();
		ArrayList<String> getData = DeCode.getUser(msg);
		ServerApp.updateMessage(msg);
		if (getData != null) {
			if (!isExsistName(getData.get(0))) {
				saveNewPeer(getData.get(0), connection.getInetAddress()
						.toString(), Integer.parseInt(getData.get(1)));
				ServerApp.updateMessage(getData.get(0));
			} else
				return false;
		}
		return true;
	}

	private void saveNewPeer(String user, String ip, int port) throws Exception {
		DataPeer newPeer = new DataPeer();
		if (dataPeer.size() == 0)
			dataPeer = new ArrayList<DataPeer>();
		newPeer.setPeer(user, ip, port);
		dataPeer.add(newPeer);
	}

	private boolean isExsistName(String name) throws Exception {
		if (dataPeer == null)
			return false;
		int size = dataPeer.size();
		for (int i = 0; i < size; i++) {
			DataPeer peer = dataPeer.get(i);
			if (peer.getName().equals(name))
				return true;
		}
		return false;
	}

	public class WaitForConnect extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				while (!isStop) {
					if (waitForConnection()) {
						obOutputClient = new ObjectOutputStream(
								connection.getOutputStream());
						obOutputClient.writeObject(sendSessionAccept());
						obOutputClient.flush();
						obOutputClient.close();
					} else {
						obOutputClient = new ObjectOutputStream(
								connection.getOutputStream());
						obOutputClient.writeObject(Tags.SESSION_DENY_TAG);
						obOutputClient.flush();
						obOutputClient.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

// private static String WORKINK_DIR = System.getProperty("user.dir");
