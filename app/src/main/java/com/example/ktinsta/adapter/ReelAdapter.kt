package com.example.ktinsta.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.instagram.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class ReelAdapter(
    private val context: Context,
    private val listVideos: List<Uri>,
    private val recyclerReel: RecyclerView
) : RecyclerView.Adapter<ReelAdapter.ReelViewHolder>() {

    var currentPlayingHolder: ReelViewHolder? = null

    init {
        recyclerReel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val currentVisibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (currentVisibleItem >= 0) {

                        currentPlayingHolder?.stopVideo()

                        val holder =
                            recyclerView.findViewHolderForAdapterPosition(currentVisibleItem) as? ReelViewHolder
                        holder?.bindVideo(listVideos[currentVisibleItem])
                        currentPlayingHolder = holder
                    }
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reel_item, parent, false)
        return ReelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReelViewHolder, position: Int) {
        if (position == 0) {
            holder.bindVideo(listVideos[position])
            currentPlayingHolder = holder
        }
    }

    override fun getItemCount(): Int = listVideos.size

    inner class ReelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playerView: PlayerView = itemView.findViewById(R.id.playerView)
        private var exoPlayer: ExoPlayer? = null

        private var txtMakerName: MaterialTextView = itemView.findViewById(R.id.txt_maker_name)
        private var materialTextView: MaterialTextView = itemView.findViewById(R.id.materialTextView)
        private var likeButton: ImageButton = itemView.findViewById(R.id.imageButton)
        private var commentButton: ImageButton = itemView.findViewById(R.id.imageButton2)
        private var shareButton: ImageButton = itemView.findViewById(R.id.imageButton3)
        private var followButton: MaterialButton = itemView.findViewById(R.id.materialButton)

        private var isLiked = false

        init {
            likeButton.setOnClickListener {
                isLiked = !isLiked
                likeButton.setImageResource(if (isLiked) R.drawable.like_filled_svg else R.drawable.like_svg_white)
            }
        }

        @OptIn(UnstableApi::class)
        fun bindVideo(videoUri: Uri) {
            stopVideo() // Stop any previous playback

            exoPlayer = ExoPlayer.Builder(context).build().also { player ->
                playerView.player = player
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                val mediaItem = MediaItem.fromUri(videoUri)
                player.setMediaItem(mediaItem)
                player.prepare()
                player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                player.playWhenReady = true
                player.repeatMode = ExoPlayer.REPEAT_MODE_ONE
            }
        }

        fun stopVideo() {
            exoPlayer?.release()
            exoPlayer = null
        }
    }
}
