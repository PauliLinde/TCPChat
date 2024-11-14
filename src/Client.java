import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends JFrame implements ActionListener{

    String fromClient = new String();
    String fromServer = new String();
    String userName = "";

    JPanel panel = new JPanel();
    JButton unplug = new JButton("Unplug");
    JTextArea text = new JTextArea();
    JTextField userText = new JTextField();
    JScrollPane scroll = new JScrollPane(text);

    PrintWriter out;
    BufferedReader in;

    public Client(){
        userName = JOptionPane.showInputDialog(null, "Enter your user name");
        setTitle("Chat - " + userName);
        add(panel);
        text.setEditable(false);
        panel.setLayout(new BorderLayout()); panel.add(unplug, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER); panel.add(userText, BorderLayout.SOUTH);
        userText.addActionListener(this);
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String hostName = "127.0.0.1";  //localhost
        int portNumber = 23456;

        try(Socket socket = new Socket(hostName, portNumber);){

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while((fromServer = in.readLine()) != null){
                text.append(fromServer + "\n");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        unplug.addActionListener(l -> {
            System.exit(0);
            text.append(userName + " left the chat");
        });

    }
    public void actionPerformed(ActionEvent e){
        String input = userName + ": " + userText.getText();
        try {
            out.println(input);
            userText.setText("");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
    }
}