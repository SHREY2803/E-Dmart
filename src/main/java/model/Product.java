package model;

public class Product {

    private int id;
    private String name;
    private String description;
    private int categoryId;
    private double price;
    private int quantity;
    private String imageUrl;
    private boolean active;

    // Default constructor
    public Product() {
        this.active = true;
        this.quantity = 0;
    }

    // Parameterized constructor
    public Product(String name,
                   String description,
                   int categoryId,
                   double price,
                   int quantity,
                   String imageUrl) {

        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.active = true;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}