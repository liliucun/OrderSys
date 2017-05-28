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
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View.OnClickListener;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AddKdActivity extends Activity {
	private static final String[] country = {"---��ѡ��---", "������", "��ľ", "����", "����", "����","��ɽ", "���", "��֬", "����", "�Ɽ", "�彧", "����", "������"};
	private ArrayAdapter<String> adapter_county;
	private Button btn_add;
	private Button btn_person;
	private DBUtil dbUtil;
	private AppData myApp;

	private SearchView userphone;
	private EditText username;
	private TextView xj;
	private TextView ywq;
	private TextView jtdw;
	private Spinner blqd;
	private Spinner county;
	private Spinner area;
	private TextView receiver;
	private TextView bgdz;
	private TextView qdbm;
	private TextView qdmc;
	private Spinner kdcp;
	private TextView kdlx;
	private TextView tclx;
	private TextView yf;
	private TextView dk;
	private TextView sender;
	private EditText remark;
	
	public class SpinnerData {

		private String value = "";
		private String text = "";

		public SpinnerData() {
			value = "";
			text = "";
		}

		public SpinnerData(String _text, String _value) {
			value = _value;
			text = _text;
		}

		@Override
		public String toString() { 

			return text;
		}

		public String getValue() {
			return value;
		}

		public String getText() {
			return text;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addkd);
		myApp=(AppData)getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("����ɵ�");
		
		dbUtil = new DBUtil();
		userphone = (SearchView) findViewById(R.id.userphone);
		username=(EditText)findViewById(R.id.username);
		xj = (TextView) findViewById(R.id.xj);
		ywq = (TextView) findViewById(R.id.ywq);
		jtdw = (TextView) findViewById(R.id.jtdw);
		blqd = (Spinner) findViewById(R.id.blqd);
		county = (Spinner) findViewById(R.id.county);
		area=(Spinner)findViewById(R.id.area);
		receiver = (TextView) findViewById(R.id.receiver);
		bgdz = (TextView) findViewById(R.id.bgdz);
		qdbm = (TextView) findViewById(R.id.qdbm);
		qdmc = (TextView) findViewById(R.id.qdmc);
		kdcp = (Spinner) findViewById(R.id.zdhd);
		kdlx = (TextView) findViewById(R.id.hdlx);
		tclx = (TextView) findViewById(R.id.yxbbm);
		yf = (TextView) findViewById(R.id.jx);
		dk = (TextView) findViewById(R.id.money);
		sender = (TextView) findViewById(R.id.sender);
		remark = (EditText) findViewById(R.id.remark);
		
		sender.setText(myApp.getName()+":"+myApp.getPhone());
		btn_add = (Button) findViewById(R.id.btn_add);
		
		userphone.setSubmitButtonEnabled(true);
		userphone.setQueryHint("��������ѯ");
		userphone.setIconified(false);
		userphone.clearFocus();
		userphone.setOnQueryTextListener(new OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				new AsyncTask<String, Void, List<HashMap<String, String>>>() {
					@Override
					protected List<HashMap<String, String>> doInBackground(
							String... params) {
						return dbUtil.GetBlqd(params[0]);
					}
					@Override
					protected void onPostExecute(List<HashMap<String, String>> result) {
						if(result==null || result.size()==0)
						{
							Toast.makeText(AddKdActivity.this, "����������Ӧ��",Toast.LENGTH_SHORT).show();
							return;
						}
						HashMap map=result.get(0);
						if(map==null || map.get("info")==null)
						{
							Toast.makeText(AddKdActivity.this, "��ѯ�޽����",Toast.LENGTH_SHORT).show();
							return;
						}
						String phoneinfo[]=map.get("info").toString().split("\\|",-1);
						xj.setText(phoneinfo[0]);
						ywq.setText(phoneinfo[1]);
						jtdw.setText(phoneinfo[2]);
						county.setSelection(0);
						//area.setSelection(0);
						receiver.setText("");
						bgdz.setText("");
						qdbm.setText("");
						qdmc.setText("");
						List<SpinnerData> list = new ArrayList<SpinnerData>();
						SpinnerData sd=new SpinnerData("�ֶ�ѡ��","");
						list.add(sd);
						ArrayAdapter<SpinnerData> adapter_blqd = new ArrayAdapter<SpinnerData>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,list);
						if(map.get("result").equals("all"))
						{
							SpinnerData t=new SpinnerData("�������� |"+map.get("name").toString(),map.get("code").toString());
							SpinnerData t1=new SpinnerData("�ͻ����� |"+map.get("name1").toString(),map.get("code1").toString());
							adapter_blqd.insert(t, 0);
							adapter_blqd.insert(t1, 1);
						}
						else if(map.get("result").equals("ckqd"))
						{
							SpinnerData t=new SpinnerData("�������� |"+map.get("name").toString(),map.get("code").toString());
							adapter_blqd.insert(t, 0);
						}
						else if(map.get("result").equals("khjl"))
						{
							SpinnerData t=new SpinnerData("�ͻ����� |"+map.get("name").toString(),map.get("code").toString());
							adapter_blqd.insert(t, 0);
						}
						blqd.setAdapter(adapter_blqd);
						super.onPostExecute(result);
					}

				}.execute(arg0);
				return true;
			}
			
		});

		bindSpinner();
		// ��Ӱ�ť��������Ӳ���
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_add.setEnabled(false);

				if(!(userphone.equals("") || county.getSelectedItem()==null || county.getSelectedItem().toString().equals("") ||area.getSelectedItem()==null || area.getSelectedItem().toString().equals("") || receiver.getText().equals("") || qdbm.getText().equals("") || 
						kdlx.getText().equals("") || yf.getText().equals("") || dk.getText().equals(""))){
					addKdJob();
				} else {
					Toast.makeText(AddKdActivity.this, "������Ϣ����Ϊ�գ�",Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
			}
		});
		blqd.setSelection(0, true);
		blqd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
