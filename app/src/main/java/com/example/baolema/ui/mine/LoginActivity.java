package com.example.baolema.ui.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.baolema.R;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtUser;
    private EditText edtPasswd;
    private SharedPreferences pref;

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
                    if (userId < 1 || userId > 6) throw new NumberFormatException("");
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("userId", userId);
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "登录成功!😆", Toast.LENGTH_LONG).show();
                    finish();
                } catch (NumberFormatException e) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("账号输入错误(•_•)")
                            .setPositiveButton("确定", null)
                            .show();
                }
                break;
        }
    }
}
