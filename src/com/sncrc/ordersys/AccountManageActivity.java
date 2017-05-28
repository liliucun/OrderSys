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
	private static final String[] country = {"������", "��ľ", "����", "����", "����","��ɽ", "���", "��֬", "����", "�Ɽ", "�彧", "����", "������" };
	private static final String[] spinner_power={"�ͻ�����","ά����Ա"};
	private static final String[] spinner_category={"����Ա��","�������","װ����Ա","ά����Ա","���վ"};
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
		actionBar.setTitle("��������");
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
					Toast.makeText(AccountManageActivity.this, "�뽫��Ϣ��д����",Toast.LENGTH_SHORT).show();
				}
				else
				{
					NewAccount(county,name,pwd,power,category,phone);
				}
			}
		});
	}
	/* ���������� */
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
				if (result.equals("�����ɹ�")) {

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
		if(Power.equals("�ͻ�����")||Power.equals("��������Ա"))
		{
			spi_county.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(spi_county,myApp.getCounty());
	}
	
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		SpinnerAdapter apsAdapter= spinner.getAdapter(); //�õ�SpinnerAdapter����
	    int k= apsAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(apsAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// Ĭ��ѡ����
				break;
			}
		}
	}
}