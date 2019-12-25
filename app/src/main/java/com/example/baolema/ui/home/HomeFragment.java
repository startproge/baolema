package com.example.baolema.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.example.baolema.bean.Activity;
import com.example.baolema.ui.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.Shop;
import com.example.baolema.util.httpUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<Shop> shopList;
    private List<Activity> activityList;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private List<Integer> integerArrayList = new ArrayList<>();

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        shopList = new ArrayList<>();
        activityList=new ArrayList<>();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("饿了么");

        viewPager = root.findViewById(R.id.viewPager_main);
        initImages();
        PagerAdapter pagerAdapter = new PagerAdapter(integerArrayList);
        viewPager.setAdapter(pagerAdapter);

        recyclerView = root.findViewById(R.id.recycler_view_shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Thread thread = new Thread(() -> {
            shopList = JSON.parseArray(httpUtil.getHttpInterface(urlStr + "/Shop/getShopList"), Shop.class);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread1=new Thread(()->{
           activityList=JSON.parseArray(httpUtil.getHttpInterface(urlStr+"/Activity/getActivityAll"),Activity.class);
        });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("main",activityList.size()+"" );

        homeRecyclerAdapter = new HomeRecyclerAdapter(activityList,shopList, getActivity());
        recyclerView.setAdapter(homeRecyclerAdapter);
        homeRecyclerAdapter.OnRecycleItemClickListener((view, position) -> {
            if (shopList.get(position).getShopStatus().equals("离线")) {
                new AlertDialog.Builder(mainActivity).setTitle("提示")
                        .setMessage("商家休息中( •̀ ω •́ )✧")
                        .setPositiveButton("确定", null)
                        .show();
            } else {
                Intent intent = new Intent(getActivity(), ShopActivity.class);
                intent.putExtra("shop", homeRecyclerAdapter.getShopList().get(position));
                startActivity(intent);
            }
        });
        for (Shop shop : shopList)
            getShopByHttp(shop.getShopId());

        return root;
    }



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
//                    homeRecyclerAdapter = new HomeRecyclerAdapter(shopList, getActivity());
//                    recyclerView.setAdapter(homeRecyclerAdapter);
//                    homeRecyclerAdapter.OnRecycleItemClickListener((view, position) -> {
//                        Intent intent = new Intent(getActivity(), ShopActivity.class);
//                        int shopId = homeRecyclerAdapter.getShopList().get(position).getShopId();
//                        String shopName = homeRecyclerAdapter.getShopList().get(position).getShopName();
//                        intent.putExtra("shopId", shopId);
//                        intent.putExtra("shopName", shopName);
//                        startActivity(intent);
//                    });
                    break;
                case 2:
                    homeRecyclerAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    void initImages() {
        integerArrayList.add(R.drawable.pager1);
        integerArrayList.add(R.drawable.pager2);
        integerArrayList.add(R.drawable.pager3);
        viewPager.setOffscreenPageLimit(integerArrayList.size());
        viewPager.setPageMargin(10);
    }

    void getShopByHttp(final int id) {
        new Thread(() -> {
            Shop shop = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Shop/getShopById?shopId=" + id), Shop.class);
            int MonthlySale=JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Shop/getMonthlyTrade?shopId=" + id), Integer.class);
            for (int i = 0; i < shopList.size(); i++)
                if (shopList.get(i).getShopId() == id) {
                    shopList.get(i).setShopTrademark(shop.getShopTrademark());
                    shopList.get(i).setShopMonthSale(MonthlySale);
                }
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
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
    public RoundedImageView instantiateItem(@NonNull ViewGroup container, int position) {
        RoundedImageView imageView = new RoundedImageView(container.getContext());
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