package com.example.baolema.DAO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.baolema.bean.Shop;
import com.example.baolema.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * fastjson解析示例
 */
public class JsonTest {
    private String urlStr = "http://47.98.229.17:8002/blm";
    public static void main(String[] args) {
        //build.gradle文件已更新fastjson库,参见README.md

        //传数组示例
        System.out.println("请求商家列表");
        List<Shop> shopList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Shop shop = new Shop(i, "学院" + i, "zucc", "12345463521", 4.5, "jfk的开始啦JFK都是浪费你\nf发生的s", null, "在线", 390);
            shopList.add(shop);
        }

        //服务器从数据库获得 List<Shop> 后,解析成json
        String str1 = JSON.toJSONString(shopList);//要发送的json字符串
        System.out.println(str1);
        //发送数据给app......

        //经过一番对http的操作,得到服务器发来的字符串
        String fromRes = str1;
        List<Shop> shopList2 = JSON.parseObject(str1, new TypeReference<List<Shop>>() {});//解析成对象
        for (int i = 0; i < shopList2.size(); i++)
            System.out.println(shopList2.get(i).getShopId() + " " + shopList2.get(i).getShopName());

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        System.out.println("\n请求用户信息");
        //数据库查询得到用户bean
        User user = new User(2, "yl", "password", "13246578945", "zucc", null);
        String str = JSON.toJSONString(user);//解析成json
        System.out.println(str);


        String str2 = str;//app从服务器获得的字符串
        User user2 = JSON.parseObject(str2, User.class);
        System.out.println(user2.getUserId() + " " + user2.getUserName());


    }
}
