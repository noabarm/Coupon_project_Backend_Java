package SQL;

import Enums.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * A Class that create the model fo the database
 */
public class DataBaseManager {

    /**
     * connection details to DB
     */
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "12345678";

    /**
     * SQL statements create & drop database
     */
    private static final String CREATE_DB = "CREATE SCHEMA if not exists couponDB";
    private static final String DROP_DB = "DROP SCHEMA IF EXISTS couponDB";

    /**
     * SQL statements create tables
     */
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE if not exists `couponDB`.`companies` " +
            "(`id` INT NOT NULL AUTO_INCREMENT," +
            "`name` VARCHAR(50) NOT NULL," +
            "`email` VARCHAR(50) NOT NULL," +
            "`password` VARCHAR(50) NOT NULL," +
            "PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE if not exists `couponDB`.`customers` " +
            "(`id` INT NOT NULL AUTO_INCREMENT," +
            "`first_name` VARCHAR(50) NOT NULL," +
            "`last_name` VARCHAR(50) NOT NULL," +
            "`email` VARCHAR(50) NOT NULL," +
            "`password` VARCHAR(50) NOT NULL," +
            "PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE if not exists `couponDB`.`categories` " +
            "(`id` INT NOT NULL AUTO_INCREMENT," +
            "`name` VARCHAR(50) NOT NULL," +
            "PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE if not exists `couponDB`.`coupons` " +
            "(`id` INT NOT NULL AUTO_INCREMENT," +
            "`company_id` INT NOT NULL," +
            "`category_id` INT NOT NULL," +
            "`title` VARCHAR(50) NOT NULL," +
            "`description` VARCHAR(150) NOT NULL," +
            "`start_date` DATE NOT NULL," +
            "`end_date` DATE NOT NULL," +
            "`amount` INT NOT NULL," +
            "`price` DOUBLE NOT NULL," +
            "`image` VARCHAR(50) NOT NULL," +
            "PRIMARY KEY (`id`)," +
            "FOREIGN KEY (`company_id`) REFERENCES `couponDB`.`companies` (`id`)," +
            "FOREIGN KEY (`category_id`) REFERENCES `couponDB`.`categories` (`id`));";

    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE if not exists `couponDB`.`customers_vs_coupons` " +
            "(`customer_id` INT NOT NULL," +
            "`coupon_id` INT NOT NULL," +
            "CONSTRAINT PK_CUSTOMERS_VS_COUPONS PRIMARY KEY (`customer_id`,`coupon_id`)," +
            "INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "FOREIGN KEY (`customer_id`) REFERENCES `couponDB`.`customers` (`id`) ON DELETE CASCADE," +
            "FOREIGN KEY (`coupon_id`) REFERENCES `couponDB`.`coupons` (`id`) ON DELETE CASCADE);";


    /**
     * SQL statement initialize categories table
     */
    private static final String ADD_CATEGORIES = "INSERT INTO `couponDB`.`categories` (`name`) VALUES (?)";

    /**
     * create database
     */
    public static void createDataBase(){
        try {
            DButils.runQuery(CREATE_DB);
        } catch (SQLException e) {
            System.out.println("Error in sql: "+ e.getMessage());
        }
    }

    /**
     * delete database
     */
    public static void dropDataBase(){
        try {
            DButils.runQuery(DROP_DB);
        } catch (SQLException e) {
            System.out.println("Error in sql: "+ e.getMessage());;
        }

    }

    /**
     * create tables
     */
    public static void createTables(){
        try {
            DButils.runQuery(CREATE_TABLE_COMPANIES);
            DButils.runQuery(CREATE_TABLE_CUSTOMERS);
            DButils.runQuery(CREATE_TABLE_CATEGORIES);
            DButils.runQuery(CREATE_TABLE_COUPONS);
            DButils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        } catch (SQLException e) {
            System.out.println("Error in sql: "+ e.getMessage());;
        }
    }

    /**
     * initialize categories table
     * @throws SQLException
     */
    public static void fillCategoriesTable() throws SQLException {
        Connection connection = null;
        for(int index = 0; index < Category.values().length ; index +=1){
            try {
                connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(ADD_CATEGORIES);
                statement.setString(1, String.valueOf(Category.values()[index]));
                statement.execute();
            } catch (InterruptedException | SQLException e) {
                System.out.println("Error in sql: "+ e.getMessage());
            } finally {
                ConnectionPool.getInstance().returnConnection(connection);
            }
        }
    }

}
