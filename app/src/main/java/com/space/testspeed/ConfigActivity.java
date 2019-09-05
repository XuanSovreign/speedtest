package com.space.testspeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by licht on 2019/9/5.
 */

public class ConfigActivity extends AppCompatActivity {

    private EditText mEdtAddress;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        mEdtAddress = findViewById(R.id.edt_address);
        Button mBtnConnection = findViewById(R.id.btn_connection);
        mPreferences = getSharedPreferences("speed_ip", MODE_PRIVATE);
        String ipAddress = mPreferences.getString("ip_address", "");
        if (!TextUtils.isEmpty(ipAddress)) {
            mEdtAddress.setText(ipAddress);
        }
        mBtnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = mEdtAddress.getText().toString().trim();
                if (TextUtils.isEmpty(ip)) {
                    return;
                }
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("ip_address",ip);
                editor.apply();
                startActivity(new Intent(ConfigActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
