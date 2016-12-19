package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.database.DiaryService;
import com.awds.mylittlepoem.global.MyApplication;
import com.awds.mylittlepoem.view.base.BaseActivity;
import com.awds.mylittlepoem.view.widget.TextPointView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {
    public static final String DIARY_UUID = "diary_uuid";

    @BindView(R.id.edit_title)
    EditText mEditTitle;
    @BindView(R.id.edit_content)
    EditText mEditContent;
    @BindView(R.id.edit_scroll_view)
    ScrollView mEditScrollView;
    @BindView(R.id.edit_save)
    TextPointView mEditSave;
    @BindView(R.id.layout_container)
    RelativeLayout mLayoutContainer;

    @Inject
    DiaryService mDiaryService;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        MyApplication.getAppComponent().inject(this);

    }

    @OnClick(R.id.edit_save)
    public void onClick() {
    }
}
