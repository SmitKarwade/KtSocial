package com.example.ktinsta.itemfragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ktinsta.adapter.StoryAdapter
import com.example.ktinsta.dataorexception.DataOrException
import com.example.ktinsta.modal.PexelResponse
import com.example.instagram.R
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.ktinsta.paging.ImagePagingAdapter
import com.example.ktinsta.paging.ImageViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var toolbar: Toolbar
    private lateinit var postView: RecyclerView
    private lateinit var storyView: RecyclerView
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var imagePagingAdapter: ImagePagingAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.title = "Instagram"

        val viewModel: ImageViewModel = ViewModelProvider(requireActivity())[ImageViewModel::class.java]

        postView = binding!!.postView
        postView.layoutManager = LinearLayoutManager(requireActivity())
        postView.hasFixedSize()
        postView.setItemViewCacheSize(10)

        storyView = binding!!.storyView
        storyView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        storyView.hasFixedSize()
        storyView.setItemViewCacheSize(10)


        imagePagingAdapter = ImagePagingAdapter(requireActivity())
        postView.adapter = imagePagingAdapter

        storyAdapter = StoryAdapter(requireActivity())
        storyView.adapter = storyAdapter


        lifecycleScope.launch {
            viewModel.movieList.collect { pagingData ->
                imagePagingAdapter.submitData(pagingData)
                storyAdapter.submitData(pagingData)
            }
        }

        postView.removeOnScrollListener(listener)
        postView.addOnScrollListener(listener)

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner)
    }

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                toolbar.animate().translationY(-toolbar.height.toFloat()).setDuration(400).start()
            } else if (dy < 0) {
                toolbar.animate().translationY(0f).setDuration(400).start()
            }
        }
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.like -> {
                    Toast.makeText(requireActivity(), "Liked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.send -> {
                    Toast.makeText(requireActivity(), "Sent", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
