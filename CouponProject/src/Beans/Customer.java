package Beans;

import java.sql.Array;
import java.util.ArrayList;

/**
 * A class depicting a Customer.
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    /**
     * A  C`TOR for a customer, taking in all parameters.
     *
     * @param id variable
     * @param firstName variable
     * @param lastName variable
     * @param email variable
     * @param password variable
     * @param coupons variable
     */
    //full const
    public Customer(int id, String firstName, String lastName, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * A  C`TOR for a customer, taking in all parameters.
     * For reading a Customer from the database.
     *
     * @param firstName variable
     * @param lastName variable
     * @param email variable
     * @param password variable
     */
    //const to add customer to db
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * A  C`TOR for a customer, taking in all parameters.
     * For reading a Customer from the database.
     *
     * @param id variable
     * @param firstName variable
     * @param lastName variable
     * @param email variable
     * @param password variable
     */
    //const to get customer for db
    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    // Getters and Setters

    /**
     * Returns a Customer's id.
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets this Customer's id.
     *
     * @param id variable
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a Customer's first name.
     *
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets this Customer's first name.
     *
     * @param firstName variable
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns a Customer's last name.
     *
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets this Customer's last name.
     *
     * @param lastName variable
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns this Customer's email.
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set's this Customer's email.
     *
     * @param email variable
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns this Customer's password.
     *
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets this Customer's password.
     *
     * @param password variable
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return's a ArrayList of this customer's coupons.
     *
     * @return ArrayList of Coupon
     */
    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Set's this Customer's ArrayList of coupons.
     *
     * @param coupons variable
     */
    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' + "\n" +
                "coupons:\n" + coupons +
                '}'+"\n----------------------------------------\n";
    }
}
