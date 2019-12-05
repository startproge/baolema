package com.example.baolema.ui.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.baolema.R;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);

        Toolbar toolbar = findViewById(R.id.tool_bar_setting);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
