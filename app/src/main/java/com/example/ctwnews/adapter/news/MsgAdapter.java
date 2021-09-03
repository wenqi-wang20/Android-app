package com.example.ctwnews.adapter.news;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ctwnews.NewsDetailActivity;
import com.example.ctwnews.R;
import com.example.ctwnews.models.news.Msg;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends BaseAdapter {

    //缓存加载图片选项(Universal Image Loader做法)
//    private DisplayImageOptions options = new DisplayImageOptions.Builder()
//        .showImageOnLoading(R.drawable.img_test)
//        .showImageForEmptyUri(R.drawable.img_test)
//        .showImageOnFail(R.drawable.img_test)
//        .cacheInMemory(true)
//        .cacheOnDisk(true).imageScaleType(ImageScaleType.NONE)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//        .considerExifParams(true)
//        .displayer(new RoundedBitmapDisplayer(20)).build();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private int selected;
    private List<Msg> mMsgList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    public MsgAdapter(List<Msg> msgList, Context context) {
        this.mMsgList = msgList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mMsgList.size();
    }
    @Override
    public Msg getItem(int position) {
        return mMsgList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //预加载view卡片防止反复加载
    private static class ViewHolder{
        ImageView imageView;
        TextView titleTV;
        TextView contentTV;
    }

    //预加载imageloader监听器
//    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener{
//
//        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//        @Override
//        public void onLoadingComplete(String imageurl, View view, Bitmap loadedImage){
//            if(loadedImage != null){
//                ImageView imageView = (ImageView) view;
//                boolean firstDisplay = !displayedImages.contains(imageurl);
//                //如果第一次加载就存入缓存
//                if(firstDisplay){
//                    FadeInBitmapDisplayer.animate(imageView, 500);
//                    displayedImages.add(imageurl);
//                }
//
//            }
//        }
//
//    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        //使用viewholder避免反复定义
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.news_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Msg msg = mMsgList.get(position);

        /**
         * 在此处展示第一张图片，并且存入缓存区
         */
        Log.i("arraynum", String.valueOf(msg.getImageUrl().size()));
        String firstimageurl;
        if(msg.getImageUrl().isEmpty()){
            firstimageurl = "https://images.cnblogs.com/cnblogs_com/blogs/669967/galleries/1942265/o_210902204244news_detail.jpg";
        }else{
            firstimageurl = msg.getImageUrl().get(0);
        }
        Glide.with(mContext)
                .load(firstimageurl)
                .asBitmap()
//                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_test)
//                .centerCrop()
                .placeholder(R.drawable.img_test)
                .centerCrop()
                .into(viewHolder.imageView);
//        viewHolder.imageView.setImageResource(msg.getImageResourceID());
        /**
         * 在此处第一次加载标题，并且存入缓存区
         */
        viewHolder.titleTV.setText(msg.getTitle());

        /**
         * 在此处设置看过的新闻标题为灰色
         */
        final FrameLayout fl = (FrameLayout) convertView.findViewById(R.id.news_items);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                viewHolder.titleTV.setTextColor(Color.parseColor("#8E8C8C"));

                //Log.i("onclick", "yes");
                Intent newsinfo = new Intent(mContext, NewsDetailActivity.class);

                //通过Bundle传递一个序列（自定义类）
                Bundle bundle = new Bundle();
                bundle.putSerializable("Newsinfo", msg);
                newsinfo.putExtra("msg", bundle);
                Log.i("bundle", "yes");
                mContext.startActivity(newsinfo);
                Log.i("start", "yes");
            }
        });

        return convertView;
    }

}
