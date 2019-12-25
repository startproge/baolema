package com.example.baolema.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.baolema.ui.MainActivity;
import com.example.baolema.R;

import java.io.ByteArrayInputStream;


public class MineFragment extends Fragment {
    private TextView textViewUserName;
    private ImageView imageViewAccountIcon;
    private SharedPreferences pref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("");
        pref = mainActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        textViewUserName = root.findViewById(R.id.text_user_name);
        imageViewAccountIcon = root.findViewById(R.id.image_mine_account);
        imageViewAccountIcon.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, LoginActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewUserName.setText(pref.getString("userName", "请登录"));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decode(pref.getString("userIcon", ""), Base64.DEFAULT));
        imageViewAccountIcon.setImageDrawable(Drawable.createFromStream(inputStream, ""));
    }
}