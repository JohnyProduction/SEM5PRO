package ServerFunctions;

public class SQLEndpoints {
    public static String getUser(String username){
        return "SELECT username, password FROM USERS WHERE username='"+username+"'";
    }
    public static String getUserPermission(int userID){return "SELECT roleID FROM USERS where userID = "+userID;}
}
