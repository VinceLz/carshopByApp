package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.ListViewUtlis;
import com.car.contractcar.myapplication.common.utils.JsonUtils2;
import com.car.contractcar.myapplication.common.utils.UIUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarConfActivity extends AppCompatActivity {

    private static final String TAG = "xuzi";
    @BindView(R.id.car_conf_view_group)
    LinearLayout carConfViewGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_conf);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initDate() {

    }

    private void initView() {
        Intent intent = getIntent();
        final String code = intent.getStringExtra("conf");


        LinkedHashMap<String, LinkedHashMap<String, String>> jsonToPojo = (LinkedHashMap<String, LinkedHashMap<String, String>>) JsonUtils2
                .jsonToPojo(code, LinkedHashMap.class);

        if (jsonToPojo != null) {
            for (String key_1 : jsonToPojo.keySet()) {
                final String title = key_1;
                LinkedHashMap<String, String> stringStringHashMap = jsonToPojo.get(key_1);
                final int size = stringStringHashMap == null ? 0 : stringStringHashMap.size();

                Set<String> keySet1 = stringStringHashMap.keySet();
                final List<String> keys_2 = new ArrayList<>();
                final List<String> values = new ArrayList<>();

                for (String s : keySet1) {
                    keys_2.add(s);
                    values.add(stringStringHashMap.get(s));
                }

                ListView listView = new ListView(this);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                listView.setLayoutParams(layoutParams);
                listView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return size;
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

                        if (position == 0) {
                            TextView textView = new TextView(CarConfActivity.this);
                            textView.setTextSize(UIUtils.px2dp(60));
                            textView.setPadding(80, 5, 0, 5);
                            textView.setTextColor(Color.parseColor("#000000"));
                            textView.setText(title);
                            return textView;
                        }

                        View xmlView = UIUtils.getXmlView(R.layout.conf_list_item);
                        TextView confName = (TextView) xmlView.findViewById(R.id.conf_name);
                        TextView confValue = (TextView) xmlView.findViewById(R.id.conf_value);
                        confName.setText(keys_2.get(position));
                        confValue.setText(values.get(position));
                        return xmlView;
                    }
                });

                ListViewUtlis.setListViewHeight(listView);

                carConfViewGroup.addView(listView);
            }
        }
    }


    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
