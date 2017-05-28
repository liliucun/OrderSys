package com.sncrc.ordersys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import org.json.*;

public class reportActivity extends Activity {
	private DBUtil dbUtil = new DBUtil();
	private static ListView listview;
	private static String[] type;
	private AppData myApp;
	private Spinner spinner_type;
	private Button ShowDataButton;
	private Button SelDateButton;
	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		myApp=(AppData)getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("统计报表");
		if(myApp.getPower().equals(""))
		{
			if(myApp.getRole().equals(""))
			{
				type = new String[]{};
			}
			else
			{
				type = new String[]{"终端汇总表"};
			}
		}
		else
		{
			if(myApp.getRole().equals(""))
			{
				type = new String[]{"新装汇总表","新装分类汇总表","新装未完成工单统计表","故障汇总表","故障未完成工单统计表"};
			}
			else
			{
				type = new String[]{"新装汇总表","新装分类汇总表","新装未完成工单统计表","故障汇总表","故障未完成工单统计表","终端汇总表"};
			}
		}
		listview = (ListView) findViewById(R.id.reportList);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		ShowDataButton = (Button) findViewById(R.id.ShowData);
		SelDateButton = (Button) findViewById(R.id.SelDate);
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");//设置日期格式
		SelDateButton.setText(df.format(new Date()));
		
		ShowDataButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{

				loadListview(spinner_type.getSelectedItem().toString(),SelDateButton.getText().toString());
			}
		});
		
		SelDateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				Calendar now = Calendar.getInstance();
				DatePickerDialog datePicker=new DatePickerDialog(reportActivity.this, new OnDateSetListener() {
	                
	                @Override
	                public void onDateSet(DatePicker view, int year, int monthOfYear,
	                        int dayOfMonth) {
	                    // TODO Auto-generated method stub
	                	String Date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                	SelDateButton.setText(Date);
	                }
	            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH) , now.get(Calendar.DAY_OF_MONTH));
	            datePicker.show();
			}
		});
		
		spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				//String type = (String) spinner_type.getSelectedItem();
				SelDateButton.setText(df.format(new Date()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
		ArrayAdapter<String> adapterfortype = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, type);
		// 将adapter 添加到spinner中
		spinner_type.setAdapter(adapterfortype);
		spinner_type.setSelection(0, true);
		loadListview(spinner_type.getSelectedItem().toString(),"");
	}
	
	private void loadListview(String type,String date) {

		new AsyncTask<String, Void, String>() {
			Toast toast;
			@Override
			protected String doInBackground(
					String... params) {

				return dbUtil.getReport(params[0],params[1]);
			}
			@Override
			protected void onPostExecute(String result) {
				toast.cancel();
				List<HashMap<String, String>> list=JsonUtil.json2list(result);
				if (list==null || list.size()==0) {
					Toast.makeText(reportActivity.this, "没有数据！",Toast.LENGTH_SHORT).show();
					listview.setAdapter(null);
				} else 
				{
					Toast.makeText(reportActivity.this, "加载完成",Toast.LENGTH_SHORT).show();
					//加表头
					HashMap<String, String> map = new HashMap<String, String>();
					Map t=(Map)list.get(0);
					int i=0;
					Iterator<Map.Entry<String, String>> entries = t.entrySet().iterator();
					while (entries.hasNext()) {
					    Map.Entry<String, String> entry = entries.next();
					    map.put(entry.getKey().toString(), entry.getKey().toString());
					    i++;
					}
					list.add(0, map);

					if(spinner_type.getSelectedItem().toString().equals("扩容汇总表"))
					{
						//装机汇总表
						MyReportAdapter adapter = new MyReportAdapter(
							reportActivity.this, list,
							R.layout.report_div, 
							new String[] { "县区","当日派单","当日成功扩容","当月派单","当月成功扩容","未完成工单","超时未完成工单","待审核工单","当日APP活跃数"}, 
							new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9});
						adapter.SetDisplayColNum(9);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("新装汇总表"))
					{
						//装机汇总表
						MyReportAdapter adapter = new MyReportAdapter(
							reportActivity.this, list,
							R.layout.report_div, 
							new String[] { "县区","当日派单","当日成功装机","当月派单","当月成功装机","未完成工单","超时未完成工单","待审核工单","当日APP活跃数"}, 
							new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9});
						adapter.SetDisplayColNum(9);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("新装分类汇总表"))
					{
						//装机分类汇总表
						MyReportAdapter adapter = new MyReportAdapter(
							reportActivity.this, list,
							R.layout.report_div, 
							new String[] { "县区","自有员工当日派单","社会渠道当日派单","宽服站当日派单","自有员工当月派单","社会渠道当月派单","宽服站当月派单","自有员工当日成功装机","社会渠道当日成功装机","宽服站当日成功装机","自有员工当月成功装机","社会渠道当月成功装机","宽服站当月成功装机"}, 
							new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9,R.id.col10,R.id.col11,R.id.col12,R.id.col13});
						adapter.SetDisplayColNum(13);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("新装未完成工单统计表"))
					{
						//装机未完成统计
						MyReportAdapter adapter=new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "县区","小于16小时","从16至24小时","从24至32小时","从32至40小时","从40至48小时","大于48小时","累计"}, 
								new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8});
						adapter.SetDisplayColNum(8);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("故障汇总表"))
					{
						//故障汇总
						MyReportAdapter adapter = new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "县区","当日派单","当日成功处理","当月派单","当月成功处理","未完成工单","超时未完成工单"}, 
								new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7});
						adapter.SetDisplayColNum(7);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("故障未完成工单统计表"))
					{
						//故障未完成统计
						MyReportAdapter adapter=new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "县区","小于16小时","从16至24小时","从24至32小时","从32至40小时","从40至48小时","大于48小时","累计"}, 
								new int[] {R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8});
						adapter.SetDisplayColNum(8);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("终端汇总表"))
					{
						//故障汇总
						MyReportAdapter adapter = new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "县区","当日派单","当日成功处理","当月派单","当月成功处理","未完成工单","超时未完成工单"}, 
								new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7});
						adapter.SetDisplayColNum(7);//设置表格显示的列数
						listview.setAdapter(adapter);
					}
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				toast=Toast.makeText(reportActivity.this, "查询中...",Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

		}.execute(type,date);

	}
}