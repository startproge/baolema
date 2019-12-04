package com.example.baolema.ui.mine;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSON;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.User;
import com.example.baolema.util.httpUtil;


public class MineFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private int userId = 1;
    private User user;
    private TextView textViewUserName;
    private ImageView imageViewAccountIcon;
    private MineViewModel mineViewModel;
    private SharedPreferences pref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mineViewModel =
                ViewModelProviders.of(this).get(MineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("我的");
        textViewUserName = root.findViewById(R.id.text_user_name);
        imageViewAccountIcon = root.findViewById(R.id.image_mine_account);

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
                    if (user.getUserImage().length > 0)
                        imageViewAccountIcon.setImageBitmap(BitmapFactory.decodeByteArray(user.getUserImage(), 0, user.getUserImage().length));
                    break;
                default:
                    break;
            }
        }
    };

    void getUserByHttp() {
        new Thread(() -> {
            user = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/User/getUser?userId=1"), User.class);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }
}