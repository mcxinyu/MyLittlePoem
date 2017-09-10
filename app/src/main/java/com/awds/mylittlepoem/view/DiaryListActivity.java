package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.database.DiaryService;
import com.awds.mylittlepoem.global.MyApplication;
import com.awds.mylittlepoem.model.Diary;
import com.awds.mylittlepoem.view.adapter.DiaryListAdapter;
import com.awds.mylittlepoem.view.base.BaseActivity;
import com.awds.mylittlepoem.view.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DiaryListActivity extends BaseActivity implements DiaryListAdapter.RecyclerViewClickListener {
    private List<Diary> mList = new ArrayList<>();
    private DiaryListAdapter mAdapter;

    @BindView(R.id.diary_list)
    RecyclerView mDiaryListRecyclerView;
    @Inject
    DiaryService mDiaryService;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, DiaryListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        //需要获取component并且调用inject()方法。
        MyApplication.getAppComponent().inject(this);

        mDiaryListRecyclerView.setHasFixedSize(true);
        mDiaryListRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        DiaryListActivity.this,
                        LinearLayoutManager.HORIZONTAL,
                        true));
        mAdapter = new DiaryListAdapter(DiaryListActivity.this, mList);
        mAdapter.setRecyclerViewClickListener(this);
        mDiaryListRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mDiaryService.getDiaryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Diary>>() {
                    @Override
                    public void call(List<Diary> diaries) {
                        mList.clear();
                        mList.addAll(diaries);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    @OnClick(R.id.view_write)
    public void onClick() {
        startActivity(EditActivity.createIntent(this, null));
    }

    @Override
    public void onItemClickListener(Diary diary) {
        startActivity(ViewActivity.createIntent(this, diary.getUuid()));
    }

    @Override
    public void onItemLongClickListener(final Diary diary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.do_you_want_to_delete_diary)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diary.setTime_removed(DateUtil.getCurrentTimeStamp());
                        mDiaryService.saveDiary(diary)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Void>() {
                                    @Override
                                    public void call(Void aVoid) {
                                        mList.remove(diary);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create()
                .show();
    }
}