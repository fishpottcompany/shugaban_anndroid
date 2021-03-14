package com.shugaban.shugaban.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shugaban.shugaban.R;

public class SignupActivity extends AppCompatActivity  implements View.OnClickListener {

    TextView m_login_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        m_login_textview = findViewById(R.id.loginactivity_textview_signup);

        m_login_textview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == m_login_textview.getId()){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            startActivity(intent);
        }
    }
}
