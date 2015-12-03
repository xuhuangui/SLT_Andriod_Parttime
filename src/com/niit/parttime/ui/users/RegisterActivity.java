package com.niit.parttime.ui.users;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.common.util.PreferencesUtils;
import com.niit.parttime.config.URLs;
import com.niit.parttime.entity.Message;
import com.niit.parttime.entity.Users;
import com.niit.parttime.ui.BaseActivity;
import com.niit.parttime.ui.LoadingDialog;
import com.niit.parttime.ui.job.ParttimeList;
import com.niit.slt_andriod_parttime.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity {

	@ViewInject(R.id.register_username)
	TextView register_username;
	@ViewInject(R.id.register_password)
	TextView register_password;
	@ViewInject(R.id.register_confirm_password)
	TextView register_confirm_password;
	@ViewInject(R.id.register_invite_code)
	TextView register_invite_code;

	private LoadingDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userregist_layout);
		ViewUtils.inject(this);// ע��
		dialog = new LoadingDialog(this);
	}

	@OnClick(R.id.register_submit)
	public void register_submit(View v) {
		String username = register_username.getText().toString().trim();
		String password = register_password.getText().toString().trim();
		String confirm_password = register_confirm_password.getText()
				.toString().trim();
		String invite_code = register_invite_code.getText().toString().trim();

		if (TextUtils.isEmpty(username)) {
			showLongToast(1, "�û���������д");
			return;
		}

		if (TextUtils.isEmpty(password)) {
			showLongToast(1, "���������д");
			return;
		}

		if (TextUtils.isEmpty(confirm_password)) {
			showLongToast(1, "���ٴ���������");
			return;
		}

		if (!confirm_password.equals(password)) {
			showLongToast(1, "�����������벻һ��");
			return;
		}

		// ����װ����������Ҫ�Ĳ���
		RequestParams params = new RequestParams();
		params.addBodyParameter("usersName", username);
		params.addBodyParameter("usersPwd", password);
		params.addBodyParameter("usersInvalitCode", invite_code);
		params.addBodyParameter("usersIsForgot", "0");
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = Format.format(new Date());
		params.addBodyParameter("usersRegDate", date);

		// �첽�������ύ��������
		HttpUtils hUtils = new HttpUtils();
		hUtils.send(HttpMethod.POST, URLs.REGISTER, params,
				new RequestCallBack<String>() {
			
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						dialog.show();
					}

					@Override
					public void onFailure(HttpException exception, String msg) {
						if(dialog!=null && dialog.isShowing()){
							dialog.dismiss();
						}
                        showLongToast(3, msg);
					}
					@Override
					public void onSuccess(ResponseInfo<String> ResponseInfo) {
						if(dialog!=null && dialog.isShowing()){
							dialog.dismiss();
						}
						
                        LogUtils.e("�������˻ش�����ֵ"+ResponseInfo.result);
                        
						ObjectMapper mapper=new ObjectMapper();
						Message message=null;
						try {
							message=mapper.readValue(ResponseInfo.result, Message.class);
						} catch (IOException e) {
							e.printStackTrace();
						}
						if(message !=null){
							int resultID=message.res;
							if(resultID>0){//ע��ɹ�
								//1-��ʾע��ɹ�
								showLongToast(2, "ע��ɹ�");
								//2-��ע��ɹ���Ϣд��Ự��������
								try {
									Users user=mapper.readValue(message.data, Users.class);
									PreferencesUtils.putInt(RegisterActivity.this, "usersID", user.usersID);
									PreferencesUtils.putString(RegisterActivity.this, "usersName", user.usersName);
									//3-��ת���б�ҳ��
									IntentUtil.start_activity(RegisterActivity.this, ParttimeList.class);
									RegisterActivity.this.finish();
								} catch (IOException e) {
									e.printStackTrace();
								}			
								}else{//ע��ʧ��
									showLongToast(3, "ע��ʧ��");
								}
						}else{
							showLongToast(3, "δ����������");
						}
					}
				});
	}

	@OnClick(R.id.back)
	public void back(View v) {
       IntentUtil.start_activity(RegisterActivity.this, ParttimeList.class);
       RegisterActivity.this.finish();
	}
}
