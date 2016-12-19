package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.base.BaseActivity;

public class ViewActivity extends BaseActivity {

    public static Intent createIntent(Context context, String diaryId) {
        Intent intent = new Intent(context, ViewActivity.class);
        intent.putExtra(EditActivity.DIARY_UUID, diaryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
}
