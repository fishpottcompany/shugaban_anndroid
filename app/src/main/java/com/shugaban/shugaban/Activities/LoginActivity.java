package com.shugaban.shugaban.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shugaban.shugaban.Inc.Util;
import com.shugaban.shugaban.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView m_signup_textview, m_resetpassword_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_signup_textview = findViewById(R.id.loginactivity_textview_signup);
        m_resetpassword_textview = findViewById(R.id.loginactivity_textview_forgotpassword);

        m_signup_textview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == m_signup_textview.getId()){
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            startActivity(intent);
        }
    }
}
