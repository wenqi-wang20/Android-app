package com.example.ctwnews.models.news;

import com.example.ctwnews.bean.ItemBean;
import com.example.ctwnews.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TagManager {
    //主页标签
    public static List<String> myTitleTag = new ArrayList<>();
    //所有标签
    public static List<String> allTitleTag = new ArrayList<>();

    public static void initmyTitleTag(){
        List<ItemBean> itemBeans = new ArrayList<>();
        if (SPUtil.init("data").getString("channel").isEmpty()) {
            List<ItemBean> initData = new ArrayList<>();
            initData.add(new ItemBean("军事", true));
            initData.add(new ItemBean("娱乐", true));
            initData.add(new ItemBean("科技", true));
            initData.add(new ItemBean("汽车", true));
            initData.add(new ItemBean("体育", true));
            initData.add(new ItemBean("教育", true));
            initData.add(new ItemBean("热点", true));
            initData.add(new ItemBean("政治", true));
            initData.add(new ItemBean("健康", true));
            initData.add(new ItemBean("财经", true));
            initData.add(new ItemBean("社会", true));
            initData.add(new ItemBean("文化", true));

            SPUtil.init("data").putString("channel", new Gson().toJson(initData));
        }
        Gson gson = new Gson();
        itemBeans = gson.fromJson(SPUtil.init("data").getString("channel"), new TypeToken<List<ItemBean>>() {
        }.getType());
        for (ItemBean item:
             itemBeans) {
            if(item.isStatus()){
                TagManager.myTitleTag.add(item.getName());
            }
        }
    }

    public static void initallTitleTag(){
//        allTitleTag.add("军事");
//        allTitleTag.add("汽车");
//        allTitleTag.add("健康");
//        allTitleTag.add("财经");
//        allTitleTag.add("文化");
//        allTitleTag.add("文化");
    }
}
