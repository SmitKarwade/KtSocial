package com.example.ktinsta.storyactivity

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.databinding.ActivityStoryViewBinding

class StoryViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryViewBinding
    private lateinit var handler: Handler
    private var progress = 0
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_story_view)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handler = Handler(Looper.getMainLooper())

        val data = intent.getStringExtra("ContentURI")
        val imageUri = data?.let { Uri.parse(it) }
        Log.d("GlideLoad", "URI: $imageUri")

        imageUri?.let {
            Glide.with(this).load(it).into(binding.storyRead)
        }

        binding.progresLinear.apply {
            progress = 0
            setIndicatorColor(ContextCompat.getColor(this@StoryViewActivity, R.color.progressIndicator))
            setTrackColor(ContextCompat.getColor(this@StoryViewActivity, R.color.transparent))
        }

        binding.storyRead.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isPaused = true
                    binding.bottomBar.visibility = View.INVISIBLE
                    binding.progresLinear.visibility = View.INVISIBLE
                }
                MotionEvent.ACTION_UP -> {
                    isPaused = false
                    binding.bottomBar.visibility = View.VISIBLE
                    binding.progresLinear.visibility = View.VISIBLE
                    v.performClick() // Ensures accessibility compatibility
                }
            }
            true
        }

// Also, override performClick() in case it's needed
        binding.storyRead.setOnClickListener {
            // Handle click event if needed
        }


        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress < 100 && !isPaused) {
                    progress += 1
                    binding.progresLinear.progress = progress
                }
                if (progress == 100) {
                    finish()
                } else {
                    handler.postDelayed(this, 100)
                }
            }
        }, 100)
    }
}
