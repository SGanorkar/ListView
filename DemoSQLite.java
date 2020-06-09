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
    public static final String col_2 = "FileID";


    public DemoSQLite(@Nullable Context context)
    {
        super(context, DATABASE, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE customerData" +
                                            "( CustId INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "FolderID TEXT," +
                                            "FileID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(("DROP TABLE IF EXISTS customerData"));
        onCreate(sqLiteDatabase);
    }

    public void insertData (String FolderID, String FileID)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(col_1, FolderID);
        contentValues.put(col_2, FileID);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT FolderID, FileID FROM customerData", null);
        return result;
    }

    public void updateData(String FileID)
    {
        String custId = "1";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(col_1, FolderID);
        contentValues.put(col_2, FileID);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "CustId=?", new String[]{custId});
    }

}
