package Facades;

import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Enums.Category;
import Exceptions.AdministratorExceptions.CompanyNotFoundException;
import Exceptions.AdministratorExceptions.CustomerNotFoundException;
import Exceptions.CompanyExceptions.CouponNotFoundException;
import Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import Exceptions.CustomerExceptions.CouponExpiredException;
import Exceptions.CustomerExceptions.CouponSoldOutException;
import Exceptions.LoginErrorException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * a class that incorporates all of the methods accessible to a registered customer
 */
public class CustomerFacade extends ClientFacade {
    /**
     * a constructor
     */
    private int customerID;

    public CustomerFacade() {
        super();
    }

    /**
     * @param customerID variable
     * a constructor
     */
    public CustomerFacade(int customerID) {
        super();
        this.customerID = customerID;

    }

    /**
     * login method to check if the email and password matches and authorize as Customer
     *
     * @param email variable
     * @param password variable
     * @return true if login succeed false otherwise
     * SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    @Override
    public boolean login(String email, String password) {
        try {
            boolean isCustomerExists = customersDAO.isCustomerExists(email, password);
            if (isCustomerExists) {
                this.customerID = customersDAO.getCustomerID(email, password);
            }
            return isCustomerExists;
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }
        return false;

    }

    /**
     * purchase a coupon for the logged customer
     *
     * @param coupon variable
     * @throws CouponSoldOutException          this exception will be thrown when customer try to purchase coupon that sold out
     * @throws CouponExpiredException          this exception will be thrown when customer try to purchase coupon that expired
     * @throws CouponAlreadyPurchasedException this exception will be thrown when customer try to purchase coupon that already purchase
     *  SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    public void purchaseCoupon(Coupon coupon) throws CouponSoldOutException, CouponExpiredException, CouponAlreadyPurchasedException {
        try {
            if (coupon.getEndDate().before(new Date(Calendar.getInstance().getTime().getTime())) ||
                    coupon.getAmount() == 0) {
                System.out.println("date of coupon expired or there no coupon left");
                return;
            } else if (couponsDAO.isCustomerPurchaseCoupon(this.customerID, coupon.getId())) {
                System.out.println("you already purchase this coupon");
                return;
            }

            couponsDAO.addCouponPurchase(this.customerID, coupon.getId());
            coupon.setAmount(coupon.getAmount() - 1);
            couponsDAO.updateCoupon(coupon);

        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

    }

    /**
     * returns all purchased coupons of the logged customer
     *
     * @return coupons list
     * SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */

    public ArrayList<Coupon> getCustomerCoupons() {
        ArrayList<Coupon> customerCoupons = new ArrayList<>();

        try {
            ArrayList<Integer> couponsID = couponsDAO.getCouponsIdByCustomerId(this.customerID);
            for (int couponId : couponsID) {
                customerCoupons.add(couponsDAO.getOneCoupon(couponId));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }

        return customerCoupons;
    }

    /**
     * returns all purchased coupons of the logged customer by category
     *
     * @param category variable
     * @return coupons list
     * @throws CouponNotFoundException this exception will be thrown when the coupon is not exists in the system
     */
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws CouponNotFoundException {
        ArrayList<Coupon> customerCoupons = getCustomerCoupons();
        ArrayList<Coupon> customerCouponsByCategory = new ArrayList<>();
        for (Coupon item : customerCoupons) {
            if (item.getCategory().equals(category)) {
                customerCouponsByCategory.add(item);
            }
        }

        return customerCouponsByCategory;
    }

    /**
     * returns all purchased coupons of the logged customer below a certain price.
     *
     * @param maxPrice variable
     * @return coupons list
     * @throws CouponNotFoundException this exception will be thrown when the coupon is not exists in the system
     * SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponNotFoundException {
        ArrayList<Coupon> customerCoupons = getCustomerCoupons();
        ArrayList<Coupon> customerCouponsByPrice = new ArrayList<>();
        for (Coupon item : customerCoupons) {
            if (item.getPrice() <= maxPrice) {
                customerCouponsByPrice.add(item);
            }
        }
        return customerCouponsByPrice;

    }

    /**
     * Get all the info of the customer
     *
     * @return customer details
     * @throws CustomerNotFoundException this exception will be thrown when the customer is not exists in the system
     * SQLException this exception provides information on a database access error or other errors and provides several kinds of information.
     */
    public Customer getCustomerDetails() throws CustomerNotFoundException {
        try {
            return customersDAO.getOneCustomer(this.customerID);
        } catch (SQLException e) {
            System.out.println("SQL Error:\n=============\n" + e.getMessage() + "\n");
        }
        return null;
    }
}
