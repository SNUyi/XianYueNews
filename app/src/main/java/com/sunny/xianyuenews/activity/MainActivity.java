package com.sunny.xianyuenews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.sunny.xianyuenews.R;
import com.sunny.xianyuenews.Utils.LogUtils;
import com.sunny.xianyuenews.bean.BottomTab;
import com.sunny.xianyuenews.fragment.AboutFragment;
import com.sunny.xianyuenews.fragment.NewsFragment;
import com.sunny.xianyuenews.fragment.PhotoFragment;
import com.sunny.xianyuenews.fragment.VideoFragment;
import com.sunny.xianyuenews.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();


    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<BottomTab> mBottomTabs = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();

    }

    // 初始化底部标签栏
    private void initTab() {
        // 新闻标签
        BottomTab bottomTab_news = new BottomTab(NewsFragment.class,R.string.news_fragment,R.drawable.select_icon_news);
        // 图片标签
        BottomTab bottomTab_photo = new BottomTab(PhotoFragment.class,R.string.photo_fragment,R.drawable.select_icon_photo);
        // 视频标签
        BottomTab bottomTab_video = new BottomTab(VideoFragment.class,R.string.video_fragment,R.drawable.select_icon_video);
        // 我 标签
        BottomTab bottomTab_about = new BottomTab(AboutFragment.class,R.string.about_fragment,R.drawable.select_icon_about);


        mBottomTabs.add(bottomTab_news);
        mBottomTabs.add(bottomTab_photo);
        mBottomTabs.add(bottomTab_video);
        mBottomTabs.add(bottomTab_about);


        // 设置FragmentTab
        mInflater = LayoutInflater.from(this);//加载布局管理器
        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);//绑定tabhost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);//绑定layout


        for (BottomTab bottomTab : mBottomTabs){
            /*新建Tabspec选项卡并设置Tab菜单栏的内容*/
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(bottomTab.getTitle()));

            tabSpec.setIndicator(buildIndicator(bottomTab));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, bottomTab.getFragment(),null);

        }

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                LogUtils.d(TAG, "onTabChanged: mTabHost.setOnTabChangedListener" + R.string.news_fragment);

            }
        });

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);

    }

    // 设置底部tab的图片和文字
    private View buildIndicator(BottomTab bottomTab){

        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(bottomTab.getIcon());
        text.setText(bottomTab.getTitle());

        return  view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String tag =  mTabHost.getCurrentTabTag();
        if (resultCode == 789){
            Bundle bundle = data.getExtras();
            int tabPosition = bundle.getInt("NewTabPostion");
            NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag(tag);
            newsFragment.setCurrentChannel(tabPosition);
            newsFragment.notifyChannelChange();
        }
    }
}
