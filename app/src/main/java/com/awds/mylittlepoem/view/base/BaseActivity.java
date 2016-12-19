package com.awds.mylittlepoem.view.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.global.MyApplication;

import butterknife.ButterKnife;

/**
 * Created by huangyuefeng on 2016/12/7.
 */

public class BaseActivity extends AppCompatActivity{
    protected String TAG = getClass().getSimpleName() + ":%s";

    protected View containerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getAppComponent().inject(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

       containerView = findViewById(R.id.layout_container);
    }
}
