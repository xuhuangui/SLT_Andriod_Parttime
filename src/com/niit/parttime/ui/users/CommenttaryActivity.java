package com.niit.parttime.ui.users;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.ui.job.ParttimeList;
import com.niit.slt_andriod_parttime.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CommenttaryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_head);
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.all_job)
	private void all_job(View v){
		IntentUtil.start_activity(this, ParttimeList.class);
		this.finish();
	}
	
}
