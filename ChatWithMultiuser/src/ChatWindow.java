import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
public class ChatWindow extends Thread{
    String userName;
    JPanel panel = new JPanel();
    JButton unplug = new JButton("Unplug");
    JTextArea text = new JTextArea();
    JTextField userText = new JTextField();
    Sender sender;
    Receiver receiver = new Receiver();


    public ChatWindow(String userName) throws IOException {
        this.userName = userName;
        sender = new Sender(userName);
        JFrame frame = new JFrame();
        frame.add(panel);
        panel.setLayout(new BorderLayout()); panel.add(unplug, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER); panel.add(userText, BorderLayout.SOUTH);

        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }
    public void updateChatWindow(String message) {
        if(message != null) {
            text.append(message + "\n");
        }
    }

    @Override
    public void run(){

        unplug.addActionListener(l -> {
                interrupt();
                text.setText(userName + " left the chat");
        });

        userText.addActionListener(l -> {
                        String input = userText.getText();
                    try {
                        sender.writeMessage(input);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    userText.setText("");
                });
        while(!interrupted()) {
            try {
                String message = receiver.printMessage();
                if (message != null) {
                    updateChatWindow(message);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
