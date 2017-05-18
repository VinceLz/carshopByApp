package com.car.contractcar.myapplication.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.utils.DecimalCalculate;
import com.car.contractcar.myapplication.common.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FengqiActivity extends AppCompatActivity {

    Double price;
    @BindView(R.id.price_bili)
    GridView price_bili;
    private Double[] inli = {0D, 0.1D, 0.2D, 0.3D, 0.4D, 0.5D, 0.6D, 0.7D, 0.8D, 0.9D};
    private double stages12;
    private double stages24;
    private double stages32;

    @BindView(R.id.fprice_backfen)
    EduSohoIconView eduSohoIconView;
    @BindView(R.id.starg1)
    TextView starg1;
    @BindView(R.id.starg2)
    TextView starg2;
    @BindView(R.id.starg3)
    TextView starg3;
    @BindView(R.id.fenqi_ok)
    Button button;
    @BindView(R.id.zonngjia)
    TextView zongjia;
    private double current = -1;
    private double current_starg = -1;
    int yue = 0;
    @BindView(R.id.fenqi_result)
    TextView fenqi_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fengqi);
        ButterKnife.bind(this);
        price = getIntent().getDoubleExtra("price", 0D);
        stages12 = getIntent().getDoubleExtra("stages12", 0d);
        stages24 = getIntent().getDoubleExtra("stages24", 0d);
        stages32 = getIntent().getDoubleExtra("stages32", 0d);
        final fenxiAdapt fen = new fenxiAdapt();
        price_bili.setAdapter(fen);
        eduSohoIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zongjia.setText(DecimalCalculate.convertsToInt(DecimalCalculate.mul(price, 10000D)) + "");
        price_bili.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = null;
                for (int i = 0; i < inli.length; i++) {
                    v = (View) price_bili.getChildAt(i);
                    if (i == position) {
                        current = i * 0.1D;
                        Log.d("----", "current设置为" + current);
                        v.setBackgroundColor(Color.parseColor("#599FAE"));
                    } else {
                        v.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }

            }
        });
        starg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讲自己选中，讲别人取消
                current_starg = stages32;
                yue = 36;
                Log.d("----", "current_starg" + current_starg);
                starg3.setBackgroundColor(Color.parseColor("#599FAE"));
                starg2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                starg1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        starg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讲自己选中，讲别人取消
                current_starg = stages24;
                yue = 24;
                Log.d("----", "current_starg" + current_starg);
                starg3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                starg2.setBackgroundColor(Color.parseColor("#599FAE"));
                starg1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        starg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讲自己选中，讲别人取消
                yue = 12;
                current_starg = stages12;
                Log.d("----", "current_starg" + current_starg);
                starg1.setBackgroundColor(Color.parseColor("#599FAE"));
                starg2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                starg3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_starg == -1 || current == -1) {
                    Toast.makeText(FengqiActivity.this, "您是否选中", Toast.LENGTH_SHORT).show();
                } else {
                    int yuan_price = DecimalCalculate.convertsToInt(DecimalCalculate.mul(price, 10000D));
                    double x = DecimalCalculate.mul(yuan_price, current);
                    double y = DecimalCalculate.sub(yuan_price, x);
                    double k = DecimalCalculate.mul(y, 1 + current_starg);
                    fenqi_result.setText(DecimalCalculate.div(k, yue) + "元");
                }
            }
        });
    }

    class fenxiAdapt extends BaseAdapter {
        @Override
        public int getCount() {
            return inli.length;
        }

        @Override
        public Object getItem(int position) {
            return inli[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = UIUtils.getXmlView(R.layout.fenqi_item);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.button.setText((int) (inli[position] * 100) + "%");
            return convertView;
        }
    }

    class ViewHolder {
        @BindView(R.id.bilix)
        TextView button;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
