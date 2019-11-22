package com.example.baolema.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.Recipe;
import com.example.baolema.bean.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private String urlStr = "231.233.42.43/?type=android&req=";
    private TextView textView;
    private ViewPager viewPager;
    private List<Shop> shopList = new ArrayList<>();

    private List<Integer> integerArrayList = new ArrayList<>();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = root.findViewById(R.id.viewPager_main);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("饱了嘛");

        initImages();
        initShops();

        PagerAdapter pagerAdapter = new PagerAdapter(integerArrayList);
        viewPager.setAdapter(pagerAdapter);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new HomeRecyclerAdapter(shopList));

        textView = root.findViewById(R.id.text_surf);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    void initShops() {
        for (int i = 0; i < 20; i++) {
            Shop shop = new Shop();
            shop.setShopName("fdfds");
            shop.setShopMonthSale(423432);
            shopList.add(shop);
        }
    }

    void initImages() {
        integerArrayList.add(R.drawable.ic_back);
        integerArrayList.add(R.drawable.ic_locate);
        integerArrayList.add(R.drawable.ic_home);
        viewPager.setOffscreenPageLimit(integerArrayList.size());
        viewPager.setPageMargin(10);
    }

    void sendHttpForShops() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                StringBuffer stringBuffer = new StringBuffer();
                try {
                    String path = urlStr + "shopList" + URLEncoder.encode("shopList", "utf-8");
                    URL url = new URL(path);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);

                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1)
                            outputStream.write(buffer, 0, len);
                        String jsonString=outputStream.toString();
                        outputStream.close();
                        inputStream.close();

                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Shop shop=new Shop();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            shop.setShopId(jsonObject.getInt("shopId"));
                            shop.setShopName(jsonObject.getString("shopName"));
                            shop.setShopAddress(jsonObject.getString("shopAddress"));
                            shop.setShopTel(jsonObject.getString("shopTel"));
                            shop.setShopScore(jsonObject.getDouble("shopScore"));
                            shop.setShopNotice(jsonObject.getString("shopNotices"));
                            shop.setShopTrademark(jsonObject.getString("shopTrademark"));
                            shop.setShopStatus(jsonObject.getString("shopStatus"));
                            shop.setShopMonthSale(jsonObject.getInt("shopMonthSale"));
                            JSONObject recipeListObject=jsonObject.getJSONObject("recipeList");
                            List<Recipe> recipeList = new ArrayList<>();

//                            shop.setRecipeList();

                            shopList.add(shop);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

}

class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    private List<Integer> integers;

    public PagerAdapter(List<Integer> integers) {
        this.integers = integers;
    }

    @Override
    public int getCount() {
        return integers.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setImageResource(integers.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}