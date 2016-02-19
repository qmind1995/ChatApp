package com.networking.login;

import com.network.client.ContractApp;
import com.networking.tags.DeCode;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

//import com.networking.client.ContractApp; // chua xai toi
import com.networking.tags.enCode;

import server.Server;

import com.networking.tags.Tags;
import com.networking.login.Signup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.Font;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import javax.swing.JOptionPane;

public class Login {

    private static String NAME_FAILED = "CONNECT WITH OTHER NAME";
    private static String NAME_EXSIST = "NAME IS EXSISED";
    private static String SERVER_NOT_START = "SERVER NOT START";

    private Pattern checkName = Pattern.compile("[a-zA-Z0-9][^<>]*");
    private Signup ab;
    private JFrame frmLogin;
    private JLabel lblError;
    private static String name = "", IP = "";
    private static String pass = "";
    private int port;
    private JTextField textName;
    private JButton btnLogin;
    private JPasswordField textField;
    private String password;
    private Socket socketClient;
    private ContractApp contract;
    //private Signup sun;

    public void start(final String ip) {
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
                    Login window = new Login(ip);
                    window.frmLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public String get_IP(){
    	return IP;
    }
    public Login(String ip) {
        IP = ip;
        initialize();
    }

    public void initialize() {
        frmLogin = new JFrame();
        frmLogin.setTitle("LOGIN");
        frmLogin.getContentPane().setBackground(Color.WHITE);
        frmLogin.setResizable(false);
        frmLogin.setBounds(100, 100, 448, 235);
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("User Name: ");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(10, 32, 86, 17);
        frmLogin.getContentPane().add(lblNewLabel);

        lblError = new JLabel("");
        lblError.setBounds(10, 166, 240, 14);
        frmLogin.getContentPane().add(lblError);

        textName = new JTextField();
        textName.setColumns(10);
        textName.setBounds(101, 27, 225, 28);
        frmLogin.getContentPane().add(textName);

        btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                name = textName.getText();
                pass = textField.getText();
                lblError.setVisible(false);
                if (checkName.matcher(name).matches() && checkName.matcher(pass).matches()) {
                    try {
                        //Socket socketClient=Connect.getSocket();
                        socketClient = new Socket(IP, 8080);
                        String msg = enCode.logIN(name, pass, InetAddress.getLocalHost().toString());
                        
                        ObjectOutputStream serverOutputStream = new ObjectOutputStream(
                                socketClient.getOutputStream());
                        serverOutputStream.writeObject(msg);
                        serverOutputStream.flush();
                        
                        ObjectInputStream serverInputStream = new ObjectInputStream(
                                socketClient.getInputStream());
                        msg = (String) serverInputStream.readObject();
                        if (msg.matches(Tags.LOG_IN_RES_FAIL)) {
                            JOptionPane.showMessageDialog(frmLogin, " sign in is unsuccessful");
                        } else {
                            JOptionPane.showMessageDialog(frmLogin, " sign in is successful");
                            String port = DeCode.portOnl(msg);
                            String msg1 = (String) serverInputStream.readObject();
                            
                            socketClient.close();
                            serverInputStream.close();
                            serverOutputStream.close();
                            contract = new ContractApp(InetAddress.getLocalHost().getHostAddress()
                                    , Integer.parseInt(port), name, msg1, get_IP());
                            frmLogin.dispose();
                        }

                    } catch (Exception e) {
                        lblError.setText(SERVER_NOT_START);
                        lblError.setVisible(true);
                        e.printStackTrace();
                    }
                } else {
                    lblError.setText(NAME_FAILED);
                    lblError.setVisible(true);
                    lblError.setText(NAME_FAILED);
                }
            }
        });
        btnLogin.setBounds(59, 118, 135, 29);
        frmLogin.getContentPane().add(btnLogin);

        JLabel lblNewLabel_1 = new JLabel("Password:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(10, 72, 86, 20);
        frmLogin.getContentPane().add(lblNewLabel_1);

        textField = new JPasswordField();
        password = textField.getText();
        textField.setBounds(101, 68, 225, 28);
        frmLogin.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    ab = new Signup(IP);
                    ab.start(IP);
                    frmLogin.dispose();
                } catch (Exception e) {

                }
            }
        });
        btnSignUp.setBounds(234, 118, 135, 29);
        frmLogin.getContentPane().add(btnSignUp);
        lblError.setVisible(false);
    }
}
