package com.example.baolema.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.baolema.R;
import com.example.baolema.ui.home.LocationActivity;
import com.example.baolema.ui.mine.LoginActivity;
import com.example.baolema.ui.mine.MinePhoneActivity;
import com.example.baolema.ui.mine.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        if (pref.getInt("userId", -1) == -1) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (pref.getBoolean("FirstStart", true)) {
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("FirstStart", false);
            editor.apply();
        }

        toolbar = findViewById(R.id.tool_bar_main);
        resetTitle("");
        setSupportActionBar(toolbar);

        imageAccount = findViewById(R.id.image_mine_account);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        /*

         三个碎片:首页,我的,订单
         对应UI下三个文件夹:home,mine,order
         所有类按此文件夹归类
         */
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_order, R.id.navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void resetTitle(String title) {
        toolbar.setTitle(title);
    }

    public void homeOnclick(View view) {
        switch (view.getId()) {
            case R.id.image_location:
            case R.id.text_title:
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void mineOnclick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.constraint_mine_setting:
            case R.id.image_mine_setting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.constraint_mine_tel:
                intent = new Intent(MainActivity.this, MinePhoneActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
