package com.awds.mylittlepoem.view.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.awds.mylittlepoem.R;

/**
 * Created by huangyuefeng on 2016/12/7.
 */

public class BaseActivity extends AppCompatActivity{
    protected boolean isVisible = false;

    protected View containerView;
    protected String TAG = getClass().getSimpleName() + ":%s";

    private boolean isNeedRegister = false;

    protected void setNeedRegister(){
        this.isNeedRegister = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

       containerView = findViewById(R.id.layout_container);
    }
}
