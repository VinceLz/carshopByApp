package com.car.contractcar.myapplication.user.adapter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.keepcar.model.YcOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.car.contractcar.myapplication.common.utils.Constant.intent;

/**
 * Created by Administrator on 2017/6/1.
 */

public class KeepCarOrderFrament extends Fragment {
    @BindView(R.id.order_list_view)
    ListView list;
    List<YcOrder> listDate = new ArrayList<>();
    ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.order_keepcar);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        //请求数据
        HttpUtil.get(Constant.HTTP_BASE + Constant.MY_ORDER + "?type=1", new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                String orders = JSONObject.parseObject(code).getString("orders");
                List<YcOrder> ycOrders = JSON.parseArray(orders, YcOrder.class);
                Log.d("----", ycOrders + "");
                listDate.clear();
                listDate.addAll(ycOrders);
                Log.d("----", "listDate size=" + listDate.size());
                if (listAdapter == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter = new ListAdapter();
                            list.setAdapter(listAdapter);
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

            @Override
            public void fail(String code) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void err() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 0);
    }

    private void initView() {
        View listEmptyView = View.inflate(getContext(), R.layout.listview_empty, (ViewGroup) list.getParent());
        list.setEmptyView(listEmptyView);
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listDate.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHoleder viewHoleder = null;
            if (viewHoleder == null) {
                convertView = View.inflate(getContext(), R.layout.myorder_item, null);
                viewHoleder = new ViewHoleder(convertView);
                convertView.setTag(viewHoleder);
            } else {
                viewHoleder = (ViewHoleder) convertView.getTag();
            }
            viewHoleder.myorder_bname.setText(listDate.get(position).getBmname());
            viewHoleder.myorder_sname.setText(listDate.get(position).getSname());
            viewHoleder.myorder_goodid.setText(listDate.get(position).getGoodid());
            viewHoleder.myorder_price.setText(listDate.get(position).getPrice() + "");
            viewHoleder.myorder_date.setText(listDate.get(position).getDate());
            String status = "";
            switch (listDate.get(position).getStatus()) {
                case 0:
                    status = "已付款";
                    break;
                case 1:
                    status = "等待退款";
                    break;
                case 2:
                    status = "交易完成";
                    break;
                case 3:
                    status = "退款成功";
                    break;
                case -3:
                    status = "异常";
                    break;
            }
            viewHoleder.myorder_status.setText(status);
            viewHoleder.myorder_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setData(Uri.parse("tel:" + listDate.get(position).getBphone()));
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    class ViewHoleder {
        @BindView(R.id.myorder_bname)
        TextView myorder_bname;
        @BindView(R.id.myorder_sname)
        TextView myorder_sname;
        @BindView(R.id.myorder_goodid)
        TextView myorder_goodid;
        @BindView(R.id.myorder_price)
        TextView myorder_price;
        @BindView(R.id.myorder_date)
        TextView myorder_date;
        @BindView(R.id.myorder_status)
        TextView myorder_status;
        @BindView(R.id.myorder_phone)
        TextView myorder_phone;

        public ViewHoleder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
