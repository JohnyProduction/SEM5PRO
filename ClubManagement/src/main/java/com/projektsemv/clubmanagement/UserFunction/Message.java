package com.projektsemv.clubmanagement.UserFunction;
import java.io.PrintWriter;
import java.io.BufferedReader;
public class Message {
    public void sendLoginMessage(PrintWriter writer,String... arguments ) {
        writer.println("LOGIN" + "|" + String.join("|", arguments));
    }
    public void sendRegisterMessage(PrintWriter writer,String... arguments ) {
        writer.println("REGISTER" + "|" + String.join("|", arguments));
    }
    public void sendGetPage(PrintWriter writer,String... arguments ) {
        writer.println("GETPAGEMEMBER" + "|" + String.join("|", arguments));
    }
}