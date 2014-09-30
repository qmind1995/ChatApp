package com.networking.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.TextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PeerMessageApp {

	private Peer peerNode;
	private static String IPClient = "", nameUser = "", dataUser = "";
	private static int portClient = 0;
	public JFrame frame;
	private JTextField textName;
	private TextArea textSend;
	private static TextArea textDisPlayChat, textDisplayFriend;
	private JButton btnDisConnect, btnSend;

	public PeerMessageApp(String arg, int arg1, String name, String msg)
			throws Exception {
		IPClient = arg;
		portClient = arg1;
		nameUser = name;
		dataUser = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PeerMessageApp window = new PeerMessageApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { PeerMessageApp window = new
	 * PeerMessageApp(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	public static void updateChat(String msg) {
		textDisPlayChat.append(msg + "\n");
	}

	public static void clearAll() {
		textDisplayFriend.setText("");
		textDisplayFriend.setText("<All Friend Online>\n");
	}

	public static void updateFiend(String msg) {
		textDisplayFriend.append(msg + "\n");
	}

	public PeerMessageApp() throws Exception {
		initialize();
		peerNode = new Peer(IPClient, portClient, nameUser, dataUser);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 777, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblClientIP = new JLabel("User Name: ");
		lblClientIP.setBounds(6, 12, 82, 16);
		frame.getContentPane().add(lblClientIP);

		textName = new JTextField(nameUser);
		textName.setEditable(false);
		textName.setBounds(86, 6, 162, 28);
		frame.getContentPane().add(textName);
		textName.setColumns(10);

		textDisPlayChat = new TextArea();
		textDisPlayChat.setEditable(false);
		textDisPlayChat.setBounds(6, 40, 587, 317);
		frame.getContentPane().add(textDisPlayChat);

		textSend = new TextArea();
		textSend.setBounds(6, 363, 491, 64);
		frame.getContentPane().add(textSend);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textSend.getText().equals(""))
					try {
						peerNode.sendMessage(textSend.getText());
						textSend.setText("");
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		btnSend.setBounds(499, 363, 94, 64);
		frame.getContentPane().add(btnSend);

		btnDisConnect = new JButton("DisConnect");
		btnDisConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnDisConnect.setBounds(304, 6, 113, 29);
		frame.getContentPane().add(btnDisConnect);

		textDisplayFriend = new TextArea();
		textDisplayFriend.setText("<All Friend Online>");
		textDisplayFriend.setEditable(false);
		textDisplayFriend.setBounds(599, 40, 162, 317);
		frame.getContentPane().add(textDisplayFriend);
	}
}
