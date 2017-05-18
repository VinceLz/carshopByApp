package com.car.contractcar.myapplication.keepcar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.utils.DateUtil;
import com.car.contractcar.myapplication.keepcar.bean.RollInfo;
import com.car.contractcar.myapplication.keepcar.view.BorderTextViews;
import com.car.contractcar.myapplication.keepcar.view.CouponDisplayView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/21.
 */

public class Roll_ListView_Adapter extends BaseAdapter {
    List<RollInfo> list;
    Context context;
    double price;

    public Roll_ListView_Adapter(Context context, List list, double price) {
        this.list = list;
        this.price = price;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (list != null && list.size() == 0) ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.roll_item, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("----", "我是按钮");
                View rootView = v.getRootView();
            }
        });
        holder.borderTextViews.setPaintColor(Color.parseColor("#000000"));
        RollInfo item = list.get(position);
        Log.d("----", "进入到适配器+" + item);
        holder.rdate.setText("截止日期:" + item.getCreatedate() + " / " + item.getPastdate());
        String typename = "";
        switch (item.getType()) {
            case 0:
                typename = "全场通用卷";
                break;
            case 1:
                typename = "仅限清洗时候使用";
                break;
            case 2:
                typename = "仅限保养时候使用";
                break;
            case 3:
                typename = "仅限装饰时候使用";
                break;
        }
        holder.type.setText(typename);
        holder.rname.setText(item.getPrice() + "元" + item.getRname());
        holder.condition.setText("满" + (int) (item.getPrice()) + "才可以使用");
        //这里进行判断优惠劵是否可用
        //1判断金额是否满足
        if (price < item.getCondition() || !DateUtil.compTo(item.getPastdate(), 0)) {
            //不满足条件
            holder.guoqi.setText("不可用");
            holder.button.setEnabled(false);
            holder.couponDisplayView.setBackgroundColor(Color.parseColor("#cccccc"));
            holder.roll_root_lin.setEnabled(false);

        } else {
            holder.guoqi.setText("");
            holder.button.setEnabled(false);
            holder.couponDisplayView.setBackgroundColor(Color.parseColor("#F3B600"));
            holder.roll_root_lin.setEnabled(false);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.rname)
        TextView rname;
        @BindView(R.id.rdate)
        TextView rdate;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.condition)
        TextView condition;
        @BindView(R.id.roll_root)
        CouponDisplayView couponDisplayView;
        @BindView(R.id.roll_button)
        Button button;
        @BindView(R.id.name)
        BorderTextViews borderTextViews;
        @BindView(R.id.guoqi)
        TextView guoqi;
        @BindView(R.id.roll_root_lin)
        LinearLayout roll_root_lin;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
