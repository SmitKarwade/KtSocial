package com.example.instagram.itemfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagram.R;
import com.example.instagram.adapter.ImageAdapter;
import com.example.instagram.adapter.StoryAdapter;
import com.example.instagram.databinding.FragmentHomeBinding;
import com.example.instagram.modal.Photo;
import com.example.instagram.viewmodel.ViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView postView, storyView;
    private ImageAdapter imageAdapter;
    private ViewModel viewModel;
    private List<Photo> listOfPhoto = new ArrayList<>();
    private FragmentHomeBinding fragmentHomeBinding;

    private StoryAdapter storyAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Instagram");

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        postView = fragmentHomeBinding.postView;
        postView.setLayoutManager(new LinearLayoutManager(getActivity()));

        storyView = fragmentHomeBinding.storyView;
        storyView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        imageAdapter = new ImageAdapter(listOfPhoto, getActivity());
        postView.setAdapter(imageAdapter);

        storyAdapter = new StoryAdapter(listOfPhoto, getActivity());
        storyView.setAdapter(storyAdapter);


        viewModel.getViewImages().observe(getActivity(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                listOfPhoto.clear();

                listOfPhoto.addAll(photos);

                imageAdapter.notifyDataSetChanged();
                storyAdapter.notifyDataSetChanged();
            }
        });
        postView.removeOnScrollListener(listener);
        postView.addOnScrollListener(listener);
        return view;
    }

    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Toast.makeText(getActivity(), "scrolled1", Toast.LENGTH_SHORT).show();
            if (dy > 0){
                toolbar.animate().translationY(-(toolbar.getHeight())).setDuration(400).start();
            } else if (dy < 0) {
                toolbar.animate().translationY(0).setDuration(400).start();
            }

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(menuProvider);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().removeMenuProvider(menuProvider);
    }

    private MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.toolbar_menu, menu);
        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            if (id == R.id.like){
                Toast.makeText(getActivity(), "Liked", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.send) {
                Toast.makeText(getActivity(), "Sent", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };


}