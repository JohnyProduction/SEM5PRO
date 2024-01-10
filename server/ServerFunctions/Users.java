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
            settingsData.append("|").append(row.get("username")).append("|")
                    .append(row.get("name")).append("|")
                    .append(row.get("surname")).append("|")
                    .append(row.get("password")).append("|")
                    .append(row.get("email")).append("|");
        }
        return settingsData.toString();
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
        System.out.println(winRatioObject);
        StringBuilder incomesData = new StringBuilder();
        for (Map<String, Object> row : winRatioObject) {
            incomesData.append(row.get("total_income")).append("|");
            incomesData.append(row.get("total_expenses")).append("|");
            incomesData.append(row.get("net_profit")).append("|");
        }
        return incomesData.toString();
    }

    public static int saveUserID(){
        return userID;
    }
}