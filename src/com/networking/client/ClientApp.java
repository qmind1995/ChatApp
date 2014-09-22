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

public class ClientApp {

	private String portClient = "", IPClient = "";
	private JFrame frame;
	private JTextField textIP;
	private JTextField textPort;
	private TextArea textSend;
	private TextArea textDisPlayChat;
	private JButton btnConnect;
	private JLabel lblFailed;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApp window = new ClientApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 603, 398);
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
		btnSend.setBounds(503, 302, 94, 64);
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
						new Client(IPClient, portClient);
					} catch (Exception e1) {
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
	}
}
