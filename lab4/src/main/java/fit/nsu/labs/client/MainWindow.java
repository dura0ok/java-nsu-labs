package fit.nsu.labs.client;

import fit.nsu.labs.client.protocol.ChatClientProtocol;
import fit.nsu.labs.client.protocol.SerializationBased;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {
    private JTextArea chatHistory;
    private String userName;
    private JTextField chatInput;
    private JButton sendButton;
    private JList<String> memberList;
    private JLabel usernameLabel;
    private ChatClientProtocol model;

    public MainWindow() {
        String name;
        name = JOptionPane.showInputDialog("Enter your name: ");
        if (name == null || name.isBlank()) {
            return;
        }
        userName = name;
        try {
            model = new SerializationBased(new SocketConnection());
            model.Login(userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        initUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chat App");

        // Set layout manager
        setLayout(new BorderLayout());

        // Create username label
        usernameLabel = new JLabel("User: " + userName, SwingConstants.CENTER);
        add(usernameLabel, BorderLayout.NORTH);

        // Create member list
        DefaultListModel<String> memberListModel = new DefaultListModel<>();
        memberListModel.addElement("Alice");
        memberListModel.addElement("Bob");
        memberListModel.addElement("Charlie");
        memberList = new JList<>(memberListModel);
        JScrollPane memberScrollPane = new JScrollPane(memberList);
        add(memberScrollPane, BorderLayout.WEST);

        // Create chat history text area
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        chatHistory.setLineWrap(true);
        JScrollPane chatScrollPane = new JScrollPane(chatHistory);
        add(chatScrollPane, BorderLayout.CENTER);

        // Create chat input field and send button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        chatInput = new JTextField(30);
        sendButton = new JButton("Send");
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

    