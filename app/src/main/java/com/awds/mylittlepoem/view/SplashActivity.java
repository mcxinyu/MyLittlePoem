package com.awds.mylittlepoem.view;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.awds.mylittlepoem.R;
import com.awds.mylittlepoem.view.base.BaseActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private final static int JUMP_TO_NEXT = 1;

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
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
