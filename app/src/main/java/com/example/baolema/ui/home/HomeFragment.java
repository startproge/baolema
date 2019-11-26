package com.example.baolema.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.Shop;
import com.example.baolema.util.httpUtil;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private String urlStr = "http://47.98.229.17:8002/blm";
    private TextView textView;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<Shop> shopList = new ArrayList<>();
    private HomeRecyclerAdapter homeRecyclerAdapter;
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
//        initShops();
        PagerAdapter pagerAdapter = new PagerAdapter(integerArrayList);
        viewPager.setAdapter(pagerAdapter);

        recyclerView = root.findViewById(R.id.recycler_view_shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getShopListByHttp();

//        recyclerView.setAdapter(new HomeRecyclerAdapter(shopList));

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

//    void initShops() {
//        for (int i = 0; i < 20; i++) {
//            Shop shop = new Shop();
//            shop.setShopName("fdfds");
//            shop.setShopMonthSale(423432);
//            shopList.add(shop);
//        }
//    }

    void initImages() {
        integerArrayList.add(R.drawable.ic_back);
        integerArrayList.add(R.drawable.ic_locate);
        integerArrayList.add(R.drawable.ic_home);
        viewPager.setOffscreenPageLimit(integerArrayList.size());
        viewPager.setPageMargin(10);
    }

    //参考地址 https://blog.csdn.net/pxcz110112/article/details/81220928

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    homeRecyclerAdapter=new HomeRecyclerAdapter(shopList);
                    recyclerView.setAdapter(homeRecyclerAdapter);
                    homeRecyclerAdapter.OnRecycleItemClickListener(new HomeRecyclerAdapter.OnRecycleItemClickListener() {
                        @Override
                        public void OnRecycleItemClickListener(int position) {
                            Intent intent=new Intent(getActivity(),ShopActivity.class);
                            int shopId=homeRecyclerAdapter.getShopList().get(position).getShopId();
                            intent.putExtra("shopId",shopId);
                            startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    void getShopListByHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                shopList = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/Shop/getShopList"), new TypeReference<List<Shop>>() {});
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
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