package com.example.baolema.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.baolema.R;

public class MinePhoneActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private EditText edt_phone;
    private Button bt_change_tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_phone);

        edt_phone = findViewById(R.id.edt_setting_phone);
        pref = getSharedPreferences("user", MODE_PRIVATE);
        edt_phone.setText(pref.getString("phone", ""));

        bt_change_tel = findViewById(R.id.bt_change_tel);

        bt_change_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("phone", edt_phone.getText().toString());
                editor.apply();
            }
        });

        Toolbar toolbar = findViewById(R.id.tool_bar_phone);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
