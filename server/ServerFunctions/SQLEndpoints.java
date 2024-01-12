package ServerFunctions;

public class SQLEndpoints {
    public static String getUser(String username){
        return "SELECT username, password, UserID FROM USERS WHERE username='"+username+"'";
    }
    public static String getUserPermission(int userID){return "SELECT roleID FROM USERS where userID = "+userID;}
    public static String registerNewUser(String username,String password, String email, String name, String surname){
        return "INSERT INTO USERS (username, password, email,roleID,name, surname) VALUES('"+username+"','"+password+"','"+email+"',3,'"+name+"','"+surname+"')";
    }
    public static String updateSettingsUser(int userID , String username,  String name, String surname, String password,String email){
        return "UPDATE users\n" +
                "SET\n" +
                "  username = '"+ username+"',\n" +
                "  password = '"+ password+"',\n" +
                "  name = '"+ name+"',\n" +
                "  surname = '"+ surname+"',\n" +
                "  email = '"+ email+"'\n" +
                "WHERE UserID = "+userID;
    }
    public static String getUsername(int userID){
        return "SELECT name FROM users where UserID='"+userID+"'";
    }
    public static String getSettingsData(int userID){
        return "SELECT\n" +
                "    UserID,\n" +
                "    username,\n" +
                "    name,\n" +
                "    surname,\n" +
                "    password,\n" +
                "    email\n" +
                "FROM\n" +
                "    users WHERE userID="+userID;
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
    public static String getMatchTable(int userID){
        return "SELECT\n" +
                "    CASE\n" +
                "        WHEN p.ClubID = m.ClubID1 THEN CONCAT(r.result_home, ' : ', r.result_guest)\n" +
                "        ELSE CONCAT(r.result_guest,' : ', r.result_home)\n" +
                "    END AS Result,\n" +
                "    CASE\n" +
                "        WHEN p.ClubID = m.ClubID1 THEN c2.club_name\n" +
                "        ELSE c1.club_name\n" +
                "    END AS OpponentClub,\n" +
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
                "    m.Date DESC";
    }
    public static String getWinRatio(int userID) {
        return "SELECT\n" +
                "    COALESCE((SUM(CASE WHEN r.WinnerClubID = p.ClubID THEN 1 ELSE 0 END) / COUNT(*)) * 100, 0) AS Win,\n" +
                "    COALESCE((SUM(CASE WHEN r.WinnerClubID != p.ClubID THEN 1 ELSE 0 END) / COUNT(*)) * 100, 0) AS Lose\n" +
                "FROM Results r\n" +
                "JOIN matches m ON r.MatchID = m.MatchID\n" +
                "JOIN players p ON (p.ClubID = m.ClubID1 OR p.ClubID = m.ClubID2)\n" +
                "WHERE p.UserID = " + userID + "\n" +
                "GROUP BY p.ClubID;";
    }
    public static String getMonthWinRatio(int userID) {
        return "SELECT\n" +
                "    MONTH(m.Date) AS Month,\n" +
                "    COALESCE(SUM(CASE WHEN r.WinnerClubID = p.ClubID THEN 1 ELSE 0 END), 0) AS Wins,\n" +
                "    COALESCE(SUM(CASE WHEN r.WinnerClubID != p.ClubID THEN 1 ELSE 0 END), 0) AS Losses\n" +
                "FROM (\n" +
                "    SELECT DISTINCT MONTH(Date) AS Month\n" +
                "    FROM matches\n" +
                ") months\n" +
                "LEFT JOIN Results r ON r.MatchID = MatchID\n" +
                "LEFT JOIN matches m ON r.MatchID = m.MatchID\n" +
                "LEFT JOIN players p ON (r.WinnerClubID = p.ClubID OR m.ClubID1 = p.ClubID OR m.ClubID2 = p.ClubID) AND p.UserID = " + userID + "\n" +
                "GROUP BY months.Month;";
    }
    //MAGANGER
    public static String getManagerSideBar(int userID) {
        return "SELECT\n" +
                "    c.club_name AS ClubName,\n" +
                "    l.name AS LeagueName,\n" +
                "    CONCAT(u.name, ' ', u.surname) AS ManagerName,\n" +
                "    c.address AS ClubAddress,\n" +
                "    c.contact AS ClubContact\n" +
                "FROM\n" +
                "    clubmanagement.clubs c\n" +
                "JOIN\n" +
                "    clubmanagement.league l ON c.LeagueID = l.LeagueID\n" +
                "JOIN\n" +
                "    clubmanagement.users u ON c.ManagerID = u.UserID\n" +
                "WHERE\n" +
                "    u.UserID = " + userID;
    }

    public static String getManagerLastMatch(int userID) {
        return "SELECT\n" +
                "    c1.short_club_name AS Club1,\n" +
                "    c2.short_club_name AS Club2,\n" +
                "    r.result_home AS HomeResult,\n" +
                "    r.result_guest AS GuestResult,\n" +
                "    DATE_FORMAT(m.Date, '%d.%m.%Y') AS MatchDate\n" +
                "FROM\n" +
                "    clubmanagement.matches m\n" +
                "JOIN\n" +
                "    clubmanagement.clubs c1 ON m.ClubID1 = c1.ClubID\n" +
                "JOIN\n" +
                "    clubmanagement.clubs c2 ON m.ClubID2 = c2.ClubID\n" +
                "LEFT JOIN\n" +
                "    clubmanagement.Results r ON m.ResultID = r.ResultID\n" +
                "WHERE\n" +
                "    (c1.ManagerID = "+ userID+" OR c2.ManagerID = "+ userID+")\n" +
                "ORDER BY\n" +
                "    m.Date DESC\n" +
                "LIMIT 1";
    }
    public static String getManagerMatchTable(int userID){
        return "SELECT\n" +
                "    CASE\n" +
                "        WHEN m.ClubID1 = c1.ClubID THEN CONCAT(r.result_home, ' : ', r.result_guest)\n" +
                "        ELSE CONCAT(r.result_guest, ' : ', r.result_home)\n" +
                "    END AS Result,\n" +
                "    CASE\n" +
                "        WHEN m.ClubID1 = c1.ClubID THEN c2.club_name\n" +
                "        ELSE c1.club_name\n" +
                "    END AS OpponentClub,\n" +
                "    DATE_FORMAT(m.Date, '%d.%m.%Y') AS MatchDate\n" +
                "FROM\n" +
                "    clubmanagement.matches m\n" +
                "JOIN\n" +
                "    clubmanagement.clubs c1 ON m.ClubID1 = c1.ClubID\n" +
                "JOIN\n" +
                "    clubmanagement.clubs c2 ON m.ClubID2 = c2.ClubID\n" +
                "JOIN\n" +
                "    clubmanagement.Results r ON m.ResultID = r.ResultID\n" +
                "WHERE\n" +
                "    (c1.ManagerID = "+ userID+" OR c2.ManagerID = "+ userID+")\n" +
                "ORDER BY\n" +
                "    m.Date DESC\n";
    }
    public  static String getManagerWinRatio(int userID){
        return"SELECT\n" +
                "    COALESCE((SUM(CASE WHEN r.WinnerClubID = m.ClubID1 THEN 1 ELSE 0 END) / COUNT(*)) * 100, 0) AS Win,\n" +
                "    COALESCE((SUM(CASE WHEN r.WinnerClubID != m.ClubID1 THEN 1 ELSE 0 END) / COUNT(*)) * 100, 0) AS Lose\n" +
                "FROM clubs c\n" +
                "JOIN matches m ON c.ClubID = m.ClubID1 OR c.ClubID = m.ClubID2\n" +
                "LEFT JOIN Results r ON m.MatchID = r.MatchID\n" +
                "WHERE c.ManagerID = "+userID;
    }
    public static String getManagerMonthWinRatio(int userID) {
        return "SELECT\n" +
                "    months.Month AS Month,\n" +
                "    COALESCE(SUM(CASE WHEN r.WinnerClubID = c.ClubID THEN 1 ELSE 0 END), 0) AS Wins,\n" +
                "    COALESCE(SUM(CASE WHEN r.WinnerClubID != c.ClubID THEN 1 ELSE 0 END), 0) AS Losses\n" +
                "FROM (\n" +
                "    SELECT DISTINCT MONTH(m.Date) AS Month\n" +
                "    FROM matches m\n" +
                ") months\n" +
                "LEFT JOIN Results r ON r.MatchID = MatchID\n" +
                "LEFT JOIN matches m ON r.MatchID = m.MatchID\n" +
                "LEFT JOIN clubs c ON r.WinnerClubID = c.ClubID OR m.ClubID1 = c.ClubID OR m.ClubID2 = c.ClubID\n" +
                "WHERE c.ManagerID = "+ userID+"\n" +
                "GROUP BY months.Month;";
    }
    public static String getManagerIncomes(int userID){
        return  "SELECT f.FinanceID, f.Income AS Value, f.date AS Date, 'INCOMES' AS Type\n" +
                "FROM finance f\n" +
                "JOIN clubs c ON f.ClubID = c.ClubID\n" +
                "WHERE f.Income IS NOT NULL\n" +
                "AND c.ManagerID = "+ userID+"\n" +
                "\n" +
                "UNION ALL\n" +
                "\n" +
                "SELECT f.FinanceID, -f.Expenses AS Value, f.date AS Date, 'EXPENSES' AS Type\n" +
                "FROM finance f\n" +
                "JOIN clubs c ON f.ClubID = c.ClubID\n" +
                "WHERE f.Income IS NULL\n" +
                "AND c.ManagerID = "+userID;
    }
    public static String getManagerFinance(int userID){
        return"SELECT\n" +
                "    MONTH(f.date) AS transaction_month,\n" +
                "    SUM(f.Income) AS total_income,\n" +
                "    SUM(f.Expenses) AS total_expenses,\n" +
                "    SUM(f.Income - f.Expenses) AS net_profit\n" +
                "FROM\n" +
                "    finance f\n" +
                "JOIN\n" +
                "    clubs c ON f.ClubID = c.ClubID\n" +
                "WHERE\n" +
                "    c.ManagerID = "+ userID+"\n" +
                "    AND MONTH(f.date) = MONTH(CURRENT_DATE())\n" +
                "GROUP BY\n" +
                "    c.club_name, MONTH(f.date);";
    }
    public static String getManagerUserList(int userID){
        return "SELECT u.UserID, u.username, u.password, u.name, u.surname, u.email, r.type as role\n" +
                "FROM users u\n" +
                "JOIN roles r ON u.roleID = r.RoleID\n" +
                "JOIN players p ON u.UserID = p.UserID\n" +
                "JOIN clubs c ON p.ClubID = c.ClubID\n" +
                "WHERE c.ManagerID = "+userID;
    }
    public static String getManagerRoles(){
        return "SELECT * FROM Roles";
    }
    public static String deleteUserFromDB(int userID){
        return"DELETE FROM users\n" +
                "WHERE userID = "+userID;
    }
    public static String updateUserFromDB(int userID , String username, String password, String name, String surname, String email, int role){
        return "UPDATE users\n" +
                "SET\n" +
                "  username = '"+ username+"',\n" +
                "  password = '"+ password+"',\n" +
                "  name = '"+ name+"',\n" +
                "  surname = '"+ surname+"',\n" +
                "  email = '"+ email+"',\n" +
                "  roleID = "+ role+"\n" +
                "WHERE UserID = "+userID;
    }





    public static String getUserID(String username, String password){
        return "SELECT UserID FROM users where username='"+username+"' and password='"+password+"'+";
    }
}