package Facades;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.AdministratorExceptions.*;
import Exceptions.LoginErrorException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * a class facade that incorporates all of the methods accessible to a registered Administrator
 */
public class AdminFacade extends ClientFacade {

    /**
     * a constructor
     */
    public AdminFacade() {
        super();
    }

    /**
     * login method to check if the email and password matches and authorize as Administrator
     *
     * @param email
     * @param password
     * @return true if login succeed false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * create a new company and sends it to be insert into the system database
     *
     * @param company
     * @throws CompanyAlreadyExistException this exception will be thrown when company name is already exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void addCompany(Company company) throws CompanyAlreadyExistException {

        try {
            if (companyDAO.isCompanyExistsNameOrEmail(company.getName(), company.getEmail())) {
                throw new CompanyAlreadyExistException("this name or email is already exists ");
            }
            companyDAO.addCompany(company);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * method to update a company personal info
     *
     * @param company
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void updateCompany(Company company) throws  CompanyNotFoundException{

        boolean isCompanyExists = false;
        try {
            isCompanyExists = companyDAO.isCompanyExistsByName(company.getName());
            if (isCompanyExists) {
                company.setId(companyDAO.getCompanyID(company.getName()));
                Company dbCompany = companyDAO.getOneCompany(company.getId());
                dbCompany.setEmail(company.getEmail());
                dbCompany.setPassword(company.getPassword());
                companyDAO.updateCompany(dbCompany);
            } else {
                throw new CompanyNotFoundException();
            }
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * method to delete a company and all of its coupons from the database tables
     *
     * @param companyID
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void deleteCompany(int companyID) throws CompanyNotFoundException {
        try {
            ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();

            for (Coupon coupon : coupons) {
                if (coupon.getCompanyID() == companyID) {
                    couponsDAO.deleteCouponPurchaseByCouponID(coupon.getId());
                    couponsDAO.deleteCoupon(coupon.getId());
                }
            }

            companyDAO.deleteCompany(companyID);

        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * returns all companies info
     *
     * @param
     * @return companies list
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public ArrayList<Company> getAllCompanies() {

        try {
            return companyDAO.getAllCompanies();
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
            return null;
        }
    }

    /**
     * gets an companyID and returns one company info
     *
     * @param companyID
     * @return company
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public Company getOneCompany(int companyID) throws CompanyNotFoundException {
        try {
            return companyDAO.getOneCompany(companyID);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
            return null;
        }

    }

    /**
     * create a new customer and sends it to be insert into the system database
     *
     * @param customer
     * @throws CustomerAlreadyExistException this exception will be thrown when customer is already exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void addCustomer(Customer customer) throws CustomerAlreadyExistException {

        try {
            if (customersDAO.isCustomerEmailExists(customer.getEmail())) {
                throw new CustomerAlreadyExistException("this email is already exists ");
            }
            customersDAO.addCustomer(customer);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * method to update a customer personal info
     *
     * @param customer
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void updateCustomer(Customer customer) throws  CustomerNotFoundException {
        try {
            Customer dbCustomer = customersDAO.getOneCustomer(customer.getId());
            dbCustomer.setFirstName(customer.getFirstName());
            dbCustomer.setLastName(customer.getLastName());
            dbCustomer.setEmail(customer.getEmail());
            dbCustomer.setPassword(customer.getPassword());
            customersDAO.updateCustomer(dbCustomer);

        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * method to delete a customer and all of its purchased history of coupons from the database tables
     *
     * @param customerID
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void deleteCustomer(int customerID) throws CustomerNotFoundException {

        try {
            couponsDAO.deleteCouponPurchaseByCustomerID(customerID);
            customersDAO.deleteCustomer(customerID);

        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * returns all of the registered Customers
     *
     * @return customer list
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public ArrayList<Customer> getAllCustomers() {
        try {
            return customersDAO.getAllCustomers();
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
            return null;
        }
    }

    /**
     * gets an ID and returns the one customer info
     *
     * @param customerID
     * @return customer
     * @throws CustomerNotFoundException this exception will be thrown when customer is not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public Customer getOneCustomer(int customerID) throws CustomerNotFoundException {

        try {
            return customersDAO.getOneCustomer(customerID);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
            return null;
        }
    }


}
