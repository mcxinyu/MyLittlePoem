package com.awds.mylittlepoem.view.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by huangyuefeng on 2016/12/12.
 */

public class DisplayUtil {
    public static int getDisplayWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Transfer sp to px in order keep font size the same
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * Transfer px to sp in order keep font size the same
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }

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
