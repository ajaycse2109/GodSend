package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oodi.godsend.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyOTPActivity extends AppCompatActivity {

    Activity mContext;

    @BindView(R.id.txtRegister)
    TextView mTxtRegister ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        mContext = this ;
        ButterKnife.bind(mContext);

        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , WalkthroughActivity.class);
                startActivity(intent);
            }
        });
    }
}
