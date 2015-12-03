package com.niit.slt_andriod_parttime;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.common.util.PreferencesUtils;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
public class SplashActivity extends Activity implements AMapLocationListener{
 
	private LocationManagerProxy mProxy=null;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		ViewUtils.inject(this);//注解
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏显示

		setContentView(R.layout.activity_splash);//要放在其上两行之后，否则报错
		
//开启多线程，3秒实现自动跳转至主页面
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				IntentUtil.start_activity(SplashActivity.this,MainActivity.class);
				SplashActivity.this.finish();
			}
		}, 3000);
		
		//3秒内能做什么？定位(先实现网络定位 粗定位)
		InitLocation();//定位
	}

	//初始化定位参数
	private void InitLocation() {
		LogUtils.e("--------高德地图--------");
		mProxy=LocationManagerProxy.getInstance(SplashActivity.this);
		
		mProxy.requestLocationData(LocationProviderProxy.AMapNetwork,
				5*1000, 10, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		
		if(location !=null){//定位获取数据成功
			Double getLat=location.getLatitude();//获取纬度
			Double getLon=location.getLongitude();//获取经度
			String cityName=location.getCity();
			String cityCode=location.getCityCode();
			
			//将定位到数据保存在sharedpreferences里(xml),主页面再在从里面获取
			
			PreferencesUtils.putString(SplashActivity.this, "Lat", getLat+"");
			PreferencesUtils.putString(SplashActivity.this, "Lon", getLon+"");
			PreferencesUtils.putString(SplashActivity.this, "cityname", cityName);
			PreferencesUtils.putString(SplashActivity.this, "citycode", cityCode);
			
			LogUtils.e("定位到的城市:"+cityName+"经纬度:"+getLon+"/"+getLat+" "+"市区编码:"+cityCode);
		}		
	}
	
	@Override
	protected void onPause() {
		if(mProxy !=null){//定位成功
			mProxy.removeUpdates(this);
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if(mProxy !=null){//定位成功
			mProxy.removeUpdates(this);
			mProxy.destory();
		}
		mProxy=null;//不让定位
		super.onDestroy();
	}
}
