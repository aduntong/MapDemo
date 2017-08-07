package com.ruiqin.mapdemo.module.home;

import com.ruiqin.mapdemo.module.home.bean.MainRecyclerData;
import com.ruiqin.mapdemo.module.map.LocationActivity;
import com.ruiqin.mapdemo.module.map.MapDistanceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiqin.shen
 * 类说明：
 */

public class MainModel implements MainContract.Model {
    @Override
    public List<MainRecyclerData> initData() {
        List<MainRecyclerData> recyclerDataList = new ArrayList<>();
        recyclerDataList.add(new MainRecyclerData("MapDistanceActivity", MapDistanceActivity.class));
        recyclerDataList.add(new MainRecyclerData("LocationActivity", LocationActivity.class));
        return recyclerDataList;
    }
}
