package com.example.ktinsta.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ktinsta.main.MainActivity
import com.example.instagram.R
import com.google.android.material.textview.MaterialTextView

class SplashActivity : AppCompatActivity() {
    var handler: Handler = Handler(Looper.getMainLooper())
    //var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var materialTextView: MaterialTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        materialTextView = findViewById(R.id.smit_text)


        //        TextPaint paint = materialTextView.getPaint();

//        Shader shader = new LinearGradient(
//                0, 0, materialTextView.getWidth(), 0, new int[]{R.color.startGrad,
//                R.color.midGrad},null , Shader.TileMode.CLAMP
//        );
//        paint.setShader(shader);

//        handler.postDelayed({
//            if (firebaseAuth.currentUser != null) {
//                val intent = Intent(
//                    this@SplashActivity,
//                    MainActivity::class.java
//                )
//                startActivity(intent)
//                finish()
//            } else {
//                val intent = Intent(
//                    this@SplashActivity,
//                    LoginActivity::class.java
//                )
//                startActivity(intent)
//                finish()
//            }
//        }, 2000)

        handler.postDelayed({
            val intent = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 2000)
    }
}