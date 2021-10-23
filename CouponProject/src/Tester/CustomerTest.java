package Tester;

import Beans.Coupon;
import DBDAO.CouponsDBDAO;
import Enums.Category;
import Enums.ClientType;
import Exceptions.AdministratorExceptions.CustomerNotFoundException;
import Exceptions.CompanyExceptions.CouponNotFoundException;
import Exceptions.CustomerExceptions.CouponAlreadyPurchasedException;
import Exceptions.CustomerExceptions.CouponExpiredException;
import Exceptions.CustomerExceptions.CouponSoldOutException;
import Exceptions.LoginErrorException;
import Facades.CustomerFacade;
import Login.LoginManager;
import Utils.ColorPrint;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomerTest {
    public static void runCustomerTest() throws LoginErrorException {

//        System.out.println(ArtUtils.customerTest);
        System.out.println(ColorPrint.ANSI_GREEN + "CUSTOMER TEST" + ColorPrint.ANSI_RESET);


        // LOGIN
        CustomerFacade customerFacade = null;

        System.out.println(ColorPrint.ANSI_BLUE + "Customer try to login..." + ColorPrint.ANSI_RESET);
        // Login - Failed
        try {
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("customerWrong@customer.com",
                    "customer111", ClientType.customer);
        } catch (LoginErrorException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN Failed" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "Customer try to login..." + ColorPrint.ANSI_RESET);
        // LOGIN - SUCCESS

        try {
            customerFacade = (CustomerFacade) LoginManager.getInstance().login("perez@walla.com", "MarkusPassword123123", ClientType.customer);
        } catch (LoginErrorException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN SUCCESS" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - SUCCESS

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon..." + ColorPrint.ANSI_RESET);

        CouponsDBDAO cdb = new CouponsDBDAO();
        ArrayList<Coupon> allCouponInDB = null;
        try {
            allCouponInDB = cdb.getAllCoupons();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        System.out.println("Print all the coupon List: ");
//        System.out.println(allCouponInDB);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        try {
            customerFacade.purchaseCoupon(allCouponInDB.get(0));
            customerFacade.purchaseCoupon(allCouponInDB.get(4));
            customerFacade.purchaseCoupon(allCouponInDB.get(6));
            customerFacade.purchaseCoupon(allCouponInDB.get(8));
            customerFacade.purchaseCoupon(allCouponInDB.get(10));
        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ColorPrint.ANSI_BLUE + "Print all the  coupons that purchased: \n" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(customerFacade.getCustomerCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");


        // COUPON PURCHASE - Failed - Cant Purchase coupon more than once
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon that the customer already purchase..." + ColorPrint.ANSI_RESET);
        try {
            customerFacade.purchaseCoupon(allCouponInDB.get(4));
        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Purchase Failed" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - Failed - Coupon is expired
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon that the end date was past..." + ColorPrint.ANSI_RESET);
        Coupon oldCoupon = new Coupon(2, Category.electricity, "battery", "batteries AAA",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-06-3"), 500, 99.9, "image99");
        try {
            customerFacade.purchaseCoupon(oldCoupon);
        } catch (CouponSoldOutException | CouponAlreadyPurchasedException | CouponExpiredException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // COUPON PURCHASE - Failed - The amount of the Coupon is empty

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to purchase a coupon with empty amount..." + ColorPrint.ANSI_RESET);
        try {
            customerFacade.purchaseCoupon(allCouponInDB.get(12));
        } catch (CouponSoldOutException | CouponExpiredException | CouponAlreadyPurchasedException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // GET ALL PURCHASE COUPONS
        System.out.println(ColorPrint.ANSI_BLUE + "Print all purchase Coupons: " + ColorPrint.ANSI_RESET);
        try {
            System.out.println(customerFacade.getCustomerCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // GET ALL PURCHASE COUPONS FROM SPECIFIC CATEGORY
        System.out.println(ColorPrint.ANSI_BLUE + "Print all coupon of food category... " + ColorPrint.ANSI_RESET);
        try {
            System.out.println(customerFacade.getCustomerCoupons(Category.food));
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // GET ALL PURCHASE COUPONS UNTIL MAX PRICE
        System.out.println(ColorPrint.ANSI_BLUE + "Print all coupon until max price 200 " + ColorPrint.ANSI_RESET);
        try {
            System.out.println(customerFacade.getCustomerCoupons(200.0));
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //print customer details
        System.out.println(ColorPrint.ANSI_BLUE + "Print customer details..." + ColorPrint.ANSI_RESET);
        try {
            System.out.println(customerFacade.getCustomerDetails());
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }


//        System.out.println(ArtUtils.finished);
        System.out.println(ColorPrint.ANSI_YELLOW + "FINISHED CUSTOMER TEST" + ColorPrint.ANSI_RESET);


    }
}
