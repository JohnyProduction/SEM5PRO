package com.projektsemv.clubmanagement;

public class UserFunctions {
    public static boolean SwitchLoginClient(String message){
        String[] parts = message.split("\\|");
        switch(parts[0].trim()) {
            case "LOGIN":
                return "SUCCESS".equals(parts[1]);
            case "REGISTER":
                return "SUCCESS".equals(parts[1]);
            default:
                throw new IllegalStateException("Unexpected value: " + parts[0].trim());
        }
    }
}
