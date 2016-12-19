package com.awds.mylittlepoem.dependencyinjection;

import com.awds.mylittlepoem.view.DiaryListActivity;
import com.awds.mylittlepoem.view.EditActivity;
import com.awds.mylittlepoem.view.MainActivity;
import com.awds.mylittlepoem.view.SettingActivity;
import com.awds.mylittlepoem.view.ViewActivity;
import com.awds.mylittlepoem.view.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by huangyuefeng on 2016/12/16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends MiscComponent {
    void inject(BaseActivity object);
    void inject(MainActivity object);
    // void inject(SignUpActivity object);
    void inject(DiaryListActivity object);
    void inject(EditActivity object);
    void inject(ViewActivity object);
    void inject(SettingActivity object);
}
