package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.base.BaseActivity;

public class SettingActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
