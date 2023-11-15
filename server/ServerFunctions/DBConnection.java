package ServerFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    public static <MyDataObject> List<MyDataObject> fetchDataFromDatabase() {
        String jdbcUrl = "jdbc:oracle:thin:@//your_oracle_host:1521/your_service_name";
        String username = "your_username";
        String password = "your_password";

        List<MyDataObject> resultList = new ArrayList<>();

        try {
            // Register the Oracle JDBC driver (you only need to do this once)
            Class.forName("oracle.jdbc.OracleDriver");

            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM your_table_name";

            ResultSet resultSet = statement.executeQuery(query);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}