package com.car.contractcar.myapplication.keepcar.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.CustomFAB;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.keepcar.bean.KeepCarShopInfo;
import com.car.contractcar.myapplication.keepcar.view.csdialog.CSOKDialog;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.common.utils.UIUtils.getContext;

public class KeepCarServiceInfoActivity extends AppCompatActivity {

    @BindView(R.id.PreferentialInformation)
    TextView PreferentialInformation;
    @BindView(R.id.keep_car_server_showimg)
    RollPagerView keepCarServerShowimg;
    @BindView(R.id.car_info_back)
    ImageView carInfoBack;
    @BindView(R.id.keep_car_server_shopname)
    TextView keepCarServerShopname;
    @BindView(R.id.keep_car_server_time)
    TextView keepCarServerTime;
    @BindView(R.id.keep_car_server_address)
    TextView keepCarServerAddress;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.count_text)
    TextView countText;
    @BindView(R.id.keepcar_shop_score)
    LinearLayout keepcarShopScore;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    AlertDialog.Builder builder;
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private int mbid;
    private int[] rids = new int[]{R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5};
    private KeepCarShopInfo keepCarShopInfo;
    private ListView service_lv1;
    private ListView service_lv;
    private ListView service_lv2;
    String code;
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
    EditText editText;
    View view;
    CustomFAB jiesuan;
    CustomFAB jiesuan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_car_service_info);
        ButterKnife.bind(this);
        initView();
        initData();
        builder = new AlertDialog.Builder(this);
    }

    private void initView() {
        //优惠信息的点击事件
        PreferentialInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("-----", "code" + code);
                if (TextUtils.isEmpty(keepCarShopInfo.getYcstore().getPreferentialInformation())) {
                    CSOKDialog.createBuilder(KeepCarServiceInfoActivity.this).setAlertTitle("优惠活动").setMsg("该店暂时没有任何活动....").show();
                } else {
                    CSOKDialog.createBuilder(KeepCarServiceInfoActivity.this).setAlertTitle("优惠活动").setMsg(keepCarShopInfo.getYcstore().getPreferentialInformation()).show();
                }
            }
        });
        keepCarServerShowimg.setPlayDelay(3000);
        //设置透明度
        keepCarServerShowimg.setAnimationDurtion(500);

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        keepCarServerShowimg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));

        mbid = getIntent().getIntExtra("mbid", 1);
        //添加页卡视图
        View xmlView = UIUtils.getXmlView(R.layout.service_list_nopay);
        View xmlView1 = UIUtils.getXmlView(R.layout.service_list);
        View xmlView2 = UIUtils.getXmlView(R.layout.service_list);
        jiesuan = (CustomFAB) xmlView1.findViewById(R.id.jiesuan);
        jiesuan2 = (CustomFAB) xmlView2.findViewById(R.id.jiesuan);
        service_lv = (ListView) xmlView.findViewById(R.id.service_list);
        service_lv1 = (ListView) xmlView1.findViewById(R.id.service_list_no_pay);
        service_lv2 = (ListView) xmlView2.findViewById(R.id.service_list_no_pay);
        mViewList.add(xmlView);
        mViewList.add(xmlView1);
        mViewList.add(xmlView2);


        mTitleList.add("清洗");
        mTitleList.add("保养");
        mTitleList.add("装潢");
        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabs.addTab(tabs.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(1)));
        tabs.addTab(tabs.newTab().setText(mTitleList.get(2)));

        //填充弹出输入框

        jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否登陆
                if (Constant.USER == null) {
                    //没有登陆
                    Intent i = new Intent(KeepCarServiceInfoActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    showAlter(2);
                }
                //弹出一个框

            }
        });
        jiesuan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否登陆
                if (Constant.USER == null) {
                    //没有登陆
                    Intent i = new Intent(KeepCarServiceInfoActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    //弹出一个框
                    showAlter(3);
                }
            }
        });
    }


    private void initData() {
        code = getIntent().getStringExtra("code");
        keepCarShopInfo = (KeepCarShopInfo) JsonUtils.json2Bean(code, KeepCarShopInfo.class);
        refreshView();

    }


    private void refreshView() {

        if (keepCarShopInfo != null && keepCarShopInfo.getYcstore() != null) {
            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mViewList);
            vpView.setAdapter(myPagerAdapter);
            tabs.setupWithViewPager(vpView);
            service_lv.setAdapter(new ServiceListAdapter(keepCarShopInfo.getYcstore().getClean()));
            service_lv1.setAdapter(new ServiceListNoPayAdapter(keepCarShopInfo.getYcstore().getMainclean()));
            service_lv2.setAdapter(new ServiceListNoPayAdapter(keepCarShopInfo.getYcstore().getDecoration()));
            keepCarServerShowimg.setAdapter(new StaticPagerAdapter() {
                @Override
                public View getView(ViewGroup container, int position) {
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ImageLoad.loadImg(imageView, keepCarShopInfo.getYcstore().getBimage().get(position));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return keepCarShopInfo.getYcstore().getBimage() != null ? keepCarShopInfo.getYcstore().getBimage().size() : 0;
                }
            });

            KeepCarShopInfo.YcstoreBean ycstore = keepCarShopInfo.getYcstore();
            keepCarServerShopname.setText(ycstore.getMbname());
            keepCarServerTime.setText("营业时间 : " + ycstore.getTime());
            keepCarServerAddress.setText(ycstore.getBaddress());
            countText.setText("用户评价(" + ycstore.getCommentcount() + ")");
            int score = ycstore.getScore();
            for (int i = score; i < 5; i++) {
                this.findViewById(rids[i]).setVisibility(View.INVISIBLE);
            }
            if (score == 0) {
                textView3.setText(score + " 分");
            } else {
                textView3.setText(score + ".0 分");
            }

        }

    }


    //
    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }


    @OnClick(R.id.car_info_back)
    public void onClickBack(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    //没有买单的item adapter
    class ServiceListNoPayAdapter extends BaseAdapter {
        private List<KeepCarShopInfo.YcstoreBean.BaseBean> data;

        public ServiceListNoPayAdapter(List<KeepCarShopInfo.YcstoreBean.BaseBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size() <= 0 ? 1 : data.size();
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
            if (data.size() <= 0) {
                TextView textView = new TextView(getContext());
                textView.setText("该店暂时不提供此服务");
                textView.setTextColor(Color.parseColor("#ff0000"));
                return textView;
            } else {
                ViewHolderNoPay holderView = null;
                if (convertView == null) {
                    convertView = UIUtils.getXmlView(R.layout.service_list_item_no_pay);
                    holderView = new ViewHolderNoPay(convertView);
                    convertView.setTag(holderView);
                } else {
                    holderView = (ViewHolderNoPay) convertView.getTag();
                }
                holderView.sname.setText(data.get(position).getSname());
                holderView.sdesc.setText(data.get(position).getSdesc());
                holderView.snewprice.setText("¥ " + data.get(position).getNewprice() + "");
                holderView.soldprice.setText("¥" + data.get(position).getOldprice() + "");
                holderView.no_pay_desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //弹出对话框，显示详细文本
                        view = LinearLayout.inflate(KeepCarServiceInfoActivity.this, R.layout.service_desc, null);
                        TextView t = (TextView) view.findViewById(R.id.desc);
                        t.setText(data.get(position).getSdesc());
                        builder.setTitle("服务介绍").setView(view).setPositiveButton("确定", null).create().show();
                        //提示框标题
                    }
                });
                return convertView;
            }

        }
    }

    class ServiceListAdapter extends BaseAdapter {
        private List<KeepCarShopInfo.YcstoreBean.BaseBean> data;

        public ServiceListAdapter(List<KeepCarShopInfo.YcstoreBean.BaseBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size() <= 0 ? 1 : data.size();
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
            if (data.size() <= 0) {
                TextView textView = new TextView(getContext());
                textView.setText("该店暂时不提供此服务");
                textView.setTextColor(Color.parseColor("#ff0000"));
                return textView;
            } else {


                ViewHolder holderView = null;
                if (convertView == null) {
                    convertView = UIUtils.getXmlView(R.layout.service_list_item);
                    holderView = new ViewHolder(convertView);
                    convertView.setTag(holderView);
                } else {
                    holderView = (ViewHolder) convertView.getTag();
                }
                holderView.sname.setText(data.get(position).getSname());
                holderView.sdesc.setText(data.get(position).getSdesc());
                holderView.snewprice.setText("¥ " + data.get(position).getNewprice() + "");
                holderView.soldprice.setText("¥" + data.get(position).getOldprice() + "");
                final ViewHolder finalHolderView = holderView;
                holderView.payBel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否登陆
                        if (Constant.USER == null) {
                            //没有登陆
                            Intent i = new Intent(KeepCarServiceInfoActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(KeepCarServiceInfoActivity.this, KeepCar_Pay.class);
                            i.putExtra("info", code);
                            i.putExtra("price", data.get(position).getNewprice());
                            i.putExtra("sname", finalHolderView.sname.getText());
                            i.putExtra("type", data.get(position).getType());
                            startActivity(i);
                        }
                    }
                });
                holderView.desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //弹出对话框，显示详细文本
                        view = LinearLayout.inflate(KeepCarServiceInfoActivity.this, R.layout.service_desc, null);
                        TextView t = (TextView) view.findViewById(R.id.desc);
                        t.setText(data.get(position).getSdesc());
                        builder.setTitle("服务介绍").setView(view).setPositiveButton("确定", null).create().show();
                        //提示框标题
                    }
                });
                return convertView;
            }

        }
    }

    static class ViewHolder {
        @BindView(R.id.sname)
        TextView sname;
        @BindView(R.id.sdesc)
        TextView sdesc;
        @BindView(R.id.snewprice)
        TextView snewprice;
        @BindView(R.id.soldprice)
        TextView soldprice;
        @BindView(R.id.pay_bel)
        Button payBel;
        @BindView(R.id.desc)
        LinearLayout desc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    static class ViewHolderNoPay {
        @BindView(R.id.sname)
        TextView sname;
        @BindView(R.id.sdesc)
        TextView sdesc;
        @BindView(R.id.snewprice)
        TextView snewprice;
        @BindView(R.id.soldprice)
        TextView soldprice;
        @BindView(R.id.no_pay_desc)
        LinearLayout no_pay_desc;

        ViewHolderNoPay(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public void showAlter(final int type) {
        view = LinearLayout.inflate(KeepCarServiceInfoActivity.this, R.layout.editbox_layout, null);
        editText = (EditText) view.findViewById(R.id.editText);
        builder.setTitle("请输入金额").setView(view).setPositiveButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sname = null;
                        String price = editText.getText().toString();
                        java.util.regex.Matcher match = pattern.matcher(price);
                        if (match.matches() == false) {
                            //价格错误
                            Toast.makeText(KeepCarServiceInfoActivity.this, "价格有误", Toast.LENGTH_SHORT).show();
                        } else {
                            //启动付款页面
                            if (type == 2) {
                                //保养
                                sname = "保养自由组合套餐";
                            } else if (type == 3) {
                                sname = "装潢自由组合套餐";
                            }
                            Intent i = new Intent(KeepCarServiceInfoActivity.this, KeepCar_Pay.class);
                            i.putExtra("info", code);
                            i.putExtra("price", Double.valueOf(price));
                            i.putExtra("sname", sname);
                            i.putExtra("type", type);
                            startActivity(i);
                        }
                    }
                }).setNegativeButton("取消", null).create().show();
        //提示框标题
    }
}

