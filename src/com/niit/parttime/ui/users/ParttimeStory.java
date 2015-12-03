package com.niit.parttime.ui.users;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.slt_andriod_parttime.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ParttimeStory extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userexperience_layout);
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.back)
	public void back(View v){
		IntentUtil.start_activity(ParttimeStory.this,AboutMeActivity.class);
		ParttimeStory.this.finish();
		
	}
}
