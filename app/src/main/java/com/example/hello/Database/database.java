package com.example.hello.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.hello.Models.Cart;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteAssetHelper {

    private static final String DB_NAME = "hello.db";
    private static final int DB_VER = 1;
    public database(Context context) {
        super(context, DB_NAME,null , DB_VER);

    }
    public List<Cart> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName","Quantity","Price"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Cart> result = new ArrayList<>();

        if(c.moveToFirst())
        {
            do {
                result.add(new Cart (c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("quantity")),
                        c.getString(c.getColumnIndex("price"))
                ));
            }while(c.moveToFirst());

        }
        return result;
    }

    public void addToCart(Cart cart)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductName,price,quantity) VALUES ('%s','%s','%s');," ,
                cart.getName(),
                cart.getPrice(),
                cart.getQuantity());
        db.execSQL(query);

    }
}
