package com.example.baolema.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.baolema.R;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private List<ImageView> imageViewList = new ArrayList<>();
    private Button btStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = findViewById(R.id.viewPager_guide);
        btStart = findViewById(R.id.bt_start);
        btStart.setOnClickListener(v -> finish());

        initViewPager();
    }

    public void initViewPager() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int[] ints = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3, R.drawable.guide4};
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(ints[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViewList.add(imageView);
        }
        GuidePagerAdapter guidePagerAdapter = new GuidePagerAdapter(imageViewList);

        viewPager.setAdapter(guidePagerAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == imageViewList.size() - 1)
            btStart.setVisibility(View.VISIBLE);
        else
            btStart.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
