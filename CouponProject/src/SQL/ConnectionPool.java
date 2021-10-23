package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * A Class that manage the connection to the database
 */
public class ConnectionPool {

    private static ConnectionPool instance = null;
    public static final int NUM_OF_CONNECTION = 10;
    private Stack<Connection> connections = new Stack<>();

    /**
     * private C`TOR that open connection to DB. only one instance created (singleton)
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    private ConnectionPool() throws SQLException {
        openAllConnections();
    }

    /**
     * method that create singleton instance of ConnectionPool
     * @return singleton instance
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * initialize the stack with predefined number of connections
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    public void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONNECTION; index += 1) {
            Connection connection =
                    DriverManager.getConnection(DataBaseManager.URL, DataBaseManager.USER_NAME, DataBaseManager.PASSWORD);
            connections.push(connection);
        }
    }

    /**
     * method that manage the connection stack, give connection or wait if the stack empty
     * @return connection
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }

            return connections.pop();

        }
    }

    /**
     * method to return the connection back to the stack
     * @param connection variable
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    /**
     * method that close all the connections to database
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONNECTION) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}
