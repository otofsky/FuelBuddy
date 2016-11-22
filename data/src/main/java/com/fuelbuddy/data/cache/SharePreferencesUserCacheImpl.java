package com.fuelbuddy.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;
import com.fuelbuddy.data.repository.datasource.UserDataStore.UserDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class SharePreferencesUserCacheImpl implements UserCache {

    private static final String SP_USER_ENTITY = "USER_ENTITY";


    public SharedPreferences sharedPreferences;
    Context mContext;

    @Inject
    public SharePreferencesUserCacheImpl(Context context, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
       mContext = context.getApplicationContext();;
    }


    private SharedPreferences getAndClearSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    @Override
    public Observable<UserEntity> get() {
        Observable<UserEntity> observable = Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                UserEntity userEntity = new UserEntity();
                userEntity.setUserId("1");
                subscriber.onNext(userEntity);
            }
        });
        return observable;
    }

    @Override
    public Observable<Boolean> delete() {
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                // remove user from storage
                //mUserCache.delete();
                subscriber.onNext(true);
            }
        });
        return observable;
    }

    @Override
    public  Observable<UserEntity> put(final UserEntity userEntity) {
        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                Log.d("setCurrentUser", "call: " + userEntity.toString());
                sharedPreferences.edit().putString("profile", userEntity.getProfileName()).commit();
                Log.d("setCurrentUser", "call: " + userEntity.toString());

            }
        });
    }

    @Override
    public boolean isCached(int userId) {
        return false;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void evictAll() {

    }
}
