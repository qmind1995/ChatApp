package com.networking.tags ;

import java.util.ArrayList;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

import com.networking.data.DataPeer;

public class enCode {
	
	private static Pattern checkMessage = Pattern.compile("[^<>]*(<|>)") ; 		//Táº¡o máº«u tin nháº¯n, tin nháº¯n nháº­p vÃ o káº¿t thÃºc báº±ng < hoáº·c >
	
	public static String signUp(String name, String pass, String IP) {	
		return Tags.SIGN_UP_OPEN_TAG + Tags.USER_NAME_OPEN_TAG + name  			//tráº£ vá»� giao thá»©c Ä‘Äƒng kÃ­
			+ Tags.USER_NAME_END_TAG + Tags.PASSWORD_OPEN_TAG + pass 
			+ Tags.PASSWORD_END_TAG + Tags.IP_OPEN_TAG + IP 
			+ Tags.IP_END_TAG + Tags.SIGN_UP_END_TAG ;
	}
	
	public static String signUpSuccess(String port) {
		return Tags.SIGN_UP_SUCCESS_OPEN_TAG + Tags.PORT_OPEN_TAG + port + Tags.PORT_END_TAG + Tags.SIGN_UP_SUCCESS_END_TAG ;
	}
	
	public static String signUpUnsuccess() {
		return Tags.SIGN_UP_UNSUCCESS ;
	}
	
	public static String logIN(String name, String pass, String IP) {			
		return Tags.LOG_IN_OPEN_TAG + Tags.USER_NAME_OPEN_TAG + name 			//tráº£ vá»� giao thá»©c Ä‘Äƒng nháº­p
			+ Tags.USER_NAME_END_TAG + Tags.PASSWORD_OPEN_TAG + pass 
			+ Tags.PASSWORD_END_TAG + Tags.IP_OPEN_TAG + IP 
			+ Tags.IP_END_TAG + Tags.LOG_IN_END_TAG ;
	}
	
	public static String logInSuccess(ArrayList<DataPeer> dataPeer, int port) {
		String str = "" ;
		 for (int i = 0; i < dataPeer.size(); i++){                
			 str += Tags.PEER_NAME_OPEN_TAG + dataPeer.get(i).getName() + Tags.PEER_NAME_END_TAG ; 
			 }
		return Tags.LOG_IN_RES_SUCCESS_OPEN_TAG + Tags.PORT_OPEN_TAG + port 
				+ Tags.PORT_END_TAG + Tags.IS_ONLINE_OPEN_TAG +
				str				
				+ Tags.IS_ONLINE_END_TAG + Tags.LOG_IN_RES_SUCCESS_END_TAG ;
	}
	
	public static String logInFail() {
		return Tags.LOG_IN_RES_FAIL ;
	}
	
	public static String logOut(String name, String pass, String IP) {			
		return Tags.LOG_OUT_OPEN_TAG + Tags.USER_NAME_OPEN_TAG + name 				//tráº£ vá»� giao thá»©c Ä‘Äƒng xuáº¥t
			+ Tags.USER_NAME_END_TAG + Tags.IP_OPEN_TAG + IP 
			+ Tags.IP_END_TAG + Tags.LOG_OUT_END_TAG ;
	} 
	
	public static String chatRequest(String mName, String IP, String pName) {	
		return Tags.CHAT_REQ_OPEN_TAG + Tags.MY_NAME_OPEN_TAG + mName 				//tráº£ vá»� giao thá»©c káº¿t ná»‘i chat P2P
			+ Tags.MY_NAME_END_TAG + Tags.IP_OPEN_TAG + IP 
			+ Tags.IP_END_TAG + Tags.PEER_NAME_OPEN_TAG + pName 
			+ Tags.PEER_NAME_END_TAG + Tags.CHAT_REQ_END_TAG ;
	}
	
	public static String sendPort(String IP, String port) {
		return Tags.SERVER_SENT_PORT_OPEN_TAG + Tags.IP_OPEN_TAG + IP +
					Tags.IP_END_TAG + Tags.PORT_OPEN_TAG + port + 
					Tags.PORT_OPEN_TAG + Tags.SERVER_SENT_PORT_END_TAG ;
	}
	
	public static String sendMessage(String message) {						
		Matcher findMessage = checkMessage.matcher(message) ;
		String result = "";
		while (findMessage.find()) {
			String subMessage = findMessage.group(0) ;
			int begin = subMessage.length() ;
			char nextChar = message.charAt(subMessage.length() - 1);
			System.out.println(result) ;
			result += subMessage + nextChar ;
			subMessage = message.substring(begin, message.length()) ;
			message = subMessage ;
			findMessage = checkMessage.matcher(message) ;
		}
		result += message;
		return Tags.MESSAGE_OPEN_TAG + result + Tags.MESSAGE_END_TAG ;							//tráº£ vá»� giao thá»©c gá»­i tin nháº¯n
	}
	
	public static String escapeChat(String name, String IP, String port) {								//tráº£ vá»� giao thá»©c ngáº¯t káº¿t ná»‘i chat
		return Tags.ESC_CHAT_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + name
			+ Tags.PEER_NAME_END_TAG + Tags.IP_OPEN_TAG + IP 
			+ Tags.IP_END_TAG + Tags.PORT_OPEN_TAG + port 
			+ Tags.PORT_END_TAG + Tags.ESC_CHAT_END_TAG ;
	}
	
	public static String shareFile(String name) {
		return Tags.SHARE_FILE_OPEN_TAG + name + Tags.SHARE_FILE_END_TAG ;								//tráº£ vá»� giao thá»©c chia sáº» file
	}
	
	public static String fileRequest(String name) {
		return Tags.FILE_REQ_OPEN_TAG + name + Tags.FILE_REQ_END_TAG ;								//tráº£ vá»� giao thá»©c táº£i file
	}

	
	public static String data2Name(DataPeer a) {
		return Tags.USER_NAME_OPEN_TAG + a.getName() + Tags.USER_NAME_END_TAG ;
	}
        public static String sendRequest(String name) {
		return Tags.SESSION_KEEP_ALIVE_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG
				+ name + Tags.PEER_NAME_END_TAG + Tags.STATUS_OPEN_TAG
				+ Tags.SERVER_ONLINE + Tags.STATUS_CLOSE_TAG
				+ Tags.SESSION_KEEP_ALIVE_CLOSE_TAG;
	}
        public static String sendRequestChat(String name) {
		return Tags.CHAT_REQ_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + name
				+ Tags.PEER_NAME_END_TAG + Tags.CHAT_REQ_END_TAG;
	}
        
        public static String exit(String name) {
		return Tags.SESSION_KEEP_ALIVE_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG
				+ name + Tags.PEER_NAME_END_TAG + Tags.STATUS_OPEN_TAG
				+ Tags.SERVER_OFFLINE + Tags.STATUS_CLOSE_TAG
				+ Tags.SESSION_KEEP_ALIVE_CLOSE_TAG;
	}
        public static String sendFile(String name) {
		return Tags.FILE_REQ_OPEN_TAG + name + Tags.FILE_REQ_END_TAG;
	}
}