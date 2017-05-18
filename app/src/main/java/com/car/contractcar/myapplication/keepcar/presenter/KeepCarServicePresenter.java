package com.car.contractcar.myapplication.keepcar.presenter;

import com.car.contractcar.myapplication.keepcar.model.IKeepCarServiceModel;
import com.car.contractcar.myapplication.keepcar.model.KeepCarServiceModel;
import com.car.contractcar.myapplication.keepcar.view.IKeepCarServiceView;

/**
 * Created by macmini2 on 17/3/27.
 */

public class KeepCarServicePresenter {

    private IKeepCarServiceModel keepCarServiceModel;
    private IKeepCarServiceView keepCarServiceView;

    public KeepCarServicePresenter(IKeepCarServiceView keepCarServiceView) {
        this.keepCarServiceView = keepCarServiceView;
        keepCarServiceModel = new KeepCarServiceModel();
    }

}
