package com.awds.mylittlepoem.view.widget.font;

import android.content.Context;
import android.graphics.Typeface;

import rx.Observable;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by huangyuefeng on 2016/12/12.
 */

public class FontFamilyFactory {
    private final static String DEFAULT_FONT_FAMILY_2 = "little_poem_default.otf";
    private static Typeface typeface = null;

    public static String getDefaultFontFamily(){
        return DEFAULT_FONT_FAMILY_2;
    }

    public static Typeface getTypeface() {
        return typeface;
    }

    public static Observable<Void> init(final Context context){
        return Observable.defer(new Func0<Observable<Void>>() {
            @Override
            public Observable<Void> call() {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + getDefaultFontFamily());
                return Observable.just(null);
            }
        }).subscribeOn(Schedulers.io());
    }
}
