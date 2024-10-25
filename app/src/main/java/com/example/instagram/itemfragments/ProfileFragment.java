package com.example.instagram.itemfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagram.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ProfileFragment extends Fragment {
    private Toolbar materialToolbar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        materialToolbar = getActivity().findViewById(R.id.toolbar);
        materialToolbar.setTitle("Username");
        materialToolbar.setTitleTextAppearance(getActivity(), R.style.textAppearanceToolbar);
        Toast.makeText(getActivity(), "pro onCreateView()", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
        Toast.makeText(getActivity(), "pro onViewCreated()", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPause() {
        super.onPause();
        materialToolbar.removeMenuProvider(menuProvider);
        Toast.makeText(getContext(), "pro onPause()", Toast.LENGTH_SHORT).show();
    }

    private MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            materialToolbar.getMenu().clear();
            menuInflater.inflate(R.menu.profile_menu, menu);
        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            int profileID = menuItem.getItemId();
            if (profileID == R.id.setting) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.frame_layout, new SettingItemsFragment()).
                        addToBackStack(null).
                        commit();
            } else if (profileID == R.id.create_post) {
                Toast.makeText(getContext(), "Create post", Toast.LENGTH_SHORT).show();
            } else if (profileID == R.id.threads) {
                Toast.makeText(getContext(), "Threads", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
}