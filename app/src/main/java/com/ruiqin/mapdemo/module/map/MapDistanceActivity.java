package com.ruiqin.mapdemo.module.map;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.ruiqin.mapdemo.R;
import com.ruiqin.mapdemo.base.BaseActivity;
import com.ruiqin.mapdemo.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MapDistanceActivity extends BaseActivity {
    @BindView(R.id.editText0)
    EditText mEditText0;
    @BindView(R.id.editText1)
    EditText mEditText1;
    @BindView(R.id.editText2)
    EditText mEditText2;
    @BindView(R.id.editText3)
    EditText mEditText3;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_distance);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }


    @OnClick(R.id.btn_calculate)
    public void onViewClicked() {

        if (checkInputError()) {
            ToastUtils.showShort("不能为空");
            return;
        }

        LatLng latLng0 = new LatLng(Double.parseDouble(mEditText0.getText().toString()), Double.parseDouble(mEditText1.getText().toString()));
        LatLng latLng1 = new LatLng(Double.parseDouble(mEditText2.getText().toString()), Double.parseDouble(mEditText3.getText().toString()));
        float v = AMapUtils.calculateLineDistance(latLng0, latLng1);
        if (v < 1000) {
            mTvResult.setText(v + "米");
        } else {
            mTvResult.setText(v / 1000 + "千米");
        }
    }

    private boolean checkInputError() {
        if (mEditText0.getText().toString().isEmpty() || mEditText1.getText().toString().isEmpty() || mEditText2.toString().isEmpty() || mEditText3.toString().isEmpty()) {
            return true;
        }
        return false;
    }
}
