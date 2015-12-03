package com.niit.parttime.ui.users;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.amap.api.mapcore.m;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.niit.parttime.common.util.IntentUtil;
import com.niit.parttime.config.URLs;
import com.niit.parttime.entity.Message;
import com.niit.parttime.entity.Users;
import com.niit.parttime.ui.BaseActivity;
import com.niit.parttime.ui.LoadingDialog;
import com.niit.parttime.ui.job.ParttimeList;
import com.niit.slt_andriod_parttime.MainActivity;
import com.niit.slt_andriod_parttime.R;
import com.niit.slt_andriod_parttime.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {

	/**
	 * //2-注入UI组件
	 * 
	 * @ViewInject(R.id.et_username) EditText et_username;
	 * @ViewInject(R.id.et_userpwd) EditText et_userpwd;
	 * 
	 *                              private LoadingDialog dialog;
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 *           super.onCreate(savedInstanceState);
	 *           setContentView(R.layout.activity_login);
	 *           ViewUtils.inject(this);//1-注入View和相关事件 dialog = new
	 *           LoadingDialog(this); }
	 * @OnClick(R.id.btn_submit) public void Submit(View v){
	 * 
	 *                           String username =
	 *                           et_username.getText().toString().trim(); String
	 *                           userpwd =
	 *                           et_userpwd.getText().toString().trim();
	 * 
	 *                           //需要将用户名和密码传入服务器端进行验证合法性 //1-怎么传:
	 *                           //用户名和密码传入服务器时候， //安卓客户端立即显示LoadingDialog,
	 *                           //等到客户端接收到服务器端回传值，则LoadingDialog停止显示； //弹出Toast
	 *                           //2-如何处理UI阻塞（异步） HttpUtils http= new
	 *                           HttpUtils(); RequestParams params = new
	 *                           RequestParams();
	 *                           params.addBodyParameter("username"
	 *                           ,username);//这里的key必须与服务器里的key一致(在myeclipse里验证)
	 *                           params.addBodyParameter("userpwd",userpwd);
	 *                           //send参数 //1-GET、pOST方法 //2-url:服务器的处理网址
	 *                           //3-上传的数据参数 //4-回调
	 *                           http.send(HttpRequest.HttpMethod.POST,
	 *                           URLs.LOGIN, params, new
	 *                           RequestCallBack<String>() {
	 * @Override public void onLoading(long total, long current, boolean
	 *           isUploading) { super.onLoading(total, current, isUploading);
	 *           dialog.show(); }
	 * 
	 *           //服务器端出错(404,405),一般引起该方法
	 * @Override public void onFailure(HttpException arg0, String arg1) {
	 *           if(dialog != null && dialog.isShowing()){ dialog.dismiss(); }
	 *           showLongToast(3, "数据执行出错"); }
	 * 
	 *           //页面成功200
	 * @Override public void onSuccess(ResponseInfo<String> msg) { if(dialog !=
	 *           null && dialog.isShowing()){ dialog.dismiss(); }
	 *           //得到服务器端传回来的结果集 String txt=msg.result;
	 * 
	 *           // //将Json传来的字符串结果集反序列成Users对象 // Users roger=new Users(); //
	 *           ObjectMapper mObjectMapper=new ObjectMapper(); // try { //
	 *           //因为从txt传来的字符串有四个属性，而Users类只有三个属性，然而造成属性不匹配 //
	 *           //因此无法讲Json字符串反序列成Users对象。(要在Users类里添加注解忽略到未匹配的属性) //
	 *           roger=mObjectMapper.readValue(txt, Users.class); // } catch
	 *           (IOException e) { // // TODO Auto-generated catch block //
	 *           e.printStackTrace(); // } // showLongToast(1, roger.userName);
	 * 
	 *           Message message=new Message(); ObjectMapper mObjectMapper=new
	 *           ObjectMapper(); LogUtils.e(txt); try {
	 *           message=mObjectMapper.readValue(txt, Message.class);
	 *           if(message.statusID==1){ showLongToast(2, message.msg); }else {
	 *           showLongToast(3, message.msg); } } catch (IOException e) {
	 *           showLongToast(3, "应用繁忙，请稍后再试"); } } }); }
	 **/

	private LoadingDialog dialog;

	@ViewInject(R.id.login_username)
	TextView login_username;
	@ViewInject(R.id.login_password)
	TextView login_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);// 1-注入View和相关事件
		dialog = new LoadingDialog(this);
	}

	@OnClick(R.id.login_submit)
	public void login_submit(View v) {
		String userName = login_username.getText().toString().trim();
		String pwd = login_password.getText().toString().trim();
		if (TextUtils.isEmpty(userName)) {
			showShortToast(1, "用戶名为必填项");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			showShortToast(1, "密码为必填项");
			return;
		}

		RequestParams params = new RequestParams();
		params.addBodyParameter("usersName", userName);
		params.addBodyParameter("usersPwd", pwd);

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, URLs.LOGIN,params,
				new RequestCallBack<String>() {

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						dialog.show();
					}

					// 验证失败方法
					@Override
					public void onFailure(HttpException arg0, String msg) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						showLongToast(3, msg);
					}

					// 验证成功方法
					@Override
					public void onSuccess(ResponseInfo<String> ResponseInfo) {
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
						LogUtils.e("服务器端传回结果:" + ResponseInfo.result);

						// 将服务器端传回来的JSON值反序列化成Message类
						ObjectMapper mapper = new ObjectMapper();
						Message message = null;
						try {
							message = mapper.readValue(ResponseInfo.result,
									Message.class);
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (message != null) {// 反序列成功
							int resultID = message.res;
							if (resultID > 0) {// 登陆成功
								showLongToast(2, "登陆成功");
								Users user;
								try {
									user = mapper.readValue(message.data,
											Users.class);
									// 将客户端登陆成功后的信息写入会话
									// SharedPreferences（保存在xml里）参数：1-xml名字
									// ；2-私有模式（只能是本应用程序访问）
									SharedPreferences mPreferences = getSharedPreferences(
											"parttiem", Activity.MODE_PRIVATE);
									SharedPreferences.Editor editor = mPreferences
											.edit();
									editor.putInt("usersID", user.usersID);
									editor.putString("usersName",
											user.usersName);
									editor.commit();
									// 跳转
									Intent mIntent=new Intent(LoginActivity.this,ParttimeList.class);
									startActivity(mIntent);
									LoginActivity.this.finish();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {// 登陆失败
								showLongToast(3, "登陆失败");
							}
						} else {// 反序列失败
							showLongToast(3, "未解析到对象");
						}
					}

				});
	}
	
	@OnClick(R.id.login_forget_password)
	public void login_forget_password(View v){
		IntentUtil.start_activity(LoginActivity.this, ForgotpwdActivity.class);
		LoginActivity.this.finish();
	}

	@OnClick(R.id.login_register)
	public void login_register(View v){
		IntentUtil.start_activity(LoginActivity.this, RegisterActivity.class);
		LoginActivity.this.finish();
	}
	
	@OnClick(R.id.back)
	public void back(View v){
		IntentUtil.start_activity(LoginActivity.this, MainActivity.class);
	}
}