//						if(position==0)
//						{
//							return;
//						}
						// TODO Auto-generated method stub
						String selectedValue = ((SpinnerData)blqd.getSelectedItem()).getText();
						if(selectedValue.substring(0,4).equals("��������"))
						{
							new AsyncTask<String, Void, List<HashMap<String, String>>>() {
								@Override
								protected List<HashMap<String, String>> doInBackground(
										String... params) {
									return dbUtil.GetJdrInfo(params[0],params[1]);
								}
								@Override
								protected void onPostExecute(List<HashMap<String, String>> result) {
									if(result==null || result.size()==0)
									{
										Toast.makeText(AddKdActivity.this, "������������Ч�ͻ�����",Toast.LENGTH_SHORT).show();
										return;
									}
									HashMap map=result.get(0);
									String array[]=map.get("result").toString().split(":",-1);
									//setSpinnerItemSelectedByValue(county,array[2]);
									ArrayAdapter<String> ad1 = new ArrayAdapter<String>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[2]});
									county.setAdapter(ad1);
									ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[3]});
									area.setAdapter(adapterperson);
									receiver.setText(array[0] + ':' + array[1]);
									bgdz.setText(array[6]);
									qdbm.setText(array[4]);
									qdmc.setText(array[5]);
									super.onPostExecute(result);
								}
	
							}.execute(((SpinnerData)blqd.getSelectedItem()).getValue(),"chlcode");
						}
						else if(selectedValue.substring(0,4).equals("�ͻ�����"))
						{
							new AsyncTask<String, Void, List<HashMap<String, String>>>() {
								@Override
								protected List<HashMap<String, String>> doInBackground(
										String... params) {
									return dbUtil.GetJdrInfo(params[0],params[1]);
								}
								@Override
								protected void onPostExecute(List<HashMap<String, String>> result) {
									if(result==null || result.size()==0)
									{
										Toast.makeText(AddKdActivity.this, "�ÿͻ���������Ч�û���",Toast.LENGTH_SHORT).show();
										return;
									}
									HashMap map=result.get(0);
									String array[]=map.get("result").toString().split(":",-1);
									//setSpinnerItemSelectedByValue(county,array[2]);
									ArrayAdapter<String> ad1 = new ArrayAdapter<String>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[2]});
									county.setAdapter(ad1);
									ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[3]});
									area.setAdapter(adapterperson);
									receiver.setText(array[0] + ':' + array[1]);
									bgdz.setText(array[6]);
									qdbm.setText(array[4]);
									qdmc.setText(array[5]);
									super.onPostExecute(result);
								}
	
							}.execute(((SpinnerData)blqd.getSelectedItem()).getValue(),"id");
						}else
						{
							bindSpinner();
							area.setAdapter(null);
							receiver.setText("");
							bgdz.setText("");
							qdbm.setText("");
							qdmc.setText("");
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});
		county.setSelection(0, true);
		county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if(position==0)
						{
							return;
						}
						// TODO Auto-generated method stub
						String selectedValue = county.getSelectedItem().toString();
						bindArea(selectedValue);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});
		area.setSelection(0,true);
		area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				if(position==0)
				{
					return;
				}
				// TODO Auto-generated method stub
				SelectArea(county.getSelectedItem().toString(),area.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
		
		kdcp.setSelection(0,true);
		kdcp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				if(position==0)
				{
					return;
				}
				// TODO Auto-generated method stub
				SelectKdcp();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});	

		} 
	 /*
	 * ����ֵ, ����spinnerĬ��ѡ��:
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		if(value.equals("����"))
		{
			value="������";
		}
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
		county.setAdapter(adapter_county);
		county.setSelection(0, true);
		
		bindZdhd();
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
				return dbUtil.GetAreaZd(params[0]);
			}
			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				String[] person = new String[result.size() + 1];
				person[0] = "---��ѡ��---";
				int i = 1;
				for (HashMap<String, String> m : result) {
					person[i] = m.get("text");
					i++;
				}
				ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,person);
				area.setAdapter(adapterperson);
				super.onPostExecute(result);
			}

		}.execute(Country);

	}
	
	//����Ƭ��������
	public void bindZdhd() {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				return dbUtil.GetZdhd();
			}
			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				List<SpinnerData> list = new ArrayList<SpinnerData>();
				int i = 0;
				for (HashMap<String, String> m : result) {
					if (i == 0) {
						SpinnerData t=new SpinnerData("---��ѡ��---","");
						list.add(t);
					} else {
						SpinnerData t=new SpinnerData(m.get("text"),m.get("id"));
						list.add(t);
					}
					i++;
				}
				
				ArrayAdapter<SpinnerData> adapterperson = new ArrayAdapter<SpinnerData>(AddKdActivity.this, android.R.layout.simple_spinner_dropdown_item,list);
				kdcp.setAdapter(adapterperson);
				super.onPostExecute(result);
			}

		}.execute();

	}
	
	//����Ƭ��������
	public void SelectArea(String County,String xq) {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {

				return dbUtil.SelectAreaZd(params[0],params[1]);

			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {

				String[] person = result.get(0).get("result").split(":",-1);
				receiver.setText(person[0]+":"+person[1]);
				bgdz.setText(person[6]);
				qdbm.setText(person[4]);
				qdmc.setText(person[5]);
				super.onPostExecute(result);
			}

		}.execute(County,xq);

	}
	
	//����Ƭ��������
	public void SelectKdcp() {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				return dbUtil.SelectZdhd(((SpinnerData)kdcp.getSelectedItem()).getValue());
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				HashMap<String,String> map=result.get(0);
				kdlx.setText(map.get("�������"));
				tclx.setText(map.get("�ײ�����"));
				yf.setText(map.get("�·�"));
				dk.setText(map.get("����"));
				super.onPostExecute(result);
			}

		}.execute();

	}

	// ��ӵ����ݿ�
	public void addKdJob() {
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {
			Toast toast;//��ʾ�ύ״̬
			@Override
			protected void onPreExecute() {
				if(!(userphone.equals("") || county.getSelectedItem().toString().equals("") || area.getSelectedItem().toString().equals("") || receiver.getText().equals("") || qdbm.getText().equals("") || 
						kdlx.getText().equals("") || yf.getText().equals("") || dk.getText().equals(""))){
					toast=Toast.makeText(AddKdActivity.this, "�����ύ,�����ظ��ύ", Toast.LENGTH_SHORT);
					toast.show();
				}else {
					Toast.makeText(AddKdActivity.this, "������Ϣ����Ϊ�գ�",Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
				super.onPreExecute();
			}

			@Override
			protected List<HashMap<String, String>> doInBackground(String... params) {				
				return dbUtil.addKdJob(params[0], params[1], params[2], params[3],	params[4], params[5], params[6], params[7], params[8], params[9],params[10],params[11],params[12],params[13],params[14],params[15],params[16]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				if (result.get(0).get("result").equals("True")) 
				{
					toast.cancel();
					Toast.makeText(AddKdActivity.this, "�ύ�ɹ�", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					toast.cancel();
					Toast.makeText(AddKdActivity.this, result.get(0).get("result"), Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
				super.onPostExecute(result);
			}

		}.execute( "0", county.getSelectedItem().toString(), area.getSelectedItem().toString(), kdlx.getText().toString(), tclx.getText().toString(), yf.getText().toString(), dk.getText().toString(), sender.getText().toString(), remark.getText().toString(), receiver.getText().toString(), qdbm.getText().toString(), qdmc.getText().toString(), username.getText().toString(), userphone.getQuery().toString(), xj.getText().toString(), ywq.getText().toString(), jtdw.getText().toString());

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
