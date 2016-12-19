package com.awds.mylittlepoem.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.awds.mylittlepoem.global.MyApplication;

import javax.inject.Inject;

/**
 * Created by huangyuefeng on 2016/12/15.
 */

public class SyncService extends IntentService {
    private static SyncManager.SyncResultListener syncResultListener;

    @Inject
    SyncManager mSyncManager;

    public SyncService() {
        super("SyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mSyncManager.sync(syncResultListener);
    }

    public static void syncImmediately(Context context) {
        syncImmediately(context, null);
    }

    public static void syncImmediately(Context context,@Nullable SyncManager.SyncResultListener listener) {
        syncResultListener = listener;
        context.startService(new Intent(context, SyncService.class));
    }
}
