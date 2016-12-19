package com.awds.mylittlepoem.global;

import android.app.Application;

import com.awds.mylittlepoem.dependencyinjection.AppComponent;
import com.awds.mylittlepoem.dependencyinjection.AppModule;
import com.awds.mylittlepoem.dependencyinjection.DaggerAppComponent;
import com.awds.mylittlepoem.view.widget.font.FontFamilyFactory;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by huangyuefeng on 2016/12/11.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(MyApplication.this))
                .build();
        instance = this;
        //初始化数据库
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        FontFamilyFactory.init(this).subscribe();
    }
}
