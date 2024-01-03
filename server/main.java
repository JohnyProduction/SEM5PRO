

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import static java.lang.System.exit;

public class main {
    // Server properties
    private static final int SERVER_PORT = 12345;
    private static final ExecutorService executorService = Executors.newCachedThreadPool(); // Unlimited threads
    private static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException {
        int clientId = 0;

        // Create server socket
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is listening on port: " + SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Server could not listen on port " + SERVER_PORT);
            exit(-1);
        }

        // Listen for client connections
        while (true) {
            Socket clientSocket = serverSocket.accept(); // Accept new connection from Client
            System.out.println("\n "+ clientSocket.getInetAddress().getHostAddress() +" New client connected with id: "+ ++clientId);
            //System.out.println("\nNew client connected with id: " + clientSocket.getInetAddress().getHostAddress());

            // Submit new task to thread pool
            FutureTask<String> task = new FutureTask<>(new ClientHandler(clientSocket, clientId));
            executorService.submit(task);
        }
    }
}