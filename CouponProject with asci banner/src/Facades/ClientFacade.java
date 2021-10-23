package Facades;

import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Dao.CompanyDAO;
import Dao.CouponsDAO;
import Dao.CustomersDAO;
import Exceptions.LoginErrorException;
/**
 * An abstract class describing a facade that will be returned when a
 * CouponSystem Client of any type logs in.
 *
 *
 */
public abstract class  ClientFacade {
    protected CompanyDAO companyDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    public ClientFacade() {
        this.companyDAO = new CompaniesDBDAO();
        this.couponsDAO = new CouponsDBDAO();
        this.customersDAO = new CustomersDBDAO();
    }

    /**
     *
     * @param email
     * @param password
     * @return
     * @throws LoginErrorException this exception will be thrown when admin try to login to the system with email and password
     */
    public abstract boolean login(String email, String password) throws LoginErrorException;
}




