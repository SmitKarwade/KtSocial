package com.example.instagram.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.instagram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private TextInputLayout username_txt_layout, email_txt_layout, pass_txt_layout;
    private TextInputEditText username_txt, email_txt, password_txt;
    private MaterialButton add_img, create_btn;

    private String mail_str;
    private String password;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_acc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username_txt_layout = findViewById(R.id.username_txt_layout);
        email_txt_layout = findViewById(R.id.email_txt_layout);
        pass_txt_layout = findViewById(R.id.pass_txt_layout);

        username_txt = findViewById(R.id.username_txt);
        email_txt = findViewById(R.id.email_txt);
        password_txt = findViewById(R.id.password_txt);

        add_img = findViewById(R.id.add_img);
        create_btn = findViewById(R.id.create_btn);

        // login
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_validation(username) && email_validation(mail_str) && password_validation(password)){
                    firebaseAuth.createUserWithEmailAndPassword(mail_str, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                email_txt.setText("");
                                username_txt.setText("");
                                password_txt.setText("");
                                username_txt_layout.setError(null);
                                email_txt_layout.setError(null);
                                pass_txt_layout.setError(null);
                                Intent intent = new Intent(CreateAccActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        // validation

        email_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mail_str = email_txt.getText().toString().trim();
                email_validation(mail_str);
            }
        });

        password_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = password_txt.getText().toString().trim();
                password_validation(password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = username_txt.getText().toString().trim();
                user_validation(username);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public boolean user_validation(String text){
        String regex = "^[a-zA-Z0-9@#$&_.]+$";
        if (text.isEmpty()) {
            username_txt_layout.setError("Please enter username");
            return false;
        } else if (!text.matches(regex)){
            username_txt_layout.setError("Enter valid username");
            return false;
        }else {
            username_txt_layout.setError(null);
            return true;
        }
    }

    public boolean password_validation(String text){
        String regex = "^[a-zA-Z0-9@#$&_]+$";
        if (text.isEmpty()) {
            pass_txt_layout.setError("Password required");
            return false;
        } else if (!text.matches(regex)){
            pass_txt_layout.setError("Enter valid password");
            return false;
        } else if ((text.length() < 8 && text.length() > 10)) {
            pass_txt_layout.setError("At least 8 character");
            return false;
        } else if (!text.contains("@") && !text.contains("#") && !text.contains("&") && !text.contains("$") && !text.contains("_")) {
            pass_txt_layout.setError("At least one special character");
            return false;
        } else {
            pass_txt_layout.setError(null);
            return true;
        }
    }

    public boolean email_validation(String text){
        String regex = "^[a-zA-Z0-9_%.+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (text.isEmpty()){
            email_txt_layout.setError("Email cannot be empty");
            return false;
        } else if (!text.matches(regex)) {
            email_txt_layout.setError("Invalid email address");
            return false;
        }else {
            email_txt_layout.setError(null);
            return true;
        }
    }

}