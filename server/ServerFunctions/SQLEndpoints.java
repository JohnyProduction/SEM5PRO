package ServerFunctions;

public class SQLEndpoints {
    public static String getUser(String username){
        return "SELECT username, password FROM USERS WHERE username='"+username+"'";
    }
    public static String getUserPermission(int userID){return "SELECT roleID FROM USERS where userID = "+userID;}
    public static String registerNewUser(String username,String password, String email,String name, String lastname){
        return "INSERT INTO USERS (username, password, name, surname, email,roleID) VALUES("+username+","+password+","+name+","+lastname+","+email+",1)";
    }
}
