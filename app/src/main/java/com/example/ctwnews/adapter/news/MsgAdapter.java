package com.example.ctwnews.adapter.news;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ctwnews.R;
import com.example.ctwnews.models.news.Msg;

import java.util.List;

public class MsgAdapter extends BaseAdapter {
    private List<Msg> mMsgList;
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

    private static class ViewHolder{
        ImageView imageView;
        TextView titleTV;
        TextView contentTV;
    }

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
         * 在此处第一次加载图片，并且存入缓存区
         */
        viewHolder.imageView.setImageResource(msg.getImageResourceID());
        viewHolder.titleTV.setText(msg.getTitle());
        //viewHolder.contentTV.setText(msg.getContent());

        return convertView;
    }

}
