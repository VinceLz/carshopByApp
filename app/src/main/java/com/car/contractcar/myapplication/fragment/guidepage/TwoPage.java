package com.car.contractcar.myapplication.fragment.guidepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.car.contractcar.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macmini2 on 16/11/6.
 */

public class TwoPage extends Fragment {
    @BindView(R.id.view_img)
    ImageView viewImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.pages_one_two, null);
        ButterKnife.bind(this, view);
        viewImg.setImageResource(R.mipmap.two);
        return view;
    }
}
