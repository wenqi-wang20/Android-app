package com.example.ctwnews.frag;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ctwnews.NewsChannelActivity;
import com.example.ctwnews.R;
import com.example.ctwnews.adapter.news.ContentAdapter;
import com.example.ctwnews.models.news.TagManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {
    //调试信息
    public static TabLayout tabLayout;
    public static final String TAG = "NewsFragment";
    public static ContentAdapter myContentAdapter;

//    private static NewsFragment instance = null;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private Observable<Boolean> observable;
    private Map<String, Fragment> map = new HashMap<>();
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.newsfragment, container, false);
        initView(view);
        //initData();
        return view;
    }


    private void initView(View view){
        tabLayout = view.findViewById(R.id.newsTabLayout);
        viewPager = view.findViewById(R.id.view_page_news);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TagManager.initmyTitleTag();
        TagManager.initallTitleTag();

        ImageView add_channel_iv = view.findViewById(R.id.tv_add);
        add_channel_iv.setOnClickListener(v->{
            startActivity(new Intent(getActivity(), NewsChannelActivity.class));
        }
        );

        for(int i = 0; i < TagManager.myTitleTag.size(); i++){
            tabLayout.addTab(tabLayout.newTab().setText(TagManager.myTitleTag.get(i)));
        }

        //预加载
        viewPager.setOffscreenPageLimit(TagManager.myTitleTag.size());

        //初始化配置器
        myContentAdapter = new ContentAdapter(getActivity().getSupportFragmentManager(), TagManager.myTitleTag);
        viewPager.setAdapter(myContentAdapter);

        //设置相互监听
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //监听切换
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
