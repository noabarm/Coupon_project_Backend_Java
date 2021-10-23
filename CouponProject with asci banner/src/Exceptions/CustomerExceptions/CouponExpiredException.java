package Exceptions.CustomerExceptions;
/**
 * Exception - points on of a attempt to purchase a expired coupon by the customer
 * may happen when trying to purchase a expired coupon
 */
public class CouponExpiredException extends Exception{
    /**
     * @param
     */
    public CouponExpiredException() {
        super(" The coupon is already purchased");
    }
    /**
     * @param message
     */
    public CouponExpiredException(String message) {
        super(message);

    }
}
