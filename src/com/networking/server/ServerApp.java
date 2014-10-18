package com.networking.server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.TextArea;

public class ServerApp {

	public static int port = 8080;
	private JFrame frame;
	private JTextField txtIP, txtPort;
	private static TextArea txtMessage;
	private static JLabel lblNumber;
	Server server;

	public static void main(String[] args) {
		com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green",
				"INSERT YOUR LICENSE KEY HERE", "my company");
		try {
			UIManager
					.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerApp window = new ServerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerApp() {
		initialize();
	}

	public static void updateMessage(String msg) {
		txtMessage.append(msg + "\n");
	}

	public static void updateNumberClient() {
		int number = Integer.parseInt(lblNumber.getText());
		lblNumber.setText(Integer.toString(number + 1));
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 522, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblIP = new JLabel("HOST :");
		lblIP.setBounds(6, 12, 61, 16);
		frame.getContentPane().add(lblIP);

		txtIP = new JTextField();
		txtIP.setEditable(false);
		txtIP.setBounds(60, 6, 176, 28);
		frame.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("PORT : ");
		lblNewLabel.setBounds(6, 49, 61, 16);
		frame.getContentPane().add(lblNewLabel);

		txtPort = new JTextField();
		txtPort.setEditable(false);
		txtPort.setColumns(10);
		txtPort.setBounds(60, 40, 176, 28);
		frame.getContentPane().add(txtPort);
		txtPort.setText("8080");

		JButton btnStart = new JButton("START");
		btnStart.setBounds(248, 7, 117, 29);
		frame.getContentPane().add(btnStart);

		JLabel lblNumberClient = new JLabel("Number Client :");
		lblNumberClient.setBounds(258, 49, 109, 16);
		frame.getContentPane().add(lblNumberClient);

		lblNumber = new JLabel("0");
		lblNumber.setBounds(379, 49, 39, 16);
		frame.getContentPane().add(lblNumber);

		txtMessage = new TextArea();
		txtMessage.setEditable(false);
		txtMessage.setBounds(10, 74, 502, 236);
		frame.getContentPane().add(txtMessage);

		JButton btnStop = new JButton("STOP");
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server.stopServer();
					ServerApp.updateMessage("STOP SERVER");
				} catch (Exception e) {
					e.printStackTrace();
					ServerApp.updateMessage("STOP SERVER");
				}
			}
		});
		btnStop.setBounds(395, 7, 117, 29);
		frame.getContentPane().add(btnStop);

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server = new Server(8080);
					ServerApp.updateMessage("START SERVER");
				} catch (Exception e) {
					ServerApp.updateMessage("START ERROR");
					e.printStackTrace();
				}
			}
		});
	}
}
