package com.awds.mylittlepoem.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by huangyuefeng on 2016/12/14.
 */
@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyLittlePoem";
    public static final int VERSION = 1;
}
