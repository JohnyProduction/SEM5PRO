package com.projektsemv.clubmanagement.UserFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import  com.projektsemv.clubmanagement.UserFunction.UserInfo;
public class UserFunctions {

    public static boolean SwitchLoginClient(String message){
        String[] parts = message.split("\\|");
        System.out.println(message);
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
            //case "ERROR":
                //System.out.println("Error");
            default:
                throw new IllegalStateException("Unexpected value: " + parts[0].trim());
        }
    }
    public static String SwitchDataClient(String message){
        String[] parts = message.split("\\|");
        System.out.println(message);
        switch(parts[0].trim()) {
            case "USERINFO":
                break;
        }
        return "";
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
