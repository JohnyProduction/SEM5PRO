package ServerFunctions;
import java.io.*;
import java.util.List;
public class SwitchBoard {
    public static void SwitchMenuBoard(String message,List<PrintWriter> clientWriters ){
        String[] parts = message.split("\\|");
        switch(parts[0].trim()) {
            case "LOGIN":
                String username = parts[1];
                String password = parts[2];
                if (Users.checkUserCredentials(username,password)) {
                    Message.sendMessage(clientWriters,"LOGIN|SUCCESS");
                } else {
                    Message.sendMessage(clientWriters,"LOGIN|ERROR");
                }
                break;
            case "REGISTER":
                String login = parts[1];
                String pass =  parts[2];
                String email = parts[3];
                if(Users.registerUserCredentials(login,pass,email)){
                    Message.sendMessage(clientWriters,"REGISTER|SUCCESS");
                }else {
                    Message.sendMessage(clientWriters,"REGISTER|ERROR");
                }
                break;
            case "":
                break;
        }
    }
}
