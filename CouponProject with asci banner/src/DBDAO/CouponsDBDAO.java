package DBDAO;

import Beans.Coupon;
import Dao.CouponsDAO;
import Enums.Category;
import SQL.ConnectionPool;
import SQL.DButils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A Class that handle coupons crud operations, this class create and execute SQL statements
 */
public class CouponsDBDAO implements CouponsDAO {
    /**
     * SQL crud statement
     */
    private static final String IS_COUPON_EXISTS = "SELECT count(*) FROM `couponDB`.`coupons` " +
            "WHERE title=? AND company_id=?";
    private static final String ADD_COUPON = "INSERT INTO `couponDB`.`coupons` (`company_id`,`category_id`,`title`," +
            "`description`,`start_date`,`end_date`,`amount`,`price`,`image`) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON = "UPDATE `couponDB`.`coupons` set company_id=?, category_id=?," +
            " title=?, description=? ,start_date=? ,end_date=?, amount=? ,price=? ,image=? WHERE id=?";
    private static final String DELETE_COUPON_BY_ID = "DELETE FROM `couponDB`.`coupons` WHERE id=?";
    private static final String GET_ALL_COUPON = "SELECT * FROM `couponDB`.`coupons`";
    private static final String GET_ONE_COUPON_BY_ID = "SELECT * FROM `couponDB`.`coupons` WHERE id=?";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `couponDB`.`customers_vs_coupons` " +
            "(`customer_id`,`coupon_id`) VALUES (?,?)";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `couponDB`.`customers_vs_coupons` " +
            "WHERE customer_id=? AND coupon_id=?";
    private static final String DELETE_COUPON_PURCHASE_BY_COUPON_ID = "DELETE FROM `couponDB`.`customers_vs_coupons` " +
            "WHERE coupon_id=?";
    private static final String DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID = "DELETE FROM `couponDB`.`customers_vs_coupons` " +
            "WHERE customer_id=?";
    private static final String IS_CUSTOMER_PURCHASE_COUPON = "SELECT count(*) FROM `couponDB`.`customers_vs_coupons` " +
            "WHERE customer_id=? AND coupon_id=?";
    private static final String GET_ALL_COUPON_ID_BY_CUSTOMER = "SELECT * FROM `couponDB`.`customers_vs_coupons` " +
            "WHERE customer_id=?";
    private static final String DELETE_COUPON_EXPIRED_DATE = "DELETE FROM `couponDB`.`coupons` WHERE end_date < ?";

    Connection connection;

    /**
     * C`TOR get instance to ConnectionPool
     */
    public CouponsDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    /**
     * method that check if coupon exist by title and id from the database
     *
     * @param title
     * @param companyID
     * @return true if coupon was exist false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean isCouponExistsTitleAndCompId(String title, int companyID) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_EXISTS);
            statement.setString(1, title);
            statement.setInt(2, companyID);
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
            System.out.println("error: could not get the record counts");
            return false;
        }
    }

    /**
     * method that add coupon to the database
     *
     * @param coupon
     * @return true if coupon was added false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean addCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, coupon.getCompanyID());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());

        return DButils.runQueryGeneric(ADD_COUPON, params);

    }

    /**
     * method that update exist coupon in the database
     *
     * @param coupon
     * @return true if coupon was updated false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean updateCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, coupon.getCompanyID());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        params.put(10, coupon.getId());

        return DButils.runQueryGeneric(UPDATE_COUPON, params);

    }

    /**
     * method that delete coupon by id
     *
     * @param couponID
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public void deleteCoupon(int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_BY_ID);
            statement.setInt(1, couponID);
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * method that get a list of all the coupons that exist in the database
     *
     * @return list of coupons
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPON);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt("id"), resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id") - 1], resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"), resultSet.getInt("amount"), resultSet.getDouble("price"),
                        resultSet.getString("image"));
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
     * get coupon by id
     *
     * @param couponID
     * @return coupon
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
        Coupon coupon = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON_BY_ID);
            statement.setInt(1, couponID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(resultSet.getInt("id"), resultSet.getInt("company_id"),
                        Category.values()[resultSet.getInt("category_id") - 1], resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"), resultSet.getInt("amount"), resultSet.getDouble("price"),
                        resultSet.getString("image"));
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }

    /**
     * method that add the purchase of coupon to the table customer vs coupons in database
     *
     * @param customerID
     * @param couponID
     * @return true if coupon was added to table false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean addCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(ADD_COUPON_PURCHASE);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * method that delete one purchase of coupon from the table customer vs coupons in database
     *
     * @param customerID
     * @param couponID
     * @return true if record deleted false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean deleteCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_PURCHASE);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * method that delete the purchases of coupons by coupon id
     *
     * @param couponID
     * @return true if records deleted false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean deleteCouponPurchaseByCouponID(int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_PURCHASE_BY_COUPON_ID);
            statement.setInt(1, couponID);
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;

    }

    /**
     * method that delete the purchases of coupons by customer id
     *
     * @param customerID
     * @return true if records deleted false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean deleteCouponPurchaseByCustomerID(int customerID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID);
            statement.setInt(1, customerID);
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * method that check by customer id and coupon id if purchase exists in the database
     *
     * @param customerID
     * @param couponID
     * @return true if purchase exists false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public boolean isCustomerPurchaseCoupon(int customerID, int couponID) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_PURCHASE_COUPON);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
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
     * method that get customer coupons list
     *
     * @param customerID
     * @return coupons list
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public ArrayList<Integer> getCouponsIdByCustomerId(int customerID) throws SQLException {
        ArrayList<Integer> couponsId = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPON_ID_BY_CUSTOMER);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int couponID = resultSet.getInt("coupon_id");
                couponsId.add(couponID);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return couponsId;
    }

    /**
     * method that run every 24 hours and delete expired coupons
     * @param endDate
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public void deleteCouponExpiredDate(Date endDate) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_EXPIRED_DATE);
            statement.setDate(1, endDate);
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Error: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }


}
