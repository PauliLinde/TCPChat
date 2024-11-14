import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends Thread {

    private Socket clientSocket;
    private MultiUser multiUser;

    public Server(Socket clientSocket, MultiUser mu) {
        this.clientSocket = clientSocket;
        this.multiUser = mu;
    }

    public void run() {
        try(PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

            multiUser.addWriter(pw);
            String inputLine;

            while((inputLine = br.readLine()) != null) {
                multiUser.print(inputLine);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}