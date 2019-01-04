package com.github.yoyozhangh.studydemo.supportlibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.yoyozhangh.studydemo.R;

/**
 * Created by yoyozhangh on 2018/12/25.
 */

public class ContentFragment extends Fragment {

    private View view;
    private static final String KEY = "title";
    private TextView tvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contentfragment, container, false);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        String title = getArguments().getString(KEY);
        tvContent.setText(title);
        tvContent.setTextColor(Color.BLUE);
        tvContent.setTextSize(30);
        return view;

    }

    public static ContentFragment newInstance(String title) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }
}
