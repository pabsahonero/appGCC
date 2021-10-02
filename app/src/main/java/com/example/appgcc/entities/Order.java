package com.example.appgcc.entities;

public class Order {
    private String itemID;
    private String itemName;
    private String itemQuantity;
    private String itemPrice;
    private String customerID;

    public Order(String itemID, String itemName, String itemQuantity, String itemPrice, String customerID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.customerID = customerID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }


    public boolean isComplete() {
        return !this.getItemID().isEmpty() && !this.getItemName().isEmpty() && !this.getItemPrice().isEmpty() && !this.getItemQuantity().isEmpty();
    }
}