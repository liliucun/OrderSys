package com.sncrc.ordersys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AddkrActivity extends Activity {
	private static final String[] country = { "---请选择---", "榆阳区", "神木", "府谷",
			"定边", "靖边", "横山", "绥德", "米脂", "佳县", "吴堡", "清涧", "子洲", "大柳塔" };
	private Spinner Spinner_country;
	private Spinner Spinner_area;
	private TextView Spinner_cell;
	private TextView Text_inmode;
	private TextView Text_celladdress;
	private TextView ReceivePerson;
	private Button btn_add;
	private DBUtil dbUtil;
	private AppData myApp;
	private ArrayAdapter<String> adapter;
	private EditText sendertEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addkr);
		myApp = (AppData) getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("扩容派单");

		dbUtil = new DBUtil();
		Spinner_country = (Spinner) findViewById(R.id.Spinner_country);
		ReceivePerson = (TextView) findViewById(R.id.Receiveperson);
		Spinner_area = (Spinner) findViewById(R.id.Spinner_area);
		Spinner_cell = (TextView) findViewById(R.id.Spinner_cell);
		Text_inmode = (TextView) findViewById(R.id.Text_inmode);
		Text_celladdress = (TextView) findViewById(R.id.celladdress);
		btn_add = (Button) findViewById(R.id.btn_add);
		sendertEditText = (EditText) findViewById(R.id.sender);
		sendertEditText.setText(myApp.getName() + ":" + myApp.getPhone());
		// btn_person = (Button) findViewById(R.id.btn_person);
		bindSpinner();
		// 添加按钮，触发添加操作
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_add.setEnabled(false);
				String country = (String) Spinner_country.getSelectedItem();
				String sender = sendertEditText.getText().toString();
				String installedPerson = ReceivePerson.getText().toString();
				String area = (String) Spinner_area.getSelectedItem();
				String cell = (String) Spinner_cell.getText().toString();
				String inmode = (String) Text_inmode.getText();
				String celladdress = Text_celladdress.getText().toString();
				EditText userinfoEditText = (EditText) findViewById(R.id.userinfo);
				String userinfo = userinfoEditText.getText().toString();
				EditText remarkEditText = (EditText) findViewById(R.id.remark);
				String remark = remarkEditText.getText().toString();
				if(!(country.equals("")	|| installedPerson.equals("")
						|| area.equals("---请选择---") || cell.equals("---请选择---"))) {
					addjob(country, sender, installedPerson, userinfo,
							 area, cell, celladdress, inmode,
							myApp.getUser(), remark);
				} else {
					Toast.makeText(AddkrActivity.this, "以上信息不能为空！",
							Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
			}
		});
		Spinner_country.setSelection(0, true);
		Spinner_country
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position == 0) {
							return;
						}
						// TODO Auto-generated method stub
						String selectedValue = (String) Spinner_country
								.getSelectedItem();
						// bindPersonByCountry(selectedValue);
						bindArea(selectedValue);
						Spinner_cell.setText("");
						Text_inmode.setText("");
						Text_celladdress.setText("");
						ReceivePerson.setText("");
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});
		Spinner_area.setSelection(0, true);
		Spinner_area
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position == 0) {
							return;
						}
						// TODO Auto-generated method stub
						SelectCell();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
					}
				});
		Spinner_cell.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SelectCell();
			}
		});

		if (!(myApp.getPower().equals("超级管理员") || myApp.getPower().equals(
				"全市派单"))) {
			Spinner_country.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(Spinner_country, myApp.getCounty());
	}
	
	private void SelectCell() {
		String selectedCountryValue = (String) Spinner_country
				.getSelectedItem();
		String selectedValue = (String) Spinner_area.getSelectedItem();
		if (selectedCountryValue == null || selectedValue == null
				|| selectedCountryValue.equals("") || selectedValue.equals("")
				|| selectedCountryValue.equals("---请选择---")
				|| selectedValue.equals("---请选择---")) {
			return;
		}
		Text_inmode.setText("");
		Bundle b = new Bundle();
		b.putString("Opt", "AddJob");
		b.putString("County", selectedCountryValue);
		b.putString("Area", selectedValue);
		Intent intent = new Intent(AddkrActivity.this,
				SelectListViewActivity.class);
		intent.putExtras(b);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Bundle b = data.getExtras(); // data为B中回传的Intent
			String cell = b.getString("cell");// str即为回传的值
			Spinner_cell.setText(cell);
			String selectedCountryValue = (String) Spinner_country
					.getSelectedItem();
			String selectedValue = (String) Spinner_area.getSelectedItem();
			bindInMode(selectedCountryValue, selectedValue, cell);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据值, 设置spinner默认选中:
	 * 
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,
			String value) {
		SpinnerAdapter apsAdapter = spinner.getAdapter(); // 得到SpinnerAdapter对象
		int k = apsAdapter.getCount();
		for (int i = 0; i < k; i++) {
			if (value.equals(apsAdapter.getItem(i).toString())) {
				spinner.setSelection(i, true);// 默认选中项
				break;
			}
		}
	}

	// /绑定下拉框
	public void bindSpinner() {
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this, R.layout.spinner, country);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		Spinner_country.setAdapter(adapter);
		Spinner_country.setSelection(0, true);

	}

	// // 根据县区下拉选项加载装机人
	// public void bindPersonByCountry(String Country) {
	// // List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
	// // String>>();
	// List<HashMap<String, String>> list = new ArrayList<HashMap<String,
	// String>>();
	// new AsyncTask<String, Void, List<HashMap<String, String>>>() {
	//
	// @Override
	// protected List<HashMap<String, String>> doInBackground(
	// String... params) {
	//
	// return dbUtil.SelectPersonByCountry(params[0]);
	//
	// }
	//
	// @Override
	// protected void onPostExecute(List<HashMap<String, String>> result) {
	//
	// String[] person = new String[result.size()];
	// int i = 0;
	// for (HashMap<String, String> m : result) {
	// if (i == 0) {
	// person[i] = "---请选择---";
	// } else {
	//
	// person[i] = m.get("name") + ":" + m.get("tel");
	// }
	//
	// i++;
	//
	// }
	//
	// ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(
	// addActivity.this, R.layout.spinner,
	// person);
	// Spinner_person.setAdapter(adapterperson);
	// super.onPostExecute(result);
	// }
	//
	// }.execute(Country);
	//
	// }

	// 加载片区下拉框
	public void bindArea(String Country) {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {

				return dbUtil.SelectArea(params[0]);

			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {

				String[] person = new String[result.size()];
				int i = 0;
				for (HashMap<String, String> m : result) {
					if (i == 0) {
						person[i] = "---请选择---";
					} else {

						person[i] = m.get("片区");
					}

					i++;

				}

				ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(
						AddkrActivity.this,
						android.R.layout.simple_spinner_dropdown_item, person);
				Spinner_area.setAdapter(adapterperson);
				super.onPostExecute(result);
			}

		}.execute(Country);

	}

	// public void bindCell(String Country,String Area) {
	// // List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
	// // String>>();
	// List<HashMap<String, String>> list = new ArrayList<HashMap<String,
	// String>>();
	// new AsyncTask<String, Void, List<HashMap<String, String>>>() {
	//
	// @Override
	// protected List<HashMap<String, String>> doInBackground(
	// String... params) {
	//
	// return dbUtil.SelectCell(params[0],params[1]);
	//
	// }
	//
	// @Override
	// protected void onPostExecute(List<HashMap<String, String>> result) {
	//
	// String[] person = new String[result.size()];
	// int i = 0;
	// for (HashMap<String, String> m : result) {
	// if (i == 0) {
	// person[i] = "---请选择---";
	// } else {
	//
	// person[i] = m.get("小区");
	// }
	//
	// i++;
	//
	// }
	//
	// ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(
	// addActivity.this, R.layout.spinner, person);
	// Spinner_cell.setAdapter(adapterperson);
	// super.onPostExecute(result);
	// }
	//
	// }.execute(Country,Area);
	//
	// }

	public void bindInMode(String Country, String Area, String Cell) {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {

				return dbUtil.SelectInMode(params[0], params[1], params[2]);

			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {

				String[][] person = new String[result.size()][4];
				int i = 0;
				for (HashMap<String, String> m : result) {
					if (i == 0) {
						person[i][0] = m.get("接入方式");
						person[i][1] = m.get("装机人员姓名");
						person[i][2] = m.get("装机人员电话");
						person[i][3] = m.get("小区详细地址");
					}
					i++;
				}
				Text_inmode.setText(person[0][0]);
				Text_celladdress.setText(person[0][3]);
				ReceivePerson.setText(person[0][1] + ":" + person[0][2]);
				super.onPostExecute(result);
			}
			// @Override
			// protected void onPreExecute() {
			// Toast.makeText(addActivity.this, "正在加载装机人员信息，请稍后...",
			// Toast.LENGTH_SHORT).show();
			// super.onPreExecute();
			// }
		}.execute(Country, Area, Cell);
	}

