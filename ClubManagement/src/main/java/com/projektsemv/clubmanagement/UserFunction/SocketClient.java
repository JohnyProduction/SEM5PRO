package com.projektsemv.clubmanagement.UserFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    private static SocketClient instance;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private SocketClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SocketClient getInstance(String serverAddress, int port) {
        if (instance == null) {
            instance = new SocketClient(serverAddress, port);
        }
        return instance;
    }

    public String receiveMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    // Do not close the socket here

    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
