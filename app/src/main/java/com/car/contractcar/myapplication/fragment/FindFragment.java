package com.car.contractcar.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.ui.MyGridView;
import com.car.contractcar.myapplication.common.utils.AppUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.common.utils.Utils;
import com.car.contractcar.myapplication.find.model.Car;
import com.car.contractcar.myapplication.find.model.Catalog;
import com.car.contractcar.myapplication.find.model.ColumnHorizontalScrollView;
import com.car.contractcar.myapplication.find.model.NewsFragment;
import com.car.contractcar.myapplication.find.model.NewsFragmentPagerAdapter;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.car.contractcar.myapplication.MyApplication.context;


/**
 * Created by macmini2 on 16/11/5.
 */

public class FindFragment extends Fragment {

    private LoadingPage loadingPage;
    private BDLocation bdLocation;

    @BindView(R.id.mColumnHorizontalScrollView)
    ColumnHorizontalScrollView mColumnHorizontalScrollView; // 自定义HorizontalScrollView
    @BindView(R.id.mRadioGroup_content)
    LinearLayout mRadioGroup_content; // 每个标题
    @BindView(R.id.ll_more_columns)
    LinearLayout ll_more_columns; // 右边+号的父布局

    ImageView button_more_columns; // 标题右边的+号

    final static int CHANNELREQUEST = 1; // 请求码
    final static int CHANNELRESULT = 10; // 返回码
    int columnSelectIndex = 0; // 当前选中的栏目索引
    ArrayList<Catalog> userChannelList;
    int mScreenWidth = 0; // 屏幕宽度
    int mItemWidth = 0; // Item宽度：每个标题的宽度
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    // ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    NewsFragment newfragment;
    @BindView(R.id.shade_left)
    ImageView shade_left; // 左阴影部分
    @BindView(R.id.shade_right)
    ImageView shade_right; // 右阴影部分
    @BindView(R.id.rl_column)
    RelativeLayout rl_column; // +号左边的布局：包括HorizontalScrollView和左右阴影部分;
    public static LoadingDialog loadingDialog = null;
    @BindView(R.id.app)
    MyGridView gridView;
    MyGrid myGrid;
    String[] app = {"违章查询", "附近加油"};
    int[] app_ioc = {R.mipmap.timg2, R.mipmap.jiayou

    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        bdLocation = MainActivity.bdLocation;
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        mItemWidth = mScreenWidth / 7;
        loadingDialog = new LoadingDialog(getContext(), "加载中...");
        loadingPage.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_find;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(FindFragment.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {
                    String list = JSONObject.parseObject(resultState.getContent()).getString("list");
                    final List<Catalog> catalogs = JSON.parseArray(list, Catalog.class);
                    if (catalogs != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userChannelList = (ArrayList<Catalog>) catalogs;
                                initView();
                                refreshView();
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }
            }

            @Override
            protected RequestParams params() {
                return null;
            }

            @Override
            protected String url() {
                return Constant.HTTP_BASE + Constant.NEW_CATALOG;
            }
        };


        return loadingPage;
    }


    private void initView() {
        myGrid = new MyGrid();
        gridView.setAdapter(myGrid);
        initTabColumn();
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        //  fragments.clear();//清空
//        int count = userChannelList.size();
//        for (int i = 0; i < count; i++) {
        newfragment = new NewsFragment(userChannelList.get(0).getCatid());
        //  fragments.add(newfragment);
        //   }
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getFragmentManager(), newfragment);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.addOnPageChangeListener(pageListener);
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = userChannelList.size();
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(userChannelList.get(i).getCatid());
            columnTextView.setText(userChannelList.get(i).getCname());
            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            // 单击监听
            columnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.show();
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                        } else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }

                    newfragment.update(v.getId());
                    // Toast.makeText(getActivity(), v.getId() + "", Toast.LENGTH_SHORT).show();
                    //在这触发
                }
            });
            mRadioGroup_content.addView(columnTextView, i, params);
        }
    }


    /**
     * 刷新UI
     */
    private void refreshView() {

    }


    /**
     * ViewPager切换监听方法
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            Log.d("----", "触发了3");
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        //判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }


    class MyGrid extends BaseAdapter {

        @Override
        public int getCount() {
            return app.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.find_grid_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(app[position]);
            viewHolder.imageView.setImageResource(app_ioc[position]);
            switch (position) {
                case 0:  //违章
                    viewHolder.app_parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), Car.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case 1: //附近加油 直接调用地图api
                    viewHolder.app_parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!AppUtil.openMapByjiaYou(context, bdLocation.getLatitude() + "", bdLocation.getLongitude() + "", "加油站")) {
                                //没有安装
                                UIUtils.Toast("您没有安装地图App，暂时无法查询", false);// TODO: 16/11/29
                            }
                        }
                    });

                    break;
            }
            return convertView;
        }
    }

    class ViewHolder {
        @BindView(R.id.app_title)
        TextView textView;
        @BindView(R.id.app_ioc)
        ImageView imageView;
        @BindView(R.id.app_parent)
        LinearLayout app_parent;

        public ViewHolder(View b) {
            ButterKnife.bind(this, b);
        }
    }

    public void update() {
        if (mRadioGroup_content != null && mRadioGroup_content.getChildCount() > 0) {
            View localView = mRadioGroup_content.getChildAt(0);
            newfragment.update(localView.getId());
        }
    }
}

