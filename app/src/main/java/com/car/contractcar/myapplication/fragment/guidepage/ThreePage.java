package com.car.contractcar.myapplication.fragment.guidepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.ui.randomLayout.AnimationUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macmini2 on 16/11/6.
 */

public class ThreePage extends Fragment {


    @BindView(R.id.pages_three_img)
    ImageView pagesThreeImg;
    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.buy_car_server)
    TextView buyCarServer;
    @BindView(R.id.keep_car_server)
    TextView keepCarServer;
    @BindView(R.id.select_server)
    LinearLayout selectServer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.pages_three, null);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.start_btn)
    public void startbtn(View view) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.CAR, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("t", Constant.BUY_CAR);
        edit.putInt("start_info", 1);
        edit.commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void showAnimation() {
        selectServer.setVisibility(View.VISIBLE);
        Animation zoomInNearAnim = AnimationUtil.createZoom();
        selectServer.startAnimation(zoomInNearAnim);
    }
}
