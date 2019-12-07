package com.example.baolema.ui;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.List;

public class GuidePagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    private List<ImageView> imageViewList;

    public GuidePagerAdapter(List<ImageView> imageViewList) {
        this.imageViewList= imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(imageViewList.get(position));
        return imageViewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageViewList.get(position));
    }
}
