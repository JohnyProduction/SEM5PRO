package com.projektsemv.clubmanagement.UserFunction;

public class Tickets {
    private int ticketID;
    private String date;
    private String price;
    private String match;

    // Constructor
    public Tickets(int ticketID, String date, String price, String match) {
        this.ticketID = ticketID;
        this.date = date;
        this.price = price;
        this.match = match;
    }

    // Getter methods
    public int getTicketID() {
        return ticketID;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getMatch() {
        return match;
    }

    // Setter methods
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMatch(String match) {
        this.match = match;
    }
    @Override
    public String toString() {
        return ticketID+" | "+ date +" | "+ price +"PLN | "+match; // Display the 'type' when the object is converted to a string
    }
}