//	addjob(country, sender, installedPerson, userinfo,
//			 area, cell, celladdress, inmode,
//			myApp.getUser(), remark);
	// 添加到数据库
	public void addjob(final String country, 
			final String sender, final String installperson,
			final String uerInfo,
			final String area, final String cell, final String celladdress,
			final String inmode, final String User,
			final String remark) {
		new AsyncTask<String, Void, String>() {
			Toast toast;// 显示提交状态

			@Override
			protected void onPreExecute() {
				toast = Toast.makeText(AddkrActivity.this, "正在提交,请勿重复提交",
						Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(String... params) {
				return dbUtil.InsertJob(params[0], params[1], params[2],
						params[3], params[4], params[5], params[6], params[7],
						params[8], params[9], params[10], params[11],
						params[12], params[13]);
			}

			@Override
			protected void onPostExecute(String result) {
				toast.cancel();
				Toast.makeText(AddkrActivity.this, result, Toast.LENGTH_SHORT)
						.show();
				if (result.equals("Success")) {
					// //给接单人发送短信
					String[] info = installperson.split(":");
					if (info.length >= 1) {
						String phoneNumber = info[1];
						String message = "宽带装维新派单：" + country + "用户[" + uerInfo
								+ "]"+type+"，地址：" + useraddress + ",片区：" + area
								+ ",小区：" + cell + ",接入方式:" + inmode + ",由["
								+ sender + "]派单。";
						Log.d("派单短信", message);
						sendSMS(phoneNumber, message);
					}
					finish();
				} else {
					btn_add.setEnabled(true);
				}
				super.onPostExecute(result);
			}

		}.execute(country, type, sender, installperson, uerInfo, useraddress,
				money, area, cell, celladdress, inmode, User, account, remark);

	}

	/**
	 * 直接调用短信接口发短信
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public void sendSMS(String phoneNumber, String message) {
		// 获取短信管理器
		try {
			android.telephony.SmsManager smsManager = android.telephony.SmsManager
					.getDefault();
			// 拆分短信内容（手机短信长度限制）
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(phoneNumber, null, text, null, null);
			}
		} catch (Exception e) {
			Log.d("派单短信", "发送短信失败！");
		}
	}
}