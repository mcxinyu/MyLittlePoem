package com.awds.mylittlepoem.view.util;

import android.content.Context;

/**
 * Created by huangyuefeng on 2016/12/12.
 */

public class DisplayUtil {

    /**
     * 将 dip 转换为 px
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context, float dip){
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    /**
     * 将 px 转换为 dip
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, float px){
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }
}
