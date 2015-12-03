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
		//"cityname"是SplashActivity里PreferencesUtils.putString传过来的key	
		cityName=PreferencesUtils.getString(this, "cityname"," ");//如果cityname不存在，就给个默认值为空
		//如果3秒内没有定位到就要重新定位
		if(TextUtils.isEmpty(cityName)){
			InitLocation();
		}else{
			city.setText(cityName);//在onCreate里只会在页面加载时显示，所有定位后还要更新UI，在onLocationChanged方法里更新
		}	
		
		InitAD();//初始化广告控件
	}
	
	/**
	 * 初始化广告控件
	 */
	private void InitAD() {
		//广告数据源（这里来自互联网）
		HashMap<String, Integer> file=new HashMap<String, Integer>();
		//参数:1-文本广告语;2-网络地址	"http://61.155.81.202/zz/banner1.jpg"	
		file.put("跑跑兼职为您成功加载",R.drawable.zhuan1);
		file.put("向钱看 、 向厚赚",R.drawable.zhuan2);
		file.put("祝您找到一份理想的工作",R.drawable.zhuan3 );
		
		for (String name: file.keySet() ) {
			//在上下文创建广告幻灯片的对象
			TextSliderView sliderView=new TextSliderView(this);
			//设置幻灯片的广告文本和图片
			sliderView.description(name).image(file.get(name))
			.setOnSliderClickListener(this);
			
			sliderView.getBundle().putString("extra", name);
			//将幻灯片放在SliderLayout布局里(添加幻灯片)
			mslider.addSlider(sliderView);
		}
		
		//对幻灯片设置一系列的属性
		//设置幻灯片的切换样式
		mslider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		//设置广告指示符位置:底部中间(广告下面的小圆圈)
		mslider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		//设置广告指示符显示出来
		//mslider.setIndicatorVisibility(IndicatorVisibility.Visible);
		//设置显示标题显示动画样式
		mslider.setCustomAnimation(new DescriptionAnimation());
		//设置切换周期
		mslider.setDuration(4000);
	}

	//初始化定位参数
		private void InitLocation() {
			LogUtils.e("--------高德地图--------");
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
			
			if(location !=null){//定位获取数据成功
				Double getLat=location.getLatitude();//获取纬度
				Double getLon=location.getLongitude();//获取经度
				String cityName=location.getCity();
				String cityCode=location.getCityCode();
				
				//将定位到数据保存在sharedpreferences里(xml),主页面再在从里面获取
				
				PreferencesUtils.putString(MainActivity.this, "Lat", getLat+"");
				PreferencesUtils.putString(MainActivity.this, "Lon", getLon+"");
				PreferencesUtils.putString(MainActivity.this, "cityname", cityName);
				PreferencesUtils.putString(MainActivity.this, "citycode", cityCode);
				
				city.setText(cityName);//定位后，要更新UI
				
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

		/**
		 * 每个幻灯片的点击事件	
		 */
		@Override
		public void onSliderClick(BaseSliderView slider) {
			// 每个幻灯片的点击事件
			
		}	
		
		//主页下面四个	Textview点击事件
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
