package com.fuelbuddy.interactor;

import com.fuelbuddy.data.model.User;

import java.util.List;


import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class GetCurrentUser  {
    @Inject
    public GetCurrentUser() {
    }
    @DebugLog
    public Observable<User> getCurrentUser(){
        Observable <User> observable = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                subscriber.onNext(new User("1"));
            }
        });
        return observable;
    }


}

