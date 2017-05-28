package com.sncrc.ordersys;

import java.util.HashMap;
import java.util.List;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FaultSinglequerryActivity extends Activity {

	private String Id;
	private TextView country;
	private TextView issuccess;
	private TextView area;
	private TextView cell;
	private TextView celladdress;
	private TextView person;
	private TextView sender;
	private TextView userphone;
	private TextView useraddress;
	private TextView faulttype;
	private TextView faultdescribe;
	private TextView state;
	private TextView jdtime;
	private TextView pdtime;
	private TextView wctime;
	private TextView usedtime;
	private TextView isover;
	private TextView inmode;
	private TextView faultreason;
	private AppData myapp;
	private String power;
	private Button cancel_bt;
//	private Button delete_bt;

	private DBUtil dbUtil = new DBUtil();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("故障工单详情");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.faultsinglequerryactivity);
		
		// 新页面接收数据
		Bundle bundle = this.getIntent().getExtras();
		// 接收name值
		Id = bundle.getString("Id");
		country = (TextView) findViewById(R.id.country);
		issuccess = (TextView) findViewById(R.id.issuccess);
		area = (TextView) findViewById(R.id.area);
		cell = (TextView) findViewById(R.id.cell);
		person = (TextView) findViewById(R.id.person);
		faultreason = (TextView) findViewById(R.id.faultreason);
		sender = (TextView) findViewById(R.id.sender);
		userphone = (TextView) findViewById(R.id.userphone);
		useraddress = (TextView) findViewById(R.id.useraddress);
		pdtime = (TextView) findViewById(R.id.pdtime);
		wctime = (TextView) findViewById(R.id.wctime);
		state = (TextView) findViewById(R.id.state);
		jdtime = (TextView) findViewById(R.id.jdtime);
		usedtime = (TextView) findViewById(R.id.usedtime);
		isover = (TextView) findViewById(R.id.isover);
		faulttype = (TextView) findViewById(R.id.faulttype);
		inmode = (TextView) findViewById(R.id.inmode);
		celladdress = (TextView) findViewById(R.id.celladdress);
		faultdescribe = (TextView) findViewById(R.id.faultdescribe);
				
		cancel_bt = (Button) findViewById(R.id.btn_cancel);
//		delete_bt = (Button) findViewById(R.id.btn_delete);
		
		//点击号码拨打电话
		userphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("确认拨打号码"+userphone.getText()+"?")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					// 点击“确认”后的操作
				    	//直接拨打号码
						Uri uri = Uri.parse("tel:"+userphone.getText());   
						Intent intent = new Intent(Intent.ACTION_CALL, uri);
						startActivity(intent);
				    }
				})
				.setNeutralButton("编辑号码", new DialogInterface.OnClickListener() {
		 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					// 点击“确认”后的操作
				    	//直接拨打号码
						Uri uri = Uri.parse("tel:"+userphone.getText());   
						Intent intent = new Intent(Intent.ACTION_DIAL, uri);
						startActivity(intent);
				    }
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
		 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					// 点击“返回”后的操作,这里不设置没有任何操作
				    }
				}).show();				
			}
			});
		
		if(power.equals("维护人员"))
		{
			cancel_bt.setText("接单");
//			delete_bt.setText("驳回");
		}
