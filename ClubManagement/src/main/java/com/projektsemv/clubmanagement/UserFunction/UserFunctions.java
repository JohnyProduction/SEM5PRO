package com.projektsemv.clubmanagement.UserFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserFunctions {

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