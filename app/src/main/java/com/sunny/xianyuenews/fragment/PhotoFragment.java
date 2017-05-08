package com.sunny.xianyuenews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.channelmanager.ProjectChannelBean;
import com.sunny.xianyuenews.R;
import com.sunny.xianyuenews.Utils.CategoryDataUtils;
import com.sunny.xianyuenews.adapter.FixedPagerAdapter;
import com.sunny.xianyuenews.fragment.photo.PicListFragment;

import java.util.ArrayList;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar myToolbar = initToolbar(mView, R.id.my_toolbar, R.id.toolbar_title, R.string.picture_home);
        initView();
    }

    @Override
    public void initView() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) mView.findViewById(R.id.news_viewpager);
        mView.findViewById(R.id.change_channel).setVisibility(View.GONE);
        channelBeanList = CategoryDataUtils.getPicCategoryBeans();
        initValidata();
        initListener();
    }

    @Override
    public void initValidata() {
        fixedPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());

        fragments = new ArrayList<BaseFragment>();
        for (int i = 0; i < channelBeanList.size(); i++) {
            // "推荐","","0031"
            // "明星","","0003"使用瀑布流
            ProjectChannelBean channelBean = channelBeanList.get(i);
            //通知图片列表fragment显示的哪个频道的图片
            BaseFragment fragment = PicListFragment.newInstance(channelBean.getTid(), channelBean.getColumn());
            fragments.add(fragment);
        }

        fixedPagerAdapter.setChannelBean(channelBeanList);
        fixedPagerAdapter.setFragments(fragments);

        mTabLayout.setupWithViewPager(mNewsViewpager);
        mNewsViewpager.setAdapter(fixedPagerAdapter);
        // mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); //适合很多tab
        mTabLayout.setTabMode(TabLayout.MODE_FIXED); // tablayout均分，适合少Tablayout
    }

    @Override
    public void bindData() {
    }

    @Override
    public void initListener() {
        mNewsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
