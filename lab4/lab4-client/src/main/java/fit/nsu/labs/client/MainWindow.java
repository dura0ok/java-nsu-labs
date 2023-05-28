package fit.nsu.labs.client;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.client.model.OnEvent;
import fit.nsu.labs.common.codec.*;
import fit.nsu.labs.common.message.client.ClientMessage;
import fit.nsu.labs.common.message.server.ServerMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainWindow extends JFrame implements OnEvent {
    private final DefaultListModel<String> memberListModel = new DefaultListModel<>();
    private JTextArea chatHistory;
    private JTextField chatInput;
    private JButton sendButton;
    private JList<String> memberList;
    private JLabel usernameLabel;
    private Lab4Client lab4Client;
    private String userName;

    public MainWindow(String host, int port,
                      Encoder<ClientMessage> encoder, Decoder<ServerMessage> decoder) {
        String name;
        name = JOptionPane.showInputDialog("Enter your name: ");
        if (name == null || name.isBlank()) {
            return;
        }
        userName = name;

        this.lab4Client = new Lab4Client(host, port, encoder, decoder);
        lab4Client.connect(this);
        lab4Client.login(name);

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
                if (text.isEmpty()) {
                    return;
                }
                lab4Client.sendTextMessage(text);
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
                lab4Client.requestListMembers();
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
                lab4Client.logout(() -> {
                    System.out.println("[DEBUG] EXIT APP");
                    setVisible(false);
                    dispose();
                    System.exit(0);
                });
            }
        });
        setVisible(true);


    }

    public static void main(String[] args) throws IOException {
        var configuration = new Configuration();
        Encoder<ClientMessage> encoder = null;
        Decoder<ServerMessage> decoder = null;

        switch (configuration.getProtocol()) {
            case SERIALIZATION -> {
                encoder = new ObjectClientCodec();
                decoder = new ObjectServerCodec();
            }
            case XML -> {
                encoder = new XmlClientCodec();
                decoder = new XmlServerCodec();
            }
        }

        new MainWindow(configuration.getServerName(), configuration.getPort(), encoder, decoder);
    }

    @Override
    public void notification(Event event) {
        if (event.type() == Event.EventType.MEMBERS_UPDATED) {
            memberListModel.clear();
            memberListModel.addAll(event.data());
        }

        if (event.type() == Event.EventType.MESSAGE_UPDATED) {
            var lastText = chatHistory.getText();
            chatHistory.setText(lastText + "\n" + String.join("\n", event.data()));
        }

        if (event.type() == Event.EventType.ERROR_MESSAGE) {
            JOptionPane.showMessageDialog(null, event.data().get(0), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}