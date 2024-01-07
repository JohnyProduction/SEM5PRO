package ServerFunctions;
import java.util.List;
import java.util.Objects;
import java.util.Map;

public class Users {
    private static int userID;
    public static boolean checkUserCredentials(String username, String password) {
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser(username));
        System.out.println(userList);
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
    public static String getUsername(int userID) {
        List<Map<String, Object>> usernameObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUsername(userID));
        String username="";

        if (!usernameObject.isEmpty()) {
            // Assuming "roleID" is the key in the map
            Object roleObject = usernameObject.get(0).get("name");

            if (roleObject != null) {
                // Safely convert the roleObject to an integer
                username = roleObject.toString();
            }
        }

        return username;
    }
    public static String getMemberSidebar(int userID) {

        List<Map<String, Object>> sidebarObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getMemberSideBar(userID));
        StringBuilder sideBarData = new StringBuilder();
        for (Map<String, Object> row : sidebarObject) {
            sideBarData.append("|").append(row.get("ClubName")).append("|")
                    .append(row.get("LeagueName")).append("|")
                    .append(row.get("ManagerName")).append("|")
                    .append(row.get("ClubAddress")).append("|")
                    .append(row.get("ClubContact")).append("|");
        }
        return sideBarData.toString();
    }
    public static String getLastMatch(int userID) {
        List<Map<String, Object>> lastMatchObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getLastMatch(userID));
        StringBuilder lastMatchData = new StringBuilder();
        for (Map<String, Object> row : lastMatchObject) {
            lastMatchData.append("|").append(row.get("Club1")).append("|");
            lastMatchData.append(row.get("Club2")).append("|");
            lastMatchData.append(row.get("HomeResult")).append("|");
            lastMatchData.append(row.get("GuestResult")).append("|");
            lastMatchData.append(row.get("MatchDate")).append("|");
        }

        return lastMatchData.toString();
    }
    public static int saveUserID(){
        return userID;
    }
}