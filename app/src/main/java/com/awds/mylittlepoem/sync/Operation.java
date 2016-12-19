package com.awds.mylittlepoem.sync;

/**
 * Created by huangyuefeng on 2016/12/15.
 */

public enum Operation {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String action;

    Operation(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
