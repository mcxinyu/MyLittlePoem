package com.awds.mylittlepoem.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.util.DisplayUtil;
import com.awds.mylittlepoem.view.widget.font.CustomTextView;

/**
 * Created by huangyuefeng on 2016/12/13.
 */

public class VerticalTextView extends CustomTextView {
    public VerticalTextView(Context context) {
        this(context, null);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.VerticalTextView, 0, 0);
        try {
            float textSizePixel = typedArray.getDimension(R.styleable.VerticalTextView_verticalTextSize,
                    getResources().getDimension(R.dimen.normal_text_size));
            int textSizeSp = DisplayUtil.px2dip(context, textSizePixel);
            setTextSize(textSizeSp);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)){
            super.setText(text, type);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0; i<text.length(); i++) {
            CharSequence charSequence = text.toString().subSequence(i, i+1);
            stringBuffer.append(charSequence);
            if (i < text.length()-1){
                stringBuffer.append("\n");
            }
        }
        super.setText(stringBuffer, type);
    }
}
