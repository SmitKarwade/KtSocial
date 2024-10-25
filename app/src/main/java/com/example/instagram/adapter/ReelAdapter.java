package com.example.instagram.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.List;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.ReelViewholder> {

    private Context context;
    private List<Uri> listVideos;
    private RecyclerView recyclerReel;

    int toggle = 0;

    public ReelAdapter(Context context, List<Uri> listVideos, RecyclerView recyclerReel) {
        this.context = context;
        this.listVideos = listVideos;
        this.recyclerReel = recyclerReel;

        this.recyclerReel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Find the current visible item
                    int currentVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (currentVisibleItem >= 0) {
                        // Stop video playback for all items
                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            ReelViewholder holder = (ReelViewholder) recyclerView.findViewHolderForAdapterPosition(i);
                            if (holder != null) {
                                holder.stopVideo();
                            }
                        }

                        // Start video for the currently visible item
                        ReelViewholder holder = (ReelViewholder) recyclerView.findViewHolderForAdapterPosition(currentVisibleItem);
                        if (holder != null) {
                            holder.bindVideo(listVideos.get(currentVisibleItem));
                        }
                    }
                }
            }


//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                // Optionally handle scrolling if needed
//            }

//            private boolean isViewVisible(RecyclerView recyclerView, View view) {
//                Rect rect = new Rect();
//                recyclerView.getGlobalVisibleRect(rect);
//                Rect viewRect = new Rect();
//                view.getGlobalVisibleRect(viewRect);
//                return Rect.intersects(rect, viewRect);
//            }
        });
    }

    @NonNull
    @Override
    public ReelViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reel_item, parent, false);
        return new ReelViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReelViewholder holder, int position) {
        Uri videoUri = listVideos.get(position);
        holder.bindVideo(videoUri);
    }


    @Override
    public int getItemCount() {
        return listVideos.size();
    }

    public class ReelViewholder extends RecyclerView.ViewHolder {
        SurfaceView surfaceView;
        MediaPlayer mediaPlayer;
        SurfaceHolder surfaceHolder;

        MaterialTextView txt_maker_name, materialTextView;
        ImageButton imageButton, imageButton2, imageButton3;
        MaterialButton materialButton;


        public ReelViewholder(@NonNull View itemView) {
            super(itemView);

            surfaceView = itemView.findViewById(R.id.surfaceView);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(surfaceCallback);

            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton2 = itemView.findViewById(R.id.imageButton2);
            imageButton3 = itemView.findViewById(R.id.imageButton3);
            materialButton = itemView.findViewById(R.id.materialButton);
            txt_maker_name = itemView.findViewById(R.id.txt_maker_name);
            materialTextView = itemView.findViewById(R.id.materialTextView);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toggle == 0){
                        imageButton.animate()
                                .scaleX(0.8f) // Shrink to 80% of the original size
                                .scaleY(0.8f) // Shrink to 80% of the original size
                                .setDuration(200) // Duration of the shrink animation
                                .withEndAction(() -> {
                                    // Change the image resource after shrinking animation ends
                                    imageButton.setImageResource(R.drawable.like_filled_svg);

                                    // Start expanding the button
                                    imageButton.animate()
                                            .scaleX(1.0f) // Expand back to the original size
                                            .scaleY(1.0f) // Expand back to the original size
                                            .setDuration(200) // Duration of the expand animation
                                            .start();
                                })
                                .start();
                        toggle = 1;
                    }else {
                        imageButton.animate()
                                .scaleX(0.8f) // Shrink to 80% of the original size
                                .scaleY(0.8f) // Shrink to 80% of the original size
                                .setDuration(200) // Duration of the shrink animation
                                .withEndAction(() -> {
                                    // Change the image resource after shrinking animation ends
                                    imageButton.setImageResource(R.drawable.like_svg_white);

                                    // Start expanding the button
                                    imageButton.animate()
                                            .scaleX(1.0f) // Expand back to the original size
                                            .scaleY(1.0f) // Expand back to the original size
                                            .setDuration(200) // Duration of the expand animation
                                            .start();
                                })
                                .start();
                        toggle = 0;
                    }
                }
            });

        }


        private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (mediaPlayer != null) {
                    mediaPlayer.setDisplay(holder);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                stopVideo();
            }
        };

        public void bindVideo(Uri videoUri) {
            if (mediaPlayer == null){
                mediaPlayer = new MediaPlayer();
            }else {
                mediaPlayer.reset();
            }
            try {
                mediaPlayer.setDataSource(context, videoUri);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (surfaceHolder.getSurface().isValid()){
                            mp.setDisplay(surfaceHolder);
                            mp.start(); // Start video playback
                            mp.setLooping(true);
                        }
                    }
                });

                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    Toast.makeText(context, "Error playing video", Toast.LENGTH_SHORT).show();
                    return true;
                });

                mediaPlayer.prepareAsync(); // Asynchronous preparation
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stopVideo() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
}
