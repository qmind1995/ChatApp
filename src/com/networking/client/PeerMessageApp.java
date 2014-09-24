package com.networking.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PeerMessageApp {

	private Peer peerNode;
	private String portClient = "", IPClient = "";
	private JFrame frame;
	private JTextField textIP;
	private JTextField textPort;
	private TextArea textSend;
	private static TextArea textDisPlayChat;
	private JButton btnConnect;
	private JLabel lblFailed;
	private JTextField peerIP;
	private JTextField peerPORT;

	public static void main(String[] args) {
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

	public static void updateChat(String msg) {
		textDisPlayChat.append(msg + "\n");
	}

	public PeerMessageApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblClientIP = new JLabel("Client IP: ");
		lblClientIP.setBounds(6, 12, 61, 16);
		frame.getContentPane().add(lblClientIP);

		textIP = new JTextField();
		textIP.setBounds(108, 6, 162, 28);
		frame.getContentPane().add(textIP);
		textIP.setColumns(10);

		JLabel labelPort = new JLabel("Client PORT: ");
		labelPort.setBounds(6, 52, 90, 16);
		frame.getContentPane().add(labelPort);

		textPort = new JTextField();
		textPort.setColumns(10);
		textPort.setBounds(108, 46, 162, 28);
		frame.getContentPane().add(textPort);

		textDisPlayChat = new TextArea();
		textDisPlayChat.setEditable(false);
		textDisPlayChat.setBounds(6, 90, 587, 206);
		frame.getContentPane().add(textDisPlayChat);

		textSend = new TextArea();
		textSend.setBounds(6, 302, 491, 64);
		frame.getContentPane().add(textSend);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.print("Send");
					IPClient = peerIP.getText();
					portClient = peerPORT.getText();
					if (!IPClient.equals("") && !portClient.equals("")) {
						peerNode.connectPeer(IPClient, portClient,
								textSend.getText());
						PeerMessageApp.updateChat(textSend.getText());
						textSend.setText("");
					} else
						PeerMessageApp.updateChat("NOT CONNECT");
				} catch (Exception e) {
					PeerMessageApp.updateChat("NOT CONNECT");
					e.printStackTrace();
				}
			}
		});
		btnSend.setBounds(499, 302, 94, 64);
		frame.getContentPane().add(btnSend);

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lblFailed.setVisible(false);
				portClient = textPort.getText();
				IPClient = textIP.getText();
				if (portClient.equals("") || IPClient.equals(""))
					lblFailed.setVisible(true);
				else {
					try {
						peerNode = new Peer(IPClient, portClient);
					} catch (Exception e1) {
						lblFailed.setVisible(true);
						e1.printStackTrace();
					}
				}
			}
		});
		btnConnect.setBounds(282, 47, 117, 29);
		frame.getContentPane().add(btnConnect);

		lblFailed = new JLabel("Connect Failed Because IP and PORT Failed");
		lblFailed.setForeground(Color.RED);
		lblFailed.setBounds(282, 12, 289, 16);
		lblFailed.setVisible(false);
		frame.getContentPane().add(lblFailed);

		JButton btnDisConnect = new JButton("DisConnect");
		btnDisConnect.setBounds(476, 47, 117, 29);
		frame.getContentPane().add(btnDisConnect);

		JLabel lblPeerConnectIp = new JLabel("Peer Connect IP :");
		lblPeerConnectIp.setBounds(6, 384, 117, 16);
		frame.getContentPane().add(lblPeerConnectIp);

		JLabel lblPeerConnectPort = new JLabel("Peer Connect PORT :");
		lblPeerConnectPort.setBounds(6, 412, 134, 16);
		frame.getContentPane().add(lblPeerConnectPort);

		peerIP = new JTextField();
		peerIP.setColumns(10);
		peerIP.setBounds(140, 378, 162, 28);
		frame.getContentPane().add(peerIP);

		peerPORT = new JTextField();
		peerPORT.setColumns(10);
		peerPORT.setBounds(140, 412, 162, 28);
		frame.getContentPane().add(peerPORT);
	}
}
