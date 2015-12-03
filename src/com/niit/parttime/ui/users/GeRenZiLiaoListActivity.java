package com.niit.parttime.ui.users;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.ui.BaseActivity;
import com.niit.parttime.ui.job.ParttimeList;
import com.niit.parttime.ui.job.parttimeView;
import com.niit.slt_andriod_parttime.R;

public class GeRenZiLiaoListActivity extends BaseActivity {

	// 性别选择框
	Spinner spinner_sex;
	String[] sp1 = new String[] { "男", "女" };
	private List<String> listsp1 = new ArrayList<String>();
	
    //
	Spinner spinner_parttime;
	String[] sp2 = new String[] { "KFC收银员", "销售实习生/在校生","牛奶周末促销","易蓓乐市场兼职"};
	private List<String> listsp2 = new ArrayList<String>();
	
	@ViewInject(R.id.user_name)
	TextView user_name;
	
	@ViewInject(R.id.user_phone)
	TextView user_phone;
	
	@ViewInject(R.id.school)
	TextView school;
	
	@ViewInject(R.id.user_height)
	TextView user_height;
	
	@ViewInject(R.id.user_weight)
	TextView user_weight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profiles);
		ViewUtils.inject(this);
		
		spinner_sex = (Spinner) findViewById(R.id.user_sex);
		for (String strsp : sp1) {
			listsp1.add(strsp);// 有了数据源
		}
		// 绑定数据源，依靠ArrayAdapter
		// -1数据源来自代码里定义的集合，一般用来读取来自数据库（SQlite）和服务器端的JSON
		ArrayAdapter arrayAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listsp1);
		arrayAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_sex.setAdapter(arrayAdapter1);
		
		//
		spinner_parttime = (Spinner) findViewById(R.id.user_parttime);
		for (String strsp : sp2) {
			listsp2.add(strsp);// 有了数据源
		}
		ArrayAdapter arrayAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listsp2);
		arrayAdapter2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_parttime.setAdapter(arrayAdapter2);
				
		user_name.setText("许环桂");
		user_phone.setText("13876287571");
		school.setText("琼州学院");
		user_height.setText(168+".0cm");
		user_weight.setText(58+".0kg");
		
	}

	@OnClick(R.id.back)
	public void back(View v) {
		IntentUtil.start_activity(GeRenZiLiaoListActivity.this,
				ParttimeList.class);
		GeRenZiLiaoListActivity.this.finish();
	}
	
	@OnClick(R.id.kkkt)
	public void kkkt(View v) {}
	
	@OnClick(R.id.qwe)
	public void qwe(View v) {}
	
	@OnClick(R.id.height_settnig)
	public void height_settnig(View v) {}
	
	
	@OnClick(R.id.weight_settnig)
	public void weight_settnig(View v) {}
	
	@OnClick(R.id.tyu)
	public void tyu(View v) {
		showLongToast(2, "没有隐私哦");
	}
	
	@OnClick(R.id.synopsis_settnig)
	public void synopsis_settnig(View v) {
		showLongToast(2, "本人暂无简介");
	}
	
}
