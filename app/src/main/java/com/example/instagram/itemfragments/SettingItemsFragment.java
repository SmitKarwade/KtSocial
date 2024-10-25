package com.example.instagram.itemfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram.R;
import com.example.instagram.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SettingItemsFragment extends Fragment {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private TextView signOut;

    private Toolbar toolbar;
    private AppCompatActivity activity;

    public SettingItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_items, container, false);
        activity = (AppCompatActivity) getActivity();
        toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("Settings and privacy");
        toolbar.setTitleTextAppearance(getActivity(), R.style.textAppearanceToolbar);

        signOut = view.findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Toast.makeText(activity, "set onCreateView()", Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity != null){
            activity.setSupportActionBar(toolbar);

            if (activity.getSupportActionBar() != null){
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
        activity.addMenuProvider(menuProvider);
        Toast.makeText(activity, "set onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(activity, "set onPause()", Toast.LENGTH_SHORT).show();
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        activity.removeMenuProvider(menuProvider);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(activity, "Set onViewCreated()", Toast.LENGTH_SHORT).show();
        activity.addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    private MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            if (menuItem.getItemId() == android.R.id.home){
                if (activity != null){
                    activity.getSupportFragmentManager().popBackStack();
                }
                return true;
            }
            // handle other items
            return false;
        }
    };

}