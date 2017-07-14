package com.sncrc.ordersys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.EditText;
import android.app.DatePickerDialog.OnDateSetListener;


import java.text.SimpleDateFormat;

public class InstallListActivity extends Activity {
	private static final String[] country = { "榆阳区", "神木", "府谷", "定边", "靖边","横山", "绥德", "米脂", "佳县", "吴堡", "清涧", "子洲", "大柳塔"};
	private static final String[] type = {"未完成工单", "已完成工单", "申请弃单工单","成功装机工单","失败装机工单","待回单工单","魔百和未完成工单","魔百和已完成工单"};
	private Spinner Spinner_country;
	private Spinner spinner_type;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapterfortype;
	private DBUtil dbUtil;
	private static ListView listview;
	private AppData myApp;
	private String Power;
	private Button ShowDataButton = null;
	private Button SelectDateButton;
	private Button SelectEndDateButton;
	private EditText Name;
	//private List<String,String> 
	private static final int DATA_PICKER_ID = 1;
	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.install_listview);
		myApp=(AppData)getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("新装工单查询");
		Power=myApp.getPower();
		Spinner_country = (Spinner) findViewById(R.id.Spinner_country);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		listview = (ListView) findViewById(R.id.listView);
		dbUtil = new DBUtil();
		ShowDataButton = (Button) findViewById(R.id.ShowData);
		SelectDateButton= (Button) findViewById(R.id.SelectData);
		Name=(EditText)findViewById(R.id.name);
		Name.setText(myApp.getName());
		if(!Power.equals("维护人员"))
		{
			Name.setText("");
		}
		SelectEndDateButton= (Button) findViewById(R.id.SelectEndData);
		SelectEndDateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				Calendar now = Calendar.getInstance();
				DatePickerDialog datePicker=new DatePickerDialog(InstallListActivity.this, new OnDateSetListener() {
	                
	                @Override
	                public void onDateSet(DatePicker view, int year, int monthOfYear,
	                        int dayOfMonth) {
	                    // TODO Auto-generated method stub
	                	String Date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                	SelectEndDateButton.setText(Date);
	                }
	            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH) , now.get(Calendar.DAY_OF_MONTH));
	            datePicker.show();

			}
		});
		
		SelectDateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				Calendar now = Calendar.getInstance();
				DatePickerDialog datePicker=new DatePickerDialog(InstallListActivity.this, new OnDateSetListener() {
	                
	                @Override
	                public void onDateSet(DatePicker view, int year, int monthOfYear,
	                        int dayOfMonth) {
	                    // TODO Auto-generated method stub
	                	String Date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                	SelectDateButton.setText(Date);
	                }
	            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH) , now.get(Calendar.DAY_OF_MONTH));
	            datePicker.show();

			}
		});
		
		ShowDataButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				if(SelectDateButton.getText().equals("起始时间") || SelectEndDateButton.getText().equals("结束时间"))
				{
					Toast.makeText(InstallListActivity.this, "请选择时间！",Toast.LENGTH_SHORT).show();
					return;
				}else
				{
					String selectedValue = (String) Spinner_country.getSelectedItem();
					String typestr = (String) spinner_type.getSelectedItem();
					String typeID = "";
					if (typestr.equals("未完成工单"))
						typeID = "1";
					else if (typestr.equals("已完成工单"))
						typeID = "2";
					else if (typestr.equals("申请弃单工单"))
						typeID = "3";
					else if (typestr.equals("接单审核工单"))
						typeID="4";
					else if (typestr.equals("成功装机工单"))
						typeID="5";
					else if (typestr.equals("失败装机工单"))
						typeID="6";
					else if (typestr.equals("待回单工单"))
						typeID="7";
					else if(typestr.equals("未完扩容工单"))
						typeID="8";
					else if(typestr.equals("魔百和未完成工单"))
						typeID="9";
					else if(typestr.equals("魔百和已完成工单"))
						typeID="10";
					Log.d("InstallList", "typeID:"+typeID);
					loadListview(selectedValue, typeID, SelectDateButton.getText().toString(),SelectEndDateButton.getText().toString(),Name.getText().toString());
				}
			}
		});
		bindSpinner();
		
		if(!(myApp.getPower().equals("超级管理员") || myApp.getPower().equals("全市派单") || myApp.getPower().equals("维护人员")))
		{
			Spinner_country.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(Spinner_country,myApp.getCounty());

	}

	/**
	 * 根据值, 设置spinner默认选中:
	 * @param spinner
	 * @param value
	 */
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
	
	/* 加载下拉框 */
	private void bindSpinner() {
		adapter = new ArrayAdapter<String>(this,R.layout.spinner, country);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		Spinner_country.setAdapter(adapter);
		Spinner_country.setSelection(0, true);
		adapterfortype = new ArrayAdapter<String>(this,R.layout.spinner, type);
		adapterfortype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner_type.setAdapter(adapterfortype);
		spinner_type.setSelection(0, true);
		// Spinner_country.setOnItemSelectedListener(spinnerSelectedListener);

	}

	// 下拉框设置监听事件
	private Spinner.OnItemSelectedListener spinnerSelectedListener = new Spinner.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String selectedValue = (String) Spinner_country.getSelectedItem();

			// loadListview(selectedValue);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}

	};

	//
	public void loadListview(String country, String typeID, String Date,String EndDate,String Name) {

		new AsyncTask<String, Void, List<HashMap<String, String>>>() {
			Toast toast;
			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {

				return dbUtil.GetAllJob(params[0], params[1], params[2],params[3],params[4],params[5],params[6]);

			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				toast.cancel();
				if (result==null || result.size() == 0) {
					Toast.makeText(InstallListActivity.this, "没有数据！",Toast.LENGTH_SHORT).show();
					listview.setAdapter(null);
				} else {
					SimpleAdapter adapter=null;
					if(Power.equals("维护人员"))
					{
						adapter = new SimpleAdapter(
								InstallListActivity.this, result,
								R.layout.install_listview_div_zw, new String[] { "ID","派单时间","片区","小区","装机人","当前状态","耗时"}, new int[] { R.id.Id,R.id.StrDatetime,R.id.area,R.id.cell,R.id.installperson,R.id.State,R.id.usedtime});
					}else{
							adapter = new SimpleAdapter(
							InstallListActivity.this, result,
							R.layout.install_listview_div, new String[] { "ID","派单时间","片区","小区","装机地址","当前状态","耗时"}, new int[] { R.id.Id,R.id.StrDatetime,R.id.area,R.id.cell,R.id.address,R.id.State,R.id.usedtime});
					}
					listview.setAdapter(adapter);
					//if (spinner_type.getSelectedItem().equals("未完成订单") || spinner_type.getSelectedItem().equals("已完成订单")) 
					{
						listview.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(
									AdapterView<?> parent, View view,
									int position, long id) {
								// TODO Auto-generated method stub
								HashMap<String, String> map = (HashMap<String, String>) listview.getItemAtPosition(position);
								String message = map.get("ID");
								Intent intent;
								intent = new Intent(InstallListActivity.this,SinglequeryActivity.class);
								// 用Bundle携带数据
								Bundle bundle = new Bundle();
								// 传递name参数为tinyphp
								bundle.putString("Id", message);
								intent.putExtras(bundle);

								startActivity(intent);
								// Toast.makeText(getApplicationContext(),
								// message,Toast.LENGTH_LONG).show();
							}
						});
					}
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				toast=Toast.makeText(InstallListActivity.this, "查询中...",Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

		}.execute(country, typeID, Date,EndDate,((AppData)getApplication()).getPower(),((AppData)getApplication()).getUser(),Name);

	}

}
