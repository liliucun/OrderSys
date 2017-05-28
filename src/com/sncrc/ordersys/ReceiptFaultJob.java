package com.sncrc.ordersys;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiptFaultJob extends Activity {
	private String[] issuccess = { "�ָ�","Э��������" };//{ "�ָ�", "δ�ָ�","Э��������" }
	private String[] faultreason={"��","�˿ڹر�","�����������߹���","TV+��װ","·��������","�û�������","��·����","�˺���������","������","�豸����","�豸����","����","�ɴ�"};
	private String Id;
	static int issuccessPosition = 0;
	ArrayAdapter<String> issuccessAdapter = null;
	ArrayAdapter<String> FaultReasonAdapter = null;

	private Spinner Spinner_issuccess;
	private Spinner Spinner_faultreason;

	private TextView country;
	private TextView Idtxt;
	private TextView address;
	private TextView sender;
	private TextView installedperson;
	private AppData myapp;
	private Button receipt_btn;
	
	private String userphone="";
	private Date pdtime=null;

	private DBUtil dbUtil = new DBUtil();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiptfaultjob);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("���ϻص�");
		myapp=(AppData)getApplication();
		// ��ҳ���������
		Bundle bundle = this.getIntent().getExtras();
		// ����nameֵ
		Id = bundle.getString("Id");
		country = (TextView) findViewById(R.id.country);
		Idtxt = (TextView) findViewById(R.id.Idtxt);
		address = (TextView) findViewById(R.id.address);
		sender = (TextView) findViewById(R.id.sender);
		installedperson = (TextView) findViewById(R.id.installedperson);
		Spinner_issuccess=(Spinner)findViewById(R.id.issuccess);
		Spinner_faultreason=(Spinner)findViewById(R.id.faultreason);
		receipt_btn = (Button) findViewById(R.id.receipt_btn);
		receipt_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String result = Spinner_issuccess.getSelectedItem().toString();
				String faultreason = Spinner_faultreason.getSelectedItem().toString();

				EditText remarkEditText=(EditText) findViewById(R.id.RemarkEdit);
				String remark=remarkEditText.getText().toString();
				String[] CallLog=GetCallLog();
				if(CallLog==null)
				{
					ReplayFaultJob(Id, result, faultreason,remark,myapp.getUser(),"","");
				}
				else
				{
					ReplayFaultJob(Id, result, faultreason,remark,myapp.getUser(),CallLog[0],CallLog[1]);
				}
			}
		});

		loadInfo(Id);
		setSpinner();

	}

	public void ReplayFaultJob(String ID, String result, String faultreason,String remark,String userid,String calltime,String duration) {
		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.ReplayFaultJob(params[0], params[1], params[2],params[3],params[4],params[5],params[6]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(ReceiptFaultJob.this, result, Toast.LENGTH_LONG)
						.show();
				if (result.equals("Success")) {

					finish();
				}
				super.onPostExecute(result);
			}
		}.execute(ID, result, faultreason,remark,userid,calltime,duration);

	}

	// ���ض�����Ϣ
	public void loadInfo(String ID) {
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				// TODO Auto-generated method stub
				return dbUtil.GetFaultJobById(params[0]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				HashMap<String, String> temphash = new HashMap<String, String>();
				if (result!=null && result.size() > 0) {
					temphash = result.get(0);
					country.setText(temphash.get("����"));
					Idtxt.setText(temphash.get("ID"));
					address.setText(temphash.get("���ϵ�ַ"));
					sender.setText(temphash.get("�ɵ���"));
					installedperson.setText(temphash.get("�ӵ���"));
					userphone=temphash.get("��ϵ��ʽ");
					String ttime=temphash.get("�ɵ�ʱ��");
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					try {
						pdtime=sdf.parse(ttime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				super.onPostExecute(result);
			}
		}.execute(ID);
	}

	/*
	 * ����������
	 */
	private void setSpinner() {
		// ����������ֵ
		FaultReasonAdapter=new ArrayAdapter<String>(ReceiptFaultJob.this,android.R.layout.simple_spinner_dropdown_item, faultreason);
		Spinner_faultreason.setAdapter(FaultReasonAdapter);
		Spinner_faultreason.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
		issuccessAdapter=new ArrayAdapter<String>(ReceiptFaultJob.this,android.R.layout.simple_spinner_dropdown_item, issuccess);
		Spinner_issuccess.setAdapter(issuccessAdapter);
		Spinner_issuccess.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ

		issuccessAdapter = new ArrayAdapter<String>(ReceiptFaultJob.this,android.R.layout.simple_spinner_dropdown_item, issuccess);
		Spinner_issuccess.setAdapter(issuccessAdapter);
		Spinner_issuccess.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
	}
	
	public String[] GetCallLog()
	{
		int sumcalltime=0;
		Cursor cursor = ReceiptFaultJob.this.getContentResolver().query(CallLog.Calls.CONTENT_URI,null, null, null, null);                                                                                                 
		if(cursor.moveToFirst()){                                                                                
			do{                                                                                                  
				CallLog calls =new CallLog();                                                                  
				//����                                                                                             
				String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));                           
				//��������                                                                                           
				String type;                                                                                     
				switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE)))) {                 
				case Calls.INCOMING_TYPE:                                                                        
					type = "����";                                                                                 
					break;                                                                                       
				case Calls.OUTGOING_TYPE:                                                                        
					type = "����";                                                                                 
					break;                                                                                       
				case Calls.MISSED_TYPE:                                                                          
					type = "δ��";                                                                                 
					break;                                                                                       
				default:                                                                                         
					type = "�Ҷ�";//Ӧ���ǹҶ�.�������ֻ������жϳ���                                                              
					break;                                                                                       
				}                                                                                                
				SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                              
				Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(Calls.DATE))));
				//����ʱ��                                                                                           
				String time = sfd.format(date);                                                                  
				//��ϵ�� 
				//String name = cursor.getString(cursor.getColumnIndex(Calls.CACHED_NAME));                 
				//ͨ��ʱ��,��λ:s                                                                                      
				String duration = cursor.getString(cursor.getColumnIndex(Calls.DURATION));   
				sumcalltime+=Integer.parseInt(duration);
				//Log.d("CallList", "type:"+type+";Num:"+number+";duration:"+duration);
				if(type.equals("����") && date.compareTo(pdtime)>0 && number.equals(userphone) && (sumcalltime==0 || (sumcalltime>0 && Integer.parseInt(duration)>5)))
				{
					String[] info=new String[2];
					info[0]=time;
					info[1]=duration;
					Log.d("ReceiptFaultJob", "Num:"+number+" time:"+time+" pdtime:"+sfd.format(pdtime)+" duration:"+duration);
					return info;
				}
			}while(cursor.moveToNext());                                                                                                                                                                    
		}
		Log.d("ReceiptFaultJob", "NoCallLog");
		return null; 
	}

}
