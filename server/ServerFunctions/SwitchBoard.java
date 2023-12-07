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
                    Message.sendMessage(clientWriters,"Zalogowano");
                } else {
                    Message.sendMessage(clientWriters,"Error");
                }
                break;
            case "REGISTER":
                String login = parts[1];
                String pass =  parts[2];
                String name = parts[3];
                String lastname = parts[4];
                String email = parts[5];
                if(Users.registerUserCredentials(login,pass,name,lastname,email)){
                    Message.sendMessage(clientWriters,"Dodano użytkownika");
                }else {
                    Message.sendMessage(clientWriters,"Error");
                }
                break;
            case "":
                break;
        }
    }
}
