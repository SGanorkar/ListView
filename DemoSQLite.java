package com.example.gdrivebackup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DemoSQLite extends SQLiteOpenHelper {
    public static final String DATABASE = "customerData.db";
    public static final String TABLE_NAME = "customerData";
    public static final String col_0 = "CustId";
    public static final String col_1 = "FolderID";
   
    public DemoSQLite(@Nullable Context context)
    {
        super(context, DATABASE, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE customerData" +
                                            "( CustId INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "CustName TEXT)"); 
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(("DROP TABLE IF EXISTS customerData"));
        onCreate(sqLiteDatabase);
    }

    public void insertData (String CustID, String CustName)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(col_0, CustID);
        contentValues.put(col_1, CustName);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT CustID, CustName FROM customerData", null);
        return result;
    }

}
