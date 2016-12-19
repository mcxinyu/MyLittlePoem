package com.awds.mylittlepoem.dependencyinjection;

import com.awds.mylittlepoem.sync.SyncService;
import com.awds.mylittlepoem.view.DiaryListActivity;
import com.awds.mylittlepoem.view.adapter.DiaryListAdapter;

/**
 * Created by huangyuefeng on 2016/12/16.
 */
public interface MiscComponent {
    void inject(DiaryListAdapter adapter);
    void inject(SyncService service);
}
