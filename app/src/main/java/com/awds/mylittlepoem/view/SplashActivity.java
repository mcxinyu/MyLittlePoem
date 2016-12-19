package com.awds.mylittlepoem.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.base.BaseActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {
    private final static int JUMP_TO_NEXT = 1;

    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler{
        private WeakReference<BaseActivity> mWeakReference;

        public MyHandler(BaseActivity activity) {
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case JUMP_TO_NEXT:
                    if (mWeakReference.get() == null){
                        return;
                    }
                    BaseActivity activity = mWeakReference.get();
                    activity.startActivity(MainActivity.createIntent(activity));
                    activity.finish();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.sendEmptyMessageDelayed(JUMP_TO_NEXT, 1000);
    }
}
