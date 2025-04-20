package com.example.assignment1;

public class Product {
    private String id;
    private String name;
    private String category; // Console, Game, Accessory
    private double price;
    private int quantity;
    private String condition; // New or Used
    private String seller;

    private boolean freeShipping;
    private boolean warrantyIncluded;

    public Product(String id, String name, String category, double price, int quantity, String condition, String seller,
                   boolean freeShipping, boolean warrantyIncluded) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.condition = condition;
        this.seller = seller;
        this.freeShipping = freeShipping;
        this.warrantyIncluded = warrantyIncluded;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public boolean isFreeShipping() { return freeShipping; }
    public void setFreeShipping(boolean freeShipping) { this.freeShipping = freeShipping; }

    public boolean isWarrantyIncluded() { return warrantyIncluded; }
    public void setWarrantyIncluded(boolean warrantyIncluded) { this.warrantyIncluded = warrantyIncluded; }
}
