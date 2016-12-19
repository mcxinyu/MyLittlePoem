package com.awds.mylittlepoem.sync;

import com.awds.mylittlepoem.database.PushData;
import com.awds.mylittlepoem.view.util.DateUtil;
import com.google.gson.JsonObject;

/**
 * Created by huangyuefeng on 2016/12/15.
 */

public class Change {
    public enum DatabaseKey{
        DIARY("diary");
        String key;

        DatabaseKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static void handleChangeByDatabaseKey(DatabaseKey key, JsonObject object) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(key.getKey(), object);
        PushData pushData = new PushData();
        pushData.setData(jsonObject.toString());
        pushData.setTimeCreated(DateUtil.getCurrentTimeStamp());
        pushData.save();
    }
}
