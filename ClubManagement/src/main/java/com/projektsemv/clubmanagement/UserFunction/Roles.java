package com.projektsemv.clubmanagement.UserFunction;

public class Roles {
    private int roleID;
    private String type;

    public Roles(int roleID, String type) {
        this.roleID = roleID;
        this.type = type;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getType() {
        return type;
    }
    public void setRoleID(int roleID){
        this.roleID=roleID;
    }
    public void setType(String type){
        this.type=type;
    }
    @Override
    public String toString() {
        return type; // Display the 'type' when the object is converted to a string
    }
}
