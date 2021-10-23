package Dao;

import Beans.Coupon;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {

    boolean isCouponExistsTitleAndCompId(String title,int companyID) throws SQLException;

    boolean addCoupon(Coupon coupon) throws SQLException;

    boolean updateCoupon(Coupon coupon) throws SQLException;

    void deleteCoupon(int couponID) throws SQLException;

    ArrayList<Coupon> getAllCoupons() throws SQLException;

    Coupon getOneCoupon(int couponID) throws SQLException;

    boolean addCouponPurchase(int customerID, int couponID) throws SQLException;

    boolean deleteCouponPurchase(int customerID, int couponID) throws SQLException;

    boolean deleteCouponPurchaseByCouponID(int couponID) throws SQLException;

    boolean deleteCouponPurchaseByCustomerID(int customerID) throws SQLException;

    boolean isCustomerPurchaseCoupon(int customerID, int couponID) throws SQLException;

    ArrayList<Integer> getCouponsIdByCustomerId(int customerID) throws SQLException;

    void deleteCouponExpiredDate(Date endDate) throws SQLException;

}
