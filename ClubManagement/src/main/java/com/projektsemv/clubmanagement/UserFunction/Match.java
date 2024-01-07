package com.projektsemv.clubmanagement.UserFunction;

public class Match {
    private String result;
    private String opponent;
    private String date;

    public Match(String result, String opponent, String date) {
        this.result = result;
        this.opponent = opponent;
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
