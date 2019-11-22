package com.example.baolema.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;

import java.util.Arrays;
import java.util.List;

/**
 * 对应图片activity_address
 */

public class ActivityAddress extends AppCompatActivity {
    private List<String> addresses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addresses = Arrays.asList("浙江大学城市学院", "浙江大学", "杭州电子科技大学");
        LocationAdapter adapter = new LocationAdapter(addresses);
        RecyclerView recyclerView = findViewById(R.id.address_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
