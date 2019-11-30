package com.example.baolema.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.baolema.R;

import java.util.ArrayList;

public class CommentGridViewAdapter extends BaseAdapter {

    private ArrayList<Integer> image;
    private Context context;

    public CommentGridViewAdapter(ArrayList<Integer> image,Context context){
        this.context=context;
        this.image=image;
    }

    @Override
    public int getCount() {
        return this.image.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.comment_grid_item,parent,false);
        ImageView imageView=convertView.findViewById(R.id.GridViewImage);
        imageView.setImageResource(image.get(position));
        return convertView;
    }
}
