package Tester;

import SQL.DataBaseManager;
import Utils.ArtUtils;
import Utils.ColorPrint;

import java.sql.SQLException;

public class DatabaseTest {

    public static void createDataBase() {
        System.out.println(ArtUtils.createDataBase);


        System.out.println(ColorPrint.ANSI_BLUE + "delete old database..." + ColorPrint.ANSI_RESET);
        DataBaseManager.dropDataBase();
        System.out.println(ColorPrint.ANSI_BLUE + "creat database and tables...." + ColorPrint.ANSI_RESET);
        DataBaseManager.createDataBase();
        DataBaseManager.createTables();
        try {
            DataBaseManager.fillCategoriesTable();
            System.out.println(ColorPrint.ANSI_BLUE + "category table has created successfully" + ColorPrint.ANSI_RESET);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(ColorPrint.ANSI_BLUE + "error in creating category table" + ColorPrint.ANSI_RESET);
        }
        System.out.println(ColorPrint.ANSI_BLUE + "all database and tables were created" + ColorPrint.ANSI_RESET);

    }
}
