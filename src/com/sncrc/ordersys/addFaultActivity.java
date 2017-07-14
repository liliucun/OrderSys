package com.sncrc.ordersys;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.sncrc.ordersys.DBUtil;

import android.R.integer;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View.OnClickListener;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class addFaultActivity extends Activity {
	private static final String[] country = {"---��ѡ��---", "������", "��ľ", "����", "����", "����","��ɽ", "���", "��֬", "����", "�Ɽ", "�彧", "����", "������"};
	private static final String[] FaultType={"---��ѡ��---","�ҿ�","����","�ҷ�","WLAN"};
	private Spinner Spinner_country;
	private Spinner Spinner_FaultType;
	private TextView ReceivePerson;
	private Spinner Spinner_area;
	private TextView Spinner_cell;
	private TextView Text_inmode;
	private TextView Text_celladdress;
	private ArrayAdapter<String> adapter_county;
	private ArrayAdapter<String> adapter_FaultType;
	private Button btn_add;
	private Button btn_person;
	private DBUtil dbUtil;
	private AppData myApp;
	private EditText sendertEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfault);
		myApp=(AppData)getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("�����ɵ�");
		dbUtil = new DBUtil();
		Spinner_country = (Spinner) findViewById(R.id.Spinner_country);
		Spinner_FaultType=(Spinner)findViewById(R.id.Spinner_faulttype);
		ReceivePerson = (TextView) findViewById(R.id.Receiveperson);
		Spinner_area = (Spinner) findViewById(R.id.Spinner_area);
		Spinner_cell = (TextView) findViewById(R.id.Spinner_cell);
		Text_inmode = (TextView) findViewById(R.id.Text_inmode);
		Text_celladdress = (TextView) findViewById(R.id.celladdress);
		btn_add = (Button) findViewById(R.id.btn_add);
		sendertEditText = (EditText) findViewById(R.id.sender);
		sendertEditText.setText(myApp.getName()+":"+myApp.getPhone());
		// btn_person = (Button) findViewById(R.id.btn_person);
		bindSpinner();
		// ��Ӱ�ť��������Ӳ���
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_add.setEnabled(false);
				String county = (String) Spinner_country.getSelectedItem();
				String sender = sendertEditText.getText().toString();
				String installedPerson = ReceivePerson.getText().toString();
				String area=(String)Spinner_area.getSelectedItem();
				String cell=(String)Spinner_cell.getText().toString();
				String celladdress=Text_celladdress.getText().toString();
				String inmode=(String)Text_inmode.getText();
				String faulttype=Spinner_FaultType.getSelectedItem().toString();
				EditText userinfoEditText = (EditText) findViewById(R.id.userphone);
				String userphone = userinfoEditText.getText().toString();
				EditText addressEditText = (EditText) findViewById(R.id.useraddress);
				String useraddres = addressEditText.getText().toString();
				EditText userAccount = (EditText) findViewById(R.id.useraccount);
				String useraccount = userAccount.getText().toString();
				String faultDescribe=((EditText)findViewById(R.id.faultDescribe)).getText().toString();

				if(!(useraccount.equals("") || county.equals("") || faulttype.equals("---��ѡ��---") || installedPerson.equals("") || userphone.equals("") || useraddres.equals("") || 
						faultDescribe.equals("") || area.equals("---��ѡ��---") || cell.equals("---��ѡ��---"))){
					addFault(county, faulttype,faultDescribe, sender, installedPerson, userphone,
							useraddres,area,cell,celladdress,inmode,myApp.getUser(),useraccount);
				} else {
					Toast.makeText(addFaultActivity.this, "������Ϣ����Ϊ�գ�",Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
			}
		});
		Spinner_country.setSelection(0, true);
		Spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if(position==0)
						{
							return;
						}
						// TODO Auto-generated method stub
						String selectedValue = (String) Spinner_country.getSelectedItem();
						//bindPersonByCountry(selectedValue);
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
		Spinner_area.setSelection(0,true);
		Spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				if(position==0)
				{
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
//		Spinner_cell.setSelection(0, true);
//		Spinner_cell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent,
//					View view, int position, long id) {
//				if(position==0)
//				{
//					return;
//				}
//				// TODO Auto-generated method stub
//				String selectedCountryValue = (String) Spinner_country.getSelectedItem();
//				String selectedValue = (String) Spinner_area.getSelectedItem();
//				String selectCellValue=(String) Spinner_cell.getSelectedItem();
//				bindInMode(selectedCountryValue,selectedValue,selectCellValue);
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
		if(!(myApp.getPower().equals("��������Ա")||myApp.getPower().equals("ȫ���ɵ�")))
		{
			Spinner_country.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(Spinner_country,myApp.getCounty());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCodeΪ�ش��ı�ǣ�����B�лش�����RESULT_OK
		   case RESULT_OK:
		    Bundle b=data.getExtras(); //dataΪB�лش���Intent
		    String cell=b.getString("cell");//str��Ϊ�ش���ֵ
		    Spinner_cell.setText(cell);
		    String selectedCountryValue = (String) Spinner_country.getSelectedItem();
			String selectedValue = (String) Spinner_area.getSelectedItem();
			bindInMode(selectedCountryValue,selectedValue,cell);
		    break;
		default:
		    break;
		    }
		}
	
	private void SelectCell()
	{
		String selectedCountryValue = (String) Spinner_country.getSelectedItem();
		String selectedValue = (String) Spinner_area.getSelectedItem();
		if(selectedCountryValue==null || selectedValue==null || selectedCountryValue.equals("") || selectedValue.equals("") || selectedCountryValue.equals("---��ѡ��---") || selectedValue.equals("---��ѡ��---"))
		{
			return;
		}
		Text_inmode.setText("");
		Bundle b=new Bundle();
		b.putString("Opt", "AddJob");
		b.putString("County", selectedCountryValue);
		b.putString("Area", selectedValue);
		Intent intent=new Intent(addFaultActivity.this,SelectListViewActivity.class);
		intent.putExtras(b);
		startActivityForResult(intent, 0);
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

	// /��������
	public void bindSpinner() {
		// ����ѡ������ArrayAdapter��������
		adapter_county = new ArrayAdapter<String>(this,R.layout.spinner, country);
		// ���������б�ķ��
		adapter_county.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		Spinner_country.setAdapter(adapter_county);
		Spinner_country.setSelection(0, true);

		adapter_FaultType = new ArrayAdapter<String>(this,R.layout.spinner, FaultType);
		adapter_FaultType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_FaultType.setAdapter(adapter_FaultType);
		Spinner_FaultType.setSelection(0, true);
	}
	
	//����Ƭ��������
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
						person[i] = "---��ѡ��---";
					} else {

						person[i] = m.get("Ƭ��");
					}

					i++;

				}

				ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(
						addFaultActivity.this, android.R.layout.simple_spinner_dropdown_item,
						person);
				Spinner_area.setAdapter(adapterperson);
				super.onPostExecute(result);
			}

		}.execute(Country);

	}
	
