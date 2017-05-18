package com.car.contractcar.myapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.fragment.guidepage.OnePage;
import com.car.contractcar.myapplication.fragment.guidepage.ThreePage;
import com.car.contractcar.myapplication.fragment.guidepage.TwoPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends FragmentActivity {

    @BindView(R.id.view_pages)
    ViewPager viewPages;
    private List<Fragment> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
        initData();
        viewPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == pages.size() - 1) {
                    ThreePage threePage = (ThreePage) pages.get(position);
                    threePage.showAnimation();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initView() {
        pages = new ArrayList<>();
        OnePage onePage = new OnePage();
        TwoPage twoPage = new TwoPage();
        ThreePage threePage = new ThreePage();
        pages.add(onePage);
        pages.add(twoPage);
        pages.add(threePage);

    }

    private void initData() {
        viewPages.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return pages.get(position);
            }

            @Override
            public int getCount() {
                return pages.size();
            }
        });
    }
}
