package Login;

import Enums.ClientType;
import Exceptions.LoginErrorException;
import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

import java.sql.SQLException;

/**
 * A Class that check login and return the correct facade
 */
public class LoginManager {
    private static LoginManager instance = null;

    /**
     * private C`TOR that create login manager
     */
    private LoginManager() {
    }

    /**
     *  method that create singleton instance of login manager
     * @return singleton instance
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }


    /**
     * method login that check by email,password and client type if the user can enter to the system
     * @param email variable
     * @param password variable
     * @param clientType variable
     * @return the correct user facade
     * @throws LoginErrorException this exception will be thrown when the client failed to login into the system
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws LoginErrorException {
        String failedLoginMsg = "Login failed. Please verify email & password.";
        String loginSuccess = "Logged in as: ";

        switch (clientType) {
            case administrator:
                ClientFacade adminFacade = new AdminFacade();
                if (!adminFacade.login(email, password)) {
                    throw new LoginErrorException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return adminFacade;
            case company:
                ClientFacade companyFacade = new CompanyFacade();
                if (!companyFacade.login(email, password)) {
                    throw new LoginErrorException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return companyFacade;

            case customer:
                ClientFacade customerFacade = new CustomerFacade();
                if (!customerFacade.login(email, password)) {
                    throw new LoginErrorException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return customerFacade;

            default:
                return null;

        }

    }


}
