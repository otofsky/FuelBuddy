package com.fuelbuddy.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.EntityJsonMapper;
import com.fuelbuddy.data.exeption.UserNotFoundException;

import org.json.JSONException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class SharePreferencesUserCacheImpl implements UserCache {

    private static final String SP_USER_ENTITY = "USER_ENTITY";
    private static final String SP_TOKEN_ENTITY = "TOKEN_ENTITY";

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
        return Observable.create(emiter -> {
            try {
                UserEntity userEntity = entityJsonMapper.fromJson(sharedPreferences.getString(SP_USER_ENTITY, ""));
                if (userEntity != null) {
                    emiter.onNext(userEntity);
                    emiter.onComplete();
                } else {
                    emiter.onError(new UserNotFoundException());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                emiter.onError(e);
            }
        });
    }

    @Override
    public Observable<Boolean> delete() {
        return Observable.create(emiter -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(SP_USER_ENTITY);
            editor.remove(SP_TOKEN_ENTITY);
            editor.apply();
            emiter.onNext(true);
        });
    }

    @Override
    public Observable<ResponseEntity> putUser(final UserEntity userEntity) {
        return Observable.create(emiter -> {
                    try {
                        sharedPreferences.edit().putString(SP_USER_ENTITY, entityJsonMapper.toJson(userEntity)).apply();
                        ResponseEntity responseEntity = new ResponseEntity();
                        responseEntity.setMessage("User added");
                        emiter.onNext(responseEntity);
                    } catch (JSONException e) {
                        emiter.onError(e);
                    }
                }
        );
    }

    @Override
    public Observable<ResponseEntity> putToken(final ResponseEntity responseEntity) {
        return Observable.create(emiter -> {
            sharedPreferences.edit().putString(SP_TOKEN_ENTITY, responseEntity.getMessage()).apply();
            ResponseEntity responseE = new ResponseEntity();
            responseE.setMessage("Token added");
            emiter.onNext(responseE);

        });
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(SP_TOKEN_ENTITY, "");
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
