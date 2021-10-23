package SQL;

import java.sql.*;
import java.util.Map;
/**
 * db util Class that run generic query
 */
public class DButils {
    /**
     * method that open connection and run the sql command
     * @param sql
     * @throws SQLException
     */
    public static void runQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            //take a connection for connection pool.
            connection = ConnectionPool.getInstance().getConnection();
            //run the sql command
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error in sql: "+ e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * method that open connection and run the sql command useful when there are different kind of values
     * @param query
     * @param params
     * @return true if SQL command executed false otherwise
     * @throws SQLException
     */
    public static boolean runQueryGeneric(String query, Map<Integer,Object> params) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (Integer) value);
                    }else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    }else if (value instanceof Date) {
                        statement.setDate(key, (Date) value);
                    }else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean) value);
                    }else if (value instanceof Double) {
                        statement.setDouble(key, (Double) value);
                    }else if (value instanceof Float) {
                        statement.setFloat(key, (Float) value);
                    }else if (value instanceof Timestamp) {
                        statement.setTimestamp(key, (Timestamp) value);
                    }
                } catch(SQLException err){
                    System.out.println("Error in sql: "+ err.getMessage());
                }
            });
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error in executing sql");
            return false;
        }finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return true;

    }
}
