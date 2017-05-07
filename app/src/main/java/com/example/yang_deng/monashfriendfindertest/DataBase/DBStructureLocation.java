package com.example.yang_deng.monashfriendfindertest.DataBase;

import android.provider.BaseColumns;

/**
 * Created by YangDeng on 6/5/17.
 */

public class DBStructureLocation {
    public static abstract class tableEntry implements BaseColumns {
        public static final String TABLE_NAME = "StudentLocation";
        public static final String COLUMN_Number = "Number";
    }
}
