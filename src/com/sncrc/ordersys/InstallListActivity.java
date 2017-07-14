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
	private static final String[] country = { "������", "��ľ", "����", "����", "����","��ɽ", "���", "��֬", "����", "�Ɽ", "�彧", "����", "������"};
	private static final String[] type = {"δ��ɹ���", "����ɹ���", "������������","�ɹ�װ������","ʧ��װ������","���ص�����","ħ�ٺ�δ��ɹ���","ħ�ٺ�����ɹ���"};
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
		actionBar.setTitle("��װ������ѯ");
		Power=myApp.getPower();
		Spinner_country = (Spinner) findViewById(R.id.Spinner_country);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		listview = (ListView) findViewById(R.id.listView);
		dbUtil = new DBUtil();
		ShowDataButton = (Button) findViewById(R.id.ShowData);
		SelectDateButton= (Button) findViewById(R.id.SelectData);
		Name=(EditText)findViewById(R.id.name);
		Name.setText(myApp.getName());
		if(!Power.equals("ά����Ա"))
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
				if(SelectDateButton.getText().equals("��ʼʱ��") || SelectEndDateButton.getText().equals("����ʱ��"))
				{
					Toast.makeText(InstallListActivity.this, "��ѡ��ʱ�䣡",Toast.LENGTH_SHORT).show();
					return;
				}else
				{
					String selectedValue = (String) Spinner_country.getSelectedItem();
					String typestr = (String) spinner_type.getSelectedItem();
					String typeID = "";
					if (typestr.equals("δ��ɹ���"))
						typeID = "1";
					else if (typestr.equals("����ɹ���"))
						typeID = "2";
					else if (typestr.equals("������������"))
						typeID = "3";
					else if (typestr.equals("�ӵ���˹���"))
						typeID="4";
					else if (typestr.equals("�ɹ�װ������"))
						typeID="5";
					else if (typestr.equals("ʧ��װ������"))
						typeID="6";
					else if (typestr.equals("���ص�����"))
						typeID="7";
					else if(typestr.equals("δ�����ݹ���"))
						typeID="8";
					else if(typestr.equals("ħ�ٺ�δ��ɹ���"))
						typeID="9";
					else if(typestr.equals("ħ�ٺ�����ɹ���"))
						typeID="10";
					Log.d("InstallList", "typeID:"+typeID);
					loadListview(selectedValue, typeID, SelectDateButton.getText().toString(),SelectEndDateButton.getText().toString(),Name.getText().toString());
				}
			}
		});
		bindSpinner();
		
		if(!(myApp.getPower().equals("��������Ա") || myApp.getPower().equals("ȫ���ɵ�") || myApp.getPower().equals("ά����Ա")))
		{
			Spinner_country.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(Spinner_country,myApp.getCounty());

	}

	/**
	 * ����ֵ, ����spinnerĬ��ѡ��:
	 * @param spinner
	 * @param value
	 */
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
	
	/* ���������� */
	private void bindSpinner() {
		adapter = new ArrayAdapter<String>(this,R.layout.spinner, country);
		// ���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		Spinner_country.setAdapter(adapter);
		Spinner_country.setSelection(0, true);
		adapterfortype = new ArrayAdapter<String>(this,R.layout.spinner, type);
		adapterfortype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		spinner_type.setAdapter(adapterfortype);
		spinner_type.setSelection(0, true);
		// Spinner_country.setOnItemSelectedListener(spinnerSelectedListener);

	}

	// ���������ü����¼�
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
					Toast.makeText(InstallListActivity.this, "û�����ݣ�",Toast.LENGTH_SHORT).show();
					listview.setAdapter(null);
				} else {
					SimpleAdapter adapter=null;
					if(Power.equals("ά����Ա"))
					{
						adapter = new SimpleAdapter(
								InstallListActivity.this, result,
								R.layout.install_listview_div_zw, new String[] { "ID","�ɵ�ʱ��","Ƭ��","С��","װ����","��ǰ״̬","��ʱ"}, new int[] { R.id.Id,R.id.StrDatetime,R.id.area,R.id.cell,R.id.installperson,R.id.State,R.id.usedtime});
					}else{
							adapter = new SimpleAdapter(
							InstallListActivity.this, result,
							R.layout.install_listview_div, new String[] { "ID","�ɵ�ʱ��","Ƭ��","С��","װ����ַ","��ǰ״̬","��ʱ"}, new int[] { R.id.Id,R.id.StrDatetime,R.id.area,R.id.cell,R.id.address,R.id.State,R.id.usedtime});
					}
					listview.setAdapter(adapter);
					//if (spinner_type.getSelectedItem().equals("δ��ɶ���") || spinner_type.getSelectedItem().equals("����ɶ���")) 
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
								// ��BundleЯ������
								Bundle bundle = new Bundle();
								// ����name����Ϊtinyphp
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
				toast=Toast.makeText(InstallListActivity.this, "��ѯ��...",Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

		}.execute(country, typeID, Date,EndDate,((AppData)getApplication()).getPower(),((AppData)getApplication()).getUser(),Name);

	}

}
