package com.projektsemv.clubmanagement.UserFunction;

public class League {
    private int LeagueID;
    private String name;

    public League(int LeagueID, String name) {
        this.LeagueID = LeagueID;
        this.name = name;
    }

    // Getter method for LeagueID
    public int getLeagueID() {
        return LeagueID;
    }

    // Setter method for LeagueID
    public void setLeagueID(int leagueID) {
        this.LeagueID = leagueID;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name; // Display the league name in the ChoiceBox
    }
}
