/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networking.tags;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Dell
 */
public class Tags {
	public static int IN_VALID = -1;

	public static int MAX_MSG_SIZE = 1024; 
	
        public static String SIGN_UP_OPEN_TAG = "<SIGN_UP>"; //1
        public static String SIGN_UP_END_TAG ="</SIGN_UP>"; //2
        public static String USER_NAME_OPEN_TAG = "<USERNAME>"; //3
        public static String USER_NAME_END_TAG = "</USERNAME>"; //4
        public static String PASSWORD_OPEN_TAG = "<PASSWORD>"; //5
        public static String PASSWORD_END_TAG = "</PASSWORD>"; //6 
        public static String SIGN_UP_SUCCESS_OPEN_TAG ="<SIGN_UP_SUCCESS>"; //7
        public static String SIGN_UP_SUCCESS_END_TAG ="</SIGN_UP_SUCCESS>"; //
        public static String SIGN_UP_UNSUCCESS = "<SIGN_UP_UNSUCCESS/>";  //8
        public static String LOG_IN_OPEN_TAG = "<LOG_IN>";  //9
        public static String LOG_IN_END_TAG = "</LOG_IN>"; //10
        public static String LOG_IN_RES_SUCCESS_OPEN_TAG = "<LOG_IN_RES_SUCCESS>";//11
        public static String LOG_IN_RES_SUCCESS_END_TAG = "</LOG_IN_RES_SUCCESS>";
        public static String LOG_IN_RES_FAIL = "<LOG_IN_RES_FAIL/>";//12
        public static String IP_OPEN_TAG = "<IP>";//13
        public static String IP_END_TAG = "</IP>";//14
        public static String LOG_OUT_OPEN_TAG = "<LOG_OUT>";//15
        public static String LOG_OUT_END_TAG = "</LOG_OUT>";//16
        public static String PEER_NAME_OPEN_TAG = "<PEER_NAME>";//17
        public static String PEER_NAME_END_TAG = "</PEER_NAME>";//18
        public static String IS_ONLINE_OPEN_TAG = "<IS_ONLINE>";//19
        public static String IS_ONLINE_END_TAG = "</IS_ONLINE>";//20
        public static String TO_PEER_OPEN_TAG = "<TO_PEER>";//21
        public static String TO_PEER_END_TAG = "</TO_PEER>";//22
        public static String TO_IP_OPEN_TAG = "<TO_IP>";//23
        public static String TO_IP_END_TAG = "</TO_IP>";//24
        public static String LIST_ONL_PEER_OPEN_TAG = "<LIST_ONL_PEER>";//25
        public static String LIST_ONL_PEER_END_TAG = "</LIST_ONL_PEER>";//26
        public static String CHAT_REQ_OPEN_TAG = "<CHAT_REQ>";//27
        public static String CHAT_REQ_END_TAG = "</CHAT_REQ>";//28
        public static String MY_NAME_OPEN_TAG = "<MY_NAME>";//29
        public static String MY_NAME_END_TAG = "</MY_NAME>";//30
        public static String SERVER_SENT_PORT_OPEN_TAG = "<SERVER_SENT_PORT>";//31
        public static String SERVER_SENT_PORT_END_TAG = "</SERVER_SENT_PORT>";//32
        public static String MESSAGE_OPEN_TAG = "<MESSAGE>";//33
        public static String MESSAGE_END_TAG = "</MESSAGE>";//34
        public static String ESC_CHAT_OPEN_TAG = "<ESC_CHAT>";//35
        public static String ESC_CHAT_END_TAG = "</ESC_CHAT>";//36
        public static String PORT_OPEN_TAG = "<PORT>";//37
        public static String PORT_END_TAG = "</PORT>";//38
        public static String SHARE_FILE_OPEN_TAG = "<SHARE_FILE>";//39
        public static String SHARE_FILE_END_TAG = "</SHARE_FILE>";//40
        public static String LIST_FILE_OPEN_TAG = "<LIST_FILE>";//41
        public static String LIST_FILE_END_TAG = "</LIST_FILE>";//42
        public static String FILE_OPEN_TAG = "<FILE>";//43
        public static String FILE_END_TAG = "</FILE>";//44
        public static String FILE_REQ_OPEN_TAG = "<FILE_REQ>";//45        
        public static String FILE_REQ_END_TAG = "</FILE_REQ>";//46
        public static String FILE_DATA_BEGIN_TAG = "<FILE_DATA_BEGIN/>";//47
        public static String FILE_DATA_END_TAG = "<FILE_DATA_END/>";//48
        public static String PEER_OPEN_TAG = "<PEER>";// 22
	public static String PEER_CLOSE_TAG = "</PEER>";// 23
        public static String SESSION_OPEN_TAG = "<SESSION>";// 1
	public static String SESSION_CLOSE_TAG = "</SESSION>";
        public static String SESSION_KEEP_ALIVE_OPEN_TAG = "<SESSION_KEEP_ALIVE>";;
        public static String SESSION_KEEP_ALIVE_CLOSE_TAG = "</SESSION_KEEP_ALIVE>";
        public static String STATUS_OPEN_TAG = "<STATUS>";
	public static String STATUS_CLOSE_TAG = "</STATUS>";
	public static String SERVER_ONLINE = "ALIVE";
	public static String SERVER_OFFLINE = "OOPS >>>WILL BE KILLED<<< ";
        public static String CHAT_DENY_TAG = "<CHAT_DENY />";
        public static String CHAT_ACCEPT_TAG = "<CHAT_ACCEPT />";
	public static String CHAT_CLOSE_TAG = "<CHAT_CLOSE />";
	public static String FILE_REQ_ACK_OPEN_TAG = "<FILE_REQ_ACK>";// 27
	public static String FILE_REQ_ACK_CLOSE_TAG = "</FILE_REQ_ACK>";// 28
        
