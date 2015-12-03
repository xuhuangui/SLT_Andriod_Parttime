package com.niit.parttime.ui.job;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;




import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.common.util.PreferencesUtils;
import com.niit.parttime.config.URLs;
import com.niit.parttime.entity.Joblist;
import com.niit.parttime.entity.Message;
import com.niit.parttime.ui.BaseActivity;
import com.niit.parttime.ui.LoadingDialog;
import com.niit.parttime.ui.users.GeRenZiLiaoListActivity;
import com.niit.slt_andriod_parttime.R;

public class parttimeView extends BaseActivity {
	//com.paopao.android.lycheepark.ui.WelfareView
	
	@ViewInject(R.id.job_theme)
	TextView job_theme;
	
	@ViewInject(R.id.job_pay)
	TextView job_pay;
	@ViewInject(R.id.job_distance)
	TextView job_distance;
	@ViewInject(R.id.publishTime)
	TextView publishTime;
	@ViewInject(R.id.job_company)
	TextView job_company;
	@ViewInject(R.id.paytime)
	TextView paytime;
	
	@ViewInject(R.id.job_requestCount)
	TextView job_requestCount;
	
	@ViewInject(R.id.job_time)
	TextView job_time;
	@ViewInject(R.id.job_address)
	TextView job_address;
	@ViewInject(R.id.inerview_time)
	TextView inerview_time;
	@ViewInject(R.id.inerview_address)
	TextView inerview_address;
	@ViewInject(R.id.job_detailshow)
	TextView job_detailshow;

	@ViewInject(R.id.job_contact)
	TextView job_contact;

	@ViewInject(R.id.job_phone)
	TextView job_phone;
	
	private int usersID=0;
	private String  jobID="0";
	
	private LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_jobdetail_layout);
		ViewUtils.inject(this);
		
		dialog=new LoadingDialog(this);
		
		//获取传来的jobid
		Intent mIntent=this.getIntent();
		jobID=mIntent.getStringExtra("jobid");		
		//判断用户是否登陆
		usersID=PreferencesUtils.getInt(this, "usersID", 0);
		
		//加载页面数据
		LodaDate();
	}

	private void LodaDate() {
		HttpUtils hUtils=new HttpUtils();
		String url=String.format(URLs.JOBVIEW, jobID);
		hUtils.send(HttpMethod.GET, url,new RequestCallBack<String>() {

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				dialog.show();
				
			};
			   
			   
			@Override
			public void onFailure(HttpException exception, String msg) {
				if(dialog !=null && dialog.isShowing()){
					dialog.dismiss();
				}
				showLongToast(3, "msg");
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ObjectMapper mapper = new ObjectMapper();
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				Message message = null;
				try {
					message = mapper.readValue(responseInfo.result,
							Message.class);
					Log.e("--test---", responseInfo.result);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					//解析出来的是对象的集合
					Joblist job = mapper.readValue(message.data,
							Joblist.class);
					job_theme.setText(job.jobTitle);
					job_pay.setText(job.jobPayFee);
					
					job_distance.setText(job.jobPostAddress);
					
					publishTime.setText(job.jobPostDate);
					job_company.setText(job.jobPostCompany);
					paytime.setText(job.jobJSRQ);
					job_requestCount.setText(job.jobZPRS+"");
					job_time.setText(job.jobGZRQ);
					job_address.setText(job.jobGZDZ);
					inerview_time.setText(job.jobMSSJ);
					inerview_address.setText(job.jobMSDZ);
					job_detailshow.setText(job.jobZWMS);
					job_contact.setText(job.jobContactUser);
					if (usersID > 0) {
						job_phone.setText(job.jobPhone);
					} else {
						job_phone.setText("联系信息请登录后查看。");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		} );
		
	}
	
	//拨打电话时间
	@OnClick(R.id.callPhone)
	public void callPhone(View v) {
		if (!job_contact.getText().toString().trim().equals("联系信息请登录后查看。")) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ job_phone.getText().toString().trim()));
			startActivity(intent);
		}
	}
	
	@OnClick(R.id.back)
	public void back(View v ){
		IntentUtil.start_activity(parttimeView.this, ParttimeList.class);
		parttimeView.this.finish();
	}
	
	//收藏按钮点击事件
    public void onClick(View v){
    	showShortToast(2, "收藏成功"); 
    	IntentUtil.start_activity(parttimeView.this, GeRenZiLiaoListActivity.class);
    	parttimeView.this.finish();
    }
}
