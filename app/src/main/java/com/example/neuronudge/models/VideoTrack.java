
package com.example.neuronudge.models;

public class VideoTrack {
    private String title;
    private int videoResId;

    public VideoTrack(String title, int videoResId) {
        this.title = title;
        this.videoResId = videoResId;
    }

    public String getTitle() {
        return title;
    }

    public int getVideoResId() {
        return videoResId;
    }
}
