package Exceptions.AdministratorExceptions;

/**
 * Exception - points on duplication of a company name\company email attempt
 * may happen when trying to create and insert a new company into the database
 */
public class CompanyAlreadyExistException extends Exception {

    /**
     * @param
     */
    public CompanyAlreadyExistException() {
        super(" Company already exist");
    }

    /**
     * @param message
     */
    public CompanyAlreadyExistException(String message) {
        super(message);
    }

}
