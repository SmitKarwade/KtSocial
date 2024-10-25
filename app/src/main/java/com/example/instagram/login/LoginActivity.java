package com.example.instagram.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.instagram.main.MainActivity;
import com.example.instagram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    MaterialTextView materialTextView;
    TextInputLayout email_text_layout, pass_text_layout;
    TextInputEditText pass_text, mail_text;

    MaterialButton login_btn;
    MaterialButton create_acc_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // login with validation
        
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail_text.getText().toString().trim().isEmpty()){
                    email_text_layout.setError("Mail cannot be empty");
                } else if (pass_text.getText().toString().trim().isEmpty()){
                    pass_text_layout.setError("Password required");
                }else {
                    signIn(mail_text.getText().toString().trim(), pass_text.getText().toString().trim());
                }

            }
        });


        // button handling
        login_btn = findViewById(R.id.login_btn);
        create_acc_btn = findViewById(R.id.create_acc_btn);

        create_acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccActivity.class);
                startActivity(intent);
            }
        });

        // set position start

        mail_text = findViewById(R.id.mail_text);
        pass_text = findViewById(R.id.pass_text);

        mail_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    mail_text.scrollTo(0, 0);
                }
            }
        });

        pass_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    pass_text.scrollTo(0, 0);
                }
            }
        });


        // login validation
        email_text_layout = findViewById(R.id.email_text_layout);
        pass_text_layout = findViewById(R.id.pass_text_layout);
        pass_text = findViewById(R.id.pass_text);
        mail_text = findViewById(R.id.mail_text);

        mail_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                mailValidation(text);
            }
        });
        

        pass_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pass_text.getText().toString().trim();
                passValidation(password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void signIn(String email, String password) {
        email_text_layout.setError(null);
        pass_text_layout.setError(null);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    String errorCode = ((FirebaseAuthInvalidCredentialsException) task.getException()).getErrorCode();
                    if (!errorCode.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean mailValidation(String text){
        String regex = "^[a-zA-Z0-9_%.+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (text.isEmpty()){
            email_text_layout.setError("Email cannot be empty");
            return false;
        } else if (!text.matches(regex)) {
            email_text_layout.setError("Invalid email address");
            return false;
        }else {
            email_text_layout.setError(null);
            return true;
        }
    }
    
    private boolean passValidation(String text){
        String regex = "^[a-zA-Z0-9@#$&_.]+$";
        if (text.isEmpty()) {
            pass_text_layout.setError("Password required");
            return false;
        } else if (!text.matches(regex)){
            pass_text_layout.setError("Enter valid password");
            return false;
        }else {
            pass_text_layout.setError(null);
            return true;
        }
    }
}