package com.example.hello.com.myapplicationest1.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter
{
    public static final String DB_NAME = "Finan1";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PRODUCTS = "ProductItem";
    public static final String TABLE_PLANN_PURCHARE = "PurchareItem";
    public static final String TABLE_DONE_PURCHARE = "DonePurchare";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE_PER_UNIT = "price_per_unit";
    public static final String PRODUCT_TYPE = "product_type";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_COUNT = "product_count";
    public static final String DATE_PURCHARE = "datePurchare";
    public static final String PRODUCT_TOTAL_AMOUNT = "product_total_amount";

    private Context context;
    private DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static DbAdapter dbAdapter;

    public static DbAdapter getInstance(Context context)
    {
        if ( dbAdapter == null )
        {
            dbAdapter = new DbAdapter(context);
            return dbAdapter;
        }
        else
        {
            return dbAdapter;
        }
    }
    private DbAdapter ( Context context)
    {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public static SQLiteDatabase getConnection()
    {
        return db;
    }

    private static class DbHelper extends SQLiteOpenHelper
    {
        public DbHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);



        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sql = "create table  if not exists ProductItem ( id integer primary key autoincrement, name VARCHAR(255), " +
                    " price_per_unit FLOAT);";
            db.execSQL(sql);

            sql = "create table if not exists PurchareItem (id integer primary key autoincrement, product_id integer, " +
                    " product_count FLOAT, product_total_amount FLOAT, datePurchare INTEGER,  product_type integer, FOREIGN KEY(product_id) REFERENCES ProductItem(id) )";
            db.execSQL(sql);

            sql = " create table if not exists DonePurchare (id integer primary key autoincrement, product_id integer, " +
                    " product_total_amount FLOAT, product_count FLOAT, datePurchare INTEGER, product_type integer, FOREIGN KEY(product_id) REFERENCES ProductItem(id) )";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }



    /*
    create table  if not exists ProductItem ( id integer primary key autoincrement, name VARCHAR(255), price_per_unit FLOAT, product_type integer);

create table if not exists PurchareItem (id integer primary key autoincrement, product_id integer,  product_count FLOAT, datePurchare INTEGER, FOREIGN KEY(product_id) REFERENCES ProductItem(product_id) )

 create table if not exists DonePurchare (id integer primary key autoincrement, product_id integer,  product_total_amount FLOAT, datePurchare INTEGER, FOREIGN KEY(product_id) REFERENCES ProductItem(product_id) )
     */
}
