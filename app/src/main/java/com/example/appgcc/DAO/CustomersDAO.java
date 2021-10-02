package com.example.appgcc.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.appgcc.db.DatabaseHelper;
import com.example.appgcc.entities.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomersDAO {

    private final static String TABLE_NAME = "customers";

    public void saveCustomer(@NonNull Customer customer, Context context) {
        SQLiteDatabase database = new DatabaseHelper(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", customer.getEmail());
        cv.put("firstName", customer.getFirstName());
        cv.put("lastName", customer.getLastName());
        cv.put("phone", customer.getPhone());
        cv.put("password", customer.getPassword());
        database.insert(TABLE_NAME, null, cv);
        database.close();
    }

    public Collection<Customer> getCustomerByExample (Customer customer, Context context) {
        SQLiteDatabase database = new DatabaseHelper(context).getReadableDatabase();
        StringBuilder sql = getSelectCustomers(customer);
        Collection<Customer> customer1 = new ArrayList();
        Cursor cursor = database.rawQuery(sql.toString(), null);
        do {
            customer1.add(extractCustomer(cursor));
        } while (cursor.moveToNext());
        database.close();
        return customer1;
    }

    public int searchCustomer (String costumer) {
        int x=0;
        return x;
    }

    private Customer extractCustomer(Cursor cursor) {
        Customer customer = new Customer();
        customer.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        customer.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow("firstName")));
        customer.setLastName(cursor.getString(cursor.getColumnIndexOrThrow("lastName")));
        customer.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
        customer.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
        return customer;
    }
    @NonNull
    private StringBuilder getSelectCustomers(Customer customer) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE 1=1");

        if (customer !=null) {

            if (!TextUtils.isEmpty(customer.getEmail())) {
                sql.append(" and email =" + customer.getEmail() + " ");
            }
            if (!TextUtils.isEmpty(customer.getFirstName())) {
                sql.append(" and firstName =" + customer.getFirstName() + " ");
            }
            if (!TextUtils.isEmpty(customer.getLastName())) {
                sql.append(" and lastName =" + customer.getLastName() + " ");
            }
            if (!TextUtils.isEmpty(customer.getPhone())) {
                sql.append(" and phone =" + customer.getPhone() + " ");
            }
        }
        return sql;
    }
}
