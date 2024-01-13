package com.projektsemv.clubmanagement.UserFunction;

public class Club {
    private int clubID;
    private String clubName;

    // Constructor
    public Club(int clubID, String clubName) {
        this.clubID = clubID;
        this.clubName = clubName;
    }

    // Getter method for clubID
    public int getClubID() {
        return clubID;
    }

    // Setter method for clubID
    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    // Getter method for clubName
    public String getClubName() {
        return clubName;
    }

    // Setter method for clubName
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    @Override
    public String toString() {
        return clubName; // Display the league name in the ChoiceBox
    }
}
