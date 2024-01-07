package ServerFunctions;

public class SQLEndpoints {
    public static String getUser(String username){
        return "SELECT username, password, UserID FROM USERS WHERE username='"+username+"'";
    }
    public static String getUserPermission(int userID){return "SELECT roleID FROM USERS where userID = "+userID;}
    public static String registerNewUser(String username,String password, String email){
        return "INSERT INTO USERS (username, password, email,roleID) VALUES('"+username+"','"+password+"','"+email+"',1)";
    }
    public static String getUsername(int userID){
        return "SELECT name FROM users where UserID='"+userID+"'";
    }
    public static String getMemberSideBar(int userID){
        return "SELECT\n" +
                "    c.club_name AS ClubName,\n" +
                "    l.name AS LeagueName,\n" +
                "    CONCAT(u.name, ' ', u.surname) AS ManagerName,\n" +
                "    c.address AS ClubAddress,\n" +
                "    c.contact AS ClubContact\n" +
                "FROM\n" +
                "    clubmanagement.players p\n" +
                "JOIN\n" +
                "    clubmanagement.clubs c ON p.ClubID = c.ClubID\n" +
                "JOIN\n" +
                "    clubmanagement.league l ON c.LeagueID = l.LeagueID\n" +
                "JOIN\n" +
                "    clubmanagement.users u ON c.ManagerID = u.UserID\n" +
                "WHERE\n" +
                "    p.UserID =" +userID;
    }
    public static String getLastMatch(int userID){
        return "SELECT\n" +
                "    c1.short_club_name AS Club1,\n" +
                "    c2.short_club_name AS Club2,\n" +
                "    r.result_home AS HomeResult,\n" +
                "    r.result_guest AS GuestResult,\n" +
                "    DATE_FORMAT(m.Date, '%d.%m.%Y') AS MatchDate\n" +
                "FROM\n" +
                "    matches m\n" +
                "JOIN\n" +
                "    clubs c1 ON m.ClubID1 = c1.ClubID\n" +
                "JOIN\n" +
                "    clubs c2 ON m.ClubID2 = c2.ClubID\n" +
                "JOIN\n" +
                "    Results r ON m.ResultID = r.ResultID\n" +
                "JOIN\n" +
                "    players p ON (p.ClubID = m.ClubID1 OR p.ClubID = m.ClubID2) AND p.UserID = "+userID+"\n" +
                "ORDER BY\n" +
                "    m.Date DESC\n" +
                "LIMIT 1;";
    }
    public static String getUserID(String username, String password){
        return "SELECT UserID FROM users where username='"+username+"' and password='"+password+"'+";
    }
}