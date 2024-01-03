package ServerFunctions;

public class SQLEndpoints {
    public static String getUser(String username){
        return "SELECT username, password, UserID FROM USERS WHERE username='"+username+"'";
    }
    public static String getUserPermission(int userID){return "SELECT roleID FROM USERS where userID = "+userID;}
    public static String registerNewUser(String username,String password, String email){
        return "INSERT INTO USERS (username, password, email,roleID) VALUES('"+username+"','"+password+"','"+email+"',1)";
    }
    public static String getUserID(String username, String password){
        return "SELECT UserID FROM users where username='"+username+"' and password='"+password+"'+";
    }
}