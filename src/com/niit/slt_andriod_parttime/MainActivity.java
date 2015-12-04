package com.niit.slt_andriod_parttime;

import java.util.HashMap;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderLayout;
import com.gitonway.androidimagesliderdemo.widget.imageslider.Animations.DescriptionAnimation;
import com.gitonway.androidimagesliderdemo.widget.imageslider.Indicators.PagerIndicator.IndicatorVisibility;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.BaseSliderView;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.gitonway.androidimagesliderdemo.widget.imageslider.SliderTypes.TextSliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.common.util.PreferencesUtils;
import com.niit.parttime.ui.job.ParttimeList;
import com.niit.parttime.ui.users.AboutMeActivity;
import com.niit.parttime.ui.users.LoginActivity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements AMapLocationListener
         ,OnSliderClickListener{

	private String cityName="";
	private LocationManagerProxy mProxy=null;
	@ViewInject(R.id.city)
	TextView city;
	@ViewInject(R.id.slider)
	SliderLayout mslider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mymainfunction_layout);
		
		ViewUtils.inject(this);
		//"cityname"��SplashActivity��PreferencesUtils.putString��������key	
		cityName=PreferencesUtils.getString(this, "cityname"," ");//���cityname�����ڣ��͸���Ĭ��ֵΪ��
		//���3����û�ж�λ����Ҫ���¶�λ
		if(TextUtils.isEmpty(cityName)){
			InitLocation();
		}else{
			city.setText(cityName);//��onCreate��ֻ����ҳ�����ʱ��ʾ�����ж�λ��Ҫ����UI����onLocationChanged���������
		}	
		
		InitAD();//��ʼ�����ؼ�
	}
	
	/**
	 * ��ʼ�����ؼ�
	 */
	private void InitAD() {
		//�������Դ���������Ի�������
		HashMap<String, Integer> file=new HashMap<String, Integer>();
		//����:1-�ı������;2-�����ַ	"http://61.155.81.202/zz/banner1.jpg"	
		file.put("���ܼ�ְΪ���ɹ�����",R.drawable.zhuan1);
		file.put("��Ǯ�� �� ���׬",R.drawable.zhuan2);
		file.put("ף���ҵ�һ������Ĺ���",R.drawable.zhuan3 );
		
		for (String name: file.keySet() ) {
			//�������Ĵ������õ�Ƭ�Ķ���
			TextSliderView sliderView=new TextSliderView(this);
			//���ûõ�Ƭ�Ĺ���ı���ͼƬ
			sliderView.description(name).image(file.get(name))
			.setOnSliderClickListener(this);
			
			sliderView.getBundle().putString("extra", name);
			//���õ�Ƭ����SliderLayout������(��ӻõ�Ƭ)
			mslider.addSlider(sliderView);
		}
		
		//�Իõ�Ƭ����һϵ�е�����
		//���ûõ�Ƭ���л���ʽ
		mslider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		//���ù��ָʾ��λ��:�ײ��м�(��������СԲȦ)
		mslider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		//���ù��ָʾ����ʾ����
		//mslider.setIndicatorVisibility(IndicatorVisibility.Visible);
		//������ʾ������ʾ������ʽ
		mslider.setCustomAnimation(new DescriptionAnimation());
		//�����л�����
		mslider.setDuration(4000);
	}

	//��ʼ����λ����
		private void InitLocation() {
			LogUtils.e("--------�ߵµ�ͼ--------");
			mProxy=LocationManagerProxy.getInstance(MainActivity.this);
			
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
				
				PreferencesUtils.putString(MainActivity.this, "Lat", getLat+"");
				PreferencesUtils.putString(MainActivity.this, "Lon", getLon+"");
				PreferencesUtils.putString(MainActivity.this, "cityname", cityName);
				PreferencesUtils.putString(MainActivity.this, "citycode", cityCode);
				
				city.setText(cityName);//��λ��Ҫ����UI
				
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

		/**
		 * ÿ���õ�Ƭ�ĵ���¼�	
		 */
		@Override
		public void onSliderClick(BaseSliderView slider) {
			// ÿ���õ�Ƭ�ĵ���¼�
			
		}	
		
		//��ҳ�����ĸ�	Textview����¼�
		@OnClick(R.id.tv_home)
		private void tv_home(View v){
			IntentUtil.start_activity(this, MainActivity.class);
			this.finish();
		}
		
		@OnClick(R.id.tv_job)
		private void tv_job(View v){
			IntentUtil.start_activity(this, ParttimeList.class);
			this.finish();
		}
		
		@OnClick(R.id.tv_parttime)
		private void tv_parttime(View v){
			IntentUtil.start_activity(this, LoginActivity.class);
			this.finish();
		}
		
		@OnClick(R.id.tv_profile)
		private void tv_profile(View v){
			IntentUtil.start_activity(this, AboutMeActivity.class);
			this.finish();
		}
}
