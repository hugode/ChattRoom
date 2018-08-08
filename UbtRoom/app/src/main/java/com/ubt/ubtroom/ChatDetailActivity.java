package com.ubt.ubtroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ChatDetailActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        user = (User) getIntent().getSerializableExtra("USER_OBJECT");
    }
}
