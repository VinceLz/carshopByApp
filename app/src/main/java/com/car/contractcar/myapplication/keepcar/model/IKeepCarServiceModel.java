package com.car.contractcar.myapplication.keepcar.model;

/**
 * Created by macmini2 on 17/3/27.
 */

public interface IKeepCarServiceModel {

    void loadData(int page,int mbid,Runnable runnable);
}
