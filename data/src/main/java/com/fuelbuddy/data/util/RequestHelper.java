package com.fuelbuddy.data.util;

import okhttp3.MediaType;

/**
 * Created by zjuroszek on 17.01.17.
 */

public class RequestHelper {



    public static MediaType getMediaType(String mediaType) {
        MediaType mt = null;
        if (mediaType != null && mediaType.length() != 0) {
            mt = MediaType.parse(mediaType);
        } else {
            throw new IllegalArgumentException("MediaType can't be null or empty string");
        }
        return mt;
    }

}
