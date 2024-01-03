import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import ServerFunctions.*;
public class ClientHandler implements Callable<String>{
    private final Socket clientSocket;
    private final int clientId;


    public ClientHandler(Socket clientSocket, int clientId) {
        this.clientSocket = clientSocket;
        this.clientId = clientId;
    }
    public String call() {
        try (
                // Create input and output streams
                BufferedReader ReadFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter SendToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            SendToClient.println(clientId);
            while (true) {
                String message = ReadFromClient.readLine();
                if (message != null) {
                    System.out.println("Received from client: " + message);
                    SwitchBoard.SwitchMenuBoard(message, SendToClient);
                }
            }
        } catch (IOException e) {
            System.out.println("\nClient with id: "+ clientId+ " disconnected.");
        }
        return "Error with ClientHandler!";
    }
}
