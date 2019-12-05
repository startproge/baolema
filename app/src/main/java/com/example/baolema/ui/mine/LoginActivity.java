package com.example.baolema.ui.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.example.baolema.R;
import com.example.baolema.bean.User;
import com.example.baolema.util.httpUtil;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtUser;
    private EditText edtPasswd;
    private SharedPreferences pref;
    private User user;

    private String urlStr = "http://47.98.229.17:8002/blm";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_login);

        pref = getSharedPreferences("user", MODE_PRIVATE);

        toolbar = findViewById(R.id.tool_bar_login);
        toolbar.setNavigationOnClickListener(v -> {
            if (pref.getInt("userId", -1) == -1) {
                new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                        .setMessage("未登录~(•_•)")
                        .setPositiveButton("确定", null)
                        .show();
            } else finish();
        });

        edtUser = findViewById(R.id.edt_user);
        edtPasswd = findViewById(R.id.edt_passwd);
    }

    public void loginClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                try {
                    int userId = Integer.parseInt(edtUser.getText().toString());
                    loging(userId, edtPasswd.getText().toString());
                } catch (NumberFormatException e) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("账号输入错误(•_•)")
                            .setPositiveButton("确定", null)
                            .show();
                }
                break;
        }
    }
//        @Override
//    public void onResume() {
//        super.onResume();
//        getUserByHttp();
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("userId", user.getUserId());
                    editor.putString("userName", user.getUserName());
                    Log.e(user.getUserName(), "handleMessage: " );
                    editor.putString("userTel", user.getUserTel());
                    editor.putString("userAddress", user.getUserAddress());
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "登录成功!😆", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 2:
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("密码错误(•_•)")
                            .setPositiveButton("确定", null)
                            .show();
                    break;
                case 3:
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("账号不存在(•_•)")
                            .setPositiveButton("确定", null)
                            .show();
                    break;
                default:
                    break;
            }
        }
    };

    void loging(int id, String passwd) {
        new Thread(() -> {
            user = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/User/getUser?userId=" + id), User.class);
            Message message = new Message();
            if (user == null)
                message.what = 3;
            else if (user.getUserPwd().equals(passwd))
                message.what = 1;
            else
                message.what = 2;
            handler.sendMessage(message);
        }).start();
    }
//    void getUserByHttp() {
//        new Thread(() -> {
//            user = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/User/getUser?userId="+pref.getInt("userId",-1)), User.class);
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);
//        }).start();
//    }
}
