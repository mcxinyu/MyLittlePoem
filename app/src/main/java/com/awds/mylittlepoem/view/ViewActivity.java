package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.database.DiaryService;
import com.awds.mylittlepoem.global.MyApplication;
import com.awds.mylittlepoem.model.Diary;
import com.awds.mylittlepoem.view.base.BaseActivity;
import com.awds.mylittlepoem.view.util.DisplayUtil;
import com.awds.mylittlepoem.view.util.LanguageUtil;
import com.awds.mylittlepoem.view.util.ScreenshotManager;
import com.awds.mylittlepoem.view.widget.MultipleRowTextView;
import com.awds.mylittlepoem.view.widget.TextPointView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ViewActivity extends BaseActivity {

    @BindView(R.id.view_title)
    TextView mViewTitle;
    @BindView(R.id.view_content)
    TextView mViewContent;
    @BindView(R.id.vertical_view_date)
    MultipleRowTextView mVerticalViewDate;
    @BindView(R.id.vertical_view_content)
    MultipleRowTextView mVerticalViewContent;
    @BindView(R.id.vertical_view_title)
    MultipleRowTextView mVerticalViewTitle;
    @BindView(R.id.text_point_view_edit)
    TextPointView mTextPointViewEdit;
    @BindView(R.id.text_point_view_share)
    TextPointView mTextPointViewShare;
    @BindView(R.id.horizontal_container)
    ScrollView mHorizontalContainer;
    @BindView(R.id.vertical_container)
    HorizontalScrollView mVerticalContainer;
    @BindView(R.id.container)
    LinearLayout mContainer;
    @BindView(R.id.normal_container)
    RelativeLayout mNormalContainer;

    private String diaryUuid;
    private boolean verticalStyle = false;

    @Inject
    DiaryService mDiaryService;

    @Inject
    ScreenshotManager mScreenshotManager;

    public static Intent createIntent(Context context, String diaryId) {
        Intent intent = new Intent(context, ViewActivity.class);
        intent.putExtra(EditActivity.DIARY_UUID, diaryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        MyApplication.getAppComponent().inject(this);
        diaryUuid = getIntent().getStringExtra(EditActivity.DIARY_UUID);
        if (diaryUuid == null) {
            finish();
        }
        verticalStyle = true;
        loadDiary();
    }

    private void loadDiary() {
        mDiaryService.getDiaryFromDatabaseByUuid(diaryUuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Diary>() {
                    @Override
                    public void call(Diary diary) {
                        showDiary(diary);
                    }
                });
    }

    private void showDiary(Diary diary) {
        if (diary == null){
            return;
        }
        String title = diary.getTitle();
        String content = diary.getContent();
        String diaryDate = LanguageUtil.getDiaryDateEnder(getApplicationContext(), diary.getTime_created());

        setVisibilityByVerticalStyle();
        if (verticalStyle) {
            mVerticalViewTitle.setText(title);
            mVerticalViewContent.setText(content);
            mVerticalViewDate.setText(diaryDate);
            // mContainer.setBackgroundResource();
            mContainer.post(new Runnable() {
                @Override
                public void run() {
                    int scrollOffsetX = mContainer.getWidth() - DisplayUtil.getDisplayWidth();
                    if (scrollOffsetX > 0) {
                        mHorizontalContainer.scrollBy(scrollOffsetX, 0);
                    }
                }
            });
        } else {
            // mNormalContainer.setBackgroundResource();
            mViewTitle.setText(title);
            mViewContent.setText(content + getString(R.string.space_of_date_record_end) + diaryDate);
        }
    }

    private void setVisibilityByVerticalStyle() {
        mVerticalContainer.setVisibility(verticalStyle ? View.VISIBLE : View.GONE);
        mHorizontalContainer.setVisibility(verticalStyle ? View.GONE : View.VISIBLE);
    }

    @OnClick({R.id.text_point_view_edit, R.id.text_point_view_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_point_view_edit:
                setResult(RESULT_OK);
                startActivity(EditActivity.createIntent(this, diaryUuid));
                finish();
                break;
            case R.id.text_point_view_share:
                share();
                break;
        }
    }

    private void share() {
        // ProgressDialog dialog = ProgressDialog.show(this, "", "加载中...");
        // View target = verticalStyle ? mContainer : mVerticalContainer;
        // mScreenshotManager.shotScreen(target, "temp.jpg")
        //         .observeOn(Schedulers.io())
        //         .filter(new Func1<String, Boolean>() {
        //             @Override
        //             public Boolean call(String s) {
        //                 return !TextUtils.isEmpty(s);
        //             }
        //         })
        //         .flatMap(new Func1<String, Observable<Pair<String, ShareContent>>>() {
        //             @Override
        //             public Observable<Pair<String, ShareContent>> call(String s) {
        //                 return null;
        //             }
        //         })
    }
}
