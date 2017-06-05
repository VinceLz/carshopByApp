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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
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
import com.car.contractcar.myapplication.keepcar.model.SelectAdapter;
import com.car.contractcar.myapplication.keepcar.model.ServerBean;
import com.car.contractcar.myapplication.keepcar.model.YcOrder;
import com.car.contractcar.myapplication.keepcar.view.csdialog.CSOKDialog;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.db.Selector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.common.utils.UIUtils.getContext;

public class KeepCarServiceInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.activity_keep_car_service_info)
    ViewGroup anim_mask_layout;
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
    private RecyclerView rvSelected;
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
    EditText editText;
    View view;
    private View bottomSheet;
    private SparseArray<ServerBean> selectedList;
    private SparseIntArray groupSelect;
    CustomFAB jiesuan;
    CustomFAB jiesuan2;
    private NumberFormat nf;
    ServiceListAdapter myAdapter;

    ServiceListNoPayAdapter myCleanAdapter;
    ServiceListNoPayAdapter myDecoAdapter;
    SelectAdapter selectAdapter;

    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tvCost)
    TextView tvCost;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    @BindView(R.id.imgCart)
    ImageView imgCart;

    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;

    @BindView(R.id.bottom)
    LinearLayout bottom;
    double cost2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_car_service_info);
        ButterKnife.bind(this);
        initView();
        initData();
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        selectedList = new SparseArray<>();//已经选中的集合
        groupSelect = new SparseIntArray();
        builder = new AlertDialog.Builder(this);
    }

    private void initView() {
        //结算点击事件
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理所有订单selectList
                //判断是否登陆
                if (Constant.USER == null) {
                    //没有登陆
                    Intent i = new Intent(KeepCarServiceInfoActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    String types = "";
                    Set<Integer> set = new HashSet();
                    List<YcOrder> order = new ArrayList<YcOrder>();
                    for (int i = 0; i < selectedList.size(); i++) {
                        set.add(selectedList.valueAt(i).getBean().getType());
                        for (int j = 0; j < selectedList.valueAt(i).getCount(); j++) {
                            YcOrder yc = new YcOrder();
                            yc.setBmname(keepCarShopInfo.getYcstore().getMbname());
                            yc.setSname(selectedList.valueAt(i).getBean().getSname());
                            yc.setMbid(keepCarShopInfo.getYcstore().getMbid());
                            yc.setUid(Constant.USER.getUid());
                            yc.setUname(Constant.USER.getUname());
                            yc.setUphone(Constant.USER.getUphone());
                            yc.setBphone(keepCarShopInfo.getYcstore().getBphone());
                            yc.setRealprice(selectedList.valueAt(i).getBean().getNewprice());
                            order.add(yc);
                        }
                    }
                    for (Integer i : set) {
                        types = i + "," + types;
                    }
                    types = types.substring(0, types.length() - 1);
                    //启动付款界面
                    String sname = "购物车自由组合套餐";
                    Intent i = new Intent(KeepCarServiceInfoActivity.this, KeepCar_Pay.class);
                    i.putExtra("bname", keepCarShopInfo.getYcstore().getMbname());
                    i.putExtra("price", cost2);
                    i.putExtra("sname", sname);
                    i.putExtra("type", types); //type
                    i.putExtra("orders", (Serializable) order);
                    //组建订单List
                    startActivity(i);
                }
            }
        });

        //弹出
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        //优惠信息的点击事件
        vpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        PreferentialInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            myAdapter = new ServiceListAdapter(keepCarShopInfo.getYcstore().getClean());
            service_lv.setAdapter(myAdapter);
            myCleanAdapter = new ServiceListNoPayAdapter(keepCarShopInfo.getYcstore().getMainclean());
            myDecoAdapter = new ServiceListNoPayAdapter(keepCarShopInfo.getYcstore().getDecoration());
            service_lv1.setAdapter(myCleanAdapter);
            service_lv2.setAdapter(myDecoAdapter);
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

    @Override
    public void onClick(View v) {

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
                ViewHolder holderView = null;
                if (convertView == null) {
                    convertView = UIUtils.getXmlView(R.layout.service_list_item_no_pay);
                    holderView = new ViewHolder(convertView);
                    convertView.setTag(holderView);
                } else {
                    holderView = (ViewHolder) convertView.getTag();
                }

                holderView.sname.setText(data.get(position).getSname());
                holderView.sdesc.setText(data.get(position).getSdesc());
                holderView.snewprice.setText("¥ " + data.get(position).getNewprice() + "");
                holderView.soldprice.setText("¥" + data.get(position).getOldprice() + "");
                holderView.item = new ServerBean();
                holderView.item.setBean(data.get(position));
                holderView.item.count = getSelectedItemCountById(holderView.item.getBean().getSid());

                holderView.tvCount.setText(String.valueOf(holderView.item.count));
                if (holderView.item.count < 1) {
                    holderView.tvCount.setVisibility(View.GONE);
                    holderView.tvMinus.setVisibility(View.GONE);
                    holderView.tvMinus.setClickable(false);
                } else {
                    holderView.tvMinus.setClickable(true);
                    holderView.tvCount.setVisibility(View.VISIBLE);
                    holderView.tvMinus.setVisibility(View.VISIBLE);
                }

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

                final ViewHolder finalHolderView = holderView;
                holderView.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击了+号 添加商品
                        int count = getSelectedItemCountById(data.get(position).getSid());
                        Log.d("----", "集合中已经存在" + count);
                        if (count < 1) {  //当前的集合为0 也就是购物车没有任何商品
                            finalHolderView.tvMinus.setClickable(true);
                            finalHolderView.tvMinus.setAnimation(getShowAnimation());
                            finalHolderView.tvMinus.setVisibility(View.VISIBLE);
                            finalHolderView.tvCount.setVisibility(View.VISIBLE);
                        }
                        //加商品添加到购物车里
                        add(finalHolderView.item, false);
                        count++;
                        finalHolderView.tvCount.setText(String.valueOf(count));
                        int[] loc = new int[2];
                        v.getLocationInWindow(loc);
                        Log.d("----", "坐标:" + loc);
                        //播放动画
                        //playAnimation(loc);
                    }
                });
                final ViewHolder finalHolderView1 = holderView;
                holderView.tvMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取到当前的购物车中对应的数量
                        Log.d("----", "sid" + data.get(position).getSid());
                        int count = getSelectedItemCountById(data.get(position).getSid());
                        if (count < 2) { //小于2 那就是1  那么需要将该-号按钮隐藏掉
                            finalHolderView1.tvMinus.setClickable(false);
                            finalHolderView1.tvMinus.setAnimation(getHiddenAnimation());
                            finalHolderView1.tvMinus.setVisibility(View.GONE);
                            finalHolderView1.tvCount.setVisibility(View.GONE);
                        }

                        Log.d("----", "原本" + count);
                        count--;
                        Log.d("----", "sid2" + finalHolderView1.item.getBean().getSid());
                        remove(finalHolderView1.item, false);
                        Log.d("----", "减少到" + count);
                        finalHolderView1.tvCount.setText(String.valueOf(count));
                    }
                });


                return convertView;
            }

        }
    }

    class ServiceListAdapter extends BaseAdapter implements View.OnClickListener {

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
                holderView.item = new ServerBean();
                holderView.item.setBean(data.get(position));
                holderView.item.count = getSelectedItemCountById(holderView.item.getBean().getSid());

                holderView.tvCount.setText(String.valueOf(holderView.item.count));
                if (holderView.item.count < 1) {
                    holderView.tvCount.setVisibility(View.GONE);
                    holderView.tvMinus.setVisibility(View.GONE);
                } else {
                    holderView.tvCount.setVisibility(View.VISIBLE);
                    holderView.tvMinus.setVisibility(View.VISIBLE);
                }
                final ViewHolder finalHolderView = holderView;
                holderView.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击了+号 添加商品
                        int count = getSelectedItemCountById(data.get(position).getSid());
                        Log.d("----", "集合中已经存在" + count);
                        if (count < 1) {  //当前的集合为0 也就是购物车没有任何商品
                            finalHolderView.tvMinus.setClickable(true);
                            finalHolderView.tvMinus.setAnimation(getShowAnimation());
                            finalHolderView.tvMinus.setVisibility(View.VISIBLE);
                            finalHolderView.tvCount.setVisibility(View.VISIBLE);
                        }
                        //加商品添加到购物车里
                        add(finalHolderView.item, false);
                        count++;
                        finalHolderView.tvCount.setText(String.valueOf(count));
                        int[] loc = new int[2];
                        v.getLocationInWindow(loc);
                        Log.d("----", "坐标:" + loc);
                        //播放动画
                        //   playAnimation(loc);
                    }
                });
                final ViewHolder finalHolderView1 = holderView;
                holderView.tvMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取到当前的购物车中对应的数量
                        Log.d("----", "sid" + data.get(position).getSid());
                        int count = getSelectedItemCountById(data.get(position).getSid());
                        if (count < 2) { //小于2 那就是1  那么需要将该-号按钮隐藏掉
                            finalHolderView1.tvMinus.setClickable(false);
                            finalHolderView1.tvMinus.setAnimation(getHiddenAnimation());
                            finalHolderView1.tvMinus.setVisibility(View.GONE);
                            finalHolderView1.tvCount.setVisibility(View.GONE);
                        }

                        Log.d("----", "原本" + count);
                        count--;
                        Log.d("----", "sid2" + finalHolderView1.item.getBean().getSid());
                        remove(finalHolderView1.item, false);
                        Log.d("----", "减少到" + count);
                        finalHolderView1.tvCount.setText(String.valueOf(count));
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


        @Override
        public void onClick(View v) {

        }
    }

    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    //添加商品
    public void add(ServerBean item, boolean refreshGoodList) {
        int groupCount = groupSelect.get(item.getBean().getSid());
        if (groupCount == 0) {
            groupSelect.append(item.getBean().getSid(), 1);
        } else {
            groupSelect.append(item.getBean().getSid(), ++groupCount);
        }

        ServerBean temp = selectedList.get(item.getBean().getSid());
        if (temp == null) {
            item.setCount(1);
            selectedList.append(item.getBean().getSid(), item);
        } else {
            temp.count++;
        }
        update(refreshGoodList);
    }


    //移除商品
    public void remove(ServerBean item, boolean refreshGoodList) {

        int groupCount = groupSelect.get(item.getBean().getSid());
        if (groupCount == 1) {
            groupSelect.delete(item.getBean().getSid());
        } else if (groupCount > 1) {
            groupSelect.append(item.getBean().getSid(), --groupCount);
        }

        ServerBean temp = selectedList.get(item.getBean().getSid());
        if (temp != null) {
            if (temp.count < 2) {
                selectedList.remove(item.getBean().getSid());
            } else {
                temp.count--;
            }
        }
        update(refreshGoodList);
    }


    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        double cost = 0;
        for (int i = 0; i < size; i++) {
            ServerBean item = selectedList.valueAt(i);
            count += item.count;
            cost = add(cost, item.count * item.getBean().getNewprice());
            cost2 = cost;
        }
        if (count < 1) {
            bottom.setVisibility(View.GONE);
            tvCount.setVisibility(View.GONE);
        } else {
            bottom.setVisibility(View.VISIBLE);
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));

        if (selectedList.size() > 0) {
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
        }

        tvCost.setText(nf.format(cost));

        if (myAdapter != null && refreshGoodList) {
            myAdapter.notifyDataSetChanged();
        }
        if (myDecoAdapter != null && refreshGoodList) {
            myDecoAdapter.notifyDataSetChanged();
        }
        if (myCleanAdapter != null && refreshGoodList) {
            myCleanAdapter.notifyDataSetChanged();
        }
        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }


    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    //根据传入的服务id获取当前采购该服务的数量
    public int getSelectedItemCountById(int id) {
        ServerBean serverBean = selectedList.get(id); //已经选中的中将对象取出来
        if (serverBean == null) {
            return 0;
        }
        return serverBean.getCount();
    }

    static class ViewHolder {
        ServerBean item;
        @BindView(R.id.sname)
        TextView sname;
        @BindView(R.id.sdesc)
        TextView sdesc;
        @BindView(R.id.snewprice)
        TextView snewprice;
        @BindView(R.id.soldprice)
        TextView soldprice;
        @BindView(R.id.tvAdd)
        TextView add;
        @BindView(R.id.desc)
        LinearLayout desc;
        @BindView(R.id.tvMinus)
        TextView tvMinus;
        @BindView(R.id.count)
        TextView tvCount;

        ViewHolder(View view) {
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
                                sname = "保养面对面套餐";
                            } else if (type == 3) {
                                sname = "装潢面对面套餐";
                            }
                            Intent i = new Intent(KeepCarServiceInfoActivity.this, KeepCar_Pay.class);
                            i.putExtra("info", code);
                            i.putExtra("price", Double.valueOf(price));
                            i.putExtra("sname", sname);
                            i.putExtra("bname", keepCarShopInfo.getYcstore().getMbname());
                            i.putExtra("type", type + "");
                            List<YcOrder> orders = new ArrayList<YcOrder>();
                            YcOrder t = new YcOrder();
                            t.setBmname(keepCarShopInfo.getYcstore().getMbname());
                            t.setSname(sname);
                            t.setMbid(keepCarShopInfo.getYcstore().getMbid());
                            t.setUid(Constant.USER.getUid());
                            t.setUname(Constant.USER.getUname());
                            t.setUphone(Constant.USER.getUphone());
                            orders.add(t);
                            i.putExtra("orders", (Serializable) orders);
                            startActivity(i);
                        }
                    }
                }).setNegativeButton("取消", null).create().show();
        //提示框标题
    }

    private void showBottomSheet() {
        if (bottomSheet == null) {
            bottomSheet = createBottomSheetView();
        }
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }


    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
                //也要刷新页面中的数据
            }
        });
        selectAdapter = new SelectAdapter(this, selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    //清空购物车
    public void clearCart() {
        selectedList.clear();
        groupSelect.clear();
        update(true);

    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
}

