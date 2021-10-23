package Exceptions;

/**
 * Exception - points on a failed attempt to log into the system
 * may happen when trying to log into the system
 */
public class LoginErrorException extends Exception {
    /**
     * @param
     */
    public LoginErrorException() {
        super("Can't Login!");

    }

    /**
     * @param message
     */
    public LoginErrorException(String message) {
        super(message);
    }

}
