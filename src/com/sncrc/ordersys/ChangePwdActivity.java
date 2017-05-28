package com.sncrc.ordersys;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePwdActivity extends Activity {
	private AppData myapp;
	private String Power;
	private EditText edit_user;
	private Button bt_change;
	private DBUtil dbUtil = new DBUtil();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwd);
		myapp=(AppData)getApplication();
		Power=myapp.getPower();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("修改密码");
		edit_user=(EditText)findViewById(R.id.user);
		bt_change=(Button)findViewById(R.id.btn_change);
		
		if(Power.equals("客户经理")||Power.equals("全市派单"))
		{
			edit_user.setEnabled(false);
			edit_user.setText(myapp.getUser());
		}
		bt_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				String user=edit_user.getText().toString();
				String oldpwd=((EditText)findViewById(R.id.oldpwd)).getText().toString();
				String pwd=((EditText)findViewById(R.id.pwd)).getText().toString();
				String pwdagain=((EditText)findViewById(R.id.pwdagain)).getText().toString();
				if(pwd.equals("")||oldpwd.equals("")||pwdagain.equals(""))
				{
					Toast.makeText(ChangePwdActivity.this, "请填入密码",Toast.LENGTH_SHORT).show();
				}else 
				{
					if(pwd.equals(pwdagain))
					{
						ChangePwd(user,oldpwd,pwd);
					}
					else
					{
						Toast.makeText(ChangePwdActivity.this, "两次密码填写不一致",Toast.LENGTH_SHORT).show();
					}
				}
			}
		});		
	}
	private void ChangePwd(String user,String oldpwd,String pwd)
	{
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.ChangePwd(params[0], params[1], params[2]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(ChangePwdActivity.this, result, Toast.LENGTH_LONG)
						.show();
				if (result.equals("修改成功")) {

					finish();
				}
				super.onPostExecute(result);
			}
		}.execute(user,oldpwd,pwd);
	}
}