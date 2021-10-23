package Enums;

public enum Category {
    /**
     * An Enum containing all types of Coupons
     *
     *
     */
    food, electricity,restaurant,vacation;

    /*
    food(1),
    electricity(2),
    restaurant(3),
    vacation(4);

    private int id;

    public int getId() {
        return id;
    }

    Category(int id){
        this.id =id;
    }

    public static Category getById(int id){
        for(Category category: Category.values()){
            if(category.getId()==id){
                return category;
            }
        }
        return null;
    }

     */
}
