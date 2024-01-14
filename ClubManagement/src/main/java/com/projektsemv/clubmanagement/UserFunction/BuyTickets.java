package com.projektsemv.clubmanagement.UserFunction;

public class BuyTickets {
    private String MatchID;
    private String Club1;
    private String Club2;
    private String date;

    // Constructor
    public BuyTickets(String matchID, String club1, String club2, String date) {
        MatchID = matchID;
        Club1 = club1;
        Club2 = club2;
        this.date = date;
    }

    // Getter methods
    public String getMatchID() {
        return MatchID;
    }

    public String getClub1() {
        return Club1;
    }

    public String getClub2() {
        return Club2;
    }

    public String getDate() {
        return date;
    }
    @Override
    public String toString() {
        return MatchID+" | "+ date +" | "+ Club1 +" vs "+Club2; // Display the 'type' when the object is converted to a string
    }
}

