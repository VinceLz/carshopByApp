package com.car.contractcar.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.entity.BuyCarIndex2;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hanks.htextview.HTextView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.car.contractcar.myapplication.MyApplication.context;

/**
 * Created by macmini2 on 16/11/5.
 */

public class FindFragment extends Fragment {
    @BindView(R.id.findtest)
    ListView findtest;

    private BuyCarIndex2 buyCArIndex2;
    private List<BuyCarIndex2.HomeImageBean> homeImage;
    private List<BuyCarIndex2.CarstoreBean> homeCarstore;
    private List<BuyCarIndex2.HomeActiveBean> homeActive;
    private LoadingPage loadingPage;
    private int statusBarHeight2;
    private static int count = 0;
    private boolean isContinue;
    private RollPagerView mRollViewPager;
    private BDLocation bdLocation;
    private Thread myThread;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                hTextView.animateText(homeActive.get(count % homeActive.size()).getTitle());
                count++;
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };
    private HTextView hTextView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        bdLocation = MainActivity.bdLocation;
        loadingPage.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_find;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(FindFragment.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {
                    buyCArIndex2 = (BuyCarIndex2) JsonUtils.json2Bean(resultState.getContent(), BuyCarIndex2.class);
                    initView();
                    refreshView();
                }
            }

            @Override
            protected RequestParams params() {

                return null;
            }

            @Override
            protected String url() {
                if (bdLocation != null) {
                    return Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + bdLocation.getLongitude() + "&latitude=" + bdLocation.getLatitude();
                } else {
                    return Constant.HTTP_BASE + Constant.HTTP_HOME;
                }

            }
        };


        return loadingPage;
    }


    private void initView() {
        View xmlView = UIUtils.getXmlView(R.layout.test);
        hTextView = (HTextView) xmlView.findViewById(R.id.htext);
        mRollViewPager = (RollPagerView) xmlView.findViewById(R.id.view_pages_carousel);
        findtest.addHeaderView(xmlView);


    }


    /**
     * 刷新UI
     */
    private void refreshView() {
        if (buyCArIndex2 != null) {
            handler.sendEmptyMessage(0);
            //设置适配器
            homeImage = buyCArIndex2.getHomeImage();
            homeCarstore = buyCArIndex2.getCarstore();
            homeActive = buyCArIndex2.getHomeActive();

            mRollViewPager.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());

                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ImageLoad.loadImg(imageView, homeImage.get(position).getImage());
                    return imageView;
                }

                @Override
                public int getCount() {
                    return homeImage.size();
                }
            });
            findtest.setAdapter(new MyListAdapter());

        }

    }

    /**
     * listView的适配器
     */
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return homeCarstore.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holderView = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.car_item, null);
                holderView = new ViewHolder(convertView);
                convertView.setTag(holderView);
            } else {
                holderView = (ViewHolder) convertView.getTag();
            }
            holderView.storeAddress.setText(homeCarstore.get(position).getBaddress());
            holderView.storeName.setText(homeCarstore.get(position).getBname());
            holderView.carOwner.setText("主营车型 : " + homeCarstore.get(position).getMajorbusiness());
            if (!TextUtils.isEmpty(homeCarstore.get(position).getTitle1())) {
                holderView.homeTitle1Text.setText(homeCarstore.get(position).getTitle1());
            } else {
                holderView.homeTitle1Ly.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(homeCarstore.get(position).getTitle2())) {
                holderView.homeTitle2Text.setText(homeCarstore.get(position).getTitle2());
            } else {
                holderView.homeTitle2Ly.setVisibility(View.INVISIBLE);
            }

            ImageLoad.loadImg(holderView.carImg, homeCarstore.get(position).getBshowImage());

            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.car_img)
        SimpleDraweeView carImg;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.store_address)
        TextView storeAddress;
        @BindView(R.id.car_owner)
        TextView carOwner;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.car_location)
        ImageView carLocation;
        @BindView(R.id.car_call)
        ImageView carCall;
        @BindView(R.id.home_title1_text)
        TextView homeTitle1Text;
        @BindView(R.id.home_title1_ly)
        LinearLayout homeTitle1Ly;
        @BindView(R.id.home_title2_text)
        TextView homeTitle2Text;
        @BindView(R.id.home_title2_ly)
        LinearLayout homeTitle2Ly;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
