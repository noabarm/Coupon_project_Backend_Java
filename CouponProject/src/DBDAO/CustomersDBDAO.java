package DBDAO;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Dao.CouponsDAO;
import Dao.CustomersDAO;
import Enums.Category;
import SQL.ConnectionPool;
import SQL.DButils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A Class that handle customer crud operations,
 * this class create and execute SQL statements
 */
public class CustomersDBDAO implements CustomersDAO {
    /**
     * SQL crud statements
     */
    private static final String IS_CUSTOMER_EXISTS = "SELECT count(*) FROM `couponDB`.`customers` " +
            "WHERE email=? AND password=?";
    private static final String IS_CUSTOMER_EMAIL_EXISTS = "SELECT count(*) FROM `couponDB`.`customers` WHERE email=?";
    private static final String ADD_CUSTOMER = "INSERT INTO `couponDB`.`customers` " +
            "(`first_name`,`last_name`,`email`,`password`) VALUES (?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `couponDB`.`customers` " +
            "set first_name=? ,last_name=? ,email=? ,password=? WHERE id=?";
    private static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM `couponDB`.`customers` WHERE id=?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `couponDB`.`customers`";
    private static final String GET_ONE_CUSTOMER_BY_ID = "SELECT * FROM `couponDB`.`customers` WHERE id=?";
    private static final String GET_ID_BY_EMAIL_AND_PASSWORD = "SELECT `id` FROM `couponDB`.`customers` " +
            "WHERE email=? AND password=?";
    private static final String GET_ALL_COUPON = "SELECT * FROM `couponDB`.`coupons`";
    private static final String GET_CUSTOMER_COUPON_ID = "SELECT * FROM `couponDB`.`customers_vs_coupons` " +
            "WHERE customer_id=?";


    Connection connection;

    /**
     * C`TOR get instance to ConnectionPool
     */
    public CustomersDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    /**
     * method that check by email and password if the customer exists in the database
     *
     * @param email variable
     * @param password variable
     * @return true if customer exists false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        } else {
            System.out.println("Error: could not get the record counts");
            return false;
        }
    }

    /**
     * method that check by email if the customer exists in the database
     *
     * @param email variable
     * @return true if customer exists false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean isCustomerEmailExists(String email) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EMAIL_EXISTS);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        } else {
            System.out.println("Error: could not get the record counts");
            return false;
        }
    }

    /**
     * method that add customer to the database
     *
     * @param customer variable
     * @return true if customer was added false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean addCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());

        return DButils.runQueryGeneric(ADD_CUSTOMER, params);

    }

    /**
     * method that update exist customer in the database
     *
     * @param customer variable
     * @return true if customer was updated false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customer.getId());

        return DButils.runQueryGeneric(UPDATE_CUSTOMER, params);

    }

    /**
     * method that delete customer by id
     *
     * @param customerID variable
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public void deleteCustomer(int customerID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_BY_ID);
            statement.setInt(1, customerID);
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * method that get a list of all the coupons of the customer that exist in the database
     *
     * @param customerID variable
     * @return companies coupons list
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public ArrayList<Coupon> getCustomerCoupons(int customerID) throws SQLException {
        ArrayList<Integer> couponsID = new ArrayList<>();
        ArrayList<Coupon> customerCoupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement1 = connection.prepareStatement(GET_CUSTOMER_COUPON_ID);
            statement1.setInt(1, customerID);
            ResultSet resultSetCoupon = statement1.executeQuery();
            while (resultSetCoupon.next()) {
                int couponID = resultSetCoupon.getInt("coupon_id");
                couponsID.add(couponID);
            }
            CouponsDAO couponsDAO = new CouponsDBDAO();
            for (int couponID : couponsID) {
                customerCoupons.add(couponsDAO.getOneCoupon(couponID));
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customerCoupons;
    }

    /**
     * method that get a list of all the customers that exist in the database with the list of coupons
     *
     * @return customers list
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("id"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("email"),
                        resultSet.getString("password"), getCustomerCoupons(resultSet.getInt("id")));
                customers.add(customer);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return customers;
    }

    /**
     * get customer by id
     *
     * @param customerID variable
     * @return customer
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public Customer getOneCustomer(int customerID) throws SQLException {
        Customer customer = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER_BY_ID);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("email"),
                        resultSet.getString("password"), getCustomerCoupons(resultSet.getInt("id")));
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customer;
    }

    /**
     * method that get customer id by email and password
     *
     * @param email variable
     * @param password variable
     * @return customer id
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public int getCustomerID(String email, String password) throws SQLException {
        int customerId = 0;
        try {
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Error: " + e.getMessage());
            }
            PreparedStatement statement = connection.prepareStatement(GET_ID_BY_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customerId = resultSet.getInt("id");
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customerId;
    }
}
