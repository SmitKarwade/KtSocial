package com.example.instagram.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.instagram.main.MainActivity;
import com.example.instagram.R;
import com.example.instagram.login.LoginActivity;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper());
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    MaterialTextView materialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        materialTextView = findViewById(R.id.smit_text);

//        TextPaint paint = materialTextView.getPaint();

//        Shader shader = new LinearGradient(
//                0, 0, materialTextView.getWidth(), 0, new int[]{R.color.startGrad,
//                R.color.midGrad},null , Shader.TileMode.CLAMP
//        );
//        paint.setShader(shader);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser() != null){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);

    }
}