package Tester;

import Beans.Company;
import Beans.Customer;
import Enums.ClientType;
import Exceptions.AdministratorExceptions.*;
import Exceptions.LoginErrorException;
import Facades.AdminFacade;
import Login.LoginManager;
import Utils.ArtUtils;
import Utils.ColorPrint;

import java.sql.SQLException;

public class AdminTest {


    public static void runAdminTest() throws SQLException {
        System.out.println(ArtUtils.adminTest);


        // Dummy Objects
        Company dummyCompany = new Company("Company Inc.", "companyinc@email.com", "CompanyPassword123");
//        Company dummyCompany = new Company(1, "Company Inc.", "companyinc@email.com", "CompanyPassword123", new Coupon(1, Category.electricity, "Chashaml 4u", "Toster",
//                (new Date(Calendar.getInstance().getTime().getTime())), Date.valueOf("2022-04-1"), 23, 25.1, "image80"));
        Company company1 = new Company(2, "super Pharm", "superfarm@gmail.com", "3455");
        Company company2 = new Company(3, "cocaCola", "cocacola@gmail.com", "5678");
        Company company3 = new Company(4, "elit", "elit@gmail.com", "8890");

        Customer dummyCustomer = new Customer(1, "Maor", "Perez", "maor1812@email.com", "maorPass1");
        Customer customer1 = new Customer(2, "noa", "barmatz", "noabar@gmail.com", "noa123");
        Customer customer2 = new Customer(3, "elad", "barmatz", "eladbar@gmail.com", "elad123");
        Customer customer3 = new Customer(4, "tamara", "barmatz", "tamara@gmail.com", "tamara123");


        // LOGIN
        AdminFacade adminFacade = null;

// Login - Failed
        System.out.println(ColorPrint.ANSI_BLUE + "Administrator try to login..." + ColorPrint.ANSI_RESET);
        try {
            adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin111", ClientType.administrator);
        } catch (LoginErrorException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN Failed" + ColorPrint.ANSI_RESET);


        System.out.println("----------------------------------------------------------------------------------------------------------------");


// LOGIN - SUCCESS
        System.out.println(ColorPrint.ANSI_BLUE + "Administrator try to login..." + ColorPrint.ANSI_RESET);
        try {
            adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.administrator);
        } catch (LoginErrorException err) {
            System.out.println(err.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "LOGIN SUCCESS" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Add Company
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add a company..." + ColorPrint.ANSI_RESET);
        try {
            adminFacade.addCompany(dummyCompany);
            adminFacade.addCompany(company1);
            adminFacade.addCompany(company2);
            adminFacade.addCompany(company3);
        } catch (CompanyAlreadyExistException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf(ColorPrint.ANSI_BLUE + "Company: '%s' and '%s' and '%s' and '%s' was added successfully!\n" + ColorPrint.ANSI_RESET,
                dummyCompany.getName(), company1.getName(), company2.getName(), company3.getName());


        System.out.println(adminFacade.getAllCompanies());

        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Add Company - Failed - Name already exists
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add company with name that already exists " + ColorPrint.ANSI_RESET);
        try {
            adminFacade.addCompany(dummyCompany);
        } catch (CompanyAlreadyExistException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

// Add Company - Failed - Email already exists
        System.out.printf(ColorPrint.ANSI_BLUE + "Preparing to add company: %s with email that already exist \n" + ColorPrint.ANSI_RESET
                , dummyCompany.getName());
        try {
            dummyCompany.setName("Temp Company Name");
            adminFacade.addCompany(dummyCompany);
        } catch (CompanyAlreadyExistException e) {
            System.out.println(e.getMessage());
            dummyCompany.setName("Company Inc.");
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Add Company - Failed - Email already exists" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.printf(ColorPrint.ANSI_BLUE + "Preparing to update ID to company: %s - \n" + ColorPrint.ANSI_RESET, dummyCompany.getName());
        // Update Company - Failed - Cannot update Company Id
        try {
            dummyCompany.setId(10);
            adminFacade.updateCompany(dummyCompany);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Failed to update the ID" + ColorPrint.ANSI_RESET);


        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Update Company - Failed - Cannot update Company Name
        System.out.printf(ColorPrint.ANSI_BLUE + "Preparing to update Company Name to company: %s  \n" + ColorPrint.ANSI_RESET
                , dummyCompany.getName());

        try {
            dummyCompany.setName("Temp Company Name");
            adminFacade.updateCompany(dummyCompany);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dummyCompany.setName("Company Inc.");
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Failed to update the Name" + ColorPrint.ANSI_RESET);


        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Get All Companies
        System.out.println(ColorPrint.ANSI_BLUE + "Printing all companies..." + ColorPrint.ANSI_RESET);
        try {
            System.out.println(adminFacade.getAllCompanies());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


// Get One Company
        System.out.println(ColorPrint.ANSI_BLUE + "Printing one company..." + ColorPrint.ANSI_RESET);
        try {
            System.out.println(adminFacade.getOneCompany(1));
        } catch (CompanyNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Delete Company
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to delete a company: " + ColorPrint.ANSI_RESET);
        try {
            adminFacade.deleteCompany(1);
        } catch (CompanyNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Company Inc. has been deleted successfully!" + ColorPrint.ANSI_RESET);

        System.out.println(ColorPrint.ANSI_BLUE + "Printing all companies...\n " + ColorPrint.ANSI_RESET + adminFacade.getAllCompanies());


        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Add Customer
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to add a customers..." + ColorPrint.ANSI_RESET);
        try {
            adminFacade.addCustomer(dummyCustomer);
            adminFacade.addCustomer(customer1);
            adminFacade.addCustomer(customer2);
            adminFacade.addCustomer(customer3);
        } catch (CustomerAlreadyExistException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ColorPrint.ANSI_BLUE + "Customers was added successfully" + ColorPrint.ANSI_RESET);

        System.out.println(ColorPrint.ANSI_BLUE + "Printing all customers...\n" + ColorPrint.ANSI_RESET + adminFacade.getAllCustomers());

        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Add Customer - Failed - Email already exists
        System.out.printf(ColorPrint.ANSI_BLUE + "Preparing to add a customer: '%s %s' \n" + ColorPrint.ANSI_RESET
                , dummyCustomer.getFirstName(), dummyCustomer.getLastName());

        try {
            adminFacade.addCustomer(dummyCustomer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Update Customer

        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to update a customer...\n" + ColorPrint.ANSI_RESET);

        dummyCustomer.setFirstName("Avri");
        dummyCustomer.setLastName("Gilad");
        dummyCustomer.setEmail("perez@walla.com");

        dummyCustomer.setPassword("MarkusPassword123123");
        try {
            adminFacade.updateCustomer(dummyCustomer);
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf(ColorPrint.ANSI_BLUE + "Customer '%s %s' has been updated successfully!\n" + ColorPrint.ANSI_RESET, dummyCustomer.getFirstName(), dummyCustomer.getLastName());

        System.out.println(ColorPrint.ANSI_BLUE + "Printing all customers...\n" + ColorPrint.ANSI_RESET + adminFacade.getAllCustomers());


        System.out.println("----------------------------------------------------------------------------------------------------------------");


// Update Customer - Failed - Cannot update customer id
        System.out.println(ColorPrint.ANSI_BLUE + "Preparing to update the ID of customer " + ColorPrint.ANSI_RESET);

        try {
            dummyCustomer.setId(4);
            adminFacade.updateCustomer(dummyCustomer);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        System.out.println(ColorPrint.ANSI_BLUE + "Update Failed - can`t update ID" + ColorPrint.ANSI_RESET);

        System.out.println("----------------------------------------------------------------------------------------------------------------");


        // Get All Customers
        System.out.println(ColorPrint.ANSI_BLUE + "Printing all customers...\n" + ColorPrint.ANSI_RESET);

        System.out.println(adminFacade.getAllCustomers());


        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Get One Customer
        System.out.println(ColorPrint.ANSI_BLUE + "Printing one customer...\n" + ColorPrint.ANSI_RESET);
        try {
            System.out.println(adminFacade.getOneCustomer(1));
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        // Delete Customer
        System.out.printf(ColorPrint.ANSI_BLUE + "Preparing to delete the customer: '%s %s' ...\n" + ColorPrint.ANSI_RESET
                , dummyCustomer.getFirstName(), dummyCustomer.getLastName());
        try {
            adminFacade.deleteCustomer(1);
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ColorPrint.ANSI_BLUE + "Customer has been deleted successfully!" + ColorPrint.ANSI_RESET);
        System.out.println(ColorPrint.ANSI_BLUE + "Printing all customers...\n" + ColorPrint.ANSI_RESET);

        System.out.println(adminFacade.getAllCustomers());

    }

}
