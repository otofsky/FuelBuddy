package com.fuelbuddy.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.EntityJsonMapper;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;
import com.fuelbuddy.data.exeption.UserNotFoundException;
import com.fuelbuddy.data.repository.datasource.UserDataStore.UserDataStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private final EntityJsonMapper entityJsonMapper;
    Context mContext;

    @Inject
    public SharePreferencesUserCacheImpl(Context context, SharedPreferences sharedPreferences, EntityJsonMapper entityJsonMapper) {
        this.sharedPreferences = sharedPreferences;
        this.mContext = context.getApplicationContext();
        this.entityJsonMapper = entityJsonMapper;
    }

    @Override
    public Observable<UserEntity> getUser() {
        Observable<UserEntity> observable = Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                try {
                   UserEntity userEntity = entityJsonMapper.fromJson(sharedPreferences.getString(SP_USER_ENTITY, ""));
                    if(userEntity!=null) {
                        subscriber.onNext(userEntity);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new UserNotFoundException());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
        return observable;
    }

    @Override
    public Observable<Boolean> delete() {
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(SP_USER_ENTITY);
                editor.apply();
                subscriber.onNext(true);
            }
        });
        return observable;
    }

    @Override
    public Observable<ResponseEntity> put(final UserEntity userEntity) {
        return Observable.create(new Observable.OnSubscribe<ResponseEntity>() {
            @Override
            public void call(Subscriber<? super ResponseEntity> subscriber) {
                try {
                    sharedPreferences.edit().putString(SP_USER_ENTITY, entityJsonMapper.toJson(userEntity)).apply();
                    ResponseEntity responseEntity = new ResponseEntity();
                    responseEntity.setMessage("User added");
                    subscriber.onNext(responseEntity);
                } catch (JSONException e) {
                    subscriber.onError(e);
                }
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
