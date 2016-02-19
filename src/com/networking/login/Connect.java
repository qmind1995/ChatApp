package com.networking.login;

import com.networking.login.Login;
import com.networking.login.Signup;
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

import com.networking.tags.enCode;
import com.networking.tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {

    private static Socket socket;
    private static String IP_FAILED = "CONNECT WITH OTHER IP";
    private static String IP_EXSIST = "IP IS EXSISED";
    private static String SERVER_NOT_START = "SERVER NOT START";

    private Pattern checkName = Pattern.compile("[a-zA-Z][^<>]*");

    private JFrame frmConnect;
    private JTextField textField_1;
    private JLabel lblError;
    private String name = "", IP = "";
    private JTextField textIP;
    private JButton btnLogin;
    private String password;
    private Login a;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public static Socket getSockett() throws IOException {
        return socket;
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
                    Connect window = new Connect();
                    window.frmConnect.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getPass() {
        return password;
    }

    public Connect() {
        initialize();
    }

    private void initialize() {
        frmConnect = new JFrame();
        frmConnect.setTitle("CONNECT");
        frmConnect.getContentPane().setBackground(Color.WHITE);
        frmConnect.setResizable(false);
        frmConnect.setBounds(100, 100, 448, 172);
        frmConnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmConnect.getContentPane().setLayout(null);

        JLabel lblHiWelcomeConnect = new JLabel("Connect With Server\r\n...");
        lblHiWelcomeConnect.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblHiWelcomeConnect.setBounds(10, 25, 258, 14);
        frmConnect.getContentPane().add(lblHiWelcomeConnect);

        JLabel lblHostServer = new JLabel("Host Server : ");
        lblHostServer.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblHostServer.setBounds(10, 50, 86, 20);
        frmConnect.getContentPane().add(lblHostServer);

        JLabel lblPortServer = new JLabel("Port Server : ");
        lblPortServer.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPortServer.setBounds(263, 53, 79, 14);
        frmConnect.getContentPane().add(lblPortServer);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textField_1.setText("8080");
        textField_1.setEditable(false);
        textField_1.setColumns(10);
        textField_1.setBounds(356, 50, 65, 20);
        frmConnect.getContentPane().add(textField_1);

        lblError = new JLabel("");
        lblError.setBounds(10, 106, 240, 14);
        frmConnect.getContentPane().add(lblError);

        textIP = new JTextField();
        textIP.setBounds(101, 46, 152, 28);
        frmConnect.getContentPane().add(textIP);
        textIP.setColumns(10);

        btnLogin = new JButton("OK");
        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                lblError.setVisible(false);
                IP = textIP.getText();
                if (!IP.equals("")) {
                    try {
                        try {
                        	
                            int portServer = Integer.parseInt("8080");
                            socket = new Socket(IP,portServer);
                            output = new ObjectOutputStream(socket.getOutputStream());
                            output.flush();
                            input =  new ObjectInputStream(socket.getInputStream());
                            output.writeObject("Connected");
                            a = new Login(IP);
                            a.start(IP);
                            frmConnect.dispose();
                        } catch (IOException ex) {
                        }
                        socket.close();
                        input.close();
                        output.close();
                    } catch (IOException ex) {                      
                    	Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    lblError.setText(IP_FAILED);
                    lblError.setVisible(true);
                    lblError.setText(IP_EXSIST);
                }
            }
        });
        btnLogin.setBounds(279, 91, 135, 29);
        frmConnect.getContentPane().add(btnLogin);
        lblError.setVisible(false);
    }
}
