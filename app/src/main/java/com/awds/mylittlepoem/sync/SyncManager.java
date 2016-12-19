package com.awds.mylittlepoem.sync;

import android.support.annotation.Nullable;

import com.awds.mylittlepoem.database.PushData;
import com.awds.mylittlepoem.model.Diary;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by huangyuefeng on 2016/12/15.
 */
@Singleton
public class SyncManager {
    @Inject
    Gson mGson;

    @Inject
    SyncManager() {
    }

    public synchronized void sync(){
        sync(null);
    }

    public synchronized void sync(@Nullable final SyncResultListener listener) {
        final List<PushData> list = SQLite.select().from(PushData.class).queryList();

        JsonParser jsonParser = new JsonParser();
        JsonArray array = new JsonArray();
        for (PushData pushData:list) {
            array.add(jsonParser.parse(pushData.getData()));
        }

        JsonObject syncData = new JsonObject();
        syncData.add("sync_items", array);
        syncData.add("need_pull", mGson.toJsonTree(1));
        // syncData.add("sync_token", );

        Observable.just(null).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (listener != null){
                            if (SQLite.select().from(Diary.class).queryList().size() > 0){
                                listener.onFailure();
                            } else {
                                listener.onSuccess();
                            }
                        }
                    }
                });
    }

    public interface SyncResultListener{
        void onSuccess();
        void onFailure();
    }
}
