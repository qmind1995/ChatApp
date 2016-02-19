package com.networking.login;

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

import com.network.client.ContractApp;
import com.networking.login.Login;
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
import javax.swing.JOptionPane;

public class Signup {

    private Pattern checkName = Pattern.compile("[a-zA-Z0-9][^<>]*");
    private Socket socketClient;
    private JFrame frmSignup;
    private JLabel lblError;
    private String name = "";
    private String IP = "";
    private JTextField textName;
    private JPasswordField textField;
    private String password = "";
    private Login a;
    private Socket socket;

    public void start(final String IP) {
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
                    Signup window = new Signup(IP);
                    window.frmSignup.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Signup(String IP) {
        this.IP = IP;
        initialize();
    }

    private void initialize() {
        frmSignup = new JFrame();
        frmSignup.setTitle("SIGNUP");
        frmSignup.getContentPane().setBackground(Color.WHITE);
        frmSignup.setResizable(false);
        frmSignup.setBounds(100, 100, 448, 199);
        frmSignup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSignup.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("User Name: ");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(57, 31, 86, 17);
        frmSignup.getContentPane().add(lblNewLabel);

        lblError = new JLabel("");
        lblError.setBounds(10, 166, 240, 14);
        frmSignup.getContentPane().add(lblError);

        textName = new JTextField();
        textName.setColumns(10);
        textName.setBounds(148, 25, 213, 28);
        frmSignup.getContentPane().add(textName);

        JLabel lblNewLabel_1 = new JLabel("Password:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(57, 70, 86, 20);
        frmSignup.getContentPane().add(lblNewLabel_1);

        textField = new JPasswordField();
        password = textField.getText();
        textField.setBounds(148, 66, 213, 28);
        frmSignup.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.setBounds(148, 116, 135, 29);
        frmSignup.getContentPane().add(btnSignUp);
        lblError.setVisible(false);
        btnSignUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                name = textName.getText();
                password = textField.getText();
                try {
                    socket = new Socket(IP, 8080);
                    String msg = Tags.SIGN_UP_OPEN_TAG + Tags.USER_NAME_OPEN_TAG + name
                            + Tags.USER_NAME_END_TAG + Tags.PASSWORD_OPEN_TAG + password + Tags.PASSWORD_END_TAG
                            + Tags.SIGN_UP_END_TAG;
                    ObjectOutputStream serverOutputStream = new ObjectOutputStream(
                            socket.getOutputStream());
                    serverOutputStream.writeObject(msg);
                    serverOutputStream.flush();
                    ObjectInputStream serverInputStream = new ObjectInputStream(
                            socket.getInputStream());
                    String msg1 = (String) serverInputStream.readObject();
                    if (msg1.matches(Tags.SIGN_UP_UNSUCCESS)) {
                        JOptionPane.showMessageDialog(frmSignup, "SIGNUP UNSUCCESS");
                    } else {
                        JOptionPane.showMessageDialog(frmSignup, "SIGNUP SUCCESS");
                        a = new Login(IP);
                        a.start(IP);
                        frmSignup.dispose();
                        serverOutputStream.close();
                        serverOutputStream.close();
                        socket.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
