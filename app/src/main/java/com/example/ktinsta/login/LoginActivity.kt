package com.example.ktinsta.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ktinsta.main.MainActivity
import com.example.instagram.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var emailTextLayout: TextInputLayout
    private lateinit var passTextLayout: TextInputLayout
    private lateinit var passText: TextInputEditText
    private lateinit var mailText: TextInputEditText
    private lateinit var loginBtn: MaterialButton
    private lateinit var createAccBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        emailTextLayout = findViewById(R.id.email_text_layout)
        passTextLayout = findViewById(R.id.pass_text_layout)
        passText = findViewById(R.id.pass_text)
        mailText = findViewById(R.id.mail_text)
        loginBtn = findViewById(R.id.login_btn)
        createAccBtn = findViewById(R.id.create_acc_btn)

        // Login button click listener
        loginBtn.setOnClickListener {
            val email = mailText.text.toString().trim()
            val password = passText.text.toString().trim()

            if (!mailValidation(email) || !passValidation(password)) {
                return@setOnClickListener
            }
            signIn(email, password)
        }

        // Create account button click listener
        createAccBtn.setOnClickListener {
            startActivity(Intent(this, CreateAccActivity::class.java))
        }

        // Prevent scrolling when losing focus
        val focusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) v.scrollTo(0, 0)
        }
        mailText.onFocusChangeListener = focusChangeListener
        passText.onFocusChangeListener = focusChangeListener

        // Add text watchers for validation
        mailText.addTextChangedListener(validationTextWatcher { text ->
            mailValidation(text)
        })
        passText.addTextChangedListener(validationTextWatcher { text ->
            passValidation(text)
        })
    }

    private fun signIn(email: String, password: String) {
        emailTextLayout.error = null
        passTextLayout.error = null

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val errorMessage = when (task.exception) {
                    is FirebaseAuthInvalidCredentialsException -> "Invalid Credentials"
                    else -> "Login Failed"
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mailValidation(text: String): Boolean {
        val regex = "^[a-zA-Z0-9_%.+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        return when {
            text.isEmpty() -> {
                emailTextLayout.error = "Email cannot be empty"
                false
            }
            !text.matches(regex) -> {
                emailTextLayout.error = "Invalid email address"
                false
            }
            else -> {
                emailTextLayout.error = null
                true
            }
        }
    }

    private fun passValidation(text: String): Boolean {
        val regex = "^[a-zA-Z0-9@#$&_.]+$".toRegex()
        return when {
            text.isEmpty() -> {
                passTextLayout.error = "Password required"
                false
            }
            !text.matches(regex) -> {
                passTextLayout.error = "Enter valid password"
                false
            }
            else -> {
                passTextLayout.error = null
                true
            }
        }
    }

    private fun validationTextWatcher(validate: (String) -> Boolean): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validate(s?.toString()?.trim().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }
}
