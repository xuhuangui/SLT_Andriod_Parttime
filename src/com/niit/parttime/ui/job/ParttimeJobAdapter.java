package com.niit.parttime.ui.job;

import java.util.List;

import com.niit.parttime.entity.Joblist;
import com.niit.slt_andriod_parttime.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ParttimeJobAdapter extends BaseAdapter {

	private LayoutInflater Inflater;
	private List<Joblist> mDate;//成员变量
	
	public ParttimeJobAdapter(Context context ,List<Joblist> datas){
		if(datas ==null){//传过来的datas不能为空，否则无法赋值给mDate
			return;
		}
		this.mDate=datas;//datas是ParttimeList里传过来的数据源
		Inflater=LayoutInflater.from(context);
	}
	
	//加载数据
	public void AddDates(List<Joblist> datas){
		if(datas==null){
			return;
		}
		this.mDate.addAll(datas);
	}
	
	@Override
	public int getCount() {
		
		return mDate.size();
	}

	@Override
	public Object getItem(int position) {
		
		return mDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//品配UI元素
		ViewHolder Holder=null;
		if(convertView==null){
			convertView=Inflater.inflate(R.layout.adapter_job_item2, null);
			Holder=new ViewHolder();
			Holder.Theme_content=(TextView) convertView.findViewById(R.id.theme_content);
			Holder.time=(TextView) convertView.findViewById(R.id.time);
			Holder.distance=(TextView) convertView.findViewById(R.id.distance);
			Holder.wage=(TextView) convertView.findViewById(R.id.wage);
			Holder.companyName=(TextView) convertView.findViewById(R.id.companyName);
			
			convertView.setTag(Holder);
		}else{
			 Holder=(ViewHolder) convertView.getTag();
		}
		
		//数据源和UI元素绑定
		Joblist job=mDate.get(position);
		Holder.Theme_content.setText(job.jobTitle);
		Holder.time.setText(job.jobPostDate);
		Holder.distance.setText(job.jobPostAddress);
		Holder.wage.setText(job.jobPayFee);
		Holder.companyName.setText(job.jobPostCompany);
		
		return convertView;
	}
	
   public  final class  ViewHolder{
	   TextView Theme_content;
	   TextView time;
	   TextView distance;
	   TextView wage;
	   TextView companyName;
   }
}
