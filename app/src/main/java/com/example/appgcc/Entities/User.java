package com.example.appgcc.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
        /*,
        foreignKeys = {
        @ForeignKey(entity = OrderV2.class,
                parentColumns = "orderID",
                childColumns = "userOrderId",
                onDelete = ForeignKey.CASCADE)})
*/
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    //private int userOrderID;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;

    @Ignore
    public User(@NonNull String email, String firstName, String lastName, String phone, String password) {
        this.email = email;
        //this.userOrderID = userOrderID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*
    public int getUserOrderID() {
        return userOrderID;
    }

    public void setUserOrderID(int userOrderID) {
        this.userOrderID = userOrderID;
    }
*/
}