//		delete_bt.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(delete_bt.getText().equals("删除")){	//非维护人员权限
//					new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("确认删除吗？")
//					.setIcon(android.R.drawable.ic_dialog_info)
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// 点击“确认”后的操作				    	
//					    	AccManagerOpt(Id, myapp.getUser(), "delete", "");		 
//					    }
//					})
//					.setNegativeButton("返回", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// 点击“返回”后的操作,这里不设置没有任何操作
//					    }
//					}).show();
//				}else{		//维护人员权限
//					new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("确认驳回吗？")
//					.setIcon(android.R.drawable.ic_dialog_info)
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// 点击“确认”后的操作
//					    	if(remark.getText().toString().equals(""))
//							{
//					    		Toast.makeText(FaultSinglequerryActivity.this, "请输入驳回原因后重新点击驳回",Toast.LENGTH_SHORT).show();
//								remark.setEnabled(true);
//								return;
//							}else
//							{
//								
//								AccManagerOpt(Id, myapp.getUser(), "reject",remark.getText().toString());
//							}
//					    }
//					})
//					.setNegativeButton("返回", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// 点击“返回”后的操作,这里不设置没有任何操作
//					    }
//					}).show();
//				}
//				
//			}
//		});
		cancel_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(cancel_bt.getText().equals("撤销")){		//非维护人员权限
					new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("确认撤销吗？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作								
					    	FaultManagerOpt(Id, myapp.getUser(), "cancel");		 
					    }
					})
					.setNegativeButton("返回", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					    }
					}).show();
					}else{		//维护人员权限
						if(((Button) findViewById(R.id.btn_cancel)).getText().equals("回单"))
						{
							Intent intent;
					    	intent = new Intent(FaultSinglequerryActivity.this,ReceiptFaultJob.class);
					    	Bundle bundle = new Bundle();
							// 传递name参数为tinyphp
							bundle.putString("Id", Id);
							intent.putExtras(bundle);
							startActivity(intent);
						}else{
						new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("确认接单吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
						    	FaultManagerOpt(Id, myapp.getUser(), "accept");
				 
						    }
						})
						.setNegativeButton("返回", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“返回”后的操作,这里不设置没有任何操作
						    }
						}).show();
						}
					}
				
			}
		});

		loadInfo(Id);

	}

	public void FaultManagerOpt(String ID, String user, String type) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.FaultManagerOpt(params[0], params[1], params[2]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(FaultSinglequerryActivity.this, result, Toast.LENGTH_LONG)
						.show();
				if (result.equals("Success")) {

					finish();
				}
				super.onPostExecute(result);
			}
		}.execute( ID,  user,  type);

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
					useraddress.setText(temphash.get("故障地址"));
					sender.setText(temphash.get("派单人"));
					person.setText(temphash.get("接单人"));
					issuccess.setText(temphash.get("状态"));
					area.setText(temphash.get("片区"));
					cell.setText(temphash.get("小区"));
					faultreason.setText(temphash.get("故障原因"));
					usedtime.setText(temphash.get("耗时"));
					userphone.setText(temphash.get("联系方式"));
					pdtime.setText(temphash.get("派单时间"));
					state.setText(temphash.get("当前状态"));
					wctime.setText(temphash.get("完成时间"));
					jdtime.setText(temphash.get("接单时间"));
					isover.setText(temphash.get("是否结束"));
					faulttype.setText(temphash.get("所属类别"));
					faultdescribe.setText(temphash.get("故障类型"));
					celladdress.setText(temphash.get("小区地址"));
					inmode.setText(temphash.get("接入方式"));
					Button cancel_bt = (Button) findViewById(R.id.btn_cancel);
//					Button delete_bt = (Button) findViewById(R.id.btn_delete);

					if(power.equals("维护人员"))
					{
						if(temphash.get("当前状态").equals("已接单") || temphash.get("当前状态").equals("协调处理"))
						{
							cancel_bt.setText("回单");
						}else if(temphash.get("是否结束").equals("是"))
						{
							cancel_bt.setVisibility(8);//已经结束的工单隐藏按钮
//							delete_bt.setVisibility(8);
						}
					}else
					{
					
						if(!(temphash.get("当前状态").equals("已提交") || temphash.get("当前状态").equals("已撤销")))//非维护人员界面下非已接单或已撤销状态时，无法点击按钮
						{
							cancel_bt.setVisibility(8);//已经结束的工单隐藏按钮
//							delete_bt.setVisibility(8);
						}
					}
					if(temphash.get("是否结束").equals("是"))//工单结束状态时取消按钮失效，如果是已撤销状态，则允许删除工单
					{
						cancel_bt.setVisibility(8);//已经结束的工单隐藏按钮
//						delete_bt.setVisibility(8);
					}

				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}
}
