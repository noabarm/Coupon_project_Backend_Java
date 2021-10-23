package Exceptions.CustomerExceptions;

/**
 * Exception - points on of a repeated attempt to purchase a coupon by the same customer
 * may happen when trying to purchase a duplicate coupon
 */
public class CouponAlreadyPurchasedException extends Exception {
    /**
     * @param
     */
    public CouponAlreadyPurchasedException() {
        super("This coupon already purchased by this customer!");

    }

    /**
     * @param message
     */
    public CouponAlreadyPurchasedException(String message) {
        super(message);
    }
}
