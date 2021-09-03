package com.example.ctwnews.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ctwnews.R;
import com.example.ctwnews.adapter.news.MsgAdapter;
import com.example.ctwnews.models.news.Msg;
import com.example.ctwnews.util.JSONUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ContentFragment extends Fragment {
    //调试信息
    private ListView listView;
    String category;
    //测试数据集
    private List<Msg> msgList;
    private MsgAdapter adapter;
    //实例化视图
    private View view;
    //滑动视图
    private SwipeRefreshLayout swipeFlushView;
    //线程提示
//    private MyHandler mHandler;

    private int pageNum;
    private int newsnumber = 50;
    Date date = new Date();
    String sizestr = String.valueOf(newsnumber);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String datestr = df.format(date);
    String baseurl = "https://api2.newsminer.net/svc/news/queryNewsList?";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.container, null);
        pageNum = 1;
//        mHandler = new MyHandler((MainActivity)getActivity());
        initView(view);
        return view;

    }

    private void initView(View view){
        Bundle bundle = getArguments();
        //获取当前所在的tab
        category  = bundle.getString("title");
        //初始化列表
        msgList = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.listview);
        adapter = new MsgAdapter(msgList, getActivity());
        listView.setAdapter(adapter);
        swipeFlushView = (SwipeRefreshLayout) view.findViewById(R.id.swipeFlushView);
        swipeFlushView.setSize(SwipeRefreshLayout.LARGE);
        swipeFlushView.setProgressBackgroundColorSchemeColor(Color.CYAN);
        swipeFlushView.setColorSchemeResources(android.R.color.holo_orange_dark
                ,android.R.color.holo_blue_dark
                ,android.R.color.holo_red_dark
                ,android.R.color.widget_edittext_dark);

        /**
         * 在这里获取新闻列表，并且绑定在adapter上
         */

//        String url = getcatogoryNews(category);
//        Log.i("url",url);
//        JSONUtil.sendRequestWithOkhttp(url, new okhttp3.Callback(){
//            @Override
//            public void onFailure(Call call, IOException e){
//                //Log.i("enterparse","no");
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException{
//                //Log.i("enterparse","yes");
//                //异步回调的数据需要返回主线程中处理
//                Handler mainHandler = new Handler(getActivity().getMainLooper());
//                mainHandler.post(new Runnable() {
//                    List<Msg> msglist = JSONUtil.parseJSON(response);
//                    @Override
//                    public void run() {
//                        msgList = msglist;
//                        Log.i("size", String.valueOf(msgList.size()));
//                        if(msgList.size() > 0) {
//                            Log.i("datefresh", "yes");
//                            //让时间增加一分钟，方便下次更新列表
//                            setnewDate(msgList.get(msgList.size() - 1).getPublishTime());
//                        }
//                        Log.i("newsnum",String.valueOf(msgList.size()));
//                        adapter = new MsgAdapter(msgList, getActivity());
//                        listView.setAdapter(adapter);
//                    }
//                });
//            }
//        });

        //这是下拉的刷新效果
        getDataList();

        swipeFlushView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                //发送一个延时1秒的handler信息
                mhandler.sendEmptyMessageDelayed(1,1000);
            }
        });
        //这是上滑的刷新效果
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //当滑动状态发生改变的时候执行
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    //当不滚动的时候
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //判断是否是最底部
                        if(view.getLastVisiblePosition()==(view.getCount())-1){

                            /**
                             * 上滑加载数据
                             */
                            getDataList();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
            //正在滑动的时候执行
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    private void setnewDate(String now_time){
        Date now_date = new Date();
        try {
            now_date = df.parse(now_time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //
        now_date.setTime(now_date.getTime() - 1*60*1000);
        datestr = df.format(now_date);
    }

    private String getcatogoryNews(String category){
        String URL = baseurl + "size=" + sizestr + "&" + "endDate=" + datestr + "&" + "words=&" + "categories=" + category;
        return URL;
    }

    private void getDataList(){
        String URL = getcatogoryNews(category);
        JSONUtil.sendRequestWithOkhttp(URL, new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                mhandler.sendEmptyMessageDelayed(-1,1000);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    List<Msg> mlist = JSONUtil.parseJSON(response);
                    @Override
                    public void run() {
                        if(mlist != null && mlist.size() > 0){
                            setnewDate(mlist.get(mlist.size()-1).getPublishTime());
                            msgList.addAll(mlist);
                            adapter.notifyDataSetChanged();
                            mhandler.sendEmptyMessageDelayed(2,1000);
                        }else{
                            mhandler.sendEmptyMessageDelayed(0,1000);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mhandler != null) {
            mhandler.removeCallbacksAndMessages(null);
            mhandler = null;
        }
    }

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message m){
            if(m.what == -1){
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
            }else if(m.what == 0){
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"没有更多数据", Toast.LENGTH_SHORT).show();
            }else if(m.what == 1){
                Toast.makeText(getContext(),"已是最新", Toast.LENGTH_SHORT).show();
                swipeFlushView.setRefreshing(false);
            }else if(m.what == 2){
                Toast.makeText(getContext(),"加载成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
//    class MyHandler extends Handler {
//        private WeakReference<MainActivity> weakReference;
//
//        MyHandler(MainActivity activity) {
//            weakReference = new WeakReference<MainActivity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case -1:
//                    swipeFlushView.setFlushing(false);
//                    swipeFlushView.setLoading(false);
//                    Toast.makeText(weakReference.get(), "获取数据失败！", Toast.LENGTH_SHORT).show();
//                    break;
//                case 0:
//                    swipeFlushView.setFlushing(false);
//                    swipeFlushView.setLoading(false);
//                    Toast.makeText(weakReference.get(), "没有更多的数据！", Toast.LENGTH_SHORT).show();
//                    break;
//                case 1:
//                    adapter.notifyDataSetChanged();
//                    swipeFlushView.setFlushing(false);
//                    break;
//                case 2:
//                    adapter.notifyDataSetChanged();
//                    swipeFlushView.setLoading(false);
//                    break;
//                case 3:
//                    if (pageNum == 1) {
//                        swipeFlushView.setFlushing(false);
//                        Toast.makeText(weakReference.get(), "刷新失败！", Toast.LENGTH_SHORT).show();
//                    } else if (pageNum > 1) {
//                        swipeFlushView.setLoading(false);
//                        Toast.makeText(weakReference.get(), "加载失败！", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
}
