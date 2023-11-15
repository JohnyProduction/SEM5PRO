package ServerFunctions;

import java.util.List;
import java.util.Objects;

public class Users {
    public boolean checkUserCredentials(String username, String password){
        List<Object[]> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser());

        for (Object[] user : userList) {
            String dbUsername = Objects.toString(user[0], "");
            String dbPassword = Objects.toString(user[1], "");

            if (username.equals(dbUsername) && password.equals(dbPassword)) {
                return true; // Uwierzytelnianie powiodło się
            }
        }
        return false; // Brak dopasowania użytkownika lub niepoprawne hasło
    }
    public int getUserPermission(int userID){
        List<Object[]> userPermission = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUserPermission(userID));
        int role = 0;
        for (Object[] user : userPermission) {
            if (user[0] != null) {
                role = Integer.parseInt(Objects.toString(user[0], "0"));
            }
        }
        return role;
    }
}
