package com.example.instagram.itemfragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.instagram.R;
import com.example.instagram.adapter.ReelAdapter;
import com.example.instagram.databinding.FragmentReelBinding;
import com.example.instagram.modal.Video;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ReelFragment extends Fragment {
    private Toolbar toolbar;
    RecyclerView recyclerReel;
    private List<Uri> listURI;
    private ReelAdapter reelAdapter;
    private FrameLayout frame_layout;
    private BottomNavigationView bottomNav;
    private int originalHeight;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference coleref = firebaseFirestore.collection("Videos");


    public ReelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentReelBinding fragmentReelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reel, container, false);
        View view = fragmentReelBinding.getRoot();

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Reels");
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.TextHead));
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.white));

        listURI = new ArrayList<>();

        frame_layout = getActivity().findViewById(R.id.frame_layout);
        originalHeight = frame_layout.getLayoutParams().height;
        bottomNav = getActivity().findViewById(R.id.bottomNav);

        recyclerReel = fragmentReelBinding.recyclerReel;
        recyclerReel.setLayoutManager(new LinearLayoutManager(getContext()));




//        listURI.add(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.black_myth_wukong));
//        listURI.add(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.black_myth_wukong));
//        listURI.add(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.black_myth_wukong));


        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerReel);

        recyclerReel.removeOnScrollListener(stateListener);
        recyclerReel.addOnScrollListener(stateListener);



        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        coleref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot video:queryDocumentSnapshots
                ) {
                    Video video1 = video.toObject(Video.class);

                    listURI.add(Uri.parse(video1.getUrl()));

                    reelAdapter = new ReelAdapter(getContext(), listURI, recyclerReel);
                    recyclerReel.setAdapter(reelAdapter);

                    reelAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private RecyclerView.OnScrollListener stateListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                Toast.makeText(getActivity(), "state DRAGGING", Toast.LENGTH_SHORT).show();
//                expandFrameLayout();
//                bottomNav.animate().translationY(Float.parseFloat(bottomNav.getHeight() + "")).setDuration(200).start();
            } else if (newState == RecyclerView.SCROLL_STATE_SETTLING){
                Toast.makeText(getActivity(), "state SETTLING", Toast.LENGTH_SHORT).show();
            }else if (newState == RecyclerView.SCROLL_STATE_IDLE){
                Toast.makeText(getActivity(), "state IDLE", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void expandFrameLayout() {
        ViewGroup.LayoutParams params = frame_layout.getLayoutParams();
        if (params.height != ViewGroup.LayoutParams.MATCH_PARENT) {
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            frame_layout.setLayoutParams(params);
        }
    }

    // Method to collapse FrameLayout back to its original height
    private void collapseFrameLayout() {
        ViewGroup.LayoutParams params = frame_layout.getLayoutParams();
        if (params.height != originalHeight) {
            params.height = originalHeight;
            frame_layout.setLayoutParams(params);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().addMenuProvider(menuProvider);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.TextHead));
        getActivity().removeMenuProvider(menuProvider);
    }


    private MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.reel_menu, menu);
        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            if (id == R.id.camera){
                Toast.makeText(getContext(), "Camera", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
}