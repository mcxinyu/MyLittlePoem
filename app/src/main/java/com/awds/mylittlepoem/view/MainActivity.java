package com.awds.mylittlepoem.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.base.BaseActivity;
import com.awds.mylittlepoem.view.util.FullDateManager;
import com.awds.mylittlepoem.view.widget.TextPointView;
import com.awds.mylittlepoem.view.widget.VerticalTextView;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_SETTING = 1024;

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    @BindView(R.id.background_image)
    ImageView mBackgroundImage;
    @BindView(R.id.setting)
    TextPointView mSettingPointView;
    @BindView(R.id.year)
    VerticalTextView mYearTextView;
    @BindView(R.id.month)
    VerticalTextView mMonthTextView;
    @BindView(R.id.day)
    VerticalTextView mDayTextView;
    @BindView(R.id.write)
    TextPointView mWritePointView;
    @BindView(R.id.read)
    TextPointView mReadPointView;

    private int year, month, day;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            year = savedInstanceState.getInt(YEAR);
            month = savedInstanceState.getInt(MONTH);
            day = savedInstanceState.getInt(DAY);
        } else {
            setTodayAsFullDate();
        }
        updateFullDate();
    }

    private void setTodayAsFullDate() {
        DateTime currentDateTime = new DateTime();
        setDate(currentDateTime);
    }

    private void setDate(DateTime dateTime) {
        year = dateTime.getYear();
        month = dateTime.getMonthOfYear();
        day = dateTime.getDayOfMonth();
    }

    private void updateFullDate() {
        FullDateManager fullDateManager = new FullDateManager();
        mYearTextView.setText(fullDateManager.getYear(year));
        mMonthTextView.setText(fullDateManager.getMonth(month));
        mDayTextView.setText(fullDateManager.getDay(day));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(YEAR, year);
        outState.putInt(MONTH, month);
        outState.putInt(DAY, day);
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.setting, R.id.write, R.id.read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                Intent settingIntent = SettingActivity.createIntent(this);
                startActivityForResult(settingIntent, REQUEST_CODE_SETTING);
                break;
            case R.id.write:
                Intent writeIntent = EditActivity.createIntent(this);
                startActivity(writeIntent);
                break;
            case R.id.read:
                Intent diaryListIntent = DiaryListActivity.createIntent(this);
                startActivity(diaryListIntent);
                break;
            default:
                break;
        }
    }
}
