import java.io.IOException;
import java.net.*;

public class Receiver extends Thread {
    int port = 34343;
    byte[] bytes = new byte[300];
    MulticastSocket socket = new MulticastSocket(port);

    Receiver() throws IOException {
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName("234.234.234.234");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("wireless_32768");
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        try {
            socket.joinGroup(socketAddress, networkInterface);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String printMessage() throws IOException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.receive(packet);
        String s = new String(packet.getData(), 0, packet.getLength());
        return s;
    }

    public static void main(String[] args) throws IOException {
        Receiver receiver = new Receiver();
    }
}
