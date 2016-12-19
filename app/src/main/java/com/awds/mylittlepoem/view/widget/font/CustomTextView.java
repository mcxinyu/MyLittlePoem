package com.awds.mylittlepoem.view.widget.font;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by huangyuefeng on 2016/12/12.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeFace();
    }

    private void initTypeFace() {
        if (FontFamilyFactory.getTypeface() != null){
            setTypeface(FontFamilyFactory.getTypeface());
        }
    }

}
