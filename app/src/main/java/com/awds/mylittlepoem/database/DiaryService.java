package com.awds.mylittlepoem.database;

import android.content.Context;

import com.awds.mylittlepoem.dependencyinjection.ForApplication;
import com.awds.mylittlepoem.model.Diary;
import com.awds.mylittlepoem.model.Diary_Table;
import com.awds.mylittlepoem.sync.Change;
import com.awds.mylittlepoem.sync.Operation;
import com.awds.mylittlepoem.sync.SyncService;
import com.awds.mylittlepoem.view.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by huangyuefeng on 2016/12/15.
 */

public class DiaryService {
    private Context mContext;

    @Inject
    Gson mGson;

    @Inject
    public DiaryService(@ForApplication Context context) {
        mContext = context;
    }

    public Observable<Void> saveDiary(final Diary diary) {
        return Observable.defer(new Func0<Observable<Void>>() {
            @Override
            public Observable<Void> call() {
                JsonObject jsonObject = new JsonObject();
                diary.setTime(DateUtil.getCurrentTimeStamp());
                if (diary.getTime_removed() > 0){
                    jsonObject.add(Operation.DELETE.getAction(), mGson.toJsonTree(diary));
                } else if (diary.getTime_modified() >= diary.getTime_created()){
                    jsonObject.add(Operation.UPDATE.getAction(), mGson.toJsonTree(diary));
                } else {
                    jsonObject.add(Operation.CREATE.getAction(), mGson.toJsonTree(diary));
                }
                Change.handleChangeByDatabaseKey(Change.DatabaseKey.DIARY, jsonObject);
                diary.save();
                SyncService.syncImmediately(mContext);
                return Observable.just(null);
            }
        });
    }

    public Observable<List<Diary>> getDiaryList() {
        return Observable.defer(new Func0<Observable<List<Diary>>>() {
            @Override
            public Observable<List<Diary>> call() {
                return Observable.just(fetchDiaryListFromDatabase());
            }
        });
    }

    private List<Diary> fetchDiaryListFromDatabase() {
        return SQLite.select().from(Diary.class).where(Diary_Table.time_removed.eq(0)).queryList();
    }

    public Observable<Diary> getDiaryFromDatabaseByUuid(final String uuid) {
        return Observable.defer(new Func0<Observable<Diary>>() {
            @Override
            public Observable<Diary> call() {
                Diary diary = SQLite.select().from(Diary.class).where(Diary_Table.uuid.is(uuid)).querySingle();
                return Observable.just(diary);
            }
        });
    }
}
