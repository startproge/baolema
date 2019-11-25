package com.example.baolema.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.MainActivity;
import com.example.baolema.R;
import com.example.baolema.bean.OrderMain;
import com.example.baolema.bean.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderFragment extends Fragment {
    private String urlStr = "http://47.98.229.17:8002/blm";
    private OrderViewModel orderViewModel;
    private RecyclerView recyclerView;
    private int userId = 1;
    private List<OrderMain> ordersList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerView.setAdapter(new OrderMainAdapter(ordersList));
                    break;
                default:
                    break;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.resetTitle("我的订单");

//        initOrderList();

        recyclerView = root.findViewById(R.id.recycler_order_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getOrderListByHttp();
//        recyclerView.setAdapter(new OrderMainAdapter(ordersList));

        return root;
    }

    void getOrderListByHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urlStr + "/Order/getOrderList?userId=" + userId).build();
                try {
                    Response response = client.newCall(request).execute();
                    ordersList = JSON.parseObject(response.body().string(), new TypeReference<List<OrderMain>>() {
                    });
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void sendHttpForOrderList() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn = null;
                StringBuffer stringBuffer = new StringBuffer();
                try {
                    String path = urlStr + "/Order/getOrderList?userId=1";
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
                        String jsonString = outputStream.toString();
                        outputStream.close();
                        inputStream.close();


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    public void initOrderList() {
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.94, "已完成"));
        ordersList.add(new OrderMain("学院学院", 2.94, "待评价"));
        ordersList.add(new OrderMain("学院学院", 329.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 2, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
        ordersList.add(new OrderMain("学院学院", 32.4, "待评价"));
    }
}