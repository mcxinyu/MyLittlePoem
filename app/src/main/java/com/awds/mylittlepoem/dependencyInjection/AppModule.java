package com.awds.mylittlepoem.dependencyinjection;

import android.content.Context;

import com.awds.mylittlepoem.BuildConfig;
import com.awds.mylittlepoem.global.MyApplication;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huangyuefeng on 2016/12/16.
 */
@Module
public class AppModule {
    private final MyApplication mMyApplication;

    public AppModule(MyApplication myApplication) {
        mMyApplication = myApplication;
    }

    @Provides
    @ForApplication
    Context provideApplicationContent() {
        return mMyApplication;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        return null;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    ExclusionStrategy provideExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz.equals(ModelAdapter.class);
            }
        };
    }

    @Provides
    Gson provideGson(ExclusionStrategy exclusionStrategy) {
        return new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();
    }
}
