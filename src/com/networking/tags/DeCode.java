package com.networking.tags;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.netwoking.data.DataPeer;

public class DeCode {

	private static Pattern createAccount = Pattern
			.compile(Tags.SESSION_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + ".*"
					+ Tags.PEER_NAME_CLOSE_TAG + Tags.PORT_OPEN_TAG + ".*"
					+ Tags.PORT_CLOSE_TAG + Tags.SESSION_CLOSE_TAG);

	private static Pattern users = Pattern.compile(Tags.SESSION_ACCEPT_OPEN_TAG
			+ "(" + Tags.PEER_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + ".+"
			+ Tags.PEER_NAME_CLOSE_TAG + Tags.IP_OPEN_TAG + ".+"
			+ Tags.IP_CLOSE_TAG + Tags.PORT_OPEN_TAG + "[0-9]+"
			+ Tags.PORT_CLOSE_TAG + Tags.PEER_CLOSE_TAG + ")*"
			+ Tags.SESSION_ACCEPT_CLOSE_TAG);

	private static Pattern request = Pattern
			.compile(Tags.SESSION_KEEP_ALIVE_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG
					+ "[^" + Tags.PEER_NAME_CLOSE_TAG + "]+"
					+ Tags.STATUS_OPEN_TAG + Tags.STATUS_CLOSE_TAG + "("
					+ Tags.SERVER_ONLINE + "|" + Tags.SERVER_OFFLINE + ")"
					+ Tags.PEER_NAME_CLOSE_TAG
					+ Tags.SESSION_KEEP_ALIVE_CLOSE_TAG);

	private static Pattern message = Pattern.compile(Tags.CHAT_MSG_OPEN_TAG
			+ ".*" + Tags.CHAT_MSG_CLOSE_TAG);

	public static ArrayList<String> getUser(String msg) {
		ArrayList<String> user = new ArrayList<String>();
		if (createAccount.matcher(msg).matches()) {
			Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + ".*"
					+ Tags.PEER_NAME_CLOSE_TAG);
			Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[0-9]*"
					+ Tags.PORT_CLOSE_TAG);
			Matcher find = findName.matcher(msg);
			if (find.find()) {
				String name = find.group(0);
				user.add(name.substring(11, name.length() - 12));
				find = findPort.matcher(msg);
				if (find.find()) {
					String port = find.group(0);
					user.add(port.substring(6, port.length() - 7));
				} else
					return null;
			} else
				return null;
		} else
			return null;

		return user;
	}

	public static ArrayList<DataPeer> getAllUser(String msg) {
		ArrayList<DataPeer> user = new ArrayList<DataPeer>();
		System.out.println(msg);
		Pattern findPeer = Pattern.compile(Tags.PEER_OPEN_TAG
				+ Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_CLOSE_TAG
				+ Tags.IP_OPEN_TAG + "[^<>]*" + Tags.IP_CLOSE_TAG
				+ Tags.PORT_OPEN_TAG + "[0-9]*" + Tags.PORT_CLOSE_TAG
				+ Tags.PEER_CLOSE_TAG);
		Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + ".*"
				+ Tags.PEER_NAME_CLOSE_TAG);
		Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[0-9]*"
				+ Tags.PORT_CLOSE_TAG);
		Pattern findIP = Pattern.compile(Tags.IP_OPEN_TAG + ".+"
				+ Tags.IP_CLOSE_TAG);
		if (users.matcher(msg).matches()) {
			Matcher find = findPeer.matcher(msg);
			while (find.find()) {
				String peer = find.group(0);
				System.out.println(peer);
				String data = "";
				DataPeer dataPeer = new DataPeer();
				Matcher findInfo = findName.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setName(data.substring(11, data.length() - 12));
				}
				findInfo = findIP.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setHost(findInfo.group(0).substring(5,
							data.length() - 5));
				}
				findInfo = findPort.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setPort(Integer.parseInt(data.substring(6,
							data.length() - 7)));
				}
				user.add(dataPeer);
			}
		} else
			return null;
		return user;
	}

	public static ArrayList<DataPeer> updatePeerOnline(
			ArrayList<DataPeer> peerList, String msg) {
		Pattern alive = Pattern.compile("[^" + Tags.SERVER_ONLINE + "]+" + "["
				+ Tags.SERVER_ONLINE + "]" + "[^" + Tags.SERVER_ONLINE + "]+");
		Pattern killUser = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^"
				+ Tags.PEER_NAME_CLOSE_TAG + "]+" + Tags.PEER_NAME_CLOSE_TAG
				+ Tags.STATUS_OPEN_TAG + Tags.SERVER_OFFLINE
				+ Tags.STATUS_CLOSE_TAG);
		if (request.matcher(msg).matches()) {
			Matcher findUser = killUser.matcher(msg);
			if (alive.matcher(msg).matches())
				return peerList;
			else if (findUser.find()) {
				String findPeer = findUser.group(0);
				int size = peerList.size();
				int lenght = findPeer.length() - 1
						- Tags.STATUS_CLOSE_TAG.length()
						- Tags.SERVER_OFFLINE.length()
						- Tags.STATUS_OPEN_TAG.length()
						- Tags.PEER_NAME_CLOSE_TAG.length();
				String name = findPeer.substring(11, lenght);
				for (int i = 0; i < size; i++)
					if (name.equals(peerList.get(i).getName())) {
						peerList.remove(i);
						break;
					}
			}
		}
		return peerList;
	}

	public static String getMessage(String msg) {
		if (message.matcher(msg).matches()) {
			int begin = Tags.CHAT_MSG_OPEN_TAG.length();
			int end = msg.length() - Tags.CHAT_MSG_CLOSE_TAG.length();
			String message = msg.substring(begin, end);
			return message;
		}
		return null;
	}
}