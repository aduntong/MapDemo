package com.ruiqin.baseproject.module.map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ruiqin.baseproject.R;
import com.ruiqin.baseproject.base.BaseActivity;
import com.ruiqin.baseproject.module.view.LoadingDialog;
import com.ruiqin.baseproject.module.view.PermissionTipDialog;
import com.ruiqin.baseproject.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        startLocation();//开始定位
        showLoadingDialog();//展示加载对话框
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            cancelLoadingDialog();
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {//定位成功
                    stopLocation();
                    mTextView.setText(aMapLocation.getLatitude() + ", " + aMapLocation.getLongitude());
                } else {
                    int errorCode = aMapLocation.getErrorCode();
                    if (errorCode == 12 || errorCode == 13) {
                        showPermission();
                    } else {
                        ToastUtils.showShort("定位失败");
                    }
                }
            } else {
                ToastUtils.showShort("设备不支持");
            }
        }
    };

    /**
     *
     */
    private void stopLocation() {
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.unRegisterLocationListener(locationListener);
            locationClient = null;
        }
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(false);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setMockEnable(false);//禁用模拟位置
        return mOption;
    }

    LoadingDialog mLoadingDialog;

    private void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
    }

    private void cancelLoadingDialog() {
        if (!isDestroy && mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        checkLocationPermission();
    }

    private static final int PERMISSION_LOCATION = 1;

    /**
     * 检查定位权限
     */
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
        } else {
            initLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length < 0) {
            showPermission();
            return;
        }
        switch (requestCode) {
            case PERMISSION_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showPermission();
                    return;
                }
                initLocation();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    PermissionTipDialog permissionTipDialog;

    /**
     * 展示权限
     */
    private void showPermission() {
        if (permissionTipDialog == null) {
            permissionTipDialog = new PermissionTipDialog(mContext);
        }
        permissionTipDialog.show();
    }

}
