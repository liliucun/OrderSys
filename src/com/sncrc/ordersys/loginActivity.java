package com.sncrc.ordersys;

import java.util.HashMap;
import java.util.List;

import android.R.id;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class loginActivity extends Activity {
	private DBUtil dbUtil;
	private Button login_btn;
	private Button cancel_btn;
	private EditText account;
	private EditText password;
	private AppData myApp;
	private SharedPreferences sp;
	private CheckBox jzmm;
	private CheckBox zddl;
	private String VersionCode;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		ActionBar actionBar = getActionBar();
		actionBar.hide();
		jzmm=(CheckBox)findViewById(R.id.jzmm);
		zddl=(CheckBox)findViewById(R.id.zddl);
		account=(EditText) findViewById(R.id.accountEdittext);
		password=(EditText) findViewById(R.id.pwdEdittext);
		//显示版本号
		try
		{
			PackageInfo info = getPackageManager().getPackageInfo("com.sncrc.ordersys", PackageManager.GET_CONFIGURATIONS);
	        String versionName = info.versionName;
	        VersionCode=String.valueOf(info.versionCode);
	        TextView ver=(TextView)findViewById(R.id.ver);
	        ver.setText("V"+versionName);
		}catch(Exception e)
		{
			Toast.makeText(loginActivity.this,"获取版本错误",Toast.LENGTH_LONG ).show();
		}
		
		dbUtil = new DBUtil();
		
		login_btn=(Button) findViewById(R.id.login_in);
		login_btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String accounttxt=account.getText().toString();
				String pswtxt=password.getText().toString();
				IsExistAccount(accounttxt,pswtxt,VersionCode);
			}
		});
		jzmm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if (jzmm.isChecked()) {
                    
					sp.edit().putBoolean("jzmm", true).commit();
					
				}else {
					
					sp.edit().putBoolean("jzmm", false).commit();
					
				}

			}
		});
		zddl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if (zddl.isChecked()) {
                    
					sp.edit().putBoolean("zddl", true).commit();
					
				}else {
					
					sp.edit().putBoolean("zddl", false).commit();
					
				}

			}
		});
		
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		if(sp.getBoolean("jzmm", false))
		{
			jzmm.setChecked(true);
			account.setText(sp.getString("USER_NAME", ""));
			password.setText(sp.getString("PASSWORD",""));
			if(sp.getBoolean("zddl", false))
			{
				zddl.setChecked(true);
				IsExistAccount(account.getText().toString(),password.getText().toString(),VersionCode);
			}
		}
	}
	public void IsExistAccount(final String account,final String psw,final String ver)
	{
		myApp=(AppData)getApplication();
		myApp.setUser(account);
		new AsyncTask<String,Void,String>() {
			protected String doInBackground(String... params) {
				String result;
				result=dbUtil.IsExistAccount(params[0],params[1],params[2]);
				return  result;
			
			}
	
			@Override
			protected void onPostExecute(String result) {
				
				 //Toast.makeText(loginActivity.this,result,Toast.LENGTH_LONG ).show();
				Log.d("Login",result);
				 String T_result[]=result.split("\\|",-1);
				 if(T_result[0].equals("Success"))
				 {
					 
					 myApp.setPower(T_result[1]);
					 myApp.setCounty(T_result[2]);
					 myApp.setPhone(T_result[3]);
					 myApp.setName(T_result[4]);
					 myApp.setNeedPhoto(T_result[5]);
					 myApp.setRole(T_result[6]);
					 if(jzmm.isChecked())
						{
						 //记住用户名、密码、
						  Editor editor = sp.edit();
						  editor.putString("USER_NAME", account);
						  editor.putString("PASSWORD",psw);
						  editor.putBoolean("jjmm", true);
						  editor.putBoolean("zddl", zddl.isChecked());
						  editor.commit();
						}
					 else{
						 Editor editor = sp.edit();
						 editor.putBoolean("jjmm", false);
						 editor.putBoolean("zddl", zddl.isChecked());
					 }
					 finish();
					 Intent MainIntent=new Intent(loginActivity.this,MainActivity.class);
						startActivity(MainIntent);
					 
				 }
				 else
				 {
					 Toast.makeText(loginActivity.this,result,Toast.LENGTH_LONG ).show();
					 if(result.contains("APP版本过低"))
					 {
						 CheckUpdate();
					 }
				 }
				super.onPostExecute(result);
			}
			
		}.execute(account,psw,ver);

	
	}
	
	public void CheckUpdate()
	{
		new AsyncTask<String,Void,String>() {
			Toast toast;
			protected String doInBackground(String... params) {
				String result;
				result=dbUtil.GetVersion();
				return  result;
			
			}
	
			@Override
			protected void onPostExecute(String result) {
				
				 //toast=Toast.makeText(MainActivity.this,"正在检查更新...",Toast.LENGTH_LONG );
				 //toast.show();
				 UpdateManager manager = new UpdateManager(loginActivity.this);
					 //检查软件更新
				//System.out.println(result);
				manager.checkUpdate(result);
				 
				super.onPostExecute(result);
			}
			
		}.execute();
	}

	
}
