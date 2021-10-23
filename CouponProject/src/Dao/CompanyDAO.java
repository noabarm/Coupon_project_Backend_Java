package Dao;

import Beans.Company;
import Beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompanyDAO {

    boolean isCompanyExists(String email,String password) throws SQLException;

    boolean isCompanyExistsNameOrEmail(String name,String email) throws SQLException;

    boolean isCompanyExistsByName(String name) throws SQLException;

    boolean addCompany(Company company) throws SQLException;

    boolean updateCompany(Company company) throws SQLException;

    void deleteCompany(int companyID) throws SQLException;

    ArrayList<Company> getAllCompanies() throws SQLException;

    ArrayList<Coupon> getCompanyCoupons(int companyID) throws SQLException;

    Company getOneCompany(int companyID) throws SQLException;

    int getCompanyID(String email,String password) throws SQLException;

    int getCompanyID(String name) throws SQLException;


}
