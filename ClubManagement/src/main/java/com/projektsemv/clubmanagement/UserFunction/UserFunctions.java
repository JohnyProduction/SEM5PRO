package com.projektsemv.clubmanagement.UserFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UserFunctions {
    public static boolean SwitchLoginClient(String message){
        String[] parts = message.split("\\|");
        switch(parts[0].trim()) {
            case "LOGIN":
                if(parts[1].trim().equals("ERROR")){
                    System.out.println("Error");
                }
                return "SUCCESS".equals(parts[1]);
            case "REGISTER":
                if(parts[1].trim().equals("ERROR")){
                    System.out.println("Error");
                }
                return "SUCCESS".equals(parts[1]);
            case "ERROR":

            default:
                throw new IllegalStateException("Unexpected value: " + parts[0].trim());
        }
    }
    public static String ReadMessage(Socket socket){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println(response);
            reader.close();
            return response;
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return "";
    }
}
