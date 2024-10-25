package com.example.instagram.storyactivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.instagram.R;
import com.example.instagram.databinding.ActivityStoryViewBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class StoryViewActivity extends AppCompatActivity {

    private ImageView story_read;
    private Handler handler = new Handler(Looper.getMainLooper());
    private LinearProgressIndicator progres_linear;
    private int progress = 0;
    private ActivityStoryViewBinding activityStoryViewBinding;
    private BottomAppBar bottom_bar;

    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);

        activityStoryViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_story_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottom_bar = findViewById(R.id.bottom_bar);

        story_read = findViewById(R.id.story_read);
        progres_linear = findViewById(R.id.progres_linear);

        String data = getIntent().getStringExtra("ContentURI");
        Uri imageUri = Uri.parse(data);
//        Glide.with(StoryViewActivity.this).load(imageUri).into(story_read).onLoadFailed(null);
        Log.d("GlideLoad", "" + imageUri);
        activityStoryViewBinding.setData(imageUri);

        progres_linear.setIndeterminate(false);
        progres_linear.setProgress(progress);
        progres_linear.setIndicatorColor(ContextCompat.getColor(this, R.color.progressIndicator));
        progres_linear.setTrackColor(ContextCompat.getColor(this , R.color.transparent));

        story_read.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isPaused = true;
                        bottom_bar.setVisibility(BottomAppBar.INVISIBLE);
                        progres_linear.setVisibility(BottomAppBar.INVISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        isPaused = false;
                        bottom_bar.setVisibility(BottomAppBar.VISIBLE);
                        progres_linear.setVisibility(BottomAppBar.VISIBLE);
                        break;
                }
                return true;
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress < 100 && !isPaused) {
                    progress += 1;
                    progres_linear.setProgress(progress);
                }
                if (progress == 100){
                    finish();
                }
                handler.postDelayed(this, 100);
            }
        }, 100);


    }
}