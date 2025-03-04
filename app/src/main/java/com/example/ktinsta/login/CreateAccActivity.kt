package com.example.ktinsta.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class CreateAccActivity : AppCompatActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var userNameText: TextInputLayout
    private lateinit var emailTextLayout: TextInputLayout
    private lateinit var passTextLayout: TextInputLayout
    private lateinit var usernameText: TextInputEditText
    private lateinit var emailText: TextInputEditText
    private lateinit var passwordText: TextInputEditText
    private lateinit var addImage: MaterialButton
    private lateinit var createBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userNameText = findViewById(R.id.username_txt_layout)
        emailTextLayout = findViewById(R.id.email_txt_layout)
        passTextLayout = findViewById(R.id.pass_txt_layout)
        usernameText = findViewById(R.id.username_txt)
        emailText = findViewById(R.id.email_txt)
        passwordText = findViewById(R.id.password_txt)
        addImage = findViewById(R.id.add_img)
        createBtn = findViewById(R.id.create_btn)

        createBtn.setOnClickListener {
            val username = usernameText.text?.toString()?.trim()
            val mail_str = emailText.text?.toString()?.trim()
            val password = passwordText.text?.toString()?.trim()

            if (username.isNullOrEmpty() || mail_str.isNullOrEmpty() || password.isNullOrEmpty()) {
                return@setOnClickListener
            }

            if (user_validation(username) && email_validation(mail_str) && password_validation(password)) {
                firebaseAuth.createUserWithEmailAndPassword(mail_str, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emailText.setText("")
                            usernameText.setText("")
                            passwordText.setText("")
                            userNameText.error = null
                            emailTextLayout.error = null
                            passTextLayout.error = null

                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            emailTextLayout.error = task.exception?.message
                        }
                    }
            }
        }

        emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                email_validation(emailText.text.toString().trim())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        passwordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password_validation(passwordText.text.toString().trim())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        usernameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                user_validation(usernameText.text.toString().trim())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun user_validation(text: String): Boolean {
        val regex = "^[a-zA-Z0-9@#$&_.]+$"
        return when {
            text.isEmpty() -> {
                userNameText.error = "Please enter username"
                false
            }
            !text.matches(regex.toRegex()) -> {
                userNameText.error = "Enter valid username"
                false
            }
            else -> {
                userNameText.error = null
                true
            }
        }
    }

    private fun password_validation(text: String): Boolean {
        val regex = "^[a-zA-Z0-9@#$&_]+$"
        return when {
            text.isEmpty() -> {
                passTextLayout.error = "Password required"
                false
            }
            !text.matches(regex.toRegex()) -> {
                passTextLayout.error = "Enter valid password"
                false
            }
            text.length < 8 || text.length > 10 -> {
                passTextLayout.error = "Password must be between 8 to 10 characters"
                false
            }
            !text.contains("@") && !text.contains("#") && !text.contains("&") &&
                    !text.contains("$") && !text.contains("_") -> {
                passTextLayout.error = "At least one special character required"
                false
            }
            else -> {
                passTextLayout.error = null
                true
            }
        }
    }

    private fun email_validation(text: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return when {
            text.isEmpty() -> {
                emailTextLayout.error = "Email cannot be empty"
                false
            }
            !text.matches(regex.toRegex()) -> {
                emailTextLayout.error = "Invalid email address"
                false
            }
            else -> {
                emailTextLayout.error = null
                true
            }
        }
    }
}
