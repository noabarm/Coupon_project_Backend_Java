package Facades;

import Beans.Company;
import Beans.Coupon;
import Enums.Category;
import Exceptions.AdministratorExceptions.CompanyNotFoundException;
import Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import Exceptions.CompanyExceptions.CouponNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * a class facade that incorporates all of the methods accessible to a registered company
 */
public class CompanyFacade extends ClientFacade {
    private int companyID;

    /**
     * a constructor
     */
    public CompanyFacade() {
        super();
    }

    /**
     * a constructor
     *
     * @param companyID
     */
    public CompanyFacade(int companyID) {
        super();
        this.companyID = companyID;

    }

    /**
     * login method to check if the email and password matches and authorize as Company
     *
     * @param email
     * @param password
     * @return true if login succeed false otherwise
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    @Override
    public boolean login(String email, String password) {

        try {
            boolean isCompanyExists = companyDAO.isCompanyExists(email, password);
            if (isCompanyExists) {
                this.companyID = companyDAO.getCompanyID(email, password);
            }
            return isCompanyExists;
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }
        return false;
    }

    /**
     * create a new coupon of the logged company into the database
     *
     * @param coupon
     * @throws CouponAlreadyRegisteredException this exception will be thrown when coupon is already exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void addCoupon(Coupon coupon) throws CouponAlreadyRegisteredException {

        try {
            if (couponsDAO.isCouponExistsTitleAndCompId(coupon.getTitle(), coupon.getCompanyID())) {
                throw new CouponAlreadyRegisteredException("this coupon is already exists");
            }
            couponsDAO.addCoupon(coupon);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }
    }

    /**
     * Updates a coupon. Changes the data of the coupon on the database, to the coupon received in the method.
     *
     * @param coupon
     * @throws CouponNotFoundException this exception will be thrown when coupon is not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void updateCoupon(Coupon coupon) throws CouponNotFoundException {
        try {
            Coupon dbCoupon = couponsDAO.getOneCoupon(coupon.getId());
            dbCoupon.setCategory(coupon.getCategory());
            dbCoupon.setTitle(coupon.getTitle());
            dbCoupon.setDescription(coupon.getDescription());
            dbCoupon.setStartDate(coupon.getStartDate());
            dbCoupon.setEndDate(coupon.getEndDate());
            dbCoupon.setAmount(coupon.getAmount());
            dbCoupon.setPrice(coupon.getPrice());
            dbCoupon.setImage(coupon.getImage());

            couponsDAO.updateCoupon(dbCoupon);

        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * Deletes A Coupon from the database. Both from all clients records and from the coupon list.
     *
     * @param couponID
     * @throws CouponNotFoundException this exception will be thrown when coupon is not exists in the system
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public void deleteCoupon(int couponID) throws CouponNotFoundException {

        try {
            couponsDAO.deleteCouponPurchaseByCouponID(couponID);
            couponsDAO.deleteCoupon(couponID);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * Get all the coupons of the company from the Database.
     *
     * @return ArrayList of Coupons.
     * @throws SQLException this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public ArrayList<Coupon> getCompanyCoupons() {
        ArrayList<Coupon> companyCoupons = null;
        try {
            companyCoupons = companyDAO.getCompanyCoupons(this.companyID);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

        return companyCoupons;
    }

    /**
     * Get all the coupons of the company from the Database by category.
     *
     * @param category
     * @return ArrayList of Coupons.
     */
    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        ArrayList<Coupon> companyCoupons = getCompanyCoupons();
        ArrayList<Coupon> companyCouponsByCategory = new ArrayList<>();
        for (Coupon item : companyCoupons) {
            if (item.getCategory().equals(category)) {
                companyCouponsByCategory.add(item);
            }
        }
        return companyCouponsByCategory;
    }

    /**
     * Get all the coupons of the company from the Database below a certain price.
     *
     * @param maxPrice
     * @return ArrayList of Coupons.
     */
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        ArrayList<Coupon> companyCoupons = getCompanyCoupons();
        ArrayList<Coupon> companyCouponsByPrice = new ArrayList<>();
        for (Coupon item : companyCoupons) {
            if (item.getPrice() <= maxPrice) {
                companyCouponsByPrice.add(item);
            }
        }
        return companyCouponsByPrice;
    }

    /**
     * Get all the info of the company
     *
     * @return company
     * @throws CompanyNotFoundException this exception will be thrown when the company not exists in the system
     * @throws SQLException             this exception provides information on a database access error or other errors
     * and provides several kinds of information.
     */
    public Company getCompanyDetails() throws CompanyNotFoundException {

        try {
            return companyDAO.getOneCompany(this.companyID);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }
        return null;
    }
}
