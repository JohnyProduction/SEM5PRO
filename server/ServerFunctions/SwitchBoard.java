package ServerFunctions;
import java.io.*;
import java.util.List;
public class SwitchBoard {
    public static int userID;
    public static void SwitchMenuBoard(String message,PrintWriter serverWriter ){
        String[] parts = message.split("\\|");
        switch(parts[0].trim()) {
            case "LOGIN":
                String username = parts[1];
                String password = parts[2];
                if (Users.checkUserCredentials(username,password)) {
                    userID = Users.saveUserID();
                    serverWriter.println(Users.getUserPermission(userID));
                    serverWriter.println("LOGIN|SUCCESS");
                } else {
                    serverWriter.println("LOGIN|ERROR");
                }
                break;
            case "REGISTER":
                String login = parts[1];
                String pass =  parts[2];
                String email = parts[3];
                if(Users.registerUserCredentials(login,pass,email)){
                    serverWriter.println("REGISTER|SUCCESS");
                }else {
                    serverWriter.println("REGISTER|ERROR");
                }
                break;
            case "":
                break;
        }
    }
}
