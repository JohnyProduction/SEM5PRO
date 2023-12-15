package ServerFunctions;
import java.util.List;
import java.util.Objects;
import java.util.Map;

public class Users {

    public static boolean checkUserCredentials(String username, String password) {
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser(username));
        System.out.println(userList);
        for (Map<String, Object> row : userList) {
            Object storedUsername = row.get("username");
            Object storedPassword = row.get("password");
            if(storedUsername != null && storedUsername.toString().trim().equals(username.trim())){
                if (storedPassword != null && storedPassword.toString().trim().equals(password.trim())) {
                    return true; // Znaleziono użytkownika z pasującym hasłem
                }
            }
        }
        return false; // Brak dopasowania użytkownika lub niepoprawne hasło
    }
    public static boolean registerUserCredentials(String username,String password, String email) {
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser(username));
        if (!userList.isEmpty()) {
            System.out.println("Taki użytkownik istnieje!");
            return false;
        } else {
            DBConnection.fetchDataFromDatabase(SQLEndpoints.registerNewUser(username,password,email));
            return true;
        }
    }














    /*
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
*/
}
