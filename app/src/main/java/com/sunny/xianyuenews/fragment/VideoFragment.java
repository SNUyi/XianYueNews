package com.sunny.xianyuenews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.sunny.xianyuenews.R;
import com.sunny.xianyuenews.Utils.ThreadManager;
import com.sunny.xianyuenews.bean.VideoBean;
import com.sunny.xianyuenews.widget.LoadMoreFooterView;
import com.sunny.xianyuenews.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：SUNny on 2017/5/4 0004 17:10
 * 邮箱：sunlipeng1994@163.com
 *
 * 视频模块
 */

public class VideoFragment extends BaseFragment {
    private final String TAG = VideoFragment.class.getSimpleName();

    private View mView;
    private ArrayList<VideoBean> mVideoBeanList;
    private IRecyclerView mIRecyclerView;
    private ThreadManager.ThreadPool mThreadPool;   // 线程池
    private int mStartIndex = 0;
    private boolean isPullRefresh;
    private List<VideoBean> newlist;   // 上拉刷新后获得的数据
    private final String VID = "VID";
    private LoadMoreFooterView mLoadMoreFooterView;
//    private VideoListAdapter mVideoListAdapter;
    private LoadingPage mLoadingPage;
    private boolean isShowCache = false; // 是否有缓存数据被展示
    private boolean isConnectState = false;  // 判断当前是否在联网刷新, false表示当前没有联网刷新


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video, null);
        initView();
        initValidata();
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
