package com.example.jaloliddin.bestblog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Currency;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BestBlog.db";
    private static final int DB_VERSION = 1;

    private static final String tableUserName = "tabUser";
    private static final String colID = "colID";
    private static final String colUserName = "colUserName";
    private static final String colPass = "colPass";


    private static DbHelper instance;

    public static DbHelper getInstance(Context context) {
        if (instance == null)
            instance = new DbHelper(context);
        return instance;
    }

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableUserName + "(" +
                colID + " integer primary key," +
                colUserName + " text, " +
                colPass + " text " +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion)
            db.execSQL(" DROP TABLE IF EXISTS " + tableUserName);

        onCreate(db);
    }

    /***
     * Insert Data
     * */

    public boolean insertUserData(int id, String userName, String userPassword) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        try {
            cv.put(colID, id);
            cv.put(colUserName, userName);
            cv.put(colPass, userPassword);

            database.insert(tableUserName, null, cv);
            database.close();
            return true;
        } catch (Exception e) {
            Log.d("MyTag", getClass().getName() + ">> InsertData: Exception -" + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeAll(database, null);
        }
    }

    /********
     *
     * Get Data
     *
     * *******/

    public String[] getUserData() {
        String data[] = new String[3];

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableUserName, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    data[0] = cursor.getString(cursor.getColumnIndex(colID));
                    data[1] = cursor.getString(cursor.getColumnIndex(colUserName));
                    data[2] = cursor.getString(cursor.getColumnIndex(colPass));
                } while (cursor.moveToNext());

                cursor.close();
                db.close();
            }
        } catch (Exception e) {
            Log.d("MyTag", getClass().getName() + ">> getDataFromDb: Exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeAll(db, cursor);
        }

        return data;
    }

    /*****
     *
     *Delete User Data
     *
     * */

    public boolean deleteUserData() {

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.delete(tableUserName, null, null);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeAll(db, null);
        }

    }

    private void closeAll(SQLiteDatabase db, Cursor cursor) {
        if (cursor != null) {
            if (!cursor.isClosed()) ;
            cursor.close();
        }
        if (db.isOpen())
            db.close();

    }


}
