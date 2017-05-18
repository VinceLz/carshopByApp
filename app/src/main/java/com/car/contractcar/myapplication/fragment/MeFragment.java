package com.car.contractcar.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.MyGridView;
import com.car.contractcar.myapplication.common.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by macmini2 on 16/11/5.
 */

public class MeFragment extends Fragment {
    @BindView(R.id.me_avatar)
    CircleImageView meAvatar;
    @BindView(R.id.me_gridview)
    MyGridView meGridview;
    Integer[] imgs = new Integer[]{R.mipmap.me_order, R.mipmap.me_shopping_cart, R.mipmap.me_coupon, R.mipmap.me_favorites,
            R.mipmap.me_invite_friends, R.mipmap.me_daily_attendance, R.mipmap.me_exchange, R.mipmap.me_contact_us, R.mipmap.me_settings};
    String[] texts = new String[]{"我的订单", "购物车", "优惠卷", "收藏夹", "邀请好友", "每日签到", "兑换专区", "联系我们", "设置"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_me);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        meGridview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
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
            public View getView(int position, View convertView, ViewGroup parent) {
                View xmlView = UIUtils.getXmlView(R.layout.me_gridview_item);
                ImageView imageView = (ImageView) xmlView.findViewById(R.id.me_gridview_item_img);
                TextView textView = (TextView) xmlView.findViewById(R.id.me_gridview_item_text);
                imageView.setImageResource(imgs[position]);
                textView.setText(texts[position]);
                return xmlView;
            }
        });
    }
}
