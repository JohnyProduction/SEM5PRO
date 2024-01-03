package com.projektsemv.clubmanagement.UserFunction;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static int clientId=-1;
    public static BufferedReader ReadFromServer;
    public static PrintWriter SendToServer;
    public static ClientWindow windowApplication = new ClientWindow();

    public static void main(String[] args) {
        Thread clientThread = new Thread(() -> {

            String serverAddress = "localhost";
            int serverPort = 12345;

            try (Socket clientSocket = new Socket(serverAddress, serverPort);
                 BufferedReader ReadFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter SendToServer = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                Client.ReadFromServer = ReadFromServer;
                Client.SendToServer = SendToServer;

                clientId = Integer.parseInt(ReadFromServer.readLine());
                System.out.println("ClientID: " + clientId + "\n");

                windowApplication.startWindow();

                try {
                    while (true) {
                        if (ReadFromServer.ready())
                            throw new RuntimeException("Something went wrong! Server is not ready to read.");
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    System.exit(10);
                }

            } catch (java.net.ConnectException e) {
                System.out.println("ERROR! Server is not started!");
                System.exit(-1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientThread.start();
    }
    public static boolean switchLoginClient(String message) {
        String[] parts = message.split("\\|");
        switch (parts[0].trim()) {
            case "LOGIN":
                if (parts[1].trim().equals("ERROR")) {
                    System.out.println("Error");
                    return false;
                }
                return "SUCCESS".equals(parts[1]);
            case "REGISTER":
                if (parts[1].trim().equals("ERROR")) {
                    System.out.println("Error");
                    return false;
                }
                return "SUCCESS".equals(parts[1]);
        }
        return false;
    }

}
