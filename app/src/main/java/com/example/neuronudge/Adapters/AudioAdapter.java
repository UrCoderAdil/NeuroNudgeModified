package com.example.neuronudge.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neuronudge.R;
import com.example.neuronudge.models.AudioTrack;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {

    private List<AudioTrack> trackList;
    private Context context;
    private MediaPlayer mediaPlayer;
    private int currentlyPlayingPosition = -1;

    public AudioAdapter(List<AudioTrack> trackList, Context context) {
        this.trackList = trackList;
        this.context = context;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        AudioTrack track = trackList.get(position);
        holder.title.setText(track.getTitle());
        holder.image.setImageResource(track.getImageResId());

        // Set initial icon based on position
        if (position == currentlyPlayingPosition && mediaPlayer != null && mediaPlayer.isPlaying()) {
            holder.playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            holder.playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }

        holder.playPauseButton.setOnClickListener(v -> {
            if (mediaPlayer != null && currentlyPlayingPosition == position) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    holder.playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    holder.playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                }
            } else {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    notifyItemChanged(currentlyPlayingPosition); // update previous item icon
                }
                mediaPlayer = MediaPlayer.create(context, track.getAudioResId());
                mediaPlayer.start();
                currentlyPlayingPosition = position;
                holder.playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        ImageButton playPauseButton;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.audioTitle);
            image = itemView.findViewById(R.id.audioImage);
            playPauseButton = itemView.findViewById(R.id.audioPlayPauseButton);
        }
    }
}
