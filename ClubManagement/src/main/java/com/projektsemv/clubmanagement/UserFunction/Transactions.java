package com.projektsemv.clubmanagement.UserFunction;

public class Transactions {
    private String TransactionID;
    private String Amount;
    private String date;
    public Transactions(String TransactionID,String Amount,String date){
        this.TransactionID =TransactionID;
        this.Amount = Amount;
        this.date = date;
    }
    public String getTransactionID(){
        return TransactionID;
    }
    public void setTransactionID(){
        this.TransactionID =TransactionID;
    }
    public String getAmount(){
        return Amount;
    }
    public void setAmount(){
        this.Amount = Amount;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
