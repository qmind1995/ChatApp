package com.networking.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.TextArea;

import javax.swing.JButton;

import com.networking.tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContractApp {

	private Peer peerNode;
	private static String IPClient = "", nameUser = "", dataUser = "";
	private static int portClient = 0;
	private static JFrame frame;
	private JTextField textName, textNameFriend;
	private static TextArea textList;
	private JButton btnChat, btnExit;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContractApp window = new ContractApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ContractApp(String arg, int arg1, String name, String msg)
			throws Exception {
		IPClient = arg;
		portClient = arg1;
		nameUser = name;
		dataUser = msg;
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
					ContractApp window = new ContractApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ContractApp() throws Exception {
		initialize();
		peerNode = new Peer(IPClient, portClient, nameUser, dataUser);
	}

	public static void updateFiend(String msg) {
		textList.append(msg + "\n");
	}

	public static void clearAll() {
		textList.setText("");
		textList.setText("<All Friend Online>\n");
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 293, 556);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textName = new JTextField(nameUser);
		textName.setEditable(false);
		textName.setColumns(10);
		textName.setBounds(90, 11, 186, 28);
		frame.getContentPane().add(textName);

		JLabel label = new JLabel("User Name: ");
		label.setBounds(10, 17, 82, 16);
		frame.getContentPane().add(label);

		textList = new TextArea();
		textList.setText("<All Friend Online>");
		textList.setEditable(false);
		textList.setBounds(10, 53, 266, 372);
		frame.getContentPane().add(textList);

		JLabel lblFriendsName = new JLabel("Friend 's Name: ");
		lblFriendsName.setBounds(10, 445, 94, 16);
		frame.getContentPane().add(lblFriendsName);

		textNameFriend = new JTextField("");
		textNameFriend.setColumns(10);
		textNameFriend.setBounds(114, 439, 162, 28);
		frame.getContentPane().add(textNameFriend);

		btnChat = new JButton("Chat");

		btnChat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name = textNameFriend.getText();
				if (name.equals("") || Peer.peer == null) {
					Tags.show(frame, "NAME 'S FRIEND ERROR", false);
					return;
				}
				if (name.equals(nameUser)) {
					Tags.show(frame, "YOU CAN'T CHAT WITH YOURSELF", false);
					return;
				}
				int size = Peer.peer.size();
				for (int i = 0; i < size; i++) {
					if (name.equals(Peer.peer.get(i).getName())) {
						try {
							peerNode.requestChat(Peer.peer.get(i).getHost(),
									Peer.peer.get(i).getPort(), name);
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				Tags.show(frame, "FRIEND NOT FOUND", false);
			}
		});
		btnChat.setBounds(10, 478, 113, 29);
		frame.getContentPane().add(btnChat);
		btnExit = new JButton("Exit");
		btnExit.setBounds(163, 478, 113, 29);
		frame.getContentPane().add(btnExit);
	}

	public static int request(String msg, boolean type) {
		return Tags.show(frame, msg, type);
	}
}
