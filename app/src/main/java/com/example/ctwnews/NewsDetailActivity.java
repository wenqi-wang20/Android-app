package com.example.ctwnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ctwnews.models.news.Msg;
import com.example.ctwnews.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends Activity {
    private List<View> pageview = new ArrayList<>();
    private ViewPager viewPager;
    private Intent intent;
    private Msg msg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("msg");

        //代表取出了每个页面的数据
        Serializable obj = bundle.getSerializable("Newsinfo");
        if(obj != null && obj instanceof Msg){
            msg = (Msg) obj;
        }

        viewPager = (ViewPager) findViewById(R.id.news_info_photo);

        //下面是获取msg的内容并且加载到页面中去
        List<String> pictureUrls = msg.getImageUrl();
        String title = msg.getTitle();
        String publisher = msg.getPublisher();
        String publishtime = msg.getPublishTime();
        String content = msg.getContent();
//        Log.i("getnum", String.valueOf(msg.getImageUrl().size()));
//        Log.i("getpic",msg.getImageUrl().get(0));

        //设置图片
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < msg.getImageUrl().size(); i++) {
            String pictureurl = msg.getImageUrl().get(i);
            Log.i("thisurl", pictureurl);
            if(i > 0){
                Log.i("morepicture", pictureurl);
            }else{
                Log.i("url", pictureurl);
            }

            View v = inflater.inflate(R.layout.news_photo, null);
            ImageView imageView = v.findViewById(R.id.news_picture);
            Glide.with(this)
                    .load(pictureurl)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                            e.printStackTrace();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            }

                            int imageWidth = glideDrawable.getIntrinsicWidth();
                            int imageHeight = glideDrawable.getIntrinsicHeight();
                            int width = ScreenUtils.getScreenWidth(getApplicationContext());
                            int height = width * imageHeight / imageWidth;
                            ViewGroup.LayoutParams param = imageView.getLayoutParams();
                            param.height = height;
                            param.width = width;
                            imageView.setLayoutParams(param);
                            return false;
                        }
                    })
                    .error(R.drawable.img_test)
                    .error(R.drawable.img_test)
                    .into(imageView);
//            Glide.with(this)
//                    .load(pictureurl)
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
////                    .listener(new RequestListener<String, GlideDrawable>() {
////                        @Override
////                        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
////                            return false;
////                        }
////                        @Override
////                        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
////                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
////                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////                            }
////                            imageView.setLayoutParams(new FrameLayout.LayoutParams(ScreenUtils.getScreenWidth(getApplicationContext()),
////                                    (ScreenUtils.getScreenHeight(getApplicationContext()))));
////                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
////                            int vw = imageView.getWidth();
////                            float scale = (float) vw / (float) glideDrawable.getIntrinsicWidth();
////                            int vh = Math.round(glideDrawable.getIntrinsicHeight() * scale);
////                            params.height = vh;
////                            imageView.setLayoutParams(params);
////                            return false;
////
////                        }
////                    })
//                    .placeholder(R.drawable.img_test)
//                    .error(R.drawable.img_test)
//                    .centerCrop()
//                    .into(new SimpleTarget<GlideDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL){
//                        @Override
//                        public void onLoadStarted(Drawable placeholder){
//                        }
//
//                        @Override
//                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                            Toast.makeText(getApplicationContext(), "图片加载失败", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation){
//                            int imageWidth = resource.getIntrinsicWidth();
//                            int imageHeight = resource.getIntrinsicHeight();
//                            int width = ScreenUtils.getScreenWidth(getApplicationContext());
//                            int height = width * imageHeight / imageWidth;
//                            ViewGroup.LayoutParams param = imageView.getLayoutParams();
//                            param.height = height;
//                            param.width = width;
//                            //super.onResourceReady(resource,glideAnimation);
//                            //imageView.setLayoutParams(param);
//                            imageView.setImageDrawable(resource);
//                        }
//                    });

            //这是静态bitmap加载方式
//            Glide.with(this)
//                    .load(pictureurl)
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .error(R.drawable.img_test)
//                    .placeholder(R.drawable.img_test)
//                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                            int imageWidth = bitmap.getWidth();
//                            int imageHeight = bitmap.getHeight();
//                            int width = ScreenUtils.getScreenWidth(getApplicationContext());
//                            int heigt = width * imageHeight / imageWidth;
//                            ViewGroup.LayoutParams param = imageView.getLayoutParams();
//                            param.height = heigt;
//                            param.width = width;
//                            imageView.setImageBitmap(bitmap);
//                        }
//                    });
            pageview.add(v);
        }
        //emptyarray
//        if(msg.getImageUrl().isEmpty()){
//            Log.i("empty", "yes");
//            View v = inflater.inflate(R.layout.news_photo, null);
//            ImageView imageView = v.findViewById(R.id.news_picture);
//            String pictureurl = "https://images.cnblogs.com/cnblogs_com/blogs/669967/galleries/1942265/o_210902204244news_detail.jpg";
//            Glide.with(this)
//                    .load(pictureurl)
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .error(R.drawable.img_test)
//                    .placeholder(R.drawable.img_test)
//                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                            int imageWidth = bitmap.getWidth();
//                            int imageHeight = bitmap.getHeight();
//                            int width = ScreenUtils.getScreenWidth(getApplicationContext());
//                            int height = width * imageHeight / imageWidth;
//                            ViewGroup.LayoutParams param = imageView.getLayoutParams();
//                            param.height = height;
//                            param.width = width;
//                            imageView.setImageBitmap(bitmap);
//                        }
//                    });
//            pageview.add(v);
//        }
        Log.i("pageviewnum", String.valueOf(pageview.size()));
        //设置标题栏
        TextView titletext = findViewById(R.id.news_info_title);
        titletext.setText(title);
        //设置信息栏
        String info = publisher + "     " + publishtime;
        TextView infotext = findViewById(R.id.news_info_publish);
        infotext.setText(info);

        //设置文本栏
        TextView contenttext = findViewById(R.id.news_info_desc);
        contenttext.setText(content);


        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pageview.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(View view , int position, Object o){
                ((ViewPager) view).removeView(pageview.get(position));
            }

            @Override
            public Object instantiateItem(View view, int position){
                ((ViewPager) view).addView(pageview.get(position));
                return pageview.get(position);
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

}
