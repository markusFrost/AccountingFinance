package com.example.hello.com.myapplicationest1.Databases;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.hello.com.myapplicationest1.Models.Product;
import com.example.hello.com.myapplicationest1.Models.ProductItem;
import com.example.hello.com.myapplicationest1.Objects.Constants;
import com.example.hello.com.myapplicationest1.Objects.HelpUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductDb
{
    public static long createProduct(Product item, Context context)
    {
        ContentValues cv = new ContentValues();
        cv.put(DbAdapter.NAME, item.Name);
        cv.put(DbAdapter.PRICE_PER_UNIT, item.PricePerUnit);


        long id = DbAdapter.getInstance(context).getConnection().insert(DbAdapter.TABLE_PRODUCTS, null, cv);

        return id;
    }

    public static void updateProduct(Product item, Context context)
    {
        ContentValues cv = new ContentValues();
        cv.put(DbAdapter.PRICE_PER_UNIT, item.PricePerUnit);

        DbAdapter.getInstance(context).getConnection().update(DbAdapter.TABLE_PRODUCTS, cv, DbAdapter.ID + " = ?",
                 new String[]{item.Id + ""});
    }

    public static Product isProductExists(String name, Context context)
    {
        // Записываем в БД с большой буквы
        String sql = "SELECT * FROM ProductItem WHERE  name = '" + HelpUtils.formatName(name) + "'";
        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);
        if (c.moveToFirst())
        {
            return getProduct(c);
        }
        else
        {
            return null;
        }
    }

    public static List<String> getProductsNames(Activity context)
    {
        String sql = "SELECT * FROM ProductItem";
        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);

        List<String> list = new ArrayList<>();

        if (c.moveToFirst())
        {
            do
            {
                list.add(getProduct(c).Name);
            }while (c.moveToNext());
        }
        return list;
    }

    public static Product getProductById(long id, Context context)
    {
        String sql = "SELECT * FROM ProductItem WHERE id = " + id;
        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);
        if (c.moveToFirst())
        {
            return getProduct(c);
        }
        return null;
    }

    public static List<Product> getProducts(Context context)
    {
        String sql = "SELECT * FROM ProductItem";
        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);

        List<Product> list = new ArrayList<>();
        Product item = null;

        if (c.moveToFirst())
        {
            do
            {
                list.add(getProduct(c));
            }while (c.moveToNext());
        }
        return list;
    }

    private static  Product getProduct(Cursor c)
    {
        Product item = new Product();
        item.Id = c.getLong(c.getColumnIndex(DbAdapter.ID));
        item.Name = c.getString(c.getColumnIndex(DbAdapter.NAME));
        item.PricePerUnit = c.getDouble(c.getColumnIndex(DbAdapter.PRICE_PER_UNIT));


        return item;
    }


}
