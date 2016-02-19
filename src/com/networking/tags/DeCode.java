package com.networking.tags;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.networking.data.DataPeer;

//import com.networking.data.DataPeer;
import com.networking.tags.Tags;

public class DeCode {

    private static Pattern users = Pattern.compile(Tags.SEND_REFRESH_OPEN_TAG
            + "(" + Tags.PEER_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + ".+"
            + Tags.PEER_NAME_END_TAG + Tags.IP_OPEN_TAG + ".+"
            + Tags.IP_END_TAG + Tags.PORT_OPEN_TAG + "[0-9]+"
            + Tags.PORT_END_TAG + Tags.PEER_CLOSE_TAG + ")*"
            + Tags.SEND_REFRESH_CLOSE_TAG);

    public static Pattern signUp = Pattern
            .compile(Tags.SIGN_UP_OPEN_TAG
                    + Tags.USER_NAME_OPEN_TAG + ".*" + Tags.USER_NAME_END_TAG
                    + Tags.PASSWORD_OPEN_TAG + ".*" + Tags.PASSWORD_END_TAG
                    + Tags.SIGN_UP_END_TAG);
    
    public static Pattern refresh = Pattern.compile(Tags.REFRESH);
    
    public static ArrayList<String> getSignUp(String msg) { // Lay thong tin username va id khi signup
        ArrayList<String> user = new ArrayList<String>();
        if (signUp.matcher(msg).matches()) {
            Pattern findName = Pattern.compile(Tags.USER_NAME_OPEN_TAG + "[^<>]*"
                    + Tags.USER_NAME_END_TAG);
            Pattern findPass = Pattern.compile(Tags.PASSWORD_OPEN_TAG + "[^<>]*"
                    + Tags.PASSWORD_END_TAG);

            Matcher find = findName.matcher(msg);
            if (find.find()) {
                String name = find.group(0);
                user.add(name.substring(Tags.USER_NAME_OPEN_TAG.length(),
                        name.length() - Tags.USER_NAME_END_TAG.length()));

                find = findPass.matcher(msg);
                if (find.find()) {
                    String port = find.group(0);
                    user.add(port.substring(Tags.PASSWORD_OPEN_TAG.length(),
                            port.length() - Tags.PASSWORD_END_TAG.length()));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return user;
    }

    public static Pattern signUpSuccess = Pattern
            .compile(Tags.SIGN_UP_SUCCESS_OPEN_TAG
                    + Tags.PORT_OPEN_TAG + ".*" + Tags.PORT_END_TAG
                    + Tags.SIGN_UP_SUCCESS_END_TAG);

    public static String getSignUpSuccess(String msg) { //lay thong tin cong khi dang ky thanh cong
        String portReturn = "";
        if (signUpSuccess.matcher(msg).matches()) {
            Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[0-9]*" + Tags.PORT_END_TAG);

            Matcher find = findPort.matcher(msg);
            if (find.find()) {
                String port = find.group(0);
                portReturn = port.substring(Tags.PORT_OPEN_TAG.length(),
                        port.length() - Tags.PORT_END_TAG.length());

            } else {
                return null;
            }
        } else {
            return null;
        }
        return portReturn;
    }

    private static Pattern signUpUnSuccess = Pattern
            .compile(Tags.SIGN_UP_UNSUCCESS);

    public static boolean checkSignUpUnSuccess(String msg) { // Khi dang ky thanh cong se tra ve true va false
        if (signUpUnSuccess.matcher(msg).matches()) {
            return true;
        }
        return false;
    }

    public static Pattern logIn = Pattern
            .compile(Tags.LOG_IN_OPEN_TAG
                    + Tags.USER_NAME_OPEN_TAG + ".*" + Tags.USER_NAME_END_TAG
                    + Tags.PASSWORD_OPEN_TAG + ".*" + Tags.PASSWORD_END_TAG
                    + Tags.IP_OPEN_TAG + ".+" + Tags.IP_END_TAG
                    + Tags.LOG_IN_END_TAG);

    public static ArrayList<String> getLogIn(String msg) { // Lay thong tin ten user , pass , ip khi dang nhap
        ArrayList<String> user = new ArrayList<String>();
        if (logIn.matcher(msg).matches()) {
            Pattern findName = Pattern.compile(Tags.USER_NAME_OPEN_TAG + "[^<>]*"
                    + Tags.USER_NAME_END_TAG);
            Pattern findPass = Pattern.compile(Tags.PASSWORD_OPEN_TAG + "[^<>]*"
                    + Tags.PASSWORD_END_TAG);
            Pattern findIp = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*"
                    + Tags.IP_END_TAG);
            Matcher find = findName.matcher(msg);
            if (find.find()) {
                String name = find.group(0);
                user.add(name.substring(Tags.USER_NAME_OPEN_TAG.length(),
                        name.length() - Tags.USER_NAME_END_TAG.length()));

                find = findPass.matcher(msg);
                if (find.find()) {
                    String port = find.group(0);
                    user.add(port.substring(Tags.PASSWORD_OPEN_TAG.length(),
                            port.length() - Tags.PASSWORD_END_TAG.length()));

                    find = findIp.matcher(msg);
                    if (find.find()) {
                        String ip = find.group(0);
                        user.add(ip.substring(Tags.IP_OPEN_TAG.length(),
                                ip.length() - Tags.IP_END_TAG.length()));
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return user;
    }

    public static ArrayList<DataPeer> updatePeerOnline(
            ArrayList<DataPeer> peerList, String msg) {
        Pattern userLogOut = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*"
                + Tags.PEER_NAME_END_TAG);
        if (logOut.matcher(msg).matches()) {
            Matcher findState = userLogOut.matcher(msg);

            if (findState.find()) {
                String findPeer = findState.group(0);
                int size = peerList.size();
                String name = findPeer.substring(Tags.PEER_NAME_OPEN_TAG.length(),
                        findPeer.length() - Tags.PEER_NAME_END_TAG.length());
                for (int i = 0; i < size; i++) {
                    if (name.equals(peerList.get(i).getName())) {
                        peerList.remove(i);
                        break;
                    }
                }
            }
        }
        return peerList;
    }

    public static Pattern logOut = Pattern
            .compile(Tags.LOG_OUT_OPEN_TAG
                    + Tags.PEER_NAME_OPEN_TAG + ".*" + Tags.PEER_NAME_END_TAG
                    + Tags.LOG_OUT_END_TAG);

    public static String getLogOut(String msg) { //Lay thong tin user va ip khi dang xuat
        String user = "";
        if (logOut.matcher(msg).matches()) {
            Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*"
                    + Tags.PEER_NAME_END_TAG);
            Pattern findIp = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*"
                    + Tags.IP_END_TAG);

            Matcher find = findName.matcher(msg);
            if (find.find()) {
                String name = find.group(0);
                user = (name.substring(Tags.PEER_NAME_OPEN_TAG.length(),
                        name.length() - Tags.PEER_NAME_END_TAG.length()));
            } else {
                return null;
            }
        } else {
            return null;
        }
        return user;
    }

    public static Pattern requestChat = Pattern
            .compile(Tags.CHAT_REQ_OPEN_TAG
                    + Tags.PEER_NAME_OPEN_TAG + ".+" + Tags.PEER_NAME_END_TAG
                    + Tags.CHAT_REQ_END_TAG);

    public static String getRequestChat(String msg) { // yeu cau chat tra ve ten nguoi ket noi den
        String name = "";
        if (requestChat.matcher(msg).matches()) {
            Pattern findPeerName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*"
                    + Tags.PEER_NAME_END_TAG);

            Matcher find = findPeerName.matcher(msg);
            if (find.find()) {
                String peerName = find.group(0);

                name = peerName.substring(Tags.PEER_NAME_OPEN_TAG.length(),
                        peerName.length() - Tags.PEER_NAME_END_TAG.length());
            } else {
                return null;
            }
        } else {
            return null;
        }
        return name;
    }

    private static Pattern serverSentPort = Pattern //Mau server sent port
            .compile(Tags.SERVER_SENT_PORT_OPEN_TAG
                    + Tags.IP_OPEN_TAG + ".+" + Tags.IP_END_TAG
                    + Tags.PORT_OPEN_TAG + ".*" + Tags.PORT_END_TAG
                    + Tags.SERVER_SENT_PORT_END_TAG);

    public static ArrayList<String> getServerSentPort(String msg) { // Lay thong tin ip 1 , user , ip2 , va port
        ArrayList<String> send_port = new ArrayList<String>();
        if (serverSentPort.matcher(msg).matches()) {
            Pattern findIpUser1 = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*"
                    + Tags.IP_END_TAG);
            Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[^<>]*"
                    + Tags.PORT_END_TAG);

            Matcher find = findIpUser1.matcher(msg);
            if (find.find()) {
                String ip = find.group(0);
                send_port.add(ip.substring(Tags.IP_OPEN_TAG.length(),
                        ip.length() - Tags.IP_END_TAG.length()));

                find = findPort.matcher(msg);
                if (find.find()) {
                    String port = find.group(0);
                    send_port.add(port.substring(Tags.PORT_OPEN_TAG.length(),
                            port.length() - Tags.PORT_END_TAG.length()));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return send_port;
    }

    private static Pattern message = Pattern.compile(Tags.MESSAGE_OPEN_TAG
            + ".*" + Tags.MESSAGE_END_TAG);

    public static String getMessage(String msg) {
        if (message.matcher(msg).matches()) {
            int begin = Tags.MESSAGE_OPEN_TAG.length();
            int end = msg.length() - Tags.MESSAGE_END_TAG.length();
            String message = msg.substring(begin, end);
            return message;
        }
        return null;
    }

    private static Pattern escape = Pattern.compile(Tags.ESC_CHAT_OPEN_TAG
            + Tags.PEER_NAME_OPEN_TAG + ".*" + Tags.PEER_NAME_END_TAG
            + Tags.IP_OPEN_TAG + ".+" + Tags.IP_END_TAG
            + Tags.PORT_OPEN_TAG + ".*" + Tags.PORT_END_TAG
            + Tags.ESC_CHAT_END_TAG);

    public static ArrayList<String> getEscape(String msg) {
        ArrayList<String> esc = new ArrayList<String>();
        if (escape.matcher(msg).matches()) {
            Pattern findMyName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*"
                    + Tags.PEER_NAME_END_TAG);
            Pattern findIp = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*"
                    + Tags.IP_END_TAG);
            Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[^<>]*"
                    + Tags.PORT_END_TAG);

            Matcher find = findMyName.matcher(msg);
            if (find.find()) {
                String myName = find.group(0);
                esc.add(myName.substring(Tags.PEER_NAME_OPEN_TAG.length(),
                        myName.length() - Tags.PEER_NAME_END_TAG.length()));

                find = findIp.matcher(msg);
                if (find.find()) {
                    String ip = find.group(0);
                    esc.add(ip.substring(Tags.IP_OPEN_TAG.length(),
                            ip.length() - Tags.IP_END_TAG.length()));

                    find = findPort.matcher(msg);
                    if (find.find()) {
                        String port = find.group(0);
                        esc.add(port.substring(Tags.PORT_OPEN_TAG.length(),
                                port.length() - Tags.PORT_END_TAG.length()));

                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return esc;
    }

    private static Pattern requestFile = Pattern
            .compile(Tags.FILE_REQ_OPEN_TAG + ".*" + Tags.FILE_REQ_END_TAG);

    public static boolean acceptRequestFile(String name) {
        if (requestFile.matcher(name).matches()) {
            return true;
        }
        return false;
    }

    private static Pattern sendFile = Pattern
            .compile(Tags.FILE_DATA_BEGIN_TAG + ".*"
                    + Tags.FILE_DATA_END_TAG);

    public static boolean sendFileSuccess(String msg) {
        if (sendFile.matcher(msg).matches()) {
            return true;
        }
        return false;
    }
    public static Pattern Online = Pattern
            .compile(Tags.LOG_IN_RES_SUCCESS_OPEN_TAG
                    + Tags.PORT_OPEN_TAG + ".*" + Tags.PORT_END_TAG
                    + Tags.LOG_IN_RES_SUCCESS_END_TAG);

    public static String portOnl(String msg) {
        Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[^<>]*"
                + Tags.PORT_END_TAG);
        String portReturn = "";
        if (Online.matcher(msg).matches()) {
            Matcher find = findPort.matcher(msg);

            if (find.find()) {
                String port = find.group(0);
                portReturn = port.substring(Tags.PORT_OPEN_TAG.length(),
                        port.length() - Tags.PORT_END_TAG.length());
            }
        }
        return portReturn;
    }

    public static ArrayList<String> userOnl(String msg) {
        Pattern findUser = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*"
                + Tags.PEER_NAME_END_TAG);
        ArrayList<String> users = new ArrayList<String>();
        if (Online.matcher(msg).matches()) {

            Matcher find = findUser.matcher(msg);
            while (find.find()) {
                String peer = find.group(0);
                users.add(peer.substring(Tags.PEER_NAME_OPEN_TAG.length(),
                        peer.length() - Tags.PEER_NAME_END_TAG.length()));
            }
        }
        return users;
    }

    public static ArrayList<DataPeer> getAllUser(String msg) { // bo sung :))
        ArrayList<DataPeer> user = new ArrayList<DataPeer>();
        Pattern findPeer = Pattern.compile(
                  Tags.PEER_OPEN_TAG
                + Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG
                + Tags.IP_OPEN_TAG + "[^<>]*" + Tags.IP_END_TAG
                + Tags.PORT_OPEN_TAG + "[0-9]*" + Tags.PORT_END_TAG
                + Tags.PEER_CLOSE_TAG);
        Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + ".*"
                + Tags.PEER_NAME_END_TAG);
        Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[0-9]*"
                + Tags.PORT_END_TAG);
        Pattern findIP = Pattern.compile(Tags.IP_OPEN_TAG + ".+"
                + Tags.IP_END_TAG);
        if (users.matcher(msg).matches()) {
            Matcher find = findPeer.matcher(msg);
            while (find.find()) {
                String peer = find.group(0);
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
                    dataPeer.setIp(findInfo.group(0).substring(4,
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
        } else {
            return null;
        }
        return user;
    }

    public static String getNameRequestChat(String msg) {
        Pattern checkRequest = Pattern.compile(Tags.CHAT_REQ_OPEN_TAG
                + Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG
                + Tags.CHAT_REQ_END_TAG);
        if (checkRequest.matcher(msg).matches()) {
            int lenght = msg.length();
            String name = msg
                    .substring(
                            (Tags.CHAT_REQ_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG)
                            .length(),
                            lenght
                            - (Tags.PEER_NAME_END_TAG + Tags.CHAT_REQ_END_TAG)
                            .length());
            return name;
        }
        return null;
    }

    public static boolean checkFile(String name) {
        if (checkNameFile.matcher(name).matches()) {
            return true;
        }
        return false;
    }
    private static Pattern checkNameFile = Pattern
            .compile(Tags.FILE_REQ_OPEN_TAG + ".*" + Tags.FILE_REQ_END_TAG);
    public static boolean checkFeedBack(String msg) {
		if (feedBack.matcher(msg).matches())
			return true;
		return false;
	}
    private static Pattern feedBack = Pattern
			.compile(Tags.FILE_REQ_ACK_OPEN_TAG + ".*"
					+ Tags.FILE_REQ_ACK_CLOSE_TAG);
}
