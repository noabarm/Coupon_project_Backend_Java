package Exceptions.CompanyExceptions;

/**
 * Exception - points on a failed attempt to get a coupon by ID
 */
public class CouponNotFoundException extends Exception {
    /**
     * @param
     */
    public CouponNotFoundException() {
        super("Coupon Not Found!");

    }

    /**
     * @param message
     */
    public CouponNotFoundException(String message) {
        super(message);

    }

}
