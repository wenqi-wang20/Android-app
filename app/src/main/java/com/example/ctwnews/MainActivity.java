package com.example.ctwnews;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.ctwnews.frag.NewsFragment;
import com.example.ctwnews.frag.SearchFragment;
import com.example.ctwnews.frag.UserFragment;

//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private NewsFragment newsFragment = new NewsFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private UserFragment userFragment = new UserFragment();
    private Fragment[] mFragments = new Fragment[3];
    private int mPreFragmentFlag = 0;

//    private NewsTabLayout newsTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * 新建toolBar代替原来的toolbar
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * 下面这段代码用于设置左端悬浮菜单栏的打开
         */
        DrawerLayout layout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();

        /**
         * 以下代表初始化底部菜单栏
         */
        init_page();
    }

    /**
     * 初始菜单栏页面
     */
    private void init_page(){
        navigationView = findViewById(R.id.bottom_navigation);
        mFragments[0] = newsFragment;
        mFragments[1] = searchFragment;
        mFragments[2] = userFragment;

        //动态加载
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i = 0; i < mFragments.length; i++){
            transaction.add(R.id.mContainerView, mFragments[i], mFragments[i].getClass().getName());
            if(i != 0){
                transaction.hide(mFragments[i]);
            }
        }
        transaction.commitAllowingStateLoss();

        //监听底部导航栏
        navigationView.setItemIconTintList(null);
        navigationView.setOnNavigationItemSelectedListener(
                menuItem->{
                    switch (menuItem.getItemId()){
                        case R.id.action_news:
                            toolbar.setTitle("新闻");
                            show_hider_fragment(mFragments[0], mFragments[mPreFragmentFlag]);
                            mPreFragmentFlag = 0;
                            break;
                        case R.id.action_research:
                            toolbar.setTitle("搜索");
                            show_hider_fragment(mFragments[1], mFragments[mPreFragmentFlag]);
                            mPreFragmentFlag = 1;
                            break;
                        case R.id.action_user:
                            toolbar.setTitle("我的");
                            show_hider_fragment(mFragments[2], mFragments[mPreFragmentFlag]);
                            mPreFragmentFlag = 2;
                            break;
                    }
                    return true;
                }
        );
    }

    private void show_hider_fragment(Fragment show, Fragment hide){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(show != hide)
            transaction.show(show).hide(hide).commitAllowingStateLoss();
    }
}

