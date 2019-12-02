package com.example.baolema.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.example.baolema.R;
import com.example.baolema.bean.User;
import com.example.baolema.util.httpUtil;

public class MinePhoneActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String urlStr = "http://47.98.229.17:8002/blm";
    private EditText edt_phone;
    private User user;
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

        getUserByHttp();
        Toolbar toolbar = findViewById(R.id.tool_bar_phone);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    edt_phone.setText(user.getUserTel());
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
                user = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/User/getUser?userId=1"), User.class);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
    }
}
