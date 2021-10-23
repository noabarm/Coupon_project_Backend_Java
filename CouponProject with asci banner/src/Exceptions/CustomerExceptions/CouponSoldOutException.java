package Exceptions.CustomerExceptions;

/**
 * Exception - points on a purchase attempt of coupon that has been sold out
 * may happen when trying to purchase a coupon
 */
public class CouponSoldOutException extends Exception {
    /**
     * @param
     */
    public CouponSoldOutException() {
        super("SOLD OUT!");

    }

    /**
     * @param message
     */
    public CouponSoldOutException(String message) {
        super(message);
    }

}
