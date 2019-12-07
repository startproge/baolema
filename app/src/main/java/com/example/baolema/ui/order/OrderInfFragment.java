package com.example.baolema.ui.order;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baolema.R;
import com.example.baolema.bean.Activity;
import com.example.baolema.bean.OrderInformation;
import com.example.baolema.bean.OrderSum;
import com.example.baolema.controller.ActivityController;
import com.example.baolema.controller.OrderController;
import com.example.baolema.controller.ShopController;

import java.util.ArrayList;
import java.util.List;

public class OrderInfFragment extends Fragment {
    public static final int CHOOSE_PHOTO=1;

    private String urlStr = "http://47.98.229.17:8002/blm";
    private RecyclerView recyclerOrderRecipe;
    private int orderId;
    private String orderStatus;
    private OrderInfAdapter orderinfAdapter;
    private List<OrderInformation> orderInfRecipes;
    private TextView shopName;
    private TextView orderNumber;
    private TextView orderTimeDay;
    private TextView orderTimeSecond;
    private TextView order_summary;
    private TextView order_reduce;
    private TextView order_money;

    private TextView label_order_grade;
    private RatingBar order_grade;
    private TextView label_order_comment;
    private TextView order_comment;
    private TextView label_order_image;
    private ImageView order_image;
    private OrderSum orderSum;
    private List<Activity> activitys;

