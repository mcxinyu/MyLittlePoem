package com.awds.mylittlepoem.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorRes;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.util.DisplayUtil;

/**
 * 自定义小园按钮
 * Created by huangyuefeng on 2016/12/11.
 */

public class TextPointView extends FrameLayout {
    private Context mContext;

    private static final int DEFAULT_TEXT_SIZE = 16;
    private static final int DEFAULT_SIZE_DP = 30;

    private @Px int size;

    private String singleText;
    private @ColorRes int circleColorRes;
    private @Px int textSize;

    private View circleView;
    TextView textView;

    public TextPointView(Context context) {
        this(context, null);
    }

    public TextPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // TODO: 2016/12/12
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.TextPointView, 0, 0);
        singleText = typedArray.getString(R.styleable.TextPointView_text);
        circleColorRes = typedArray.getInt(R.styleable.TextPointView_redPointViewBgColor, R.color.bright_red);
        textSize = typedArray.getDimensionPixelSize(R.styleable.TextPointView_textSize, DEFAULT_TEXT_SIZE);
        typedArray.recycle();

        circleView = new View(mContext);
        circleView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setCircleBackgroundColor(circleColorRes);

        textView = new TextView(mContext);
        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        textView.setTextSize(textSize);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        textView.setText(singleText);

        addView(circleView);
        addView(textView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY
                || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY){
            size = DisplayUtil.dip2px(mContext, DEFAULT_SIZE_DP);
        } else {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            size = Math.min(width, height);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }

    private void setCircleBackgroundColor(@ColorRes int circleColorRes) {
        ShapeDrawable circleShapeDrawable = new ShapeDrawable();
        circleShapeDrawable.setShape(new OvalShape());
        circleShapeDrawable.getPaint().setColor(ContextCompat.getColor(mContext, circleColorRes));
        circleView.setBackgroundDrawable(circleShapeDrawable);
    }
}
