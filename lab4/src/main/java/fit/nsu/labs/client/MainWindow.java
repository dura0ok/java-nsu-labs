package fit.nsu.labs.client;


import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.client.model.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements OnEvent {
    private JTextArea chatHistory;
    private String userName;
    private JTextField chatInput;
    private JButton sendButton;
    private JList<String> memberList;
    private JLabel usernameLabel;
    private ChatClientModel model;
    private DefaultListModel<String> memberListModel;

    public MainWindow() {
        String name;
        name = JOptionPane.showInputDialog("Enter your name: ");
        if (name == null || name.isBlank()) {
            return;
        }
        userName = name;
        model = new SerializationModel(userName);
        model.registerObserver(this);
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
        memberListModel = new DefaultListModel<>();
        memberListModel.addElement("Alice");
//        memberListModel.addElement("Bob");
//        memberListModel.addElement("Charlie");
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

    @Override
    public void notification(Event event) {
        if (event.type() == EventType.USERS_UPDATE) {
            var users = event.data().getUsers();
            System.out.println(users);
            memberListModel.removeAllElements();
            memberListModel.addAll(users);
        }
    }
}

