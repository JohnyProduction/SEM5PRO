package com.projektsemv.clubmanagement.UserFunction;

public class News {
    private String sender;
    private String description;
    public News (String sender, String description){
        this.sender=sender;
        this.description= description;
    }

    public String getSender(){
        return sender;
    }
    public String getDescription(){
        return description;
    }
    public void setSender(String sender){
        this.sender=sender;
    }
    public void setDescription(String description){
        this.description=description;
    }
    @Override
    public String toString() {
        return sender+" | "+ description; // Display the 'type' when the object is converted to a string
    }
}
