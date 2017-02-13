package com.fuelbuddy.mobile.map.event;

import com.fuelbuddy.mobile.base.Event;

/**
 * Created by zjuroszek on 13.02.17.
 */

public class ResponseEvent extends Event {

    public ResponseEvent(Integer code, String message) {
        super(code, message);
    }
}
