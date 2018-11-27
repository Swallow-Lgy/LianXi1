package com.example.day01;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Util util;
    private String url="http://www.zhaoapi.cn/product/getProductDetail?pid=1";
     private List<String> list;
     private MyViewPager pager;
     private LinearLayout linearLayout;
     @SuppressLint("HandlerLeak")
     private Handler handler = new Handler(){
         @Override
         public void handleMessage(Message msg) {
             viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            sendEmptyMessageDelayed(0,2000);
         }
     };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        linearLayout =  findViewById(R.id.linea);
        util = new Util();
        list = new ArrayList<>();
        pager = new MyViewPager(this);
        viewPager.setAdapter(pager);
        LoadData();
    }

    public void LoadData(){
        util.getData(url, ImageBean.class, new Util.CallBack<ImageBean>() {
            @Override
            public void onSuccess(ImageBean dataBean) {
                String images = dataBean.getData().getImages();
                jie(images);
                pager.setList(list);
                viewPager.setCurrentItem(200);
                //handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,2000);
            }
        });
    }
    public void jie(String str){
        int index  = str.indexOf("|");
        if (index>=0){
            String sub  = str.substring(0,index);
            list.add(sub);
            jie(str.substring(index+1,str.length()));
        }
        else {
            list.add(str);
        }

    }
    public void initDot(){

    }
}
