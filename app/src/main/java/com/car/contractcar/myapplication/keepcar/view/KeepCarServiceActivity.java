package com.car.contractcar.myapplication.keepcar.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.andview.refreshview.XRefreshView;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.keepcar.presenter.KeepCarServicePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeepCarServiceActivity extends AppCompatActivity implements IKeepCarServiceView{

    @BindView(R.id.keep_car_server_city)
    Spinner keepCarServerCity;
    @BindView(R.id.keep_car_service_back)
    EduSohoIconView keepCarServiceBack;
    @BindView(R.id.keep_car_server_server)
    Spinner keepCarServerServer;
    @BindView(R.id.keep_car_server_condition)
    Spinner keepCarServerCondition;
    @BindView(R.id.keep_car_server_listview)
    ListView keepCarServerListview;
    @BindView(R.id.keep_car_server_xview)
    XRefreshView keepCarServerXview;
    @BindView(R.id.activity_keep_car_service)
    LinearLayout activityKeepCarService;
    private KeepCarServicePresenter keepCarServicePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_car_service);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        keepCarServicePresenter = new KeepCarServicePresenter(this);

        // 设置是否可以上拉刷新
        keepCarServerXview.setPullLoadEnable(true);
        // 设置刷新view的类型
        keepCarServerXview.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                // TODO: 17/3/27 加载数据
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                // TODO: 17/3/27 加载数据
            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
    }
}
