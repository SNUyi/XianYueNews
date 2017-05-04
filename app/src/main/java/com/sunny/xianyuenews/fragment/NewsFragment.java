package com.sunny.xianyuenews.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.channelmanager.APPConst;
import com.example.channelmanager.ProjectChannelBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.sunny.xianyuenews.R;
import com.sunny.xianyuenews.Utils.CategoryDataUtils;
import com.sunny.xianyuenews.Utils.IOUtils;
import com.sunny.xianyuenews.Utils.ListDataSave;
import com.sunny.xianyuenews.activity.ChannelManagerActivity;
import com.sunny.xianyuenews.adapter.FixedPagerAdapter;
import com.sunny.xianyuenews.fragment.news.NewsListFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsFragment extends BaseFragment {

    private final String TAG = NewsFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private View mView;
    private FixedPagerAdapter fixedPagerAdapter;
    private List<BaseFragment> fragments;
    private List<ProjectChannelBean> myChannelList;
    private List<ProjectChannelBean> moreChannelList;
    private ImageButton mChange_channel;
    // 当前新闻频道的位置
    private int tabPosition;
    private SharedPreferences sharedPreferences;
    private ListDataSave listDataSave;
    private boolean isFirst;
    private BaseFragment baseFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tablayout_pager, container, false);

        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    @Override
    public void initView() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) mView.findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) mView.findViewById(R.id.change_channel);

        Toolbar myToolbar = initToolbar(mView, R.id.my_toolbar, R.id.toolbar_title, R.string.news_home);
        initValidata();
        initListener();
    }


    @Override
    public void initValidata() {
        sharedPreferences = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        listDataSave = new ListDataSave(getActivity(), "channel");
        fragments = new ArrayList<BaseFragment>();
        fixedPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());

        mTabLayout.setupWithViewPager(mNewsViewpager);
        bindData();
    }

    @Override
    public void initListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mChange_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChannelManagerActivity.class);
                intent.putExtra("TABPOSITION", tabPosition);
                startActivityForResult(intent, 999);
            }
        });
    }

    @Override
    public void bindData() {
        getDataFromSharedPreference();
        fixedPagerAdapter.setChannelBean(myChannelList);
        fixedPagerAdapter.setFragments(fragments);
        mNewsViewpager.setAdapter(fixedPagerAdapter);
    }

    /**
     * 判断是否第一次进入程序
     * 如果第一次进入，直接获取设置好的频道
     * 如果不是第一次进入，则从sharedPrefered中获取设置好的频道
     */
    private void getDataFromSharedPreference() {
        isFirst = sharedPreferences.getBoolean("isFirst", true);
        if (isFirst) {
            myChannelList = CategoryDataUtils.getChannelCategoryBeans();
            moreChannelList = getMoreChannelFromAsset();
            myChannelList = setType(myChannelList);
            moreChannelList = setType(moreChannelList);
            listDataSave.setDataList("myChannel", myChannelList);
            listDataSave.setDataList("moreChannel", moreChannelList);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("isFirst", false);
            edit.commit();
        } else {
            myChannelList = listDataSave.getDataList("myChannel", ProjectChannelBean.class);
        }
        fragments.clear();
        for (int i = 0; i < myChannelList.size(); i++) {
            baseFragment = NewsListFragment.newInstance(myChannelList.get(i).getTid());

            fragments.add(baseFragment);
        }
        if (myChannelList.size() <= 4) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }

    /**
     * 在ManiActivty中被调用，当从ChanelActivity返回时设置当前tab的位置
     * @param tabPosition
     */
    public void setCurrentChannel(int tabPosition) {
        mNewsViewpager.setCurrentItem(tabPosition);
        mTabLayout.setScrollPosition(tabPosition, 1, true);
    }

    /**
     * 在myChannelList发生改变的时候更新ui，在MainActivity调用
     */
    public void notifyChannelChange() {
        getDataFromSharedPreference();
        fixedPagerAdapter.setChannelBean(myChannelList);

        fixedPagerAdapter.setFragments(fragments);
        fixedPagerAdapter.notifyDataSetChanged();

    }

    private List<ProjectChannelBean> setType(List<ProjectChannelBean> list) {
        Iterator<ProjectChannelBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            ProjectChannelBean channelBean = iterator.next();
            channelBean.setTabType(APPConst.ITEM_EDIT);
        }
        return list;
    }

    /**
     * 从Asset目录中读取更多频道
     *
     * @return
     */
    public List<ProjectChannelBean> getMoreChannelFromAsset() {
        String moreChannel = IOUtils.readFromFile("projectChannel.txt");
        List<ProjectChannelBean> projectChannelBeanList = new ArrayList<>();
        JsonArray array = new JsonParser().parse(moreChannel).getAsJsonArray();
        for (final JsonElement elem : array) {
            projectChannelBeanList.add(new Gson().fromJson(elem, ProjectChannelBean.class));
        }
        return projectChannelBeanList;
    }
}
