package DBDAO;

import Beans.Company;
import Beans.Coupon;
import Dao.CompanyDAO;
import Enums.Category;
import Exceptions.LoginErrorException;
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
 * A Class that handle company crud operations, this class create and execute SQL statements
 */
public class CompaniesDBDAO implements CompanyDAO {
    /**
     * SQL crud statements
     */
    private static final String IS_COMPANY_EXISTS = "SELECT count(*) FROM `couponDB`.`companies` " +
            "WHERE email=? AND password=?";
    private static final String IS_COMPANY_EXISTS_BY_NAME_OR_EMAIL = "SELECT count(*) FROM `couponDB`.`companies` " +
            "WHERE email=? OR name=?";
    private static final String IS_COMPANY_EXISTS_BY_NAME = "SELECT count(*) FROM `couponDB`.`companies` WHERE name=?";
    private static final String ADD_COMPANY = "INSERT INTO `couponDB`.`companies` (`name`,`email`,`password`) VALUES (?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE `couponDB`.`companies` set name=? ,email=? ,password=? WHERE id=?";
    private static final String DELETE_COMPANY_BY_ID = "DELETE FROM `couponDB`.`companies` WHERE id=?";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `couponDB`.`companies`";
    private static final String GET_ONE_COMPANY_BY_ID = "SELECT * FROM `couponDB`.`companies` WHERE id=?";
    private static final String GET_ID_BY_EMAIL_AND_PASSWORD = "SELECT `id` FROM `couponDB`.`companies` " +
            "WHERE email=? AND password=?";
    private static final String GET_ID_BY_NAME = "SELECT `id` FROM `couponDB`.`companies` WHERE name=?";
    private static final String GET_ALL_COUPON = "SELECT * FROM `couponDB`.`coupons`";
    private static final String GET_ALL_COMPANY_COUPONS = "SELECT * FROM `couponDB`.`coupons` WHERE company_id=?";

    Connection connection;

    /**
     * C`TOR get instance to ConnectionPool
     */
    public CompaniesDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //to check about close connection

    /**
     * method that check by email and password if the company exists in the database
     *
     * @param email variable
     * @param password variable
     * @return true if company exists false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS);
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
     * method that check by mail and email if the company exists in the database
     *
     * @param email variable
     * @param name variable
     * @return true if company exists false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean isCompanyExistsNameOrEmail(String name, String email) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS_BY_NAME_OR_EMAIL);
            statement.setString(1, email);
            statement.setString(2, name);
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
     * method that check by name if the company exists in the database
     *
     * @param name variable
     * @return true if company exists false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean isCompanyExistsByName(String name) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS_BY_NAME);
            statement.setString(1, name);
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
     * method that add company to the database
     *
     * @param company variable
     * @return true if company was added false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean addCompany(Company company) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());

        return DButils.runQueryGeneric(ADD_COMPANY, params);
    }

    /**
     * method that update exist company in the database it can update only name,email and password
     *
     * @param company variable
     * @return true if company was updated false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean updateCompany(Company company) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, company.getId());

        return DButils.runQueryGeneric(UPDATE_COMPANY, params);
    }

    /**
     * method that delete company by id
     *
     * @param companyID variable
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public void deleteCompany(int companyID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY_BY_ID);
            statement.setInt(1, companyID);
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * method that get a list of all the companies that exist in the database with the list of coupons
     *
     * @return companies list
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Company company = new Company(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("password"),
                        getCompanyCoupons(resultSet.getInt("id")));
                companies.add(company);
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return companies;
    }

    /**
     * method that get a list of all the coupons of the company that exist in the database
     *
     * @param companyID variable
     * @return companies coupons list
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public ArrayList<Coupon> getCompanyCoupons(int companyID) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement1 = connection.prepareStatement(GET_ALL_COMPANY_COUPONS);
            statement1.setInt(1, companyID);
            ResultSet resultSetCoupon = statement1.executeQuery();
            while (resultSetCoupon.next()) {
                Coupon coupon = new Coupon(resultSetCoupon.getInt("id"),
                        resultSetCoupon.getInt("company_id"),
                        Category.values()[resultSetCoupon.getInt("category_id") - 1],
                        resultSetCoupon.getString("title"),
                        resultSetCoupon.getString("description"),
                        resultSetCoupon.getDate("start_date"),
                        resultSetCoupon.getDate("end_date"),
                        resultSetCoupon.getInt("amount"),
                        resultSetCoupon.getDouble("price"),
                        resultSetCoupon.getString("image"));
                coupons.add(coupon);
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    /**
     * get company info by id
     *
     * @param companyID variable
     * @return company
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public Company getOneCompany(int companyID) throws SQLException {
        Company company = null;
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_ID);
            statement.setInt(1, companyID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                company = new Company(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("password"), getCompanyCoupons(companyID));
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return company;
    }

    /**
     * method that get company id if company exists by email and password
     *
     * @param email variable
     * @param password variable
     * @return company id
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public int getCompanyID(String email, String password) throws SQLException {
        int companyId = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ID_BY_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                companyId = resultSet.getInt("id");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companyId;
    }

    /**
     * method that get company id by name
     *
     * @param name variable
     * @return company id
     * @throws SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public int getCompanyID(String name) throws SQLException {
        int companyId = 0;
        try {
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Error: " + e.getMessage());
            }
            PreparedStatement statement = connection.prepareStatement(GET_ID_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                companyId = resultSet.getInt("id");
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companyId;
    }
}
