package com.car.contractcar.myapplication.keepcar.model;

import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.keepcar.bean.KeepCarShopInfo;

/**
 * Created by macmini2 on 17/3/27.
 */

public class KeepCarServiceModel implements IKeepCarServiceModel {

    @Override
    public void loadData(int page, int mbid, final Runnable runnable) {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR_SHOP_INFO + mbid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                KeepCarShopInfo keepCarShopInfo = (KeepCarShopInfo) JsonUtils.json2Bean(code, KeepCarShopInfo.class);
                UIUtils.runOnUIThread(runnable);
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        }, 3600 * 2);
    }

}
