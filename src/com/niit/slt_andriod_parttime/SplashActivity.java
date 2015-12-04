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
		ViewUtils.inject(this);//ע��
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȫ����ʾ

		setContentView(R.layout.activity_splash);//Ҫ������������֮�󣬷��򱨴�
		
//�������̣߳�3��ʵ���Զ���ת����ҳ��
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				IntentUtil.start_activity(SplashActivity.this,MainActivity.class);
				SplashActivity.this.finish();
			}
		}, 3000);
		
		//3��������ʲô����λ(��ʵ�����綨λ �ֶ�λ)
		InitLocation();//��λ
	}

	//��ʼ����λ����
	private void InitLocation() {
		LogUtils.e("--------�ߵµ�ͼ--------");
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
		
		if(location !=null){//��λ��ȡ���ݳɹ�
			Double getLat=location.getLatitude();//��ȡγ��
			Double getLon=location.getLongitude();//��ȡ����
			String cityName=location.getCity();
			String cityCode=location.getCityCode();
			
			//����λ�����ݱ�����sharedpreferences��(xml),��ҳ�����ڴ������ȡ
			
			PreferencesUtils.putString(SplashActivity.this, "Lat", getLat+"");
			PreferencesUtils.putString(SplashActivity.this, "Lon", getLon+"");
			PreferencesUtils.putString(SplashActivity.this, "cityname", cityName);
			PreferencesUtils.putString(SplashActivity.this, "citycode", cityCode);
			
			LogUtils.e("��λ���ĳ���:"+cityName+"��γ��:"+getLon+"/"+getLat+" "+"��������:"+cityCode);
		}		
	}
	
	@Override
	protected void onPause() {
		if(mProxy !=null){//��λ�ɹ�
			mProxy.removeUpdates(this);
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if(mProxy !=null){//��λ�ɹ�
			mProxy.removeUpdates(this);
			mProxy.destory();
		}
		mProxy=null;//���ö�λ
		super.onDestroy();
	}
}
