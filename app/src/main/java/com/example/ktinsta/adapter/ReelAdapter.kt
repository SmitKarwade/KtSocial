package com.example.ktinsta.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ktinsta.adapter.ReelAdapter.ReelViewholder
import com.example.instagram.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.io.IOException

class ReelAdapter(
    private val context: Context,
    private val listVideos: List<Uri>,
    private val recyclerReel: RecyclerView
) :
    RecyclerView.Adapter<ReelViewholder>() {
    var toggle: Int = 0

    init {
        recyclerReel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    val currentVisibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (currentVisibleItem >= 0) {

                        for (i in 0 until recyclerView.childCount) {
                            val holder =
                                recyclerView.findViewHolderForAdapterPosition(i) as ReelViewholder?
                            holder?.stopVideo()
                        }


                        val holder =
                            recyclerView.findViewHolderForAdapterPosition(currentVisibleItem) as ReelViewholder?
                        holder?.bindVideo(listVideos[currentVisibleItem], holder)
                    }
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelViewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.reel_item, parent, false)
        return ReelViewholder(view)
    }


    override fun onBindViewHolder(holder: ReelViewholder, position: Int) {
        val videoUri = listVideos[position]
        holder.bindVideo(videoUri, holder)
    }


    override fun getItemCount(): Int {
        return listVideos.size
    }

    inner class ReelViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var surfaceView: SurfaceView = itemView.findViewById(R.id.surfaceView)
        var mediaPlayer: MediaPlayer? = null
        private var surfaceHolder: SurfaceHolder = surfaceView.holder

        private var txt_maker_name: MaterialTextView
        private var materialTextView: MaterialTextView
        private var imageButton: ImageButton
        private var imageButton2: ImageButton
        private var imageButton3: ImageButton
        private var materialButton: MaterialButton


        private val surfaceCallback = object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (mediaPlayer != null) {
                    mediaPlayer!!.setDisplay(holder)
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                stopVideo()
            }
        }

        init {
            surfaceHolder.addCallback(surfaceCallback)

            imageButton = itemView.findViewById(R.id.imageButton)
            imageButton2 = itemView.findViewById(R.id.imageButton2)
            imageButton3 = itemView.findViewById(R.id.imageButton3)
            materialButton = itemView.findViewById(R.id.materialButton)
            txt_maker_name = itemView.findViewById(R.id.txt_maker_name)
            materialTextView = itemView.findViewById(R.id.materialTextView)

            imageButton.setOnClickListener {
                if (toggle == 0) {
                    imageButton.animate()
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(200)
                        .withEndAction {

                            imageButton.setImageResource(R.drawable.like_filled_svg)


                            imageButton.animate()
                                .scaleX(1.0f) // Expand back to the original size
                                .scaleY(1.0f) // Expand back to the original size
                                .setDuration(200) // Duration of the expand animation
                                .start()
                        }
                        .start()
                    toggle = 1
                } else {
                    imageButton.animate()
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(200)
                        .withEndAction {

                            imageButton.setImageResource(R.drawable.like_svg_white)

                            imageButton.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(200)
                                .start()
                        }
                        .start()
                    toggle = 0
                }
            }
        }


        fun bindVideo(videoUri: Uri, holder: ReelViewholder) {
            if (holder.mediaPlayer == null) {
                holder.mediaPlayer = MediaPlayer()
            }else{
                holder.mediaPlayer?.reset()
            }
            try {
                holder.mediaPlayer!!.setDataSource(context, videoUri)
                holder.mediaPlayer!!.setOnPreparedListener { mp ->
                    if (holder.surfaceHolder.surface.isValid) {

                        //mp.setDisplay(holder.surfaceHolder)
                        mp.start()
                        mp.isLooping = true
                    }
                }

                holder.mediaPlayer!!.setOnErrorListener { _: MediaPlayer?, _: Int, _: Int ->
                    Toast.makeText(
                        context, "Error playing video", Toast.LENGTH_SHORT
                    ).show()
                    true
                }

                holder.mediaPlayer!!.prepareAsync()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun stopVideo() {
            if (mediaPlayer != null) {
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
                mediaPlayer = null
            }
        }
    }
}
