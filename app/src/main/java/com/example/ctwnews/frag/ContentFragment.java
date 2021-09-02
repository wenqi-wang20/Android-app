package com.example.ctwnews.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v4.app.Fragment;
//import androidx.fragment.app.Fragment;

import com.example.ctwnews.R;

public class ContentFragment extends Fragment {
    //调试信息
    private TextView mTagText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.container, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        mTagText = (TextView) view.findViewById(R.id.mTagText);
        Bundle bundle = getArguments();

        String title  = bundle.getString("title");
        mTagText.setText(title);
    }
}
