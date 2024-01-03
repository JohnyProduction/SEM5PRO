package com.projektsemv.clubmanagement.UserFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
<<<<<<< HEAD
import  com.projektsemv.clubmanagement.UserFunction.UserInfo;
public class UserFunctions {

    public static boolean SwitchLoginClient(String message){
        String[] parts = message.split("\\|");
        System.out.println(message);
=======

public class UserFunctions {
    public static boolean SwitchLoginClient(String message){
        String[] parts = message.split("\\|");
>>>>>>> parent of 297f159 (Repair everything)
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
<<<<<<< HEAD
            //case "ERROR":
                //System.out.println("Error");
=======
            case "ERROR":

>>>>>>> parent of 297f159 (Repair everything)
            default:
                throw new IllegalStateException("Unexpected value: " + parts[0].trim());
        }
    }
<<<<<<< HEAD
    public static String SwitchDataClient(String message){
        String[] parts = message.split("\\|");
        System.out.println(message);
        switch(parts[0].trim()) {
            case "USERINFO":
                break;
        }
        return "";
    }
=======
>>>>>>> parent of 297f159 (Repair everything)
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
