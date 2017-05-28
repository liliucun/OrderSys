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

public class AddZdActivity extends Activity {
	private static final String[] country = {"---请选择---", "榆阳区", "神木", "府谷", "定边", "靖边","横山", "绥德", "米脂", "佳县", "吴堡", "清涧", "子洲", "大柳塔"};
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
	private Spinner zdhd;
	private TextView hdlx;
	private TextView yxbbm;
	private TextView jx;
	private TextView money;
	private TextView yfhf;
	private TextView fhys;
	private TextView zdxf;
	private TextView kbys;
	private TextView cpyq;
	private TextView xjcj;
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
		setContentView(R.layout.addzd);
		myApp=(AppData)getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("终端派单");
		
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
		zdhd = (Spinner) findViewById(R.id.zdhd);
		hdlx = (TextView) findViewById(R.id.hdlx);
		yxbbm = (TextView) findViewById(R.id.yxbbm);
		jx = (TextView) findViewById(R.id.jx);
		money = (TextView) findViewById(R.id.money);
		yfhf = (TextView) findViewById(R.id.yfhf);
		fhys = (TextView) findViewById(R.id.fhys);
		zdxf = (TextView) findViewById(R.id.zdxf);
		kbys = (TextView) findViewById(R.id.kbys);
		cpyq = (TextView) findViewById(R.id.cpyq);
		xjcj = (TextView) findViewById(R.id.xjcj);
		sender = (TextView) findViewById(R.id.sender);
		remark = (EditText) findViewById(R.id.remark);
		
		sender.setText(myApp.getName()+":"+myApp.getPhone());
		btn_add = (Button) findViewById(R.id.btn_add);
		
