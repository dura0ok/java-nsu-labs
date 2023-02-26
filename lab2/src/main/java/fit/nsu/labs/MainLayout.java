package fit.nsu.labs;

import javax.swing.*;

public class MainLayout extends JFrame{
    private JButton button1;
    private JTextField textField1;
    private JPanel panel;

    public static void main(String[] args) {
        MainLayout form = new MainLayout();
        form.setContentPane(form.panel);
    }
}
