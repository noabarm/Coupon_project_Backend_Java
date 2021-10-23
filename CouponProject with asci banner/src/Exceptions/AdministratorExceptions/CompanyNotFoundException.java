package Exceptions.AdministratorExceptions;

/**
 * Exception - points on a failed attempt to get non exist company information
 */
public class CompanyNotFoundException extends Exception {

    /**
     * @param
     */
    public CompanyNotFoundException() {
        super("Company Not Found!");

    }

    /**
     * @param message
     */
    public CompanyNotFoundException(String message) {
        super(message);

    }

}