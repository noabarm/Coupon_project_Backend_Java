package Tester;

import Beans.Coupon;
import SQL.ConnectionPool;
import Threads.CouponExpirationDailyJob;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void testAll() {

//        CouponExpirationDailyJob dailyJob;
//        dailyJob = new CouponExpirationDailyJob();



        CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();

        try {
            Thread dailyJobThread = new Thread((dailyJob));


            DatabaseTest.createDataBase();

            dailyJobThread.start();

            AdminTest.runAdminTest();

            CompanyTest.runCompanyTest();

            CustomerTest.runCustomerTest();
 //           dailyJobThread.start();

//            new Thread(dailyJob).start();
            Thread.sleep(5000);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
           dailyJob.stop();
           System.out.println("after stopping thread main program");
        }
        try {
            try {
                ConnectionPool.getInstance().closeAllConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        System.out.println("Ending main program");
    }

}


