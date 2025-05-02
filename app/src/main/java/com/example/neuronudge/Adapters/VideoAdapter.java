package com.example.neuronudge.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neuronudge.R;
import com.example.neuronudge.models.VideoTrack;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoTrack> videoList;
    private final Context context;

    public VideoAdapter(List<VideoTrack> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoTrack track = videoList.get(position);
        holder.title.setText(track.getTitle());

        Uri videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + track.getVideoResId());
        holder.videoView.setVideoURI(videoUri);
        holder.videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            holder.playPauseBtn.setImageResource(android.R.drawable.ic_media_play); // set play icon initially
        });


        holder.playPauseBtn.setOnClickListener(v -> {
            if (holder.videoView.isPlaying()) {
                holder.videoView.pause();
                holder.playPauseBtn.setImageResource(android.R.drawable.ic_media_play);
            } else {
                holder.videoView.start();
                holder.playPauseBtn.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageButton playPauseBtn;
        TextView title;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.recyclerVideoView);
            playPauseBtn = itemView.findViewById(R.id.recyclerVideoPlayPause);
            title = itemView.findViewById(R.id.videoTitle);
        }
    }
}
