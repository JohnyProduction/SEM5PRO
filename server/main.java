import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                Thread clientThread = new Thread(new ClientHandler(clientSocket, clientWriter));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter clientWriter;

        public ClientHandler(Socket clientSocket, PrintWriter clientWriter) {
            this.clientSocket = clientSocket;
            this.clientWriter = clientWriter;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Otrzymano od klienta: " + message);

                    // Rozdziel komunikat na części
                    String[] parts = message.split("\\|");
                    if (parts.length >= 3 && parts[0].equals("LOGIN")) {
                        String username = parts[1];
                        String password = parts[2];

                        // Sprawdzenie poprawności loginu i hasła
                        if (checkCredentials(username, password)) {
                            // Udane uwierzytelnienie
                            broadcastMessage("Udane uwierzytelnienie");
                        } else {
                            // Błąd uwierzytelnienia
                            broadcastMessage("Błąd uwierzytelnienia");
                        }
                    } else {
                        // Obsługa innych rodzajów komunikatów
                    }
                }
                reader.close();
                clientSocket.close();

                // Usuń obiekt PrintWriter po zakończeniu obsługi klienta.
                clientWriters.remove(clientWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean checkCredentials(String username, String password) {
        return username.equals("test") && password.equals("test");
    }
    private static void broadcastMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println("Serwer: " + message);
        }
    }
}
