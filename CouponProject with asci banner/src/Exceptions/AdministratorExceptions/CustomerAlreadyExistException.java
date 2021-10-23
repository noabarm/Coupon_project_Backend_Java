package Exceptions.AdministratorExceptions;

/**
 * Exception - points on duplication of a Customer name\email attempt
 * may happen when trying to create and insert a new Customer into the database
 */
public class CustomerAlreadyExistException extends Exception {
    /**
     * @param
     */
    public CustomerAlreadyExistException() {
        super("Name or email already registered!");

    }

    /**
     * @param message
     */
    public CustomerAlreadyExistException(String message) {
        super(message);
    }

}