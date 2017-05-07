package com.example.yang_deng.monashfriendfindertest.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Yang-Deng on 2017/4/9.
 */

public class DBManagement {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "friendship.db";
    private final Context context;
    private MySQLiteOpenHelper myDBHelper;
    private SQLiteDatabase db;
    private static final String TEXT_TYPE = " TEXT";
    private static final String Integer_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBStructure.tableEntry.TABLE_NAME + " (" +
                    DBStructure.tableEntry._ID + " INTEGER PRIMARY KEY," +
                    DBStructure.tableEntry.COLUMN_Number + TEXT_TYPE +
                    " );";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBStructure.tableEntry.TABLE_NAME;

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public DBManagement open() throws SQLException {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        myDBHelper.close();
    }

    public DBManagement(Context ctx) {
        this.context = ctx;
        myDBHelper = new MySQLiteOpenHelper(context);
    }

    public long insertNumber(String number) {
        ContentValues values = new ContentValues();
        values.put(DBStructure.tableEntry.COLUMN_Number, number);
        return db.insert(DBStructure.tableEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllNumbers() {
        return db.query(DBStructure.tableEntry.TABLE_NAME, projection, null, null,
                null, null, null);
    }

    private String[] projection = {
            DBStructure.tableEntry.COLUMN_Number
    };

    public int deleteNumber(String rowId) {
        String[] selectionArgs = { String.valueOf(rowId) };
        String selection = DBStructure.tableEntry.COLUMN_Number + " LIKE ?";
        return db.delete(DBStructure.tableEntry.TABLE_NAME, selection,selectionArgs );
    }

    public Cursor getNumber(String rowId) throws SQLException{
        String selection = DBStructure.tableEntry.COLUMN_Number + " LIKE ?";
        String[] selectionArgs = {String.valueOf(rowId)};
        Cursor cursor = db.query(true,
                DBStructure.tableEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean updateNumber(String id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBStructure.tableEntry.COLUMN_Number, name);
        String selection = DBStructure.tableEntry.COLUMN_Number + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int count = db.update(
                DBStructure.tableEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count > 0;
    }
}
