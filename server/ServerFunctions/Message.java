package ServerFunctions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Message {
    public static void sendMessage(List<PrintWriter> clientWriters, String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }
    public static String receiveMessage(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            return new String(buffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace(); // handle exceptions properly in a real application
            return null;
        }
    }
}
