package ServerFunctions;
import java.io.*;
import java.util.List;
public class SwitchBoard {
    public static int userID;
    public static void SwitchMenuBoard(String message,PrintWriter serverWriter ){
        String[] parts = message.split("\\|");
        switch(parts[0].trim()) {
            case "LOGIN":
                String username = parts[1];
                String password = parts[2];
                if (Users.checkUserCredentials(username,password)) {
                    userID = Users.saveUserID();
                    serverWriter.println(Users.getUserPermission(userID));
                    serverWriter.println("LOGIN|SUCCESS");
                } else {
                    serverWriter.println("LOGIN|ERROR");
                }
                break;
            case "REGISTER":
                String login = parts[1];
                String pass =  parts[2];
                String email = parts[3];
                String name = parts[4];
                String surname = parts[5];
                if(Users.registerUserCredentials(login,pass,email,name,surname)){
                    serverWriter.println("REGISTER|SUCCESS");
                }else {
                    serverWriter.println("REGISTER|ERROR");
                }
                break;
            case "GETPAGE":
                serverWriter.println(Users.getUsername(userID));
                if(parts[1].equals("MEMBER")){
                    serverWriter.println("MEMBERSIDEBAR"+Users.getMemberSidebar(userID));
                    serverWriter.println("MEMBERLASTMATCH"+Users.getLastMatch(userID));
                    serverWriter.println("MEMBERMATCHTABLE|"+Users.getTableMatch(userID));
                } else if (parts[1].equals("MANAGER")) {
                    serverWriter.println("MANAGERSIDEBAR"+Users.getManagerSidebar(userID));
                    serverWriter.println("MANAGERLASTMATCH"+Users.getManagerLastMatch(userID));
                    serverWriter.println("MANAGERMATCHTABLE|"+Users.getManagerMatchTable(userID));
                }else if (parts[1].equals("FAN")) {

                }
                break;
            case "GETSTATISICS":
                if(parts[1].equals("MEMBER")){
                    serverWriter.println("MEMBERCHART"+Users.getStatisticsWinRatio(userID));
                    //System.out.println(Users.getMonthlyStatisticsWinRatio(userID));
                    serverWriter.println("MEMBERCHARTLINE|"+Users.getMonthlyStatisticsWinRatio(userID));
                }else if (parts[1].equals("MANAGER")) {
                    serverWriter.println(Users.getUsername(userID));
                    serverWriter.println("MANAGERCHART"+Users.getManagerStatisticsWinRatio(userID));
                    //System.out.println(Users.getManagerStatisticsWinRatio(userID));
                    serverWriter.println("MANAGERCHARTLINE|"+Users.getManagerMonthlyStatisticsWinRatio(userID));
                }else if (parts[1].equals("FAN")) {

                }
                break;
            case "GETSETTINGS":
                    serverWriter.println(Users.getUsername(userID));
                    System.out.println(Users.getSettings(userID));
                    serverWriter.println("USERSETTINGS"+Users.getSettings(userID));
                break;
            case "GETFINANCE":
                    serverWriter.println(Users.getUsername(userID));
                    serverWriter.println("INCOMES|"+Users.getManagerIncomesChart(userID));
                    System.out.println("CHART|"+Users.getManagerFinanceChart(userID));
                    serverWriter.println("CHART|"+Users.getManagerFinanceChart(userID));
                    //System.out.println("INCOMES"+Users.getManagerIncomesChart(userID));
                    //serverWriter.println("EXPENSES"+Users.getManagerExpensesChart(userID));
                    //System.out.println("EXPENSES"+Users.getManagerExpensesChart(userID));
                break;
            case "":
                break;
        }
    }
}