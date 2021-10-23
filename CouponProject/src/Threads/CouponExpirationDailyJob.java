package Threads;

import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

/**
 * A Class that run and delete coupons their date were expired
 */
public class CouponExpirationDailyJob implements Runnable{


    private CouponsDBDAO couponsDAO = new CouponsDBDAO();
    private boolean quit = false;


    /**
     * default C`TOR that stat the daily job
     */
    public CouponExpirationDailyJob() {

    }

    /**
     * method that run and delete coupons their date were expired
     */
    @Override
    public void run(){
        while(!quit) {
            try {
                couponsDAO.deleteCouponExpiredDate(new Date(Calendar.getInstance().getTime().getTime()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
              //  System.out.println("thread go to sleep");
                Thread.sleep(500);
                // to run every 24 hours
                //Thread.sleep(1000*60*60*24)
            } catch (InterruptedException e) {
                System.out.println("Interrupted Error: " + e.getMessage());
            }
        }

        System.out.println("after while");
    }

    /**
     * method that stop the daily job
     */
    public void stop(){

        System.out.println("stopping thread");
        quit = true;
    }

}