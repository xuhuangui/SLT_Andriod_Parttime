package com.niit.parttime.ui.users;

import java.io.IOException;

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
import com.niit.parttime.config.URLs;
import com.niit.parttime.entity.Message;
import com.niit.parttime.ui.BaseActivity;
import com.niit.parttime.ui.LoadingDialog;
import com.niit.slt_andriod_parttime.MainActivity;
import com.niit.slt_andriod_parttime.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class ForgotpwdActivity extends BaseActivity {

	@ViewInject(R.id.register_telephone)
	TextView register_telephone;

	@ViewInject(R.id.register_password)
	TextView register_password;

	@ViewInject(R.id.register_confirm_password)
	TextView register_confirm_password;

	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		ViewUtils.inject(this);
		dialog = new LoadingDialog(this);
	}

	@OnClick(R.id.register_submit)
	public void register_submit(View v) {
		String telephone = register_telephone.getText().toString().trim();
		String password = register_password.getText().toString().trim();
		String confirm_password = register_confirm_password.getText()
				.toString().trim();
		if (TextUtils.isEmpty(telephone)) {
			showLongToast(1, "�û�������Ϊ��");
			return;
		}

		if (TextUtils.isEmpty(password)) {
			showLongToast(1, "���벻��Ϊ��");
			return;
		}

		if (TextUtils.isEmpty(confirm_password)) {
			showLongToast(1, "���ٴ���������");
			return;
		}

		if (!password.equals(confirm_password)) {
			showLongToast(1, "�����������벻һ�� ");
			return;
		}

		// ��װ����������Ҫ�Ĳ���
		RequestParams params = new RequestParams();
		params.addBodyParameter("usersName", telephone);
		params.addBodyParameter("usersPwd", password);
		params.addBodyParameter("usersIsForgot", "1");

		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, URLs.RESTPWD, params,
				new RequestCallBack<String>() {

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

						super.onLoading(total, current, isUploading);
						dialog.show();
					}

					@Override
					public void onFailure(HttpException exception, String msg) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}

						showLongToast(3, msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> ResponseInfo) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						LogUtils.e("�������˻ش�����ֵ:" + ResponseInfo.result);

						ObjectMapper mapper = new ObjectMapper();
						Message message = null;
						try {
							message=mapper.readValue(ResponseInfo.result, Message.class);
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (message != null) {// �����ɹ�(��ȡ������)
							int resultID = message.res;
							if (resultID > 0) {// �޸�����ɹ�
								showLongToast(2, "�����޸ĳɹ�");
								//��ת����½����
								IntentUtil.start_activity(
										ForgotpwdActivity.this,
										LoginActivity.class);
								ForgotpwdActivity.this.finish();
							} else {// �޸�ʧ��
								showLongToast(3, "�û���������");
							}
						} else {// ����ʧ��
							showLongToast(3, "δ����������");
						}
					}
				});
	}

	@OnClick(R.id.back)
	public void back(View v) {
		IntentUtil.start_activity(ForgotpwdActivity.this, MainActivity.class);
		ForgotpwdActivity.this.finish();
	}
}
