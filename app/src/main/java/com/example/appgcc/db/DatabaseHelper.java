package com.example.appgcc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appgcc.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "GCCData";

    //Table for costumers
    public static final String TABLE_NAME = "Customers";
    public static final String COLUMN_1 = "Email";
    public static final String COLUMN_2 = "FirstName";
    public static final String COLUMN_3 = "LastName";
    public static final String COLUMN_4 = "Phone";
    public static final String COLUMN_5 = "Password";

    //Table for order details
    public static final String TABLE_NAME_2 = "OrderDetails";
    public static final String KEY_1 = "OrderID";
    public static final String KEY_2 = "ItemName";
    public static final String KEY_3 = "ItemQuantity";
    public static final String KEY_4 = "ItemPrice";
    public static final String KEY_5 = "CustomerID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (email TEXT PRIMARY KEY, firstName TEXT, lastName TEXT, phone TEXT, password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_2 + " (orderid INTEGER PRIMARY KEY AUTOINCREMENT, itemname TEXT, itemquantity TEXT, itemprice TEXT, FOREIGN KEY(customerid) REFERENCES orderdetails(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public void addCustomer(String firstName, String lastName, String phone, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, email);
        contentValues.put(COLUMN_2, firstName);
        contentValues.put(COLUMN_3, lastName);
        contentValues.put(COLUMN_4, phone);
        contentValues.put(COLUMN_5, password);
        double check = db.insert(TABLE_NAME, null, contentValues);
        if (check == -1) {
            Toast.makeText(context, "Falló el registro", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Registrado con exito", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor checkCostumer(String searchstr) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Customers WHERE email = " + searchstr, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Order table methods
    public boolean addOrder(String name, String quantity, String price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(KEY_2, name);
        contentValues1.put(KEY_3, quantity);
        contentValues1.put(KEY_4, price);
        double check = db.insert(TABLE_NAME_2, null, contentValues1);
        if (check == -1) return false;
        else return true;
    }

    public Cursor getOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE_NAME_2, null);
        return data;
    }

    public Integer deleteOrder (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_2,"Email = ?",new String [] {id});

    }
    public void deleteAllOrders ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_2);
    }
}