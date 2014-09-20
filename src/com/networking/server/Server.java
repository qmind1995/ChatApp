package com.networking.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class Server {

	private static String WORKINK_DIR = System.getProperty("user.dir");
	private static String URL_DATA_FILE = "/data/dataFile.txt";

	JSONObject accountClient;
	ServerSocket server;
	Socket serverSocket;

	public Server(int port) throws Exception {
		loadData();
		server = new ServerSocket(port);
	}

	private void loadData() throws Exception {
		String URL = WORKINK_DIR + URL_DATA_FILE;
		File fileDataFile = new File(URL);
		String data = "";
		if (fileDataFile.exists()) {
			if (fileDataFile.length() > 0) {
				BufferedReader bufferedReaderEcho = new BufferedReader(
						new FileReader(URL));
				String tempData;
				while ((tempData = bufferedReaderEcho.readLine()) != null) {
					data += tempData;
				}
				bufferedReaderEcho.close();
				accountClient = new JSONObject(data);
			} else
				accountClient = new JSONObject();
		}
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */
}
