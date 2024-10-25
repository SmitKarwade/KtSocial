package com.example.instagram.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.instagram.R;
import com.example.instagram.itemfragments.AddFragment;
import com.example.instagram.itemfragments.HomeFragment;
import com.example.instagram.itemfragments.ProfileFragment;
import com.example.instagram.itemfragments.ReelFragment;
import com.example.instagram.itemfragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImageView imageView;
    private Toolbar toolbar;

    private NavigationBarView.OnItemSelectedListener itemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int getId = item.getItemId();
            if (getId == R.id.insta_home){
                loadFragment(new HomeFragment());
            } else if (getId == R.id.search) {
                loadFragment(new SearchFragment());
            } else if (getId == R.id.add) {
                loadFragment(new AddFragment());
            } else if (getId == R.id.reels) {
                loadFragment(new ReelFragment());
            } else if (getId == R.id.cstm_icon) {
                loadFragment(new ProfileFragment());
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null){
            loadFragment(new HomeFragment());
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNav);

        View customView = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_item_layout, null);

        imageView = customView.findViewById(R.id.cstm_user);
        bottomNavigationView.getMenu().findItem(R.id.cstm_icon).setActionView(customView);

        bottomNavigationView.setOnItemSelectedListener(itemSelectedListener);

    }



    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.frame_layout, fragment).
                commit();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
//    }
}