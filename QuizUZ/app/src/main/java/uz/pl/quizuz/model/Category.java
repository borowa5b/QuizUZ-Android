package uz.pl.quizuz.model;

/**
 * Class that stores category from database
 * @author Mateusz Borowski
 */
public class Category {
    //Variables corresponding to those from database
    private int categoryID;
    private String categoryName;

    //Getters and setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
