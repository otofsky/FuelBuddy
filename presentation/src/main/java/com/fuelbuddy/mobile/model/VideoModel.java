package com.fuelbuddy.mobile.model;

/**
 * Created by zjuroszek on 11.01.17.
 */

public class VideoModel {

    private final String mVideo;

    public VideoModel(String video) {
        mVideo = video;
    }

    public String getVideo() {
        return mVideo;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "mVideo='" + mVideo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoModel that = (VideoModel) o;
        return mVideo != null ? mVideo.equals(that.mVideo) : that.mVideo == null;

    }

    @Override
    public int hashCode() {
        return mVideo != null ? mVideo.hashCode() : 0;
    }
}


