package com.example.neuronudge.models;
public class AudioTrack {
    private String title;
    private int audioResId;
    private int imageResId;

    public AudioTrack(String title, int audioResId, int imageResId) {
        this.title = title;
        this.audioResId = audioResId;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public int getImageResId() {
        return imageResId;
    }
}
