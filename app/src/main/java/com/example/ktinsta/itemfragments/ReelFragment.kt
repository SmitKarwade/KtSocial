package com.example.ktinsta.itemfragments

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ktinsta.adapter.ReelAdapter
import com.example.ktinsta.modal.Video
import com.example.instagram.R
import com.example.instagram.databinding.FragmentReelBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class ReelFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerReel: RecyclerView
    private lateinit var frameLayout: FrameLayout
    private lateinit var bottomNav: BottomNavigationView
    private var originalHeight: Int = 0

    private val listURI = mutableListOf<Uri>()
    private lateinit var reelAdapter: ReelAdapter

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val coleref = firebaseFirestore.collection("Videos")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentReelBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reel, container, false
        )

        val view = binding.root

        activity?.let { act ->
            toolbar = act.findViewById(R.id.toolbar)
            frameLayout = act.findViewById(R.id.frame_layout)
            bottomNav = act.findViewById(R.id.bottomNav)
        }

        // Toolbar Styling
        toolbar.apply {
            title = "Reels"
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.TextHead))
            setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

        originalHeight = frameLayout.layoutParams.height

        recyclerReel = binding.recyclerReel
        recyclerReel.layoutManager = LinearLayoutManager(requireContext())

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerReel)

        reelAdapter = ReelAdapter(requireContext(), listURI, recyclerReel)
        recyclerReel.adapter = reelAdapter

        // recyclerReel.addOnScrollListener(stateListener)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(menuProvider, viewLifecycleOwner)

        fetchReelsData()
    }

    private fun fetchReelsData() {
        coleref.get().addOnSuccessListener { queryDocumentSnapshots ->
            listURI.clear() // Avoid duplicates
            for (video in queryDocumentSnapshots) {
                val videoData = video.toObject(Video::class.java)
                listURI.add(Uri.parse(videoData.url))
            }
            reelAdapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to load reels", Toast.LENGTH_SHORT).show()
        }
    }

//    private val stateListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            when (newState) {
//                RecyclerView.SCROLL_STATE_DRAGGING -> {
//                    Toast.makeText(activity, "state DRAGGING", Toast.LENGTH_SHORT).show()
//                }
//                RecyclerView.SCROLL_STATE_SETTLING -> {
//                    Toast.makeText(activity, "state SETTLING", Toast.LENGTH_SHORT).show()
//                }
//                RecyclerView.SCROLL_STATE_IDLE -> {
//                    Toast.makeText(activity, "state IDLE", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun expandFrameLayout() {
        frameLayout.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        frameLayout.requestLayout()
    }

    private fun collapseFrameLayout() {
        frameLayout.layoutParams.height = originalHeight
        frameLayout.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar.apply {
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.TextHead))
        }
        activity?.removeMenuProvider(menuProvider)
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.reel_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.camera -> {
                    Toast.makeText(requireContext(), "Camera", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}
