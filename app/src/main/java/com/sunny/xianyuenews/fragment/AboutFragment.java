package com.sunny.xianyuenews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sunny.xianyuenews.R;

/**
 * 作者：SUNny on 2017/5/4 0004 17:18
 * 邮箱：sunlipeng1994@163.com
 *
 * 我 页面模块
 */

public class AboutFragment extends BaseFragment {

    private final String TAG = AboutFragment.class.getSimpleName();
    private String[] data;
//    private AboutAdapter adapter;
    private ListView mListView;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_about, null);
        Toolbar myToolbar = initToolbar(mView, R.id.my_toolbar, R.id.toolbar_title, R.string.user_home);
        initView();
        bindData();
        initListener();
        return mView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}
