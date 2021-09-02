package com.example.ctwnews.frag;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ctwnews.R;
import com.example.ctwnews.adapter.news.MsgAdapter;
import com.example.ctwnews.models.news.Msg;
import com.example.ctwnews.util.JSONUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ContentFragment extends Fragment {
    //调试信息
    private ListView listView;
    //测试数据集
    private List<Msg> msgList;
    private MsgAdapter adapter;

    //实例化视图
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.container, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        Bundle bundle = getArguments();
        //获取当前所在的tab
        String category  = bundle.getString("title");
        listView = (ListView) view.findViewById(R.id.listview);

        /**
         * 在这里获取新闻列表，并且绑定在adapter上
         */
        msgList = new ArrayList<>();
        String url = getcatogoryNews(15, category);
        Log.i("url",url);
        JSONUtil.sendRequestWithOkhttp(url, new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                //Log.i("enterparse","no");
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException{
                Log.i("enterparse","yes");
                //异步回调的数据需要返回主线程中处理
                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                     List<Msg> msglist = JSONUtil.parseJSON(response);
                    @Override
                    public void run() {
                        msgList = msglist;
                        Log.i("newsnum",String.valueOf(msgList.size()));
                        adapter = new MsgAdapter(msgList, getActivity());
                        listView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private String getcatogoryNews(int newsnumber, String category){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datestr = df.format(date);

        String baseurl = "https://api2.newsminer.net/svc/news/queryNewsList?";
        String URL = baseurl + "endDate=" + datestr + "&" + "words=&" + "categories=" + category;
        return URL;
    }
}
