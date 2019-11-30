package com.example.baolema.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.customControl.CommentGridView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headImage;
        TextView name;
        TextView content;
        TextView evaluate;
        TextView date;
        CommentGridView gridView;
        public ViewHolder(View view) {
            super(view);
            headImage = view.findViewById(R.id.headImage);
            date=view.findViewById(R.id.date);
            name = view.findViewById(R.id.name);
            evaluate=view.findViewById(R.id.evaluate);
            content = view.findViewById(R.id.content);
            gridView = view.findViewById(R.id.contentImage);

        }
    }

    public CommentAdapter(Context context){
        super();
        this.context=context;
    }
    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_comment,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder mholder=(ViewHolder)holder;
        /*mholder.headImage.setImageResource();
        mholder.date.setText();
        mholder.name.setText();
        mholder.evaluate.setText();
        mholder.content.setText();
        mholder.gridView.setAdapter(new CommentGridViewAdapter(),context);*/

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
