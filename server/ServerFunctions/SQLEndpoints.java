package ServerFunctions;

public class SQLEndpoints {
    static String getUser(){
        return "SELECT username, password FROM USERS";
    }
    static String getUserPermission(int userID){return "SELECT roleID FROM USERS where userID = "+userID;}
}
