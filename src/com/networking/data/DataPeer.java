/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.networking.data;

/**
 *
 * @author Dell
 */
public class DataPeer {
	private String namePeer = "";
	private String passPeer = "";
	private int port = -1;
	private boolean isOnline = false;
        private String ipaddress = "";

        public boolean get_isOnl(){
            return isOnline;
        }
    
        
        public void setPeer(String name, String pass,int peerPort) {
		namePeer = name;
		passPeer = pass;
		port = peerPort;
	}
	
        public void setIp(String ip){
            ipaddress = ip;
        }
        public String getIp(){
            return ipaddress;
        }
	public void setName(String name) {
		namePeer = name;
	}
	
	public void setPass(String pass) {
		passPeer = pass;
	}

	public void setPort(int peerPort) {
		port = peerPort;
	}
        public void setIsOnline(boolean isOnl){
            isOnline = isOnl;
        }
/*	public void setPort(int port) {
		portPeer = port;
	}*/

	public String getName() {
		return namePeer;
	}
	
	public String getPass() {
		return passPeer;
	}

	public int getPort() {
		return port;
	}
        public boolean getIsOnline(){
            return isOnline;
        }
	/*public int getPort() {
		return portPeer;
	}*/
    
}
