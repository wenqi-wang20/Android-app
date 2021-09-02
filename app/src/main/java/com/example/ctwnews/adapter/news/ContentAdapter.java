package com.example.ctwnews.adapter.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.example.ctwnews.frag.ContentFragment;

import java.util.List;

//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;

public class ContentAdapter extends FragmentPagerAdapter {
    public static List<String> mTitle;

    @Override
    public CharSequence getPageTitle(int position){
        return mTitle.get(position);
    }

    public ContentAdapter(FragmentManager fm, List<String> mTitle){

        super(fm);
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int i){
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", mTitle.get(i));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount(){
        return mTitle.size();
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i){
        ContentFragment fragment = (ContentFragment) super.instantiateItem(container, i);
        Bundle bundle = new Bundle();
        bundle.putString("title", mTitle.get(i));
        fragment.setArguments(bundle);
        return fragment;
    }

}
