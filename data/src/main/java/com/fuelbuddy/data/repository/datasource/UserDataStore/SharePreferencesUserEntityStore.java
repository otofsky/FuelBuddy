package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zjuroszek on 18.11.16.
 */
public class SharePreferencesUserEntityStore implements UserDataStore {

    private static final String SP_USER_ENTITY = "USER_ENTITY";
   // private final SharedPreferences sharedPreferences;
    private final UserJsonEntityMapper mUserJsonEntityMapper;
    Context mContext;

    @Inject
    public SharePreferencesUserEntityStore(Context context, UserJsonEntityMapper userJsonEntityMapper) {
        //this.sharedPreferences = getAndClearSharedPreferences();
        mUserJsonEntityMapper = userJsonEntityMapper;
        mContext = context;
    }

    private SharedPreferences getAndClearSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public Observable<UserEntity> getCurrentUserEntity() {
       return getUserEntity();
    }

    @Override
    public Observable<UserEntity> setCurrentUser(final UserEntity userEntity) {
        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                Log.d("setCurrentUser", "call: " + userEntity.toString());
            }
        });
    }

    @DebugLog
    public Observable<UserEntity> getUserEntity(){
        Observable <UserEntity> observable = Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                subscriber.onNext(new UserEntity("1"));
            }
        });
        return observable;
    }
}
