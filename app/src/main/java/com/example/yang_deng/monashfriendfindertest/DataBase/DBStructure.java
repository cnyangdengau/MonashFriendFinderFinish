package com.example.yang_deng.monashfriendfindertest.DataBase;

/**
 * Created by YangDeng on 2/5/17.
 */
import android.provider.BaseColumns;

public class DBStructure {
    public static abstract class tableEntry implements BaseColumns {
        public static final String TABLE_NAME = "friendship";
        public static final String COLUMN_Number = "Number";
    }
}