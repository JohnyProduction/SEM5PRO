package ServerFunctions;
import java.util.List;
import java.util.Objects;
import java.util.Map;

public class Users {
    private static int userID;
    public static boolean checkUserCredentials(String username, String password) {
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser(username));
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
    public static boolean registerUserCredentials(String username,String password, String email, String name, String surname) {
        List<Map<String, Object>> userList = DBConnection.fetchDataFromDatabase(SQLEndpoints.getUser(username));
        if (!userList.isEmpty()) {
            System.out.println("Taki użytkownik istnieje!");
            return false;
        } else {
            DBConnection.fetchDataFromDatabase(SQLEndpoints.registerNewUser(username,password,email,name,surname));
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
    public static String getSettings(int userID) {

        List<Map<String, Object>> settingsObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getSettingsData(userID));
        //System.out.println(settingsObject);
        StringBuilder settingsData = new StringBuilder();
        for (Map<String, Object> row : settingsObject) {
            settingsData.append("|")
                    .append(row.get("UserID")).append("|")
                    .append(row.get("username")).append("|")
                    .append(row.get("name")).append("|")
                    .append(row.get("surname")).append("|")
                    .append(row.get("password")).append("|")
                    .append(row.get("email")).append("|");
        }
        return settingsData.toString();
    }

    public static boolean updateSettingsUser(int userID , String username,  String name, String surname, String password,String email){
        List<Map<String, Object>> result = DBConnection.fetchDataFromDatabase(SQLEndpoints.updateSettingsUser(userID,username,name,surname,password,email));
        // Assuming your query returns a list with a single map for simplicity
        if (!result.isEmpty()) {
            Map<String, Object> resultMap = result.get(0);

            // Check if the deletion was successful (assuming a key "rowsAffected" in the map)
            return resultMap.containsKey("rowsAffected") && (int) resultMap.get("rowsAffected") > 0;
        }

        return false; // No result or an empty result means the deletion was not successful
    }
    public static String getMemberSidebar(int userID) {

        List<Map<String, Object>> sidebarObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getMemberSideBar(userID));
        //System.out.println(sidebarObject);
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
        //System.out.println(lastMatchObject);
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
    public static String getTableMatch(int userID) {
        List<Map<String, Object>> tableMatchObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getMatchTable(userID));
        StringBuilder tableMatchData = new StringBuilder();
        for (Map<String, Object> row : tableMatchObject) {
            tableMatchData.append(row.get("Result")).append("|");
            tableMatchData.append(row.get("OpponentClub")).append("|");
            tableMatchData.append(row.get("MatchDate")).append("|");
        }
        return tableMatchData.toString();
    }
    public static String getStatisticsWinRatio(int userID) {
        List<Map<String, Object>> winRatioObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getWinRatio(userID));
        StringBuilder winRatioData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            winRatioData.append("|").append(row.get("Win")).append("|");
            winRatioData.append(row.get("Lose")).append("|");
        }
        return winRatioData.toString();
    }
    public static String getMonthlyStatisticsWinRatio(int userID) {
        List<Map<String, Object>> winRatioObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getMonthWinRatio(userID));
        //System.out.println(winRatioObject);
        StringBuilder winRatioData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            winRatioData.append(row.get("Month")).append("|");
            winRatioData.append(row.get("Wins")).append("|");
            winRatioData.append(row.get("Losses")).append("|");
        }
        return winRatioData.toString();
    }


    //MANAGER
    public static String getManagerSidebar(int userID) {

        List<Map<String, Object>> sidebarObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerSideBar(userID));
        //System.out.println(sidebarObject);
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
    public static String getManagerLastMatch(int userID) {
        List<Map<String, Object>> lastMatchObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerLastMatch(userID));
        //System.out.println(lastMatchObject);
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
    public static String getManagerMatchTable(int userID) {
        List<Map<String, Object>> tableMatchObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerMatchTable(userID));
        //System.out.println(tableMatchObject);
        StringBuilder tableMatchData = new StringBuilder();
        for (Map<String, Object> row : tableMatchObject) {
            tableMatchData.append(row.get("Result")).append("|");
            tableMatchData.append(row.get("OpponentClub")).append("|");
            tableMatchData.append(row.get("MatchDate")).append("|");
        }
        System.out.println(tableMatchData.toString());
        return tableMatchData.toString();
    }
    public static String getManagerStatisticsWinRatio(int userID) {
        List<Map<String, Object>> winRatioObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerWinRatio(userID));
        StringBuilder winRatioData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            winRatioData.append("|").append(row.get("Win")).append("|");
            winRatioData.append(row.get("Lose")).append("|");
        }
        return winRatioData.toString();
    }
    public static String getManagerMonthlyStatisticsWinRatio(int userID) {
        List<Map<String, Object>> winRatioObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerMonthWinRatio(userID));
        //System.out.println(winRatioObject);
        StringBuilder winRatioData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            winRatioData.append(row.get("Month")).append("|");
            winRatioData.append(row.get("Wins")).append("|");
            winRatioData.append(row.get("Losses")).append("|");
        }
        return winRatioData.toString();
    }
    public static String getManagerIncomesChart(int userID){
        List<Map<String, Object>> winRatioObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerIncomes(userID));
        //System.out.println(winRatioObject);
        StringBuilder incomesData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            incomesData.append(row.get("FinanceID")).append("|");
            incomesData.append(row.get("Value")).append("|");
            incomesData.append(row.get("Date")).append("|");
        }
        return incomesData.toString();
    }
    public static String getManagerFinanceChart(int userID){
        List<Map<String, Object>> winRatioObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerFinance(userID));
        //System.out.println(winRatioObject);
        StringBuilder incomesData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            incomesData.append(row.get("total_income")).append("|");
            incomesData.append(row.get("total_expenses")).append("|");
            incomesData.append(row.get("net_profit")).append("|");
        }
        return incomesData.toString();
    }
    public static String getManagerUserList(int userID){
        List<Map<String, Object>> userListObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerUserList(userID));
        //System.out.println(userListObject);
        StringBuilder userListData = new StringBuilder();
        for (Map<String, Object> row : userListObject) {
            userListData.append(row.get("UserID")).append("|");
            userListData.append(row.get("username")).append("|");
            userListData.append(row.get("password")).append("|");
            userListData.append(row.get("name")).append("|");
            userListData.append(row.get("surname")).append("|");
            userListData.append(row.get("email")).append("|");
            userListData.append(row.get("role")).append("|");
        }
        return userListData.toString();
    }
    public static String getManagerUserRoles(){
        List<Map<String, Object>> rolesObject = DBConnection.fetchDataFromDatabase(SQLEndpoints.getManagerRoles());
        //System.out.println(rolesObject);
        StringBuilder rolesData = new StringBuilder();
        for (Map<String, Object> row : rolesObject) {
            rolesData.append(row.get("RoleID")).append("|");
            rolesData.append(row.get("type")).append("|");
        }
        return rolesData.toString();
    }
    public static boolean deleteUser(int userID) {
        List<Map<String, Object>> result = DBConnection.fetchDataFromDatabase(SQLEndpoints.deleteUserFromDB(userID));

        // Assuming your query returns a list with a single map for simplicity
        if (!result.isEmpty()) {
            Map<String, Object> resultMap = result.get(0);

            // Check if the deletion was successful (assuming a key "rowsAffected" in the map)
            return resultMap.containsKey("rowsAffected") && (int) resultMap.get("rowsAffected") > 0;
        }

        return false; // No result or an empty result means the deletion was not successful
    }
    public static boolean updateUser(int userID , String username, String password, String name, String surname, String email, int role){
        List<Map<String, Object>> result = DBConnection.fetchDataFromDatabase(SQLEndpoints.updateUserFromDB(userID,username,password,name,surname,email,role));
        // Assuming your query returns a list with a single map for simplicity
        if (!result.isEmpty()) {
            Map<String, Object> resultMap = result.get(0);

            // Check if the deletion was successful (assuming a key "rowsAffected" in the map)
            return resultMap.containsKey("rowsAffected") && (int) resultMap.get("rowsAffected") > 0;
        }

        return false; // No result or an empty result means the deletion was not successful
    }

    public static int saveUserID(){
        return userID;
    }
}