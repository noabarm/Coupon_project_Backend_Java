package Beans;

import java.util.ArrayList;

/**
 * A Class for depicting a Company in the Coupon System.
 */
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    /**
     * Full C`TOR for a company taking in all parameters
     *
     * @param name variable
     * @param id variable
     * @param email variable
     * @param password variable
     * @param coupons variable
     */
    //full const.
    public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;

    }

    /**
     * A C`TOR taking in all parameters. for reading from database purposes.
     *
     * @param name variable
     * @param email variable
     * @param password variable
     */

    //const to add company to db
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * A C`TOR taking in all parameters. for reading from database purposes.
     *
     * @param id variable
     * @param name variable
     * @param email variable
     * @param password variable
     */
    //const to get data from db
    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
// Getters and Setters

    /**
     * Returns this Company's id.
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets this Company's id.
     *
     * @param id variable
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns this Company's name.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set's this Company's name.
     *
     * @param name variable
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns this Company's email.
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set's this Company's email.
     *
     * @param email variable
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns this Company's password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets this Company's password.
     *
     * @param password variable
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns this Company's coupons.
     *
     * @return ArrayList of Coupon
     */

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Sets this Company's ArrayList of Coupons.
     *
     * @param coupons variable
     */
    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }


    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' + "\n" +
                "coupons:\n" + coupons +
                '}'+"\n----------------------------------------\n";
    }
}
