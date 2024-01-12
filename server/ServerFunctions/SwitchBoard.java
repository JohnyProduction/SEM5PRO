package ServerFunctions;
import javax.swing.plaf.synth.SynthTextAreaUI;
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
                    //System.out.println(Users.getFanLastMatch(userID));
                    serverWriter.println("FANSIDEBAR"+Users.getFanSidebar(userID));
                    serverWriter.println("FANLASTMATCH" +Users.getFanLastMatch(userID));
                    serverWriter.println("FANMATCHTABLE|"+Users.getFanMatchTable(userID));
                }
                break;
            case "GETSTATISICS":
                serverWriter.println(Users.getUsername(userID));
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
                    //System.out.println(Users.getSettings(userID));
                    serverWriter.println("USERSETTINGS"+Users.getSettings(userID));
                break;
            case "GETSETTINGSFAN":

                break;
            case"UPDATESETTINGSUSER":
                Users.updateSettingsUser(Integer.parseInt(parts[1]),parts[2],parts[3],parts[4],parts[5],parts[6]);
                break;
            case "GETFINANCE":
                    serverWriter.println(Users.getUsername(userID));
                    serverWriter.println("INCOMES|"+Users.getManagerIncomesChart(userID));
                    //System.out.println("CHART|"+Users.getManagerFinanceChart(userID));
                    serverWriter.println("CHART|"+Users.getManagerFinanceChart(userID));
                break;
            case "GETUSERLIST":
                    serverWriter.println(Users.getUsername(userID));
                    serverWriter.println("USERROLES|"+ Users.getManagerUserRoles());
                    serverWriter.println("USERLIST|"+ Users.getManagerUserList(userID));
                break;
            case "DELETEUSER":
                    String userIDNumber = parts[1];
                    if(Users.deleteUser(Integer.parseInt(userIDNumber))){
                        serverWriter.println("DELETE|SUCCESS");
                    }else {
                        serverWriter.println("DELETE|ERROR");
                    }
                break;
            case "UPDATEUSER":
                    Users.updateUser(Integer.parseInt(parts[1]),parts[2],parts[3],parts[4],parts[5],parts[6],Integer.parseInt(parts[7]));
                break;
            case "GETNEWS":
                //System.out.println(Users.getUsername(userID));
                //serverWriter.println(Users.getUsername(userID));
                    if(parts[1].equals("MEMBER")){

                       // System.out.println(Users.getMemberNews(userID));
                       serverWriter.println("NEWS|"+ Users.getMemberNews(userID));
                    }else if (parts[1].equals("MANAGER")) {

                    }else if (parts[1].equals("FAN")) {

                    }
                break;
            case "":
                break;
        }
    }
}