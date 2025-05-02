// MeditationFragment.java
package com.example.neuronudge.Fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neuronudge.Adapters.AudioAdapter;
import com.example.neuronudge.Adapters.VideoAdapter;
import com.example.neuronudge.R;
import com.example.neuronudge.models.AudioTrack;
import com.example.neuronudge.models.VideoTrack;

import java.util.ArrayList;
import java.util.List;

public class MeditationFragment extends Fragment {

    private RecyclerView nasheedRecyclerView, musicRecyclerView, videoRecyclerView;
    private List<AudioTrack> nasheedList = new ArrayList<>();
    private List<AudioTrack> musicList = new ArrayList<>();
    private List<VideoTrack> videoList = new ArrayList<>();

    private VideoView meditationVideo;
    private ImageButton playPauseButton, fullScreenButton;
    private boolean isPlaying = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meditation, container, false);

        meditationVideo = view.findViewById(R.id.meditationVideo);
        playPauseButton = view.findViewById(R.id.playPauseButton);

        meditationVideo.setVideoURI(Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.meditation_video));
        meditationVideo.setOnPreparedListener(mp -> mp.setLooping(true));
        meditationVideo.start();

        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                meditationVideo.pause();
                playPauseButton.setImageResource(android.R.drawable.ic_media_play);
            } else {
                meditationVideo.start();
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            }
            isPlaying = !isPlaying;
        });



        nasheedRecyclerView = view.findViewById(R.id.nasheedRecyclerView);
        musicRecyclerView = view.findViewById(R.id.musicRecyclerView);
        videoRecyclerView = view.findViewById(R.id.videoRecyclerView);

        nasheedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        populateTracks();

        nasheedRecyclerView.setAdapter(new AudioAdapter(nasheedList, getContext()));
        musicRecyclerView.setAdapter(new AudioAdapter(musicList, getContext()));
        videoRecyclerView.setAdapter(new VideoAdapter(videoList, getContext()));

        return view;
    }

    private void populateTracks() {
        nasheedList.add(new AudioTrack("Nasheed 1", R.raw.nasheed1, R.drawable.nasheedbag));
        nasheedList.add(new AudioTrack("Nasheed 2", R.raw.nasheed2, R.drawable.nasheed2));
        nasheedList.add(new AudioTrack("Nasheed 3", R.raw.nasheed3, R.drawable.nasheed3));
        nasheedList.add(new AudioTrack("Nasheed 4", R.raw.nasheed4, R.drawable.nasheed4));

        musicList.add(new AudioTrack("Meditation 1", R.raw.meditation1, R.drawable.musicbag));
        musicList.add(new AudioTrack("Meditation 2", R.raw.meditation2, R.drawable.musicbag));
        musicList.add(new AudioTrack("Meditation 3", R.raw.meditation3, R.drawable.musicbag));
        musicList.add(new AudioTrack("Meditation 4", R.raw.meditation4, R.drawable.musicbag));

        videoList.add(new VideoTrack("Relaxing Video 1", R.raw.relax_video));
        videoList.add(new VideoTrack("Relaxing Video 2", R.raw.relax_video));
        videoList.add(new VideoTrack("Relaxing Video 3", R.raw.relax_video));
    }
}
