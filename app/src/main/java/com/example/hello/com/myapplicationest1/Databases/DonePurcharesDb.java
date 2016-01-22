package com.example.hello.com.myapplicationest1.Databases;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.hello.com.myapplicationest1.Models.DayDto;
import com.example.hello.com.myapplicationest1.Models.PurchareItem;
import com.example.hello.com.myapplicationest1.Objects.HelpUtils;

import java.util.ArrayList;
import java.util.List;

public class DonePurcharesDb
{
    public static  long createPurchareItem(PurchareItem item, Context context)
    {
        ContentValues cv = new ContentValues();

        cv.put(DbAdapter.PRODUCT_ID, item.Product.Id);
        cv.put(DbAdapter.PRODUCT_COUNT, item.Count);
        cv.put(DbAdapter.DATE_PURCHARE, item.DatePurchare);
        cv.put(DbAdapter.PRODUCT_TOTAL_AMOUNT, item.TotalPrice);
        cv.put(DbAdapter.PRODUCT_TYPE, item.Type);

        long id = DbAdapter.getInstance(context).getConnection().insert(DbAdapter.TABLE_DONE_PURCHARE, null, cv);

        return id;
    }

    public static List<PurchareItem> getDonePurchareItems(Activity context)
    {
        List<PurchareItem> list = new ArrayList<>();

        String sql = "select * from " + DbAdapter.TABLE_DONE_PURCHARE;

        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                list.add(getPurchareItem(c, context));
            }while (c.moveToNext());
        }
        return list;

    }

    public static List<PurchareItem> getDonePurchareItems(Activity context, long time)
    {
        List<PurchareItem> list = new ArrayList<>();

        String sql = "select * from " + DbAdapter.TABLE_DONE_PURCHARE + " where " + DbAdapter.DATE_PURCHARE + " = " + time;

        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                list.add(getPurchareItem(c, context));
            }while (c.moveToNext());
        }
        return list;

    }

    public static List<DayDto> getDaysDayDto(Activity context)
    {


        List<DayDto> list = new ArrayList<>();
        DayDto item = null;

        String sql = "select DISTINCT " + DbAdapter.DATE_PURCHARE + " from " +
        DbAdapter.TABLE_DONE_PURCHARE +
        " Order By " + DbAdapter.DATE_PURCHARE + " DESC";

        Cursor c = DbAdapter.getInstance(context).getConnection().rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                item = new DayDto();
                item.Time = c.getLong(c.getColumnIndex(DbAdapter.DATE_PURCHARE));
                item.Name = HelpUtils.getTimeString(item.Time);
                list.add(item);
            }while (c.moveToNext());
        }
        return list;
    }

    public static long deletePurchareItem(PurchareItem item, Context context )
    {
        return  DbAdapter.getInstance(context).getConnection().delete(DbAdapter.TABLE_DONE_PURCHARE,
                DbAdapter.ID + " = " + item.Id, null);
    }

    private static PurchareItem getPurchareItem(Cursor c,  Context context)
    {
        PurchareItem item = new PurchareItem();

        item.Id = c.getLong(c.getColumnIndex(DbAdapter.ID));
        item.Count = c.getDouble(c.getColumnIndex(DbAdapter.PRODUCT_COUNT));
        item.DatePurchare = c.getLong(c.getColumnIndex(DbAdapter.DATE_PURCHARE));
        item.Type = c.getInt(c.getColumnIndex(DbAdapter.PRODUCT_TYPE));
        long product_id = c.getLong(c.getColumnIndex(DbAdapter.PRODUCT_ID));
        item.Product = ProductDb.getProductById(product_id, context);
        item.TotalPrice = c.getDouble(c.getColumnIndex(DbAdapter.PRODUCT_TOTAL_AMOUNT));

        return item;

    }
}
