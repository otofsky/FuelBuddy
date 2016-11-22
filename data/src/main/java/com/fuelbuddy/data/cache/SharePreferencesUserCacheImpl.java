package com.fuelbuddy.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;
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
    public Observable<UserEntity> get() {
        Observable<UserEntity> observable = Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                UserEntity userEntity = new UserEntity();
                userEntity.setUserId("1");
                try {
                    subscriber.onNext(entityJsonMapper.fromJson(sharedPreferences.getString(SP_USER_ENTITY, "")));
                    //subscriber.onNext(null);
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
                Log.d("Logout Share preferen", "onNext: ");
                subscriber.onNext(true);
            }
        });
        return observable;
    }

    @Override
    public Observable<UserEntity> put(final UserEntity userEntity) {
        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                try {
                    sharedPreferences.edit().putString(SP_USER_ENTITY, entityJsonMapper.toJson(userEntity)).apply();
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

    public static class EntityJsonMapper {

        @Inject
        public EntityJsonMapper() {
        }

        private String toJson(UserEntity entity) throws JSONException {
            JSONObject obj = new JSONObject();
            obj.put("userId", entity.getUserId());
            obj.put("profileName", entity.getProfileName());
            /*obj.put("username", entity.getUsername());
            obj.put("email", entity.getEmail());
            obj.put("profileLink", entity.getProfileLink());*/
            return obj.toString();
        }


        private UserEntity fromJson(String obj) throws JSONException {
            Log.d("from", "fromJson: " + obj);
            UserEntity userEntity = new UserEntity();
            if (!obj.equalsIgnoreCase("")) {
                JSONObject jsonObject = new JSONObject(obj);
                userEntity = new UserEntity();
                userEntity.setUserId(jsonObject.getString("userId"));
                userEntity.setProfileName("profileName");
            }
            return userEntity;
        }

    }
}