//	public void bindCell(String Country,String Area) {
//		if(Area.equals("����"))
//		{
//			Spinner_FaultType.setSelection(2, true);
//			Spinner_FaultType.setEnabled(false);
//		}
//		else
//		{
//			Spinner_FaultType.setSelection(0, true);
//			Spinner_FaultType.setEnabled(true);
//		}
//		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//		new AsyncTask<String, Void, List<HashMap<String, String>>>() {
//
//			@Override
//			protected List<HashMap<String, String>> doInBackground(
//					String... params) {
//
//				return dbUtil.SelectCell(params[0],params[1]);
//
//			}
//
//			@Override
//			protected void onPostExecute(List<HashMap<String, String>> result) {
//
//				String[] person = new String[result.size()];
//				int i = 0;
//				for (HashMap<String, String> m : result) {
//					if (i == 0) {
//						person[i] = "---��ѡ��---";
//					} else {
//
//						person[i] = m.get("С��");
//					}
//
//					i++;
//
//				}
//
//				ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(
//						addFaultActivity.this, R.layout.spinner,
//						person);
//				Spinner_cell.setAdapter(adapterperson);
//				super.onPostExecute(result);
//			}
//
//		}.execute(Country,Area);
//
//	}
	
	public void bindInMode(String Country,String Area,String Cell) {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {

				return dbUtil.SelectInMode(params[0],params[1],params[2]);

			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {

				String[][] person = new String[result.size()][4];
				int i = 0;
				for (HashMap<String, String> m : result) {
					if (i == 0) {
						person[i][0] = m.get("���뷽ʽ");
						person[i][1] = m.get("ά����Ա����");
						person[i][2] = m.get("ά����Ա�绰");
						person[i][3] = m.get("С����ϸ��ַ");
					}
					i++;
				}
				Text_inmode.setText(person[0][0]);
				ReceivePerson.setText(person[0][1]+":"+person[0][2]);
				Text_celladdress.setText(person[0][3]);
				super.onPostExecute(result);
			}
//			@Override
//			protected void onPreExecute() {
//				Toast.makeText(addActivity.this, "���ڼ���װ����Ա��Ϣ�����Ժ�...",
//						Toast.LENGTH_SHORT).show();
//				super.onPreExecute();
//			}
		}.execute(Country,Area,Cell);
	}

	// ��ӵ����ݿ�
	public void addFault(final String country,final String FaultType,final String FaultDescribe,final String Sender,final String ReceivePerson,final String UserPhone,final String UserAddress,final String area,final String cell,final String celladdress,final String inmode ,final String User,final String useraccount) {
		new AsyncTask<String, Void, String>() {
			Toast toast;//��ʾ�ύ״̬
			@Override
			protected void onPreExecute() {
				toast=Toast.makeText(addFaultActivity.this, "�����ύ,�����ظ��ύ", Toast.LENGTH_SHORT);
				toast.show();
				
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(String... params) {				
				return dbUtil.InsertFault(params[0], params[1], params[2], params[3],	params[4], params[5], params[6], params[7], params[8], params[9],params[10],params[11],params[12]);
			}

			@Override
			protected void onPostExecute(String result) {
				toast.cancel();
				Toast.makeText(addFaultActivity.this, result, Toast.LENGTH_SHORT).show();
				if (result.equals("�ύ�ɹ�")) 
				{
					//���ӵ��˷��Ͷ���
//					String[] info = ReceivePerson.split(":");
//					if (info.length >= 1) {
//						String phoneNumber = info[1];
//						String message = "����������ɵ���"+country + "�û�[" + UserPhone+"]���ϣ���ַ��"
//								+ UserAddress + ",Ƭ����"+area+",С����"+cell+",���뷽ʽ:"+inmode+",��["+Sender+"]�ɵ���";
//						System.out.println(message);
//						sendSMS(phoneNumber, message);
//					}
					finish();
				}
				else
				{
					btn_add.setEnabled(true);
				}
				super.onPostExecute(result);
			}

		}.execute(country,FaultType,FaultDescribe,Sender,ReceivePerson,UserPhone,UserAddress,area,cell,celladdress,inmode,User,useraccount);

	}

	/**
	 * ֱ�ӵ��ö��Žӿڷ�����
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public void sendSMS(String phoneNumber, String message) {
		// ��ȡ���Ź�����
		try{
			android.telephony.SmsManager smsManager = android.telephony.SmsManager
					.getDefault();
			// ��ֶ������ݣ��ֻ����ų������ƣ�
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
		}catch(Exception e)
		{
			System.out.println("���Ͷ���ʧ�ܣ�");
		}
	}
	
	

}
