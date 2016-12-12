package com.awds.mylittlepoem.global;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by huangyuefeng on 2016/12/11.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }
}
