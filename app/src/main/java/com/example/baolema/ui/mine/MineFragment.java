package com.example.baolema.ui.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.Shop;
import com.example.baolema.bean.User;
import com.example.baolema.ui.home.HomeRecyclerAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MineFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private int userId = 1;
    private User user;
    private TextView textViewUserName;
    private ImageView imageViewAccountIcon;
    private MineViewModel mineViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mineViewModel =
                ViewModelProviders.of(this).get(MineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("我的");
        textViewUserName = root.findViewById(R.id.user_name);

        getUserByHttp();

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        mineViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    textViewUserName.setText(user.getUserName());
//                    imageViewAccountIcon.setImageBitmap(user.getUserImage());
                    break;
                default:
                    break;
            }
        }
    };

    void getUserByHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urlStr + "/User/getUser?userId=1").build();
                try {
                    Response response = client.newCall(request).execute();
                    user = JSON.parseObject(response.body().toString(), User.class);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}