package com.example.yang_deng.monashfriendfindertest.DataBase;

/**
 * Created by YangDeng on 6/5/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Yang-Deng on 2017/4/9.
 */

public class DBManagementLocation {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StudentLocation.db";
    private final Context context2;
    private MySQLiteOpenHelper2 myDBHelper2;
    private SQLiteDatabase db2;
    private static final String TEXT_TYPE = " TEXT";
    private static final String Integer_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBStructureLocation.tableEntry.TABLE_NAME + " (" +
                    DBStructureLocation.tableEntry._ID + " INTEGER PRIMARY KEY," +
                    DBStructureLocation.tableEntry.COLUMN_Number + TEXT_TYPE +
                    " );";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBStructureLocation.tableEntry.TABLE_NAME;

    private static class MySQLiteOpenHelper2 extends SQLiteOpenHelper {
        public MySQLiteOpenHelper2(Context context) {
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

    public DBManagementLocation open() throws SQLException {
        db2 = myDBHelper2.getWritableDatabase();
        return this;
    }
    public void close() {
        myDBHelper2.close();
    }

    public DBManagementLocation(Context ctx) {
        this.context2 = ctx;
        myDBHelper2 = new MySQLiteOpenHelper2(context2);
    }

    public long insertNumber(String number) {
        ContentValues values = new ContentValues();
        values.put(DBStructureLocation.tableEntry.COLUMN_Number, number);
        return db2.insert(DBStructureLocation.tableEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllNumbers() {
        return db2.query(DBStructureLocation.tableEntry.TABLE_NAME, projection, null, null,
                null, null, null);
    }

    private String[] projection = {
            DBStructureLocation.tableEntry.COLUMN_Number
    };

    public int deleteNumber(String rowId) {
        String[] selectionArgs = { String.valueOf(rowId) };
        String selection = DBStructureLocation.tableEntry.COLUMN_Number + " LIKE ?";
        return db2.delete(DBStructureLocation.tableEntry.TABLE_NAME, selection,selectionArgs );
    }

    public Cursor getNumber(String rowId) throws SQLException{
        String selection = DBStructureLocation.tableEntry.COLUMN_Number + " LIKE ?";
        String[] selectionArgs = {String.valueOf(rowId)};
        Cursor cursor = db2.query(true,
                DBStructureLocation.tableEntry.TABLE_NAME,
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
        values.put(DBStructureLocation.tableEntry.COLUMN_Number, name);
        String selection = DBStructureLocation.tableEntry.COLUMN_Number + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int count = db2.update(
                DBStructureLocation.tableEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count > 0;
    }
}