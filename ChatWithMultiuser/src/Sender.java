import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender{

    private String userName;

    String userInput = new String();
    String send = new String();

    InetAddress ip = InetAddress.getByName("234.234.234.234");
    int toPort = 34343;
    MulticastSocket socket = new MulticastSocket();

    Sender(String userName) throws IOException {
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
    public void writeMessage(String message) throws IOException {
        userInput = message;
        send = getUserName() + ": " + userInput;
        byte[] bytes = send.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, ip, toPort);
        socket.send(packet);
    }
    public static void main(String[] args) throws IOException {
        String userName = JOptionPane.showInputDialog("Username: ");
        if(userName == null){
            System.exit(0);
        }
        Sender sender = new Sender(userName);
        ChatWindow chatWindow = new ChatWindow(userName);
        chatWindow.start();
    }
}



