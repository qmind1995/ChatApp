package com.network.client;

import com.networking.data.DataPeer;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ContractApp {

    private int portServer = 8080;
    private Socket socketClient;
    private Peer peerNode;
    private static String IPClient = "", nameUser = "", dataUser = "";
    private static int portClient = 0;
    private JFrame frame;
    private JTextField textName, textNameFriend;
    private static TextArea textList;
    private JButton btnChat, btnExit;
    private JButton button;
    private String IPserver ="";

    private ObjectInputStream input;
    private ObjectOutputStream output;
    public static ArrayList<DataPeer> peer_data = null;
    
    
    public void start(final String ip) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ContractApp window = new ContractApp(ip);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void set_IPServer(String IPs){
    	this.IPserver = IPs;
    }
    
    public ContractApp(String arg, int arg1, String name, String msg,final String IPSer)
            throws Exception {
        IPClient = arg;
        portClient = arg1;
        nameUser = name;
        dataUser = msg;
        set_IPServer(IPSer);
        
        
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

                    ContractApp window = new ContractApp(IPSer);
                    window.frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ContractApp(String ip) throws Exception {
    	IPserver = ip;
    	set_IPServer(ip);
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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        textName = new JTextField(nameUser);
        textName.setEditable(false);
        textName.setColumns(10);
        textName.setBounds(73, 11, 149, 28);
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
        lblFriendsName.setBounds(10, 445, 110, 16);
        frame.getContentPane().add(lblFriendsName);

        textNameFriend = new JTextField("");
        textNameFriend.setColumns(10);
        textNameFriend.setBounds(130, 439, 146, 28);
        frame.getContentPane().add(textNameFriend);

        btnChat = new JButton("Chat");

        btnChat.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String name = textNameFriend.getText();
                if (name.equals("") || Peer.peer == null) {
                    Tags.show(frame, "Name 's friend mistake!", false);
                    return;
                }
                if (name.equals(nameUser)) {
                    Tags.show(frame, "You can't chat with yourself ><", false);
                    return;
                }
                int size = Peer.peer.size();
                for (int i = 0; i < size; i++) {
                    if (name.equals(Peer.peer.get(i).getName())) {
                        try {
                            peerNode.requestChat(Peer.peer.get(i).getIp(),
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
        btnExit.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent arg0) {
                int result = Tags.show(frame, "Do you want exit now?", true);
                if (result == 0) {
                    try {
                        peerNode.exit();
                        frame.dispose();
                        System.exit(0);
                    } catch (Exception e) {
                        frame.dispose();
                    }
                }
            }
        });
        btnExit.setBounds(163, 478, 113, 29);
        frame.getContentPane().add(btnExit);
        button = new JButton("F5");
        
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //code here
                try {
                	
                	
                	
                	
                    socketClient = new Socket(IPserver, 8080);// IPserver
                    output = new ObjectOutputStream(socketClient.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(socketClient.getInputStream());
                    
                    output.writeObject(com.networking.tags.Tags.REFRESH);
                    output.flush();
                    String ref_msg=(String)input.readObject();
                    peer_data = new ArrayList<DataPeer>();
                    peer_data = com.networking.tags.DeCode.getAllUser(ref_msg);
                    
                    
                   
                    peerNode.set_datapeer(peer_data);
                   // peerNode.updateFiend();
                    clearAll();
                    for(int i =0 ;i< peer_data.size(); i++)
                    	updateFiend(peer_data.get(i).getName());
                    
                }catch(Exception e){}
            }
        });
        button.setBounds(232, 11, 44, 29);
        frame.getContentPane().add(button);
    }

    public static int request(String msg, boolean type) {
        JFrame frameMessage = new JFrame();
        return Tags.show(frameMessage, msg, type);
    }
}
