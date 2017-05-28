package com.sncrc.ordersys;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class AccountManageActivity extends Activity {
	private AppData myApp;
	private static final String[] country = {"榆阳区", "神木", "府谷", "定边", "靖边","横山", "绥德", "米脂", "佳县", "吴堡", "清涧", "子洲", "大柳塔" };
	private static final String[] spinner_power={"客户经理","维护人员"};
	private static final String[] spinner_category={"自有员工","社会渠道","装机人员","维护人员","宽服站"};
	private Spinner spi_county;
	private Spinner spi_power;
	private Spinner spi_category;
	private String Power;
	private EditText Name;
	private EditText Pwd;
	private EditText Phone;
	private DBUtil dbUtil = new DBUtil();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountmanageactivity);
		myApp=(AppData)getApplication();
		Power=myApp.getPower();
		spi_county=(Spinner)findViewById(R.id.Spinner_county);
		spi_power=(Spinner)findViewById(R.id.power);
		spi_category=(Spinner)findViewById(R.id.category);
		Name=(EditText)findViewById(R.id.name);
		Pwd=(EditText)findViewById(R.id.pwd);
		Phone=(EditText)findViewById(R.id.phone);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("工号新增");
		bindSpinner();
		Button bt_add=(Button)findViewById(R.id.btn_add);
		bt_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				String county=spi_county.getSelectedItem().toString();
				String power=spi_power.getSelectedItem().toString();
				String category=spi_category.getSelectedItem().toString();
				String name=Name.getText().toString();
				String pwd=Pwd.getText().toString();
				String phone=Phone.getText().toString();
				if(name.equals("") || pwd.equals("") || phone.equals(""))
				{
					Toast.makeText(AccountManageActivity.this, "请将信息填写完整",Toast.LENGTH_SHORT).show();
				}
				else
				{
					NewAccount(county,name,pwd,power,category,phone);
				}
			}
		});
	}
	/* 加载下拉框 */
	private void NewAccount(String county,String name,String pwd,String power,String category,String phone)
	{
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.NewAccount(params[0], params[1], params[2],params[3],params[4],params[5],params[6]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(AccountManageActivity.this, result, Toast.LENGTH_LONG)
						.show();
				if (result.equals("新增成功")) {

					finish();
				}
				super.onPostExecute(result);
			}
		}.execute(county,name,pwd,power,category,phone,myApp.getUser().toString());
	}
	private void bindSpinner() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner, country);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spi_county.setAdapter(adapter);
		
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner, spinner_power);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spi_power.setAdapter(adapter1);
		
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner, spinner_category);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spi_category.setAdapter(adapter2);
		if(Power.equals("客户经理")||Power.equals("县区管理员"))
		{
			spi_county.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(spi_county,myApp.getCounty());
	}
	
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
	    int k= apsAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(apsAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// 默认选中项
				break;
			}
		}
	}
}