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
		actionBar.setTitle("ͳ�Ʊ���");
		if(myApp.getPower().equals(""))
		{
			if(myApp.getRole().equals(""))
			{
				type = new String[]{};
			}
			else
			{
				type = new String[]{"�ն˻��ܱ�"};
			}
		}
		else
		{
			if(myApp.getRole().equals(""))
			{
				type = new String[]{"��װ���ܱ�","��װ������ܱ�","��װδ��ɹ���ͳ�Ʊ�","���ϻ��ܱ�","����δ��ɹ���ͳ�Ʊ�"};
			}
			else
			{
				type = new String[]{"��װ���ܱ�","��װ������ܱ�","��װδ��ɹ���ͳ�Ʊ�","���ϻ��ܱ�","����δ��ɹ���ͳ�Ʊ�","�ն˻��ܱ�"};
			}
		}
		listview = (ListView) findViewById(R.id.reportList);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		ShowDataButton = (Button) findViewById(R.id.ShowData);
		SelDateButton = (Button) findViewById(R.id.SelDate);
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");//�������ڸ�ʽ
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
		// ��adapter ��ӵ�spinner��
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
					Toast.makeText(reportActivity.this, "û�����ݣ�",Toast.LENGTH_SHORT).show();
					listview.setAdapter(null);
				} else 
				{
					Toast.makeText(reportActivity.this, "�������",Toast.LENGTH_SHORT).show();
					//�ӱ�ͷ
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

					if(spinner_type.getSelectedItem().toString().equals("���ݻ��ܱ�"))
					{
						//װ�����ܱ�
						MyReportAdapter adapter = new MyReportAdapter(
							reportActivity.this, list,
							R.layout.report_div, 
							new String[] { "����","�����ɵ�","���ճɹ�����","�����ɵ�","���³ɹ�����","δ��ɹ���","��ʱδ��ɹ���","����˹���","����APP��Ծ��"}, 
							new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9});
						adapter.SetDisplayColNum(9);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("��װ���ܱ�"))
					{
						//װ�����ܱ�
						MyReportAdapter adapter = new MyReportAdapter(
							reportActivity.this, list,
							R.layout.report_div, 
							new String[] { "����","�����ɵ�","���ճɹ�װ��","�����ɵ�","���³ɹ�װ��","δ��ɹ���","��ʱδ��ɹ���","����˹���","����APP��Ծ��"}, 
							new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9});
						adapter.SetDisplayColNum(9);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("��װ������ܱ�"))
					{
						//װ��������ܱ�
						MyReportAdapter adapter = new MyReportAdapter(
							reportActivity.this, list,
							R.layout.report_div, 
							new String[] { "����","����Ա�������ɵ�","������������ɵ�","���վ�����ɵ�","����Ա�������ɵ�","������������ɵ�","���վ�����ɵ�","����Ա�����ճɹ�װ��","����������ճɹ�װ��","���վ���ճɹ�װ��","����Ա�����³ɹ�װ��","����������³ɹ�װ��","���վ���³ɹ�װ��"}, 
							new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9,R.id.col10,R.id.col11,R.id.col12,R.id.col13});
						adapter.SetDisplayColNum(13);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("��װδ��ɹ���ͳ�Ʊ�"))
					{
						//װ��δ���ͳ��
						MyReportAdapter adapter=new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "����","С��16Сʱ","��16��24Сʱ","��24��32Сʱ","��32��40Сʱ","��40��48Сʱ","����48Сʱ","�ۼ�"}, 
								new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8});
						adapter.SetDisplayColNum(8);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("���ϻ��ܱ�"))
					{
						//���ϻ���
						MyReportAdapter adapter = new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "����","�����ɵ�","���ճɹ�����","�����ɵ�","���³ɹ�����","δ��ɹ���","��ʱδ��ɹ���"}, 
								new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7});
						adapter.SetDisplayColNum(7);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("����δ��ɹ���ͳ�Ʊ�"))
					{
						//����δ���ͳ��
						MyReportAdapter adapter=new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "����","С��16Сʱ","��16��24Сʱ","��24��32Сʱ","��32��40Сʱ","��40��48Сʱ","����48Сʱ","�ۼ�"}, 
								new int[] {R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8});
						adapter.SetDisplayColNum(8);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
					else if(spinner_type.getSelectedItem().toString().equals("�ն˻��ܱ�"))
					{
						//���ϻ���
						MyReportAdapter adapter = new MyReportAdapter(
								reportActivity.this, list,
								R.layout.report_div, 
								new String[] { "����","�����ɵ�","���ճɹ�����","�����ɵ�","���³ɹ�����","δ��ɹ���","��ʱδ��ɹ���"}, 
								new int[] { R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7});
						adapter.SetDisplayColNum(7);//���ñ����ʾ������
						listview.setAdapter(adapter);
					}
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				toast=Toast.makeText(reportActivity.this, "��ѯ��...",Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

		}.execute(type,date);

	}
}