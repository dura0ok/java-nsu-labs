package fit.nsu.labs.client;

import fit.nsu.labs.client.model.*;
import fit.nsu.labs.client.model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainWindow extends JFrame implements OnEvent {
    private JTextArea chatHistory;
    private JTextField chatInput;
    private JButton sendButton;
    private JList<String> memberList;
    private JLabel usernameLabel;
    private ChatClientModel model;
    private String userName;
    private final DefaultListModel<String> memberListModel = new DefaultListModel<>();

    public MainWindow() throws IOException{
        String name;
        name = JOptionPane.showInputDialog("Enter your name: ");
        if (name == null || name.isBlank()) {
            return;
        }
        userName = name;
        model = new SerializationModel(name);
        model.registerObserver(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chat App");

        // Set layout manager
        setLayout(new BorderLayout());

        // Create username label
        usernameLabel = new JLabel("User: " + userName, SwingConstants.CENTER);
        add(usernameLabel, BorderLayout.NORTH);
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
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var text = chatInput.getText();
                if(text.isEmpty()){
                    return;
                }
                model.sendTextMessage(text);
            }
        });

        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Create button to show member list in new window
        JButton showMembersButton = new JButton("Show Members");
        showMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateMembersRequest();
                memberListModel.clear();
                JFrame memberWindow = new JFrame("Member List");
                memberWindow.setLayout(new BorderLayout());
                memberWindow.add(memberScrollPane, BorderLayout.CENTER);
                memberWindow.pack();
                memberWindow.setLocationRelativeTo(null);
                memberWindow.setVisible(true);
            }
        });
        add(showMembersButton, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
        setSize(1000, 500);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.logout();
            }
        });
        setVisible(true);
    }

    @Override
    public void notification(Event event) {
        if (event.type() == Event.EventType.MEMBERS_UPDATED) {
            memberListModel.clear();
            memberListModel.addAll(event.data());
        }

        if (event.type() == Event.EventType.MESSAGE_UPDATED) {
            chatHistory.setText(String.join("\n", event.data()));
        }
    }
    public static void main(String[] args) throws IOException {
        new MainWindow();
    }
}