package com.networking.tags;

public class EnCode {

	public static String getCreateAccount(String name, String port) {
		return Tags.SESSION_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + name
				+ Tags.PEER_NAME_CLOSE_TAG + Tags.PORT_OPEN_TAG + port
				+ Tags.PORT_CLOSE_TAG + Tags.SESSION_CLOSE_TAG;
	}

	public static String sendRequest(String name, String status) {
		return Tags.SESSION_KEEP_ALIVE_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG
				+ name + Tags.PEER_NAME_CLOSE_TAG + Tags.STATUS_OPEN_TAG
				+ status + Tags.STATUS_CLOSE_TAG;
	}

	public static String sendMessage(String message) {
		return Tags.CHAT_MSG_OPEN_TAG + message + Tags.CHAT_MSG_CLOSE_TAG;
	}

}
