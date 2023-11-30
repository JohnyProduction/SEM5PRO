import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import ServerFunctions.*;

public class main {
    private static List<PrintWriter> clientWriters = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Serwer nasłuchuje na porcie 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Połączono z klientem: " + clientSocket.getInetAddress().getHostAddress());

                // Tworzenie obiektu PrintWriter do wysyłania wiadomości do klienta.
                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(clientWriter);

                // Tworzenie wątku obsługującego każdego klienta.
                new Thread(new FutureTask<>(new ClientHandler(clientSocket, clientWriter))).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Callable<Void> {
        private Socket clientSocket;
        private PrintWriter clientWriter;

        public ClientHandler(Socket clientSocket, PrintWriter clientWriter) {
            this.clientSocket = clientSocket;
            this.clientWriter = clientWriter;
        }

        @Override
        public Void call() {
            try {
                //System.out.println(DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser()));
                String message = Message.receiveMessage(clientSocket);
                System.out.println(message);

                assert message != null;
                String[] parts = message.split("\\|");
                if (parts.length >= 3 && parts[0].equals("LOGIN")) {
                    String username = parts[1];
                    String password = parts[2];
                    if (Users.checkUserCredentials(username,password)) {
                        Message.sendMessage(clientWriters,"Zalogowano");
                    } else {
                        Message.sendMessage(clientWriters,"Error");
                    }
                }else{
                    System.out.println("Text null");
                }
                clientSocket.close();
                clientWriters.remove(clientWriter); // Usuń obiekt PrintWriter po zakończeniu obsługi klienta.
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
