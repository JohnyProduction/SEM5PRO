package com.projektsemv.clubmanagement.UserFunction;

public class UserInfo {
    private int userID;
    private int userRole;
    public UserInfo(int userID, int userRole){
        this.userID = userID;
        this.userRole = userRole;
    }
    public enum UserType{
        MANAGER, MEMBER, FAN
    }
}
