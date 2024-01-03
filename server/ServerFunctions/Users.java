package ServerFunctions;
import java.util.List;
import java.util.Objects;
import java.util.Map;

public class Users {
    private static int userID;
    public static boolean checkUserCredentials(String username, String password) {
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser(username));
        //System.out.println(userList);
        for (Map<String, Object> row : userList) {
            Object storedUsername = row.get("username");
            Object storedPassword = row.get("password");
            Object storedUserID = row.get("UserID");
            if(storedUsername != null && storedUsername.toString().trim().equals(username.trim())){
                if (storedPassword != null && storedPassword.toString().trim().equals(password.trim())) {
                    userID = Integer.parseInt(storedUserID.toString());
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
<<<<<<< Updated upstream
    public static int getUserPermission(int userID) {
        List<Map<String, Object>> userPermission = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUserPermission(userID));
        int role = 0;

        if (!userPermission.isEmpty()) {
            // Assuming "roleID" is the key in the map
            Object roleObject = userPermission.get(0).get("roleID");

            if (roleObject != null) {
                // Safely convert the roleObject to an integer
                role = Integer.parseInt(roleObject.toString());
            }
        }

        return role;
    }

    public static int saveUserID(){
        return userID;
    }
=======
    public static String getUserInfo(String username, String password){
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getAllUserInfo(username,password));
        StringBuilder formattedString = new StringBuilder();
        for (Map<String, Object> row : userList) {
            formattedString.append("|").append(row.get("UserID"));
            formattedString.append("|").append(row.get("roleID"));
            formattedString.append("|").append(row.get("username"));
            formattedString.append("|").append(row.get("password"));
            formattedString.append("|").append(row.get("name"));
            formattedString.append("|").append(row.get("surname"));
        }
        return formattedString.toString();
    }
    public int getUserPermission(int userID){
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUserPermission(userID));
        System.out.println(userList);
        for(Map<String, Object> row : userList) {
            Object storedRoleID = row.get("RoleID");
        }
        return 0; // Brak dopasowania użytkownika lub niepoprawne hasło
    }
>>>>>>> Stashed changes
}
