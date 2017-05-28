package com.sncrc.ordersys;

import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiptJob extends Activity {
	private String[] issuccess = { "是", "否" };
	private String[] reason_emp={"无"};
	private String[] reason_emp1={"无"};
	private String[] linetype = { "有综合布线", "无综合布线", "FTTH装机", "农村装机" };
//	private String[][] mainReason = { { "无" },// 是
//			{ "覆盖原因", "资源原因", "无法安装","办理错误","客户原因","其他" } };// 否
//	private String[][][] reason = {
//			{ { "无" } },
//			{ { "客户居住地距离分纤箱过远", "客户所居住楼宇无覆盖", "小区整体未覆盖" }, { "录入资管资料和实际不符", "录入资管资料表述不清", "设备不在线","端口满，需线路扩容","端口满，需设备扩容" },
//			{ "客户家暗线不通", "入户线无法穿线", "客户不允许打孔等", "卡单原因"},{"派单错误，客户地址有误","B模式与H模式混淆","客户未报装","资费办理错误"},{"另约时间","客户无法联系","客户取消安装"},{"有纠纷","号码错误","重复工单","未挂侧","光路故障","非新增用户"} } };

	private String[][] mainReason = { { "无" },// 是
			{ "用户原因", "前台原因", "网络原因","工程原因","其他原因" } };// 否
	private String[][][] reason = {
			{ { "无" } },
			{ { "原因1:用户明确表示不安装", "原因2:用户短期内不安装", "原因3:用户长期无法联系或拒接电话","原因4:用户/邻居/物业不同意走明线、飞线、穿墙等协调问题","原因5:客户需求变更，如更改资费套餐或用户改装机地址等","原因6:用户信息箱无电源，且用户不同意通过POE供电","原因7:用户不愿买交换机（一般为校园宽带）" },
				{ "原因8:前台选择地址与实际严重不符，如跨小区、跨OLT等", "原因9:前台同小区内选错地址，但用户实际安装地址未覆盖", "原因10:用户不知情开通","原因11:前台重复派单、派单错误（含接入类型错误）","原因12:前台未派终端设备工单或终端设备派发错误","原因13:前台业务办理错误（如少办业务、套餐错误或电视牌照方错误等）","原因14:前台营销人员宣传与实际严重不符","原因15:前台提供的客户联系人和联系方式错误"},
			{ "原因16:无法入户（入户管道已被其它运营商占用等）", "原因17:设备端口或箱体资源满，但扩容无法实施", "原因18:装机地址在资管系统中，但实际未覆盖，不能安装", "原因19:移动网速无法满足用户玩游戏等要求","原因20:不能访问用户需求的网站（封堵网站）","原因21:用户需要固定的IP地址","原因22:光功率不达标"},
			{"原因23:客户居住地距离分纤箱过远","原因24:部分楼宇覆盖，但客户所居住楼宇无覆盖","原因25:整体未覆盖","原因26:开发商投资新建的小区户线不通且无法穿线","原因27:老旧小区共建共享线路不通且无法穿线","原因28:工程未完工"},
			{"原因29:物业纠纷","原因30:第三方区域","原因31:自建测试单","原因32:挂单时间长导致客户取消装机","原因33:其他"}} };	
	private String Id,mode;
	static int issuccessPosition = 0;
	ArrayAdapter<String> issuccessAdapter = null;
	ArrayAdapter<String> mainReasonAdapter = null;
	ArrayAdapter<String> reasonAdapter = null;
	ArrayAdapter<String> linetypeAdapter = null;
	private Spinner Spinner_issuccess;
	private Spinner Spinner_mainreason;
	private Spinner Spinner_reason;
	private Spinner Spinner_linetype;

	private TextView country;
	private TextView Idtxt;
	private TextView address;
	private TextView sender;
	private TextView installedperson;
	private TextView account;
	private Button receipt_btn;
	private AppData myapp;
	private DBUtil dbUtil = new DBUtil();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiptjob);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("装机回单");
		myapp=(AppData)getApplication();
		// 新页面接收数据
		Bundle bundle = this.getIntent().getExtras();
		// 接收name值
		Id = bundle.getString("Id");
		mode = bundle.getString("mode");
		country = (TextView) findViewById(R.id.country);
		Idtxt = (TextView) findViewById(R.id.Idtxt);
		address = (TextView) findViewById(R.id.address);
		sender = (TextView) findViewById(R.id.sender);
		account = (TextView) findViewById(R.id.UserAccount);
		installedperson = (TextView) findViewById(R.id.installedperson);
		receipt_btn = (Button) findViewById(R.id.receipt_btn);
		receipt_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String result = Spinner_issuccess.getSelectedItem().toString();
				String MainReason = Spinner_mainreason.getSelectedItem().toString();
				String Reason = Spinner_reason.getSelectedItem().toString();
				String WiringMode = Spinner_linetype.getSelectedItem().toString();
				EditText SNEdit = (EditText) findViewById(R.id.SNEdit);
				String SN = SNEdit.getText().toString();
				EditText remarkEditText=(EditText) findViewById(R.id.RemarkEdit);
				String remark=remarkEditText.getText().toString();
				String useraccount=((EditText)findViewById(R.id.UserAccount)).getText().toString();
				if(result.equals("是") && useraccount.equals(""))
				{
					Toast.makeText(ReceiptJob.this, "请输入用户账号", Toast.LENGTH_LONG).show();
				}
				else
				{
					ReplyJob(Id, result, MainReason, Reason, SN, WiringMode,remark,useraccount,myapp.getUser(),myapp.GetNeedPhoto());
				}
			}
		});

		loadInfo(Id);
		setSpinner();

	}

	public void ReplyJob(String ID,final String issuccess, String MainReason,String Reason, String SN, String WiringMode,String remark,String useraccount,String userid,String needphoto) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.ReplayJob(params[0], params[1], params[2],
						params[3], params[4], params[5],params[6],params[7],params[8],params[9]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(ReceiptJob.this, result, Toast.LENGTH_LONG).show();
				if (result.equals("Success")) {
					setResult(RESULT_OK);
					//向派单人发送反馈短信
					String[] sendnum=sender.getText().toString().split(":");
					if(issuccess.equals("是"))
					{
						sendSMS(sendnum[1],"[装机回单提醒]你编号为"+Id+"的工单已成功装机，进入APP查看详细信息。");
					}
					else
					{
						sendSMS(sendnum[1],"[装机回单提醒]你编号为"+Id+"的工单已申请弃单，进入APP查看详细信息。");
					}
					finish();
				}else if(result.equals("请上传分光箱数据"))
				{
					Bundle b=new Bundle();
            		b.putString("Id", Id);
            		b.putString("Msg", "请上传分光箱数据！");
            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
            		intent.putExtras(b);
					startActivity(intent);
				}
				super.onPostExecute(result);
			}
		}.execute(ID, issuccess, MainReason, Reason, SN, WiringMode,remark,useraccount,userid,needphoto);

	}

	// 加载订单信息
	public void loadInfo(String ID) {
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				// TODO Auto-generated method stub
				return dbUtil.GetJobById(params[0]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				HashMap<String, String> temphash = new HashMap<String, String>();
				if (result!=null && result.size() > 0) {
					temphash = result.get(0);
					country.setText(temphash.get("归属"));
					Idtxt.setText(temphash.get("ID"));
					address.setText(temphash.get("装机地址"));
					sender.setText(temphash.get("派单人"));
					installedperson.setText(temphash.get("装机人"));
					account.setText(temphash.get("用户账号"));
				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}

	/*
	 * 设置下拉框
	 */
	private void setSpinner() {

		Spinner_issuccess = (Spinner) findViewById(R.id.Spinner_issuccess);
		Spinner_mainreason = (Spinner) findViewById(R.id.Spinner_mainreason);
		Spinner_reason = (Spinner) findViewById(R.id.Spinner_reason);
		Spinner_linetype = (Spinner) findViewById(R.id.Spinner_linetype);
		// 绑定适配器和值
		mainReasonAdapter=new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, reason_emp);
		Spinner_mainreason.setAdapter(mainReasonAdapter);
		Spinner_mainreason.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
		reasonAdapter=new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, reason_emp1);
		Spinner_reason.setAdapter(reasonAdapter);
		Spinner_reason.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
		linetypeAdapter = new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, linetype);
		Spinner_linetype.setAdapter(linetypeAdapter);
		Spinner_linetype.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值

		issuccessAdapter = new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, issuccess);
		Spinner_issuccess.setAdapter(issuccessAdapter);
		if(mode.equals("0"))
		{
			Spinner_issuccess.setEnabled(true);
			Spinner_issuccess.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
		}else
		{
			Spinner_issuccess.setSelection(1); // 设置默认选中项，此处为默认选中第1个值
			Spinner_issuccess.setEnabled(false);
		}

		// 省级下拉框监听
		Spinner_issuccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					// 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// position为当前省级选中的值的序号

						// 将地级适配器的值改变为city[position]中的值
						mainReasonAdapter = new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item,mainReason[position]);
						// 设置二级下拉列表的选项内容适配器
						Spinner_mainreason.setAdapter(mainReasonAdapter);
						issuccessPosition = position; // 记录当前省级序号，留给下面修改县级适配器时用
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		// 地级下拉监听
		Spinner_mainreason
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						reasonAdapter = new ArrayAdapter<String>(
								ReceiptJob.this,
								android.R.layout.simple_spinner_dropdown_item,
								reason[issuccessPosition][position]);
						Spinner_reason.setAdapter(reasonAdapter);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
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
			android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
			Log.d("sendSMS",phoneNumber+":"+message);
			// 拆分短信内容（手机短信长度限制）
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
		}catch(Exception e)
		{
			Log.d("派单短信","发送短信失败！");
		}
	}

}
