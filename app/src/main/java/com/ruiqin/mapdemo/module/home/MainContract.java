package com.ruiqin.mapdemo.module.home;

import com.ruiqin.mapdemo.base.BaseModel;
import com.ruiqin.mapdemo.base.BasePresenter;
import com.ruiqin.mapdemo.base.BaseView;
import com.ruiqin.mapdemo.module.home.adapter.MainRecyclerAdapter;
import com.ruiqin.mapdemo.module.home.bean.MainRecyclerData;

import java.util.List;

/**
 * Created by ruiqin.shen
 * 类说明：
 */

public interface MainContract {
    interface Model extends BaseModel {
        List<MainRecyclerData> initData();
    }

    interface View extends BaseView {
        void setRecyclerAdapterSuccess(MainRecyclerAdapter mainRecyclerAdapter);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract void setAdapter();
    }
}
