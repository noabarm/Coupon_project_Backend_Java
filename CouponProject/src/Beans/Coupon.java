package Beans;


import Enums.Category;

import java.sql.Date;

/**
 * A Class for depicting a Coupon in the Coupon System
 */

public class Coupon {
    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * Full C`TOR for Coupon Containing all parameters
     *
     * @param id variable
     * @param companyID variable
     * @param category variable
     * @param title variable
     * @param description variable
     * @param startDate variable
     * @param endDate variable
     * @param amount variable
     * @param price variable
     * @param image variable
     */
    //ful const
    public Coupon(int id, int companyID, Category category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * A  C`TOR for Coupon Containing all parameters without id
     * For reading a coupon object from the database
     *
     * @param companyID variable
     * @param category variable
     * @param title variable
     * @param description variable
     * @param startDate variable
     * @param endDate variable
     * @param amount variable
     * @param price variable
     * @param image variable
     */
    //cont to add coupon to db
    public Coupon(int companyID, Category category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

// Getters and Setters

    /**
     * Returns this Coupon's id.
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets this Coupon's id.
     *
     * @param id variable
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns this Coupon's company id.
     *
     * @return int companyID
     */
    public int getCompanyID() {
        return companyID;
    }

    /**
     * Sets this Coupon's company id.
     *
     * @param companyID variable
     */
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    /**
     * Returns the category of this Coupon.
     *
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the type of this Coupon.
     *
     * @param category variable
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Return's this Coupon's title.
     *
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets this Coupon's title
     *
     * @param title variable
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return's the description of this Coupon.
     *
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the message of this Coupon.
     *
     * @param description variable
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return's this Coupon's Creation date.
     *
     * @return Date startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets this Coupon's Creation Date.
     *
     * @param startDate variable
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns this Coupon's Expiration Date.
     *
     * @return Date endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set's this Coupon's Expiration Date.
     *
     * @param endDate variable
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the amount of this Coupon in Stock.
     *
     * @return int amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of this Coupon in stock.
     *
     * @param amount variable
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the price of this Coupon.
     *
     * @return double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets this Coupon's price.
     *
     * @param price variable
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the String of this Coupon's image.
     *
     * @return String image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the String of this Coupon's image.
     *
     * @param image variable
     */
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}' + "\n";
    }
}
