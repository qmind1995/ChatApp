package com.networking.client;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.TextArea;

import javax.swing.JButton;

import com.networking.data.DataFile;
import com.networking.tags.DeCode;
import com.networking.tags.EnCode;
import com.networking.tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JProgressBar;

import java.awt.Label;

public class ChatApp {

	private static String URL_DIR = System.getProperty("user.dir");

	private ChatRoom chat;
	private Socket socketChat;
	private String nameUser = "", nameGuest = "", nameFile = "";
	private JFrame frame;
	private JTextField textName;
	private Label textState;
	private TextArea textSend, textDisPlayChat;
	private JButton btnDisConnect, btnSend, btnChoose, btnDel;
	public boolean isStop = false, isSendFile = false, isReceiveFile = false;
	private JProgressBar progressSendFile;
	private JTextField textPath;
	private int portServer = 0;

	public ChatApp(String user, String guest, Socket socket, int port) {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
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
					ChatApp window = new ChatApp(nameUser, nameGuest,
							socketChat, portServer, 0);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
					ChatApp window = new ChatApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateChat(String msg) {
		textDisPlayChat.append(msg + "\n");
	}

	public ChatApp() {
		initialize();
	}

	public ChatApp(String user, String guest, Socket socket, int port, int a)
			throws Exception {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		initialize();
		chat = new ChatRoom(socketChat, nameUser, nameGuest);
		chat.start();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 588, 538);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JLabel lblClientIP = new JLabel("Member In Group Chat:");
		lblClientIP.setBounds(6, 12, 155, 16);
		frame.getContentPane().add(lblClientIP);

		textName = new JTextField(nameUser);
		textName.setEditable(false);
		textName.setBounds(171, 6, 280, 28);
		frame.getContentPane().add(textName);
		textName.setText(nameUser + " and " + nameGuest);
		textName.setColumns(10);

		textDisPlayChat = new TextArea();
		textDisPlayChat.setEditable(false);
		textDisPlayChat.setBounds(6, 40, 568, 317);
		frame.getContentPane().add(textDisPlayChat);

		textSend = new TextArea();
		textSend.setBounds(6, 363, 481, 73);
		frame.getContentPane().add(textSend);

		btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!isSendFile) {
					final String msg = textSend.getText();
					if (msg.equals(""))
						return;
					try {
						chat.sendMessage(msg);
						textSend.setText("");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						chat.sendMessage(EnCode.sendFile(nameFile));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnSend.setBounds(493, 363, 81, 72);
		frame.getContentPane().add(btnSend);

		btnDisConnect = new JButton("CLOSE");
		btnDisConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnDisConnect.setBounds(461, 6, 113, 29);
		frame.getContentPane().add(btnDisConnect);

		progressSendFile = new JProgressBar(0, 100);
		progressSendFile.setBounds(93, 485, 481, 14);
		progressSendFile.setStringPainted(true);
		frame.getContentPane().add(progressSendFile);
		progressSendFile.setVisible(false);

		btnChoose = new JButton(new ImageIcon(this.getClass().getResource(
				"/Attach_icon.png")));
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (result == JFileChooser.APPROVE_OPTION) {
					isSendFile = true;
					String path_send = fileChooser.getSelectedFile()
							.getAbsolutePath();
					nameFile = fileChooser.getSelectedFile().getName();
					textPath.setText(path_send);
				}
			}
		});
		btnChoose.setBounds(498, 446, 33, 31);
		btnChoose.setBorder(BorderFactory.createEmptyBorder());
		btnChoose.setContentAreaFilled(false);
		frame.getContentPane().add(btnChoose);

		Label label = new Label("Path : ");
		label.setBounds(6, 455, 49, 22);
		frame.getContentPane().add(label);

		textPath = new JTextField("");
		textPath.setEditable(false);
		textPath.setColumns(10);
		textPath.setBounds(61, 449, 428, 28);
		frame.getContentPane().add(textPath);

		btnDel = new JButton(new ImageIcon(this.getClass().getResource(
				"/Delete_icon.png")));
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isSendFile = false;
				textSend.setText("");
				textPath.setText("");
			}
		});
		btnDel.setContentAreaFilled(false);
		btnDel.setBorder(BorderFactory.createEmptyBorder());
		btnDel.setBounds(541, 446, 33, 31);
		frame.getContentPane().add(btnDel);

		textState = new Label("");
		textState.setBounds(6, 477, 81, 22);
		textState.setVisible(false);
		frame.getContentPane().add(textState);
	}

	public class ChatRoom extends Thread {

		public Socket connect;
		private ObjectOutputStream outPeer;
		private ObjectInputStream inPeer;
		private boolean continueSendFile = true;// , isSending = false;
		private int sizeOfSend = 0, sizeOfData = 0, sizeFile = 0,
				sizeReceive = 0;
		private String nameFileReceive = "";
		private InputStream inFileSend;
		private DataFile dataFile;

		public ChatRoom(Socket connection, String name, String guest)
				throws Exception {
			connect = new Socket();
			connect = connection;
			nameGuest = guest;
		}

		@Override
		public void run() {
			super.run();
			OutputStream out = null;
			while (true) {
				try {
					inPeer = new ObjectInputStream(connect.getInputStream());
					Object obj = inPeer.readObject();
					if (obj instanceof String) {
						String msgObj = obj.toString();
						if (DeCode.checkFile(msgObj)) {
							isReceiveFile = true;
							nameFileReceive = msgObj.substring(10,
									msgObj.length() - 11);
							int result = Tags.show(frame, nameGuest
									+ " SEND FILE " + nameFileReceive
									+ " FOR YOU", true);
							if (result == 0) {
								File fileReceive = new File(URL_DIR + "/"
										+ nameFileReceive);
								if (!fileReceive.exists()) {
									fileReceive.createNewFile();
								}
								String msg = Tags.FILE_REQ_ACK_OPEN_TAG
										+ Integer.toBinaryString(portServer)
										+ Tags.FILE_REQ_ACK_CLOSE_TAG;
								sendMessage(msg);
							} else {
								sendMessage(Tags.FILE_REQ_NOACK_TAG);
							}
						} else if (DeCode.checkFeedBack(msgObj)) {
							btnChoose.setEnabled(false);
							btnDel.setEnabled(false);
							new Thread(new Runnable() {
								public void run() {
									try {
										sendMessage(Tags.FILE_DATA_BEGIN_TAG);
										updateChat("YOU ARE SENDING FILE : "
												+ nameFile);
										isSendFile = false;
										sendFile(textPath.getText());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).start();
						} else if (msgObj.equals(Tags.FILE_REQ_NOACK_TAG)) {
							Tags.show(frame, nameGuest
									+ " DO NOT RECEIVE YOUR FILE", false);
						} else if (msgObj.equals(Tags.FILE_DATA_BEGIN_TAG)) {
							out = new FileOutputStream(URL_DIR + "/"
									+ nameFileReceive);
						} else if (msgObj.equals(Tags.FILE_DATA_CLOSE_TAG)) {
							updateChat("YOU RECEIVED FILE " + nameFileReceive);
							updateChat(Integer.toString((sizeReceive) * 1024));
							out.flush();
							out.close();
							/*
							 * new Thread(new Runnable() {
							 * 
							 * @Override public void run() { JFileChooser
							 * fileChooser = new JFileChooser();
							 * fileChooser.setCurrentDirectory(new File(
							 * System.getProperty("user.home"))); int result =
							 * fileChooser .showOpenDialog(frame); fileChooser
							 * .setFileSelectionMode
							 * (JFileChooser.DIRECTORIES_ONLY); if (result ==
							 * JFileChooser.APPROVE_OPTION) {
							 * 
							 * } } }).start();
							 */
						} else {
							String message = DeCode.getMessage(msgObj);
							updateChat("[" + nameGuest + "]	:" + message);
						}
					} else if (obj instanceof DataFile) {
						DataFile data = (DataFile) obj;
						++sizeReceive;
						out.write(data.data);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void getData(String path) throws Exception {
			File fileData = new File(path);
			if (fileData.exists()) {
				sizeOfSend = 0;
				dataFile = new DataFile();
				// isSending = true;
				sizeFile = (int) fileData.length();
				sizeOfData = sizeFile % 1024 == 0 ? (int) (fileData.length() / 1024)
						: (int) (fileData.length() / 1024) + 1;
				textState.setVisible(true);
				progressSendFile.setVisible(true);
				progressSendFile.setValue(0);
				inFileSend = new FileInputStream(fileData);
			}
		}

		public void sendFile(String path) throws Exception {
			getData(path);
			textState.setText("Sending ...");

			do {
				if (continueSendFile) {
					continueSendFile = false;
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								inFileSend.read(dataFile.data);
								sendMessage(dataFile);
								sizeOfSend++;
								if (sizeOfSend == sizeOfData - 1) {
									int size = sizeFile - sizeOfSend * 1024;
									dataFile = new DataFile(size);
								}
								progressSendFile
										.setValue((int) (sizeOfSend * 100 / sizeOfData));
								if (sizeOfSend >= sizeOfData) {
									inFileSend.close();
									isSendFile = true;
									sendMessage(Tags.FILE_DATA_CLOSE_TAG);
									progressSendFile.setVisible(false);
									textState.setVisible(false);
									isSendFile = false;
									textPath.setText("");
									btnChoose.setEnabled(true);
									btnDel.setEnabled(true);
									updateChat("... SUCCESSFUL");
									inFileSend.close();
									// isSending = false;
								}
								continueSendFile = true;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			} while (sizeOfSend < sizeOfData);
		}

		public synchronized void sendMessage(Object obj) throws Exception {
			outPeer = new ObjectOutputStream(connect.getOutputStream());
			if (obj instanceof String) {
				String message = obj.toString();
				if (!isSendFile && !isReceiveFile) {
					message = EnCode.sendMessage(obj.toString());
					updateChat("[ME]	:" + obj.toString());
				}
				outPeer.writeObject(message);
				outPeer.flush();
				if (isReceiveFile)
					isReceiveFile = false;
			} else if (obj instanceof DataFile) {
				outPeer.writeObject(obj);
				outPeer.flush();
			}
		}
	}

	public void copyFileReceive(InputStream inputStr, OutputStream outputStr,
			String path) throws Exception {
		byte[] buffer = new byte[1024];
		int lenght;
		while ((lenght = inputStr.read(buffer)) > 0) {
			outputStr.write(buffer, 0, lenght);
		}
		inputStr.close();
		outputStr.close();
		File fileTemp = new File(path);
		if (fileTemp.exists()) {
			fileTemp.delete();
		}
	}
}
/*
 * public void dialog() { JFileChooser fileChooser = new JFileChooser();
 * fileChooser.setCurrentDirectory(new File(System .getProperty("user.home")));
 * int result = fileChooser.showOpenDialog(this); //
 * fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); if (result
 * == JFileChooser.APPROVE_OPTION) { // Load(file.getAbsolutePath()); path_send
 * = fileChooser.getSelectedFile().getAbsolutePath(); } add(file); add(lpath); }
 */