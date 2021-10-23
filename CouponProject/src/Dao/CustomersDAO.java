package Dao;

import Beans.Coupon;
import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {

    boolean isCustomerExists(String email,String password) throws SQLException;

    boolean isCustomerEmailExists(String email) throws SQLException;

    boolean addCustomer(Customer customer) throws SQLException;

    boolean updateCustomer(Customer customer) throws SQLException;

    void deleteCustomer(int customerID) throws SQLException;

    ArrayList<Coupon> getCustomerCoupons(int customerID) throws SQLException;

    ArrayList<Customer> getAllCustomers() throws SQLException;

    Customer getOneCustomer(int customerID) throws SQLException;

    int getCustomerID(String email,String password) throws SQLException;


}
