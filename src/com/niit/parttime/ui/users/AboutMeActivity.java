package com.niit.parttime.ui.users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.slt_andriod_parttime.MainActivity;
import com.niit.slt_andriod_parttime.R;

public class AboutMeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		ViewUtils.inject(this);
   }
	
	@OnClick(R.id.login_btn)
	private void tv_profile(View v){
		IntentUtil.start_activity(this, LoginActivity.class);
		this.finish();
	}
	
	@OnClick(R.id.arrow_right)
	private void arrow_right(View v){
		IntentUtil.start_activity(this, MainActivity.class);
		this.finish();
	}
	
	@OnClick(R.id.commentary_list)
	private void commentary_list(View v){
		IntentUtil.start_activity(this, CommenttaryActivity.class);
		this.finish();
	}
	
	
	@OnClick(R.id.experience_list)
	private void experience_list(View v){
		IntentUtil.start_activity(this, ParttimeStory.class);
		this.finish();
	}
	
	@OnClick(R.id.setting_list)
	private void setting_list(View v){
		Intent mIntent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivityForResult(mIntent, 0);//跳转至手机设置界面
	}	
}