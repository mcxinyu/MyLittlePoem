package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.database.DiaryService;
import com.awds.mylittlepoem.global.MyApplication;
import com.awds.mylittlepoem.model.Diary;
import com.awds.mylittlepoem.view.base.BaseActivity;
import com.awds.mylittlepoem.view.util.DateUtil;
import com.awds.mylittlepoem.view.widget.TextPointView;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    String diaryUuid;
    private Diary mDiary;
    private String originTitle, originContent;
    private boolean unChanged = true;

    public static Intent createIntent(Context context, @Nullable String diaryUuid) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(DIARY_UUID, diaryUuid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        MyApplication.getAppComponent().inject(this);

        diaryUuid = getIntent().getStringExtra(DIARY_UUID);
        if (diaryUuid != null){
            loadDiary();
        }

        SimpleTextWatcher textWatcher = new SimpleTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                unChanged = false;
            }
        };
        mEditTitle.addTextChangedListener(textWatcher);
        mEditContent.addTextChangedListener(textWatcher);

        mEditScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditContent.requestFocus()){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(mEditContent, inputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ((TextUtils.equals(originTitle, mEditTitle.getText().toString().trim())
                && TextUtils.equals(originContent, mEditContent.getText().toString().trim()))
                || unChanged){
            super.onBackPressed();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
            builder.setTitle(R.string.want_to_save)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveDiary();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private void loadDiary() {
        mDiaryService.getDiaryFromDatabaseByUuid(diaryUuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Diary, Boolean>() {
                    @Override
                    public Boolean call(Diary diary) {
                        return diary != null;
                    }
                })
                .subscribe(new Action1<Diary>() {
                    @Override
                    public void call(Diary diary) {
                        mDiary = diary;
                        originTitle = mDiary.getTitle();
                        originContent = mDiary.getContent();
                        mEditTitle.setText(originTitle);
                        mEditContent.setText(originContent);
                    }
                });
    }

    @OnClick(R.id.edit_save)
    public void onSaveClick() {
        saveDiary();
    }

    private void saveDiary() {
        if (TextUtils.isEmpty(mEditContent.getText())){
            Toast.makeText(this, R.string.edit_content_no_null, Toast.LENGTH_SHORT).show();
            return;
        }
        String titleString;
        String contentString;
        if (TextUtils.isEmpty(mEditTitle.getText())){
            titleString = mEditContent.getText().toString();
            contentString = mEditContent.getText().toString();
        } else {
            titleString = mEditTitle.getText().toString();
            contentString = mEditContent.getText().toString();
        }

        if (mDiary == null){
            mDiary = new Diary();
            mDiary.setTitle(titleString);
            mDiary.setContent(contentString);
            mDiary.setUuid(UUID.randomUUID().toString().toUpperCase());
            mDiary.setTime_created(DateUtil.getCurrentTimeStamp());
        } else {
            mDiary.setTitle(titleString);
            mDiary.setContent(contentString);
            mDiary.setTime_modified(DateUtil.getCurrentTimeStamp());
        }
        mDiaryService.saveDiary(mDiary)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(ViewActivity.createIntent(EditActivity.this, diaryUuid));
                        finish();
                    }
                });
    }

    public class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