		userphone.setSubmitButtonEnabled(true);
		userphone.setQueryHint("输入号码查询");
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
							Toast.makeText(AddZdActivity.this, "服务器无响应！",Toast.LENGTH_SHORT).show();
							return;
						}
						HashMap map=result.get(0);
						if(map==null || map.get("info")==null)
						{
							Toast.makeText(AddZdActivity.this, "查询无结果！",Toast.LENGTH_SHORT).show();
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
						SpinnerData sd=new SpinnerData("手动选择","");
						list.add(sd);
						ArrayAdapter<SpinnerData> adapter_blqd = new ArrayAdapter<SpinnerData>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,list);
						if(map.get("result").equals("all"))
						{
							SpinnerData t=new SpinnerData("常客渠道 |"+map.get("name").toString(),map.get("code").toString());
							SpinnerData t1=new SpinnerData("客户经理 |"+map.get("name1").toString(),map.get("code1").toString());
							adapter_blqd.insert(t, 0);
							adapter_blqd.insert(t1, 1);
						}
						else if(map.get("result").equals("ckqd"))
						{
							SpinnerData t=new SpinnerData("常客渠道 |"+map.get("name").toString(),map.get("code").toString());
							adapter_blqd.insert(t, 0);
						}
						else if(map.get("result").equals("khjl"))
						{
							SpinnerData t=new SpinnerData("客户经理 |"+map.get("name").toString(),map.get("code").toString());
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
		// 添加按钮，触发添加操作
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_add.setEnabled(false);

				if(!(userphone.equals("") || county.getSelectedItem()==null || county.getSelectedItem().toString().equals("") ||area.getSelectedItem()==null || area.getSelectedItem().toString().equals("") || receiver.getText().equals("") || qdbm.getText().equals("") || 
						hdlx.getText().equals("") || jx.getText().equals("") || money.getText().equals("") || yfhf.getText().equals("") || fhys.getText().equals("") || zdxf.getText().equals("") || kbys.getText().equals("") || cpyq.getText().equals("") || xjcj.getText().equals(""))){
					addZdJob();
				} else {
					Toast.makeText(AddZdActivity.this, "以上信息不能为空！",Toast.LENGTH_SHORT).show();
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
						if(selectedValue.substring(0,4).equals("常客渠道"))
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
										Toast.makeText(AddZdActivity.this, "该渠道下无有效客户经理！",Toast.LENGTH_SHORT).show();
										return;
									}
									HashMap map=result.get(0);
									String array[]=map.get("result").toString().split(":",-1);
									//setSpinnerItemSelectedByValue(county,array[2]);
									ArrayAdapter<String> ad1 = new ArrayAdapter<String>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[2]});
									county.setAdapter(ad1);
									ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[3]});
									area.setAdapter(adapterperson);
									receiver.setText(array[0] + ':' + array[1]);
									bgdz.setText(array[6]);
									qdbm.setText(array[4]);
									qdmc.setText(array[5]);
									super.onPostExecute(result);
								}
	
							}.execute(((SpinnerData)blqd.getSelectedItem()).getValue(),"chlcode");
						}
						else if(selectedValue.substring(0,4).equals("客户经理"))
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
										Toast.makeText(AddZdActivity.this, "该客户经理不是有效用户！",Toast.LENGTH_SHORT).show();
										return;
									}
									HashMap map=result.get(0);
									String array[]=map.get("result").toString().split(":",-1);
									//setSpinnerItemSelectedByValue(county,array[2]);
									ArrayAdapter<String> ad1 = new ArrayAdapter<String>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[2]});
									county.setAdapter(ad1);
									ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,new String[]{array[3]});
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
		
		zdhd.setSelection(0,true);
		zdhd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				if(position==0)
				{
					return;
				}
				// TODO Auto-generated method stub
				SelectZdhd();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});	

		} 
	 /*
	 * 根据值, 设置spinner默认选中:
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		if(value.equals("榆阳"))
		{
			value="榆阳区";
		}
		SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
	    int k= apsAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(apsAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// 默认选中项
				break;
			}
		}
	} 

	// /绑定下拉框
	public void bindSpinner() {
		// 将可选内容与ArrayAdapter连接起来
		adapter_county = new ArrayAdapter<String>(this,R.layout.spinner, country);
		// 设置下拉列表的风格
		adapter_county.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		county.setAdapter(adapter_county);
		county.setSelection(0, true);
		
		bindZdhd();
	}
	
	//加载片区下拉框
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
				person[0] = "---请选择---";
				int i = 1;
				for (HashMap<String, String> m : result) {
					person[i] = m.get("text");
					i++;
				}
				ArrayAdapter<String> adapterperson = new ArrayAdapter<String>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,person);
				area.setAdapter(adapterperson);
				super.onPostExecute(result);
			}

		}.execute(Country);

	}
	
	//加载片区下拉框
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
						SpinnerData t=new SpinnerData("---请选择---","");
						list.add(t);
					} else {
						SpinnerData t=new SpinnerData(m.get("text"),m.get("id"));
						list.add(t);
					}
					i++;
				}
				
				ArrayAdapter<SpinnerData> adapterperson = new ArrayAdapter<SpinnerData>(AddZdActivity.this, android.R.layout.simple_spinner_dropdown_item,list);
				zdhd.setAdapter(adapterperson);
				super.onPostExecute(result);
			}

		}.execute();

	}
	
	//加载片区下拉框
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
	
	//加载片区下拉框
	public void SelectZdhd() {
		// List<HashMap<String, String>> listall=new ArrayList<HashMap<String,
		// String>>();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				return dbUtil.SelectZdhd(((SpinnerData)zdhd.getSelectedItem()).getValue());
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				HashMap<String,String> map=result.get(0);
				hdlx.setText(map.get("活动类型"));
				yxbbm.setText(map.get("营销包编码"));
				jx.setText(map.get("机型"));
				money.setText(map.get("用户交款"));
				yfhf.setText(map.get("月返话费"));
				zdxf.setText(map.get("最低消费"));
				kbys.setText(map.get("捆绑月数"));
				cpyq.setText(map.get("产品要求"));
				xjcj.setText(map.get("现结酬金"));
				fhys.setText(map.get("返还月数"));
				super.onPostExecute(result);
			}

		}.execute();

	}

	// 添加到数据库
	public void addZdJob() {
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {
			Toast toast;//显示提交状态
			@Override
			protected void onPreExecute() {
				if(!(userphone.equals("") || county.getSelectedItem().toString().equals("") || area.getSelectedItem().toString().equals("") || receiver.getText().equals("") || qdbm.getText().equals("") || 
						hdlx.getText().equals("") || jx.getText().equals("") || money.getText().equals("") || yfhf.getText().equals("") || fhys.getText().equals("") || zdxf.getText().equals("") || kbys.getText().equals("") || cpyq.getText().equals("") || xjcj.getText().equals(""))){
					toast=Toast.makeText(AddZdActivity.this, "正在提交,请勿重复提交", Toast.LENGTH_SHORT);
					toast.show();
				}else {
					Toast.makeText(AddZdActivity.this, "以上信息不能为空！",Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
				super.onPreExecute();
			}

			@Override
			protected List<HashMap<String, String>> doInBackground(String... params) {				
				return dbUtil.addZdJob(params[0], params[1], params[2], params[3],	params[4], params[5], params[6], params[7], params[8], params[9],params[10],params[11],params[12],params[13],params[14],params[15],params[16],params[17],params[18],params[19],params[20],params[21],params[22]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				if (result.get(0).get("result").equals("True")) 
				{
					toast.cancel();
					Toast.makeText(AddZdActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					toast.cancel();
					Toast.makeText(AddZdActivity.this, result.get(0).get("result"), Toast.LENGTH_SHORT).show();
					btn_add.setEnabled(true);
				}
				super.onPostExecute(result);
			}

		}.execute( "0", county.getSelectedItem().toString(), area.getSelectedItem().toString(), hdlx.getText().toString(), yxbbm.getText().toString(), jx.getText().toString(), money.getText().toString(), yfhf.getText().toString(), fhys.getText().toString(), zdxf.getText().toString(), kbys.getText().toString(), cpyq.getText().toString(), xjcj.getText().toString(), sender.getText().toString(), remark.getText().toString(), receiver.getText().toString(), qdbm.getText().toString(), qdmc.getText().toString(), username.getText().toString(), userphone.getQuery().toString(), xj.getText().toString(), ywq.getText().toString(), jtdw.getText().toString());

	}

	/**
	 * 直接调用短信接口发短信
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public void sendSMS(String phoneNumber, String message) {
		// 获取短信管理器
		try{
			android.telephony.SmsManager smsManager = android.telephony.SmsManager
					.getDefault();
			// 拆分短信内容（手机短信长度限制）
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
		}catch(Exception e)
		{
			System.out.println("发送短信失败！");
		}
	}

}
