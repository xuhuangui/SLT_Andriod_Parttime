package com.niit.parttime.ui.job;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.leelistview.view.LeeListView;
import com.leelistview.view.LeeListView.IXListViewListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.config.URLs;
import com.niit.parttime.entity.Joblist;
import com.niit.parttime.entity.Message;
import com.niit.parttime.ui.BaseActivity;
import com.niit.parttime.ui.LoadingDialog;
import com.niit.slt_andriod_parttime.MainActivity;
import com.niit.slt_andriod_parttime.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ParttimeList extends BaseActivity implements IXListViewListener {
	
	@ViewInject(R.id.my_listview)
	LeeListView mlistview;
	
	private LoadingDialog dialog ;
	
	//1-����Դ
	List<Joblist> mDate=new ArrayList<Joblist>();
	
	private ParttimeJobAdapter adapter; 
	//����ListView������
	
	private int start=0;
	private int currentPage=0;//��ǰ��ҳ��
	private static String refreshTime="";//ˢ�µ�ʱ��
	private static String refreshCount="0";//ˢ�µ�����
	private Handler mhandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobinvitation_layout);
		ViewUtils.inject(this);
		dialog=new LoadingDialog(this);
		mhandler=new Handler();
		LoadingDate();
		//ʵ����������
		adapter=new ParttimeJobAdapter(this,mDate);
		
		mlistview.setAdapter(adapter);
		mlistview.setPullLoadEnable(true, "Ϊ���Ƽ���"+refreshCount+"�˷ݹ���");
		mlistview.setPullRefreshEnable(true);
		mlistview.setXListViewListener(this);
		
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Joblist item=(Joblist)adapter.getItem(position-1);
				
				BasicNameValuePair Pair=new BasicNameValuePair("jobid",item.jobID+"");
				
				IntentUtil.start_activity(ParttimeList.this, parttimeView.class, Pair);
				
				//���ﲻҪfinish();���finish()������ʱ�����������¼���
			}
		});
				
	}
	
	@OnClick(R.id.back)
	public void Back(View v){
		IntentUtil.start_activity(ParttimeList.this,MainActivity.class);
		ParttimeList.this.finish();
	}

	//����ˢ��
	@Override
	public void onRefresh() {
		
		mhandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				LoadingDate();
				adapter.AddDates(mDate);
				adapter.notifyDataSetChanged();//֪ͨ���ݸ���
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				refreshTime=sdf.format(new Date());
				refreshCount=mDate.size()+"";
				//ֹͣˢ��
				StopRefresh();
			}
		}, 0);
		
	}

	//�ײ����ظ���
	@Override
	public void onLoadMore() {
        mhandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				LoadingDate();
				adapter.AddDates(mDate);
				adapter.notifyDataSetChanged();//֪ͨ���ݸ���
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				refreshTime=sdf.format(new Date());
				refreshCount=mDate.size()+"";
				//ֹͣˢ��
				StopRefresh();
			}
		}, 0);
		
	}
	
	
	//ֹͣˢ��
	private void StopRefresh() {
		mlistview.stopRefresh();//ֹͣˢ��
		mlistview.stopLoadMore();//ֹͣ���ظ���
		mlistview.setRefreshTime(refreshTime);
	}
	
	/**
	 * ��ȡ����Դ���ӷ������˻�ȡ
	 */
	public void LoadingDate(){
		
		HttpUtils hUtils=new HttpUtils();
		hUtils.send(HttpMethod.GET, URLs.JOBLIST,new RequestCallBack<String>() {

			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				
				super.onLoading(total, current, isUploading);
				dialog.show();
			}
			
			@Override
			public void onFailure(HttpException exception, String msg) {
				if(dialog !=null && dialog.isShowing()){
					dialog.dismiss();
				}
				
				showLongToast(3, msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> ResponseInfo) {
				if(dialog !=null && dialog.isShowing()){
					dialog.dismiss();
				}
				
				ObjectMapper mapper=new ObjectMapper();
				Message message=null;
				try {
					message=mapper.readValue(ResponseInfo.result, Message.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(message !=null){
					//������������Ƕ���ļ���
					try {
					List<Joblist> lists=mapper.readValue(message.data, new TypeReference<List<Joblist>>(){});
					//�������������ݷ���mDate��
					mDate=lists;
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					refreshTime=sdf.format(new Date());
					refreshCount=mDate.size()+"";
					adapter.AddDates(mDate);
					adapter.notifyDataSetChanged();
					mlistview.stopRefresh();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					showLongToast(3, "Ϊ����������");
		 		}
		   }	
       });
	}
}