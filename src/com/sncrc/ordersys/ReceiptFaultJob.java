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
	private String[] issuccess = { "恢复","协调处理中" };//{ "恢复", "未恢复","协调处理中" }
	private String[] faultreason={"无","端口关闭","室内外引入线故障","TV+安装","路由器故障","用户端问题","光路故障","账号密码问题","网速慢","设备故障","设备掉电","纠纷","派错单"};
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
		actionBar.setTitle("故障回单");
		myapp=(AppData)getApplication();
		// 新页面接收数据
		Bundle bundle = this.getIntent().getExtras();
		// 接收name值
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

	// 加载订单信息
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
					country.setText(temphash.get("归属"));
					Idtxt.setText(temphash.get("ID"));
					address.setText(temphash.get("故障地址"));
					sender.setText(temphash.get("派单人"));
					installedperson.setText(temphash.get("接单人"));
					userphone=temphash.get("联系方式");
					String ttime=temphash.get("派单时间");
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
	 * 设置下拉框
	 */
	private void setSpinner() {
		// 绑定适配器和值
		FaultReasonAdapter=new ArrayAdapter<String>(ReceiptFaultJob.this,android.R.layout.simple_spinner_dropdown_item, faultreason);
		Spinner_faultreason.setAdapter(FaultReasonAdapter);
		Spinner_faultreason.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
		issuccessAdapter=new ArrayAdapter<String>(ReceiptFaultJob.this,android.R.layout.simple_spinner_dropdown_item, issuccess);
		Spinner_issuccess.setAdapter(issuccessAdapter);
		Spinner_issuccess.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值

		issuccessAdapter = new ArrayAdapter<String>(ReceiptFaultJob.this,android.R.layout.simple_spinner_dropdown_item, issuccess);
		Spinner_issuccess.setAdapter(issuccessAdapter);
		Spinner_issuccess.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
	}
	
	public String[] GetCallLog()
	{
		int sumcalltime=0;
		Cursor cursor = ReceiptFaultJob.this.getContentResolver().query(CallLog.Calls.CONTENT_URI,null, null, null, null);                                                                                                 
		if(cursor.moveToFirst()){                                                                                
			do{                                                                                                  
				CallLog calls =new CallLog();                                                                  
				//号码                                                                                             
				String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));                           
				//呼叫类型                                                                                           
				String type;                                                                                     
				switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE)))) {                 
				case Calls.INCOMING_TYPE:                                                                        
					type = "呼入";                                                                                 
					break;                                                                                       
				case Calls.OUTGOING_TYPE:                                                                        
					type = "呼出";                                                                                 
					break;                                                                                       
				case Calls.MISSED_TYPE:                                                                          
					type = "未接";                                                                                 
					break;                                                                                       
				default:                                                                                         
					type = "挂断";//应该是挂断.根据我手机类型判断出的                                                              
					break;                                                                                       
				}                                                                                                
				SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                              
				Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(Calls.DATE))));
				//呼叫时间                                                                                           
				String time = sfd.format(date);                                                                  
				//联系人 
				//String name = cursor.getString(cursor.getColumnIndex(Calls.CACHED_NAME));                 
				//通话时间,单位:s                                                                                      
				String duration = cursor.getString(cursor.getColumnIndex(Calls.DURATION));   
				sumcalltime+=Integer.parseInt(duration);
				//Log.d("CallList", "type:"+type+";Num:"+number+";duration:"+duration);
				if(type.equals("呼出") && date.compareTo(pdtime)>0 && number.equals(userphone) && (sumcalltime==0 || (sumcalltime>0 && Integer.parseInt(duration)>5)))
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
