package ServerFunctions;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/clubmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static List<Map<String, Object>> fetchDataFromDatabase(String query) {

        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
                if (query.toLowerCase().startsWith("select")) {
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        while (resultSet.next()) {
                            Map<String, Object> row = new HashMap<>();
                            for (int i = 1; i <= columnCount; i++) {
                                String columnName = metaData.getColumnName(i);
                                Object value = resultSet.getObject(i);
                                row.put(columnName, value);
                            }
                            resultList.add(row);
                        }
                    }
                } else {
                    // For non-SELECT queries (INSERT, UPDATE, DELETE, etc.)
                    int rowsAffected = statement.executeUpdate(query);
                    System.out.println("Rows affected: " + rowsAffected);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
