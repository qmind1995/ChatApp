/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.networking.tags.DeCode;
import com.networking.tags.enCode;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.networking.data.DataPeer;
import com.networking.tags.Tags;
import java.io.IOException;

/**
 *
 * @author Dell
 */
public class Server {

	private String username = "";
	public ArrayList<DataPeer> dataPeer = null;
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream obOutputClient;
	private ObjectInputStream obInputStream;
	public boolean isStop = false, isExit = false;
	public int port = 8080;
	public int port_client = 6789;

	public Server() throws Exception {
		server = new ServerSocket(port);
		dataPeer = new ArrayList<DataPeer>();
		(new WaitForConnect()).start();
	}

	private boolean check_isOnline(String username) throws IOException {
		int size = dataPeer.size();
		if (size == 0) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			DataPeer index = dataPeer.get(i);
			if (index.getName().equals(username)) {
				if (index.getIsOnline() == true) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean check_exist(String name) throws IOException {
		int size = dataPeer.size();
		if (size == 0) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			DataPeer index = dataPeer.get(i);
			if (index.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkNewClient(String newUsername, String pass) throws IOException {
		if (newUsername.equals("") || pass.equals(""))
			return false;
		if (check_exist(newUsername) == true)
			return false;
		return true;
	}

	class WaitForConnect extends Thread {

		public void run() {
			super.run();
			String message1 = "";
			while (!isStop) {
				try {
					connection = server.accept();
					ServerApp.updateMessage("SERVER DANG CO KET NOI DEN");
					saveNewPeer("truong", "a", 9112);
					saveNewPeer("truong1", "a", 9812);
					obOutputClient = new ObjectOutputStream(connection.getOutputStream());
					obOutputClient.flush();
					obInputStream = new ObjectInputStream(connection.getInputStream());
					message1 = (String) obInputStream.readObject();
					if (com.networking.tags.DeCode.signUp.matcher(message1).matches()) {
						ArrayList<String> data = new ArrayList<String>();
						data = DeCode.getSignUp(message1);
						if (checkNewClient(data.get(0), data.get(1)) == true) {
							port_client = port_client + 11;
							saveNewPeer(data.get(0), data.get(1), port_client);
							ServerApp.updateMessage("ACCOUNT SIGN IN: " + data.get(0) + " SUCCESS");
							sendData(com.networking.tags.Tags.SIGN_UP_SUCCESS_OPEN_TAG + port_client
									+ com.networking.tags.Tags.SIGN_UP_SUCCESS_OPEN_TAG);
						} else {
							sendData(com.networking.tags.Tags.SIGN_UP_UNSUCCESS);
						}
					}
					if (com.networking.tags.DeCode.logIn.matcher(message1).matches()) {
						ArrayList<String> client_data = com.networking.tags.DeCode.getLogIn(message1);
						if (client_data == null) {

						} else {
							if (check_exist(client_data.get(0)) == true) {
								if (check_isOnline(client_data.get(0)) == false) {
									for (int i = 0; i < dataPeer.size(); i++) {
										if (dataPeer.get(i).getName().equals(client_data.get(0))
												&& dataPeer.get(i).getPass().equals(client_data.get(1))) {
											username = dataPeer.get(i).getName();

										}
									}
								}
							}
							if (username == "") {
								obOutputClient.writeObject(com.networking.tags.Tags.LOG_IN_RES_FAIL);
								obOutputClient.flush();

							} else {
								int pos = pos_in_datapeerList(username);
								obOutputClient.writeObject(sendSessionAccept(dataPeer.get(pos).getPort()));
								obOutputClient.flush();
								dataPeer.get(pos).setIsOnline(true);
								dataPeer.get(pos).setIp(connection.getInetAddress().getHostAddress().toString());
								obOutputClient.writeObject(sendList());
								obOutputClient.flush();
							}
						}
					}
					if (com.networking.tags.DeCode.refresh.matcher(message1).matches()) {
						System.out.println("refresh");
						obOutputClient.writeObject(send_refresh());
						obOutputClient.flush();

					}

				} catch (Exception e) {
				}
			}

		}
	}

	private void SignAccount(String message) throws Exception {
		ArrayList<String> data = new ArrayList<String>();
		data = DeCode.getSignUp(message);
		if (check_exist(data.get(0)) == false) {
			int portnumber = port + 11;
			port++;
			saveNewPeer(data.get(0), data.get(1), portnumber);
			ServerApp.updateMessage("ACCOUNT SIGN IN: " + data.get(0) + " SUCCESS");
			sendData(com.networking.tags.Tags.SIGN_UP_SUCCESS_OPEN_TAG + portnumber
					+ com.networking.tags.Tags.SIGN_UP_SUCCESS_OPEN_TAG);
		} else {
			sendData(com.networking.tags.Tags.SIGN_UP_UNSUCCESS);
		}
	}

	private void saveNewPeer(String name, String pass, int port) throws Exception {
		DataPeer peer = new DataPeer();
		if (dataPeer.size() == 0) {
			dataPeer = new ArrayList<DataPeer>();
		}
		peer.setPeer(name, pass, port);
		dataPeer.add(peer);
	}

	private void sendData(final String message) throws IOException {
		try {
			obOutputClient.writeObject(message);
			obOutputClient.flush();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private String sendSessionAccept(int port_for_client) throws Exception {
		int size = dataPeer.size();
		String msg = com.networking.tags.Tags.LOG_IN_RES_SUCCESS_OPEN_TAG;
		msg += com.networking.tags.Tags.PORT_OPEN_TAG;
		msg += port_for_client;
		msg += com.networking.tags.Tags.PORT_END_TAG;

		msg += com.networking.tags.Tags.LOG_IN_RES_SUCCESS_END_TAG;
		return msg;
	}

	private String send_refresh() throws Exception {
		String msg = Tags.SEND_REFRESH_OPEN_TAG;
		int size = dataPeer.size();
		for (int i = 0; i < size; i++) {
			if (dataPeer.get(i).getIsOnline() == true) {
				DataPeer peer = dataPeer.get(i);
				msg += Tags.PEER_OPEN_TAG;
				msg += Tags.PEER_NAME_OPEN_TAG;
				msg += peer.getName();
				msg += Tags.PEER_NAME_END_TAG;
				msg += Tags.IP_OPEN_TAG;
				msg += peer.getIp();
				msg += Tags.IP_END_TAG;
				msg += Tags.PORT_OPEN_TAG;
				msg += peer.getPort();
				msg += Tags.PORT_END_TAG;
				msg += Tags.PEER_CLOSE_TAG;
			}
		}
		msg += Tags.SEND_REFRESH_CLOSE_TAG;
		return msg;
	}

	public int pos_in_datapeerList(String usename) {
		// ham nay tra ve vi tri cua peer trong datapeer
		int i;
		int size = dataPeer.size();
		for (i = 0; i < size; i++) {
			if (dataPeer.get(i).getName() == usename) {
				return i;
			}
		}
		return -1;
	}

	private String sendList() throws IOException {
		String msg = "";
		int size = dataPeer.size();
		if (size == 0) {
			return msg;
		}
		msg += Tags.SESSION_OPEN_TAG;
		for (int i = 0; i < size; i++) {
			DataPeer peer = dataPeer.get(i);
			if (peer.getIsOnline() == true) {
				msg += Tags.PEER_OPEN_TAG;
				msg += Tags.PEER_NAME_OPEN_TAG;
				msg += peer.getName();
				msg += Tags.PEER_NAME_END_TAG;
				msg += Tags.IP_OPEN_TAG;
				msg += peer.getIp();
				msg += Tags.IP_END_TAG;
				msg += Tags.PORT_OPEN_TAG;
				msg += peer.getPort();
				msg += Tags.PORT_END_TAG;
				msg += Tags.PEER_CLOSE_TAG;
			}
		}
		msg += Tags.SESSION_CLOSE_TAG;
		return msg;
	}

	private int portNumber(String ipaddress) throws IOException {
		int size = dataPeer.size();
		if (size == 0) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (dataPeer.get(i).getIp().equals(ipaddress)) {
				return dataPeer.get(i).getPort();
			}
		}
		return 0;
	}

	public int indexLogout(String name) throws IOException {
		int size = dataPeer.size();
		for (int i = 1; i < size; i++) {
			if (dataPeer.get(i).getName().equals(name)) {
				return i;
			}
		}
		return 0;
	}

	public void LogoutAccount(String username) throws IOException {
		for (int i = 1; i < dataPeer.size(); i++) {
			if (dataPeer.get(i).getName().equals(username)) {
				dataPeer.get(i).setIsOnline(false);
				sendList();
			}
		}
	}

	public void stopServer() throws IOException {
		isStop = true;
		server.close();
		connection.close();
	}

	private void sendRequestChat(String msg) throws Exception {
		int size = dataPeer.size();
		String name = DeCode.getRequestChat(msg);
		for (int i = 0; i < size; i++) {
			if (dataPeer.get(i).getName().equals(name) && dataPeer.get(i).getIsOnline() == true) {
				obOutputClient.writeObject(com.networking.tags.Tags.SERVER_SENT_PORT_OPEN_TAG
						+ com.networking.tags.Tags.IP_OPEN_TAG + dataPeer.get(i) + com.networking.tags.Tags.IP_END_TAG
						+ com.networking.tags.Tags.IP_OPEN_TAG + connection.getInetAddress().getHostAddress().toString()
						+ com.networking.tags.Tags.IP_END_TAG + com.networking.tags.Tags.PORT_OPEN_TAG
						+ dataPeer.get(i).getPort() + com.networking.tags.Tags.PORT_END_TAG
						+ com.networking.tags.Tags.CHAT_REQ_END_TAG);
				obOutputClient.flush();
				return;
			}
		}
	}
}
