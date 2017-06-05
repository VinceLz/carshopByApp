package com.car.contractcar.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.MyGridView;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.car.contractcar.myapplication.user.adapter.MyOrderActivity;
import com.car.contractcar.myapplication.user.adapter.MyRoll;
import com.car.contractcar.myapplication.user.adapter.SettingActivity;

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
    @BindView(R.id.car)
    TextView car;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.user_denglu)
    TextView user_denglu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_me);
        ButterKnife.bind(this, view);
        initView();
        initUser();
        return view;
    }

    public void initUser() {
        if (Constant.USER == null) {
            //没有登陆
            model.setVisibility(View.GONE);
            car.setVisibility(View.GONE);
            meAvatar.setVisibility(View.GONE);
            user_denglu.setVisibility(View.VISIBLE);
        } else {
            model.setVisibility(View.VISIBLE);
            car.setVisibility(View.VISIBLE);
            meAvatar.setVisibility(View.VISIBLE);
            user_denglu.setVisibility(View.GONE);
        }
    }

    private void initView() {
        user_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登陆
                Intent i = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(i, 1);

            }
        });
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
            public View getView(final int position, View convertView, final ViewGroup parent) {
                ViewHolder viewHolder = null;
                if (convertView == null) {
                    convertView = UIUtils.getXmlView(R.layout.me_gridview_item);
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.me_gridview_item_img);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.me_gridview_item_text);
                    viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.order_quan);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                //  View xmlView = UIUtils.getXmlView(R.layout.me_gridview_item);
                //   ImageView imageView = (ImageView) xmlView.findViewById(R.id.me_gridview_item_img);
                //   TextView textView = (TextView) xmlView.findViewById(R.id.me_gridview_item_text);
                viewHolder.imageView.setImageResource(imgs[position]);
                viewHolder.textView.setText(texts[position]);
                viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0 || position == 1 || position == 2 || position == 3 || position == 4 || position == 5 || position == 6) {
                            if (Constant.USER == null) {
                                //没有登陆
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "您没有登陆", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                        }
                        switch (position) {
                            case 0: //我的订单
                                Log.d("----", "点击了我的订单");
                                getActivity().startActivity(new Intent(getActivity(), MyOrderActivity.class));
                                break;
                            case 1:
                                break;
                            case 2: //优惠劵
                                getActivity().startActivity(new Intent(getActivity(), MyRoll.class));
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                                break;
                        }
                    }
                });
                return convertView;
            }
        });


    }

    class ViewHolder {
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == 2) {
            //失败
            initUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("---", "调用了");
        initUser();
    }
}
