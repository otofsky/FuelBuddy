package com.fuelbuddy.mobile.map.event;

import com.fuelbuddy.mobile.base.Event;

/**
 * Created by zjuroszek on 13.02.17.
 */

public class UpdateResponseEvent extends Event {

    public UpdateResponseEvent(Integer code, String message) {
        super(code, message);
    }
}