	public static String FILE_REQ_NOACK_TAG = "<FILE_REQ_NOACK />";// 26
        public static String SESSION_ACCEPT_OPEN_TAG = "<SESSION_ACCEPT>";
	public static String SESSION_ACCEPT_CLOSE_TAG = "</SESSION_ACCEPT>";// 13
        
        public static String REFRESH="</REFRESH>";
        
        public static String SEND_REFRESH_OPEN_TAG = "<SEND_RE>";
        public static String SEND_REFRESH_CLOSE_TAG = "</SEND_RE>";
        private static int equalData(String msg) {
            if (msg.equals(""))	return IN_VALID;
            if (msg.equals(SIGN_UP_OPEN_TAG  )) return 1;
            if (msg.equals(SIGN_UP_END_TAG )) return 2;
            if (msg.equals(USER_NAME_OPEN_TAG )) return 3;
            if (msg.equals(USER_NAME_END_TAG )) return 4;
            if (msg.equals(PASSWORD_OPEN_TAG )) return 5;
            if (msg.equals(PASSWORD_END_TAG )) return 6;
            if (msg.equals(SIGN_UP_SUCCESS_OPEN_TAG )) return 7;
            if (msg.equals(SIGN_UP_UNSUCCESS )) return 8;
            if (msg.equals(LOG_IN_OPEN_TAG )) return 9;
            if (msg.equals(LOG_IN_END_TAG )) return 10;
            if (msg.equals(LOG_IN_RES_SUCCESS_OPEN_TAG )) return 11;
            if (msg.equals(LOG_IN_RES_FAIL )) return 12;
            if (msg.equals(IP_OPEN_TAG )) return 13;
            if (msg.equals(IP_END_TAG )) return 14;
            if (msg.equals(LOG_OUT_OPEN_TAG )) return 15;
            if (msg.equals(LOG_OUT_END_TAG )) return 16;
            if (msg.equals(PEER_NAME_OPEN_TAG )) return 17;
            if (msg.equals(PEER_NAME_END_TAG )) return 18;
            if (msg.equals(IS_ONLINE_OPEN_TAG )) return 19;
            if (msg.equals(IS_ONLINE_END_TAG )) return 20;
            if (msg.equals(TO_PEER_OPEN_TAG )) return 21;
            if (msg.equals(TO_PEER_END_TAG )) return 22;
            if (msg.equals(TO_IP_OPEN_TAG )) return 23;
            if (msg.equals(TO_IP_END_TAG )) return 24;
            if (msg.equals(LIST_ONL_PEER_OPEN_TAG )) return 25;
            if (msg.equals(LIST_ONL_PEER_END_TAG )) return 26;
            if (msg.equals(CHAT_REQ_OPEN_TAG )) return 27;
            if (msg.equals(CHAT_REQ_END_TAG )) return 28;
            if (msg.equals(MY_NAME_OPEN_TAG )) return 29;
            if (msg.equals(MY_NAME_END_TAG )) return 30;
            if (msg.equals(SERVER_SENT_PORT_OPEN_TAG )) return 31;
            if (msg.equals(SERVER_SENT_PORT_END_TAG )) return 32;
            if (msg.equals(MESSAGE_OPEN_TAG )) return 33;
            if (msg.equals(MESSAGE_END_TAG )) return 34;
            if (msg.equals(ESC_CHAT_OPEN_TAG )) return 35 ;
            if (msg.equals(ESC_CHAT_END_TAG )) return 36;
            if (msg.equals(PORT_OPEN_TAG )) return 37;
            if (msg.equals(PORT_END_TAG )) return 38;
            if (msg.equals(SHARE_FILE_OPEN_TAG )) return 39 ;
            if (msg.equals(SHARE_FILE_END_TAG )) return 40;
            if (msg.equals(LIST_FILE_OPEN_TAG )) return 41;
            if (msg.equals(LIST_FILE_END_TAG )) return 42;
            if (msg.equals(FILE_OPEN_TAG )) return 43;
            if (msg.equals(FILE_END_TAG )) return 44;
            if (msg.equals(FILE_REQ_OPEN_TAG )) return 45;
            if (msg.equals(FILE_REQ_END_TAG )) return 46;
            if (msg.equals(FILE_DATA_BEGIN_TAG )) return 47;
            if (msg.equals(FILE_DATA_END_TAG )) return 48;
            if (msg.equals(SIGN_UP_SUCCESS_OPEN_TAG )) return 49;
            if (msg.equals(PEER_OPEN_TAG)) return 50;
            if (msg.equals(PEER_CLOSE_TAG)) return 51;
            if (msg.equals(SESSION_OPEN_TAG)) return 52; //bo sung :)))
            if (msg.equals(SESSION_CLOSE_TAG)) return 53;
            if (msg.equals(SESSION_KEEP_ALIVE_CLOSE_TAG)) return 54;
            if (msg.equals(SESSION_KEEP_ALIVE_OPEN_TAG)) return 55;
            
            
            return IN_VALID; // other case!
        }
        public static int show(JFrame frame, String msg, boolean type) {
		if (type)
			return JOptionPane.showConfirmDialog(frame, msg, null,
					JOptionPane.YES_NO_OPTION);
		JOptionPane.showMessageDialog(frame, msg);
		return IN_VALID;
	}
}
