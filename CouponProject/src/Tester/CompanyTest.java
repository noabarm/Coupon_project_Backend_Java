package Tester;

import Beans.Coupon;
import Enums.Category;
import Enums.ClientType;
import Exceptions.AdministratorExceptions.CompanyNotFoundException;
import Exceptions.CompanyExceptions.CouponAlreadyRegisteredException;
import Exceptions.CompanyExceptions.CouponNotFoundException;
import Exceptions.LoginErrorException;
import Facades.CompanyFacade;
import Login.LoginManager;
import Utils.ColorPrint;

import java.sql.Date;
import java.util.Calendar;

public class CompanyTest {
    public static void runCompanyTest() {

//        System.out.println(ArtUtils.companyTest);
        System.out.println(ColorPrint.ANSI_GREEN + "COMPANY TEST" + ColorPrint.ANSI_RESET);


        // LOGIN
        CompanyFacade companyFacade = null;

        // Login - Failed

        System.out.println(ColorPrint.ANSI_BLUE + "Company try to login..." + ColorPrint.ANSI_RESET);

        try {
            companyFacade = (CompanyFacade) LoginManager.getInstance().login("noabar@gmail.com", "1234", ClientType.company);
        } catch (LoginErrorException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN Failed" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");


        // LOGIN - SUCCESS

        System.out.println(ColorPrint.ANSI_BLUE + "Company try to login..." + ColorPrint.ANSI_RESET);

        try {
            companyFacade = (CompanyFacade) LoginManager.getInstance().login("elit@gmail.com", "8890", ClientType.company);
        } catch (LoginErrorException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN SUCCESS" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //define coupons

        Coupon coupon1 = new Coupon(2, Category.electricity, "battery", "batteries AAA",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-09-3"), 500, 99.9, "image1");
        Coupon coupon2 = new Coupon(2, Category.food, "organic juice", "orange juice",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-09-1"), 40, 10.0, "image2");
        Coupon coupon3 = new Coupon(3, Category.food, "cola drinks", "coca cola drinks",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-06-10"), 220, 40.5, "image3");
        Coupon coupon4 = new Coupon(3, Category.vacation, "usa", "vacation in NY"
                , (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-07-15"), 10, 1500.0, "image4");
        Coupon coupon5 = new Coupon(4, Category.food, "chocolate", "dark chocolate",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-08-10"), 1000, 29.90, "image5");
        Coupon coupon6 = new Coupon(4, Category.restaurant, "elit factory", "visit elit factory",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-09-1"), 50, 230.0, "image6");
        Coupon coupon7 = new Coupon(2, Category.electricity, "clock battery", "batteries AAA",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-08-3"), 500, 99.9, "image7");
        Coupon coupon8 = new Coupon(2, Category.food, "lemon juice", "lemon juice",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-07-15"), 40, 10.0, "image8");
        Coupon coupon9 = new Coupon(3, Category.food, "sprite drinks", "coca cola drinks",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-05-10"), 220, 40.5, "image9");
        Coupon coupon10 = new Coupon(3, Category.vacation, "Italy", "vacation in Italy"
                , (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-07-15"), 10, 1500.0, "image10");
        Coupon coupon11 = new Coupon(4, Category.food, "milk chocolate", "white chocolate",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-08-10"), 1000, 29.90, "image11");
        Coupon coupon12 = new Coupon(4, Category.restaurant, "max factory", "visit elit factory",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-09-1"), 50, 230.0, "image12");
        Coupon coupon14 = new Coupon(4, Category.food, "coffee", "black coffee",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-09-1"), 50, 88.8, "image14");

        Coupon coupon19 = new Coupon(3, Category.restaurant, "Meat Ball", "Spagetti",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-04-1"), 15, 35.3, "image19");


        // Add Coupons
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add a coupons..." + ColorPrint.ANSI_RESET);
        try {
            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            companyFacade.addCoupon(coupon3);
            companyFacade.addCoupon(coupon4);
            companyFacade.addCoupon(coupon5);
            companyFacade.addCoupon(coupon6);
            companyFacade.addCoupon(coupon7);
            companyFacade.addCoupon(coupon8);
            companyFacade.addCoupon(coupon9);
            companyFacade.addCoupon(coupon10);
            companyFacade.addCoupon(coupon11);
            companyFacade.addCoupon(coupon12);
            companyFacade.addCoupon(coupon14);
            companyFacade.addCoupon(coupon19);
        } catch (CouponAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ColorPrint.ANSI_BLUE + "coupons were added successfully!\n" + ColorPrint.ANSI_RESET);
        System.out.println();

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "try to add coupon with the same name and the same company id...\n" + ColorPrint.ANSI_RESET);
        //Add Coupons - Failed - Name already exists in this company
        try {
            companyFacade.addCoupon(coupon1);
        } catch (CouponAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //Add Coupons - with same Name - different company
        System.out.println(ColorPrint.ANSI_BLUE + "try to add coupon with the same name and different company id...\n" + ColorPrint.ANSI_RESET);
        Coupon coupon13 = new Coupon(4, Category.food, "organic juice", "apple juice",
                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2021-08-1"), 40, 12.0, "image13");
        try {
            companyFacade.addCoupon(coupon13);
        } catch (CouponAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "coupons were added successfully!" + ColorPrint.ANSI_RESET);


        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //update coupon amount
        System.out.println(ColorPrint.ANSI_BLUE + "update the amount of the coupon to 0..." + ColorPrint.ANSI_RESET);
        coupon13.setAmount(0);
        coupon13.setId(14);
        try {
            companyFacade.updateCoupon(coupon13);
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "print all the coupon of elit company" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyFacade.getCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //try to update coupon id or company id
        System.out.println(ColorPrint.ANSI_BLUE + "try to update coupon id or company id.." + ColorPrint.ANSI_RESET);
        coupon13.setId(100);
        coupon13.setCompanyID(8);
        try {
            System.out.println(ColorPrint.ANSI_BLUE + "Print all the coupons: \n" + ColorPrint.ANSI_RESET + companyFacade.getCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");


        System.out.println(ColorPrint.ANSI_BLUE + "elit company has the following coupons:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyFacade.getCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        //delete coupon
        System.out.println(ColorPrint.ANSI_BLUE + "delete coupon..." + ColorPrint.ANSI_RESET);
        try {
            companyFacade.deleteCoupon(5);
        } catch (CouponNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Delete Successfully" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "elit company has the following coupons:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyFacade.getCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "print all elit coupons from food category:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyFacade.getCompanyCoupons(Category.food));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "print all elit coupons until max price 100:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyFacade.getCompanyCoupons(100.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println(ColorPrint.ANSI_BLUE + "print elit company details:" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(companyFacade.getCompanyDetails());
        } catch (CompanyNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }


}