    private Button shopEvaCommit;
    private OrderInfActivity orderInfActivity=(OrderInfActivity)getActivity();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order_inf,container,false);
        Bundle bundle= OrderInfFragment.this.getArguments();
        //orderId=bundle.getInt("orderId");
        recyclerOrderRecipe=view.findViewById(R.id.order_recipe_recycleview);
        shopName=view.findViewById(R.id.shop_name);
        orderNumber=view.findViewById(R.id.order_number);
        orderTimeDay=view.findViewById(R.id.order_time_day);
        orderTimeSecond=view.findViewById(R.id.order_time_second);
        order_summary=view.findViewById(R.id.total_money);
        order_reduce=view.findViewById(R.id.order_reduce);
        order_money=view.findViewById(R.id.total_paid_money);

        label_order_grade=view.findViewById(R.id.label_order_grade);
        order_grade=view.findViewById(R.id.order_grade);
        label_order_comment=view.findViewById(R.id.label_order_comment);
        order_comment=view.findViewById(R.id.order_comment);
        label_order_image=view.findViewById(R.id.label_order_image);
        order_image=view.findViewById(R.id.order_image);
        shopEvaCommit=view.findViewById(R.id.shopEva_commit);


        recyclerOrderRecipe.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderInfRecipes=new ArrayList<>();
        orderinfAdapter= new OrderInfAdapter(orderInfRecipes, getActivity());
        recyclerOrderRecipe.setAdapter(orderinfAdapter);
        orderId=(int) bundle.getInt("orderId");
        orderStatus=(String) bundle.getString("orderStatus");
        Log.d("orderStatus1",orderStatus);
        if(orderId!=0){
            try {
                ThreadgetOrderSum thread1 =new ThreadgetOrderSum();
                    thread1.start();
                    thread1.join();
                ThreadgetOrderInf thread2 = new ThreadgetOrderInf();
                    thread2.start();
                    thread2.join();
                ThreadgetActivity thread3=new ThreadgetActivity();
                    thread3.start();
                    thread3.join();
                shopEvaCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } catch (Exception e){
            }
        }
        return view;
    }

    private class  ThreadgetOrderSum extends Thread{
        @Override
        public void run() {
            orderSum=new OrderController().getOrderSumById(orderId);
            /*if(orderSum!=null)
            Log.d("OrderCommitStatus",orderSum.getOrderStatus());*/
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

    }


    private class  ThreadgetOrderInf extends Thread {
        @Override
        public void run() {
            Log.d("orderInfId",String.valueOf(orderSum.getOrderId()));
            orderInfRecipes=new OrderController().getOrderInformationList(orderSum.getOrderId());
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
    }


    private class  ThreadgetActivity extends Thread {
        @Override
        public void run() {

            activitys=new ActivityController().getActivitiesByShopId(orderSum.getShopId());
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
        }
    }

    private class ThreadaddShopEva extends Thread{
        private Double grade;
        private String content;
        private int shopId;
        private int orderId;
        private int userId;
        public ThreadaddShopEva(int shopId,int orderId,double grade,int userId,String content){
            this.shopId=shopId;
            this.orderId=orderId;
            this.grade=grade;
            this.userId=userId;
            this.content=content;
        }
        @Override
        public void run() {
            new ShopController().addShopEvaluate(orderSum.getShopId(), orderSum.getOrderId(), grade,
                    orderSum.getUserId(), content);
        }
    }

//    void getOrderInfByHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                /*orderInfRecipes = JSON.parseObject(httpUtil.getHttpInterface(urlStr + "/OrderInformation/getOrderInformationList?orderId=" + orderId),
//                        new TypeReference<List<OrderInformation>>() {
//                        });*/
//                orderInfRecipes=new OrderController().getOrderInformationList(orderSum.getOrderId());
//                //Log.d("orderInfRecipes",String.valueOf(orderInfRecipes.size()));
//                Message message = new Message();
//                message.what = 1;
//                handler.sendMessage(message);
//                //Log.e("ShopActivity", String.valueOf(recipes.size()));
//
//            }
//        }).start();
//    }


//    void getActivityByHttp() {
//        new Thread(() -> {
//            activitys=new ActivityController().getActivitiesByShopId(orderSum.getShopId());
//            Message message = new Message();
//            message.what = 2;
//            handler.sendMessage(message);
//        }).start();
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("getOrderSum", "handleMessage: " );
                    break;
                case 2:
                    Log.d("getOrderInf", "handleMessage: " );
                    break;
                case 3:
                    shopName.setText(orderSum.getShopName());
                    orderNumber.setText(String.valueOf(orderSum.getTemporaryId()));

                    orderinfAdapter.setOrderRecipes(orderInfRecipes);
                    orderinfAdapter.setMoney(orderSum.getOrdersum());
                    //Log.d("orderInfRecipes",orderinfAdapter.getOrderRecipes().get(2).getRecipeName());
                    orderinfAdapter.notifyDataSetChanged();

                    orderinfAdapter.setActivities(activitys);
                    orderinfAdapter.resetReduce();
                    orderinfAdapter.notifyDataSetChanged();
                    order_summary.setText("总价￥"+String.valueOf(orderSum.getOrdersum()));
                    order_reduce.setText("优惠￥"+String.valueOf(orderinfAdapter.getReduce()));
                    order_money.setText("实付￥"+String.valueOf(orderSum.getOrdersum()-orderinfAdapter.getReduce()));
                    if(orderStatus.equals("完成")){
                        label_order_grade.setVisibility(View.VISIBLE);
                        order_grade.setVisibility(View.VISIBLE);
                        label_order_comment.setVisibility(View.VISIBLE);
                        order_comment.setVisibility(View.VISIBLE);
                        label_order_image.setVisibility(View.VISIBLE);
                        order_image.setVisibility(View.VISIBLE);
                        order_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    //Toast.makeText(getActivity(),"权限申请",Toast.LENGTH_SHORT).show();
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                                }else{
                                    openAlbum();
                                    Toast.makeText(getActivity(),"点击图片",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        shopEvaCommit.setVisibility(View.VISIBLE);
                        shopEvaCommit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Double grade=Double.valueOf(order_grade.getRating());
                                String content=String.valueOf(order_comment.getText());
                                try {
                                    ThreadaddShopEva addShopEva=new ThreadaddShopEva(orderSum.getShopId(),orderSum.getOrderId(),grade
                                            ,orderSum.getUserId(),content);
                                    addShopEva.start();
                                    addShopEva.join();
                                    Toast.makeText(getActivity(),"评论提交成功",Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(getActivity(),"评论提交失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "you use the permission", Toast.LENGTH_SHORT).show();
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            /*case TAKE_PHOTO:
                if(resultCode == getActivity().RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        image_head.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;*/
            case CHOOSE_PHOTO:
                if(resultCode==getActivity().RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(getActivity(),uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null,selection,null,null);
        if(cursor !=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            order_image.setImageBitmap(bitmap);
        }else {
            Toast.makeText(getActivity(),"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
}



class OrderInfAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderInformation> orderInfRecipes;
    private Context context;
    private Double money;
    private Double reduce;
    private List<Activity> activities;


    static class OrderInfViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView money;
        TextView num;

        public OrderInfViewHolder(View view){
            super(view);
            name=view.findViewById(R.id.recipe_name);
            money=view.findViewById(R.id.recipe_money);
            num=view.findViewById(R.id.recipe_num);
        }
    }

    public OrderInfAdapter (List<OrderInformation> arrayList, Context context){
        if(arrayList.size()==0)
            orderInfRecipes=new ArrayList<>();
        else
            orderInfRecipes=arrayList;
        this.context=context;
        activities=new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order,parent,false);
        OrderInfViewHolder holder=new OrderInfViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderInfViewHolder mholder=(OrderInfViewHolder) holder;
        final OrderInformation orderInfRecipe=orderInfRecipes.get(position);
        mholder.name.setText( orderInfRecipe.getRecipeName());
        mholder.money.setText("￥"+(orderInfRecipe.getRecipePrice()*orderInfRecipe.getOrderRecipeNumber()));
        mholder.num.setText("×"+Integer.toString(orderInfRecipe.getOrderRecipeNumber()));

    }

    @Override
    public int getItemCount() {
        return orderInfRecipes.size();
    }

    public List<OrderInformation> getOrderRecipes(){
        return this.orderInfRecipes;
    }

    public void setOrderRecipes(List<OrderInformation> shopCarRecipes) {
        this.orderInfRecipes = shopCarRecipes;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getReduce() {
        return reduce;
    }

    public void resetReduce() {
        this.reduce = 0.0;
        //Log.d("activities.size",String.valueOf(activities.get(0).getFullMoney()));
        for(int i=0;i<activities.size();i++){
            if(this.money>activities.get(i).getFullMoney()&&this.reduce<activities.get(i).getReduceMoney())
                this.reduce=activities.get(i).getReduceMoney();
            Log.d("thissize",String.valueOf( this.money));
        }

    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}