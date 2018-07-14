package com.ubt.ubtroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText displayName, email, password;
    Button createAccount;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayName = findViewById(R.id.display_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        createAccount = findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String DISPLAY_NAME = displayName.getText().toString();
                String EMAIL = email.getText().toString();
                String PASSWORD = password.getText().toString();

                // Validate DisplayName, Email and Password
                if (!TextUtils.isEmpty(DISPLAY_NAME) || !TextUtils.isEmpty(EMAIL) || !TextUtils.isEmpty(PASSWORD)) {
                    register_user(DISPLAY_NAME, EMAIL, PASSWORD);
                } else {
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void register_user(final String displayName, String email, String password) {

        /*
            From the instance of FirebaseAuth 'mAuth', we've called a function createUserWithEmailAndPassword(email, password)
            and we've added a listener (.addOnCompleteListener(Task<AuthResult> task)) to know when the action is done successfully and when it fails
        */
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String userId = currentUser.getUid();

                             /*
                                If the response is successful then get UserId from FirebaseUser instance,
                                and create a new reference in the database with nodes 'User/{USERID}'
                              */

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("id", userId);
                            userMap.put("name", displayName);
                            userMap.put("status", "default");
                            userMap.put("image", "default");

                            /*
                                Create a HashMap Object and fill it with required fields such as (name, status, image),
                                and set HashMap object in the database.
                             */

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        /*
                                            Check if this task is done successfully,
                                            then send the user to the Main Screen of Application which is MainActivity
                                         */

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
