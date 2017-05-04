package com.sunny.xianyuenews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.channelmanager.ProjectChannelBean;
import com.sunny.xianyuenews.R;
import com.sunny.xianyuenews.adapter.FixedPagerAdapter;

import java.util.List;

/**
 * 孙立鹏 on 2017/3/24 13:51
 *
 * 图片模块
 */

public class PhotoFragment extends BaseFragment {
    private final String TAG = PhotoFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private View mView;
    private FixedPagerAdapter fixedPagerAdapter;
    private List<BaseFragment> fragments;
    private static List<ProjectChannelBean> channelBeanList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tablayout_pager, container, false);
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
