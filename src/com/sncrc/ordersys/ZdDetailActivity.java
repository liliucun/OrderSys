package com.sncrc.ordersys;

import java.lang.reflect.Field;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ZdDetailActivity extends Activity {

	private String Id;
	private TextView country;
	private TextView hdlx;
	private TextView area;
	private TextView packageid;
	private TextView phonetype;
	private TextView money;
	private TextView yfhf;
	private TextView fhys;
	private TextView zdxf;
	private TextView kbys;
	private TextView cpyq;
	private TextView xjcj;
	private TextView sender;
	private TextView sendtime;
	private TextView pdremark;
	private TextView receiver;
	private TextView receivetime;
	private TextView userphone;
	private TextView username;
	
	private TextView xj;
	private TextView ywq;
	private TextView jtdw;
	private TextView sfjs;
	private TextView cgbl;
	private TextView sbyy;
	private TextView wcsj;
	private TextView usedtime;
	private TextView state;
	private TextView remark;
	private AppData myapp;
	private String power;
	private Button cancel_bt;
//	private Button delete_bt;

	private DBUtil dbUtil = new DBUtil();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("终端工单详情");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.zddetial);
		
		// 新页面接收数据
		Bundle bundle = this.getIntent().getExtras();
		// 接收name值
		Id = bundle.getString("Id");
		country = (TextView) findViewById(R.id.country);
		area = (TextView) findViewById(R.id.area);
		hdlx = (TextView) findViewById(R.id.hdlx);
		packageid = (TextView) findViewById(R.id.packageid);
		phonetype = (TextView) findViewById(R.id.phonetype);
		money = (TextView) findViewById(R.id.money);
		yfhf = (TextView) findViewById(R.id.yfhf);
		fhys = (TextView) findViewById(R.id.fhys);
		zdxf = (TextView) findViewById(R.id.zdxf);
		kbys = (TextView) findViewById(R.id.kbys);
		cpyq = (TextView) findViewById(R.id.cpyq);
		xjcj = (TextView) findViewById(R.id.xjcj);
		sender = (TextView) findViewById(R.id.sender);
		sendtime = (TextView) findViewById(R.id.sendtime);
		pdremark = (TextView) findViewById(R.id.pdremark);
		receiver = (TextView) findViewById(R.id.receiver);
		receivetime = (TextView) findViewById(R.id.receivetime);
		userphone = (TextView) findViewById(R.id.userphone);
		username = (TextView) findViewById(R.id.username);
		xj = (TextView) findViewById(R.id.xj);
		ywq = (TextView) findViewById(R.id.ywq);
		xjcj = (TextView) findViewById(R.id.xjcj);
		jtdw = (TextView) findViewById(R.id.jtdw);
		sfjs = (TextView) findViewById(R.id.sfjs);
		cgbl = (TextView) findViewById(R.id.cgbl);
		sbyy = (TextView) findViewById(R.id.sbyy);
		wcsj = (TextView) findViewById(R.id.wcsj);
		usedtime = (TextView) findViewById(R.id.usedtime);
		state = (TextView) findViewById(R.id.state);
		remark = (TextView) findViewById(R.id.remark);
				
		cancel_bt = (Button) findViewById(R.id.btn_cancel);
//		delete_bt = (Button) findViewById(R.id.btn_delete);
		
		//点击号码拨打电话
		userphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(ZdDetailActivity.this).setTitle("确认拨打号码"+userphone.getText()+"?")
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
		
		cancel_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					if(((Button) findViewById(R.id.btn_cancel)).getText().equals("回单"))
					{
						LinearLayout view = new LinearLayout(ZdDetailActivity.this);
						view.setOrientation(LinearLayout.VERTICAL);
						final Spinner spi_cgbl=new Spinner(ZdDetailActivity.this);
						final Spinner spi_sbyy=new Spinner(ZdDetailActivity.this);
						ArrayAdapter<String> cgblAdapter=new ArrayAdapter<String>(ZdDetailActivity.this,android.R.layout.simple_spinner_dropdown_item,new String[] {"是","否"});
						spi_cgbl.setAdapter(cgblAdapter);
						spi_cgbl.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
						
						ArrayAdapter<String> sbyyAdapter=new ArrayAdapter<String>(ZdDetailActivity.this,android.R.layout.simple_spinner_dropdown_item,new String[] {"无","用户不想办理","用户未收到外呼"});
						spi_sbyy.setAdapter(sbyyAdapter);
						spi_sbyy.setSelection(0, true); // 设置默认选中项，此处为默认选中第0个值
						final TextView tv1=new TextView(ZdDetailActivity.this);
						final TextView tv2=new TextView(ZdDetailActivity.this);
						final TextView tv3=new TextView(ZdDetailActivity.this);
						final EditText et_remark=new EditText(ZdDetailActivity.this);
						et_remark.setHint("备注");
						tv1.setText("是否成功办理");
						tv2.setText("办理失败原因");
						tv3.setText("备注");
						view.addView(tv1);
						view.addView(spi_cgbl);
						view.addView(tv2);
						view.addView(spi_sbyy);
						view.addView(tv3);
						view.addView(et_remark);
						
						new AlertDialog.Builder(ZdDetailActivity.this).setTitle("终端回单")
						.setIcon(android.R.drawable.ic_dialog_info)
						//.setMessage("将新的信息输入对应输入框,提交后将会覆盖原有信息。")
						.setView(view)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
						    	if(spi_cgbl.getSelectedItem().toString().equals("是") && !spi_sbyy.getSelectedItem().toString().equals("无"))
								{
						    		Toast.makeText(ZdDetailActivity.this, "成功办理，失败原因请选择无",Toast.LENGTH_SHORT).show();
						    		try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   将mShowing变量设为false，表示对话框已关闭 
						    		    field.set(dialog, false );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}
						    	else if(spi_cgbl.getSelectedItem().toString().equals("否") && spi_sbyy.getSelectedItem().toString().equals("无"))
								{
						    		Toast.makeText(ZdDetailActivity.this, "办理失败，请选择失败原因",Toast.LENGTH_SHORT).show();
						    		try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   将mShowing变量设为false，表示对话框已关闭 
						    		    field.set(dialog, false );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}
						    	else
								{
									
						    		ReplayZdJob(Id,spi_cgbl.getSelectedItem().toString(),spi_sbyy.getSelectedItem().toString(),et_remark.getText().toString(),myapp.getUser());
								}			 
						    }
						})
						.setNegativeButton("返回", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“返回”后的操作,这里不设置没有任何操作
						    }
						}).show();
					}
					else{
						new AlertDialog.Builder(ZdDetailActivity.this).setTitle("确认接单吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
						    	ZdListOpt(Id, myapp.getUser(),myapp.getName(), "accept");
				 
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
		});

		loadInfo(Id);

	}
	
	public void ReplayZdJob(String ID, String result, String Reason,String remark,String userid) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.ReplayZdJob(params[0], params[1], params[2],params[3],params[4]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(ZdDetailActivity.this, result, Toast.LENGTH_LONG)
						.show();
				if (result.equals("Success")) {

					finish();
				}
				super.onPostExecute(result);
			}
		}.execute(ID, result, Reason,remark,userid);

	}
	
	public void ZdListOpt(String ID, String user,String name, String type) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.ZdListOpt(params[0], params[1], params[2],params[3]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(ZdDetailActivity.this, result, Toast.LENGTH_LONG)
						.show();
				if (result.equals("Success")) {

					finish();
				}
				super.onPostExecute(result);
			}
		}.execute( ID,  user, name, type);

	}

	// 加载订单信息
	public void loadInfo(String ID) {
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				// TODO Auto-generated method stub
				return dbUtil.GetZdJobById(params[0]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				HashMap<String, String> temphash = new HashMap<String, String>();
				if (result!=null && result.size() > 0) {
					temphash = result.get(0);
					country.setText(temphash.get("县区"));
					area.setText(temphash.get("片区"));
					hdlx.setText(temphash.get("活动类型"));
					packageid.setText(temphash.get("营销包编码"));
					phonetype.setText(temphash.get("机型"));
					money.setText(temphash.get("用户交款"));
					yfhf.setText(temphash.get("月返话费"));
					fhys.setText(temphash.get("返还月数"));
					zdxf.setText(temphash.get("最低消费"));
					kbys.setText(temphash.get("捆绑月数"));
					cpyq.setText(temphash.get("产品要求"));
					xjcj.setText(temphash.get("现结酬金"));
					sender.setText(temphash.get("派单人"));
					sendtime.setText(temphash.get("派单时间"));
					pdremark.setText(temphash.get("派单备注"));
					receiver.setText(temphash.get("接单人"));
					receivetime.setText(temphash.get("接单时间"));
					userphone.setText(temphash.get("客户电话"));
					username.setText(temphash.get("客户姓名"));
					xj.setText(temphash.get("星级"));
					ywq.setText(temphash.get("归属业务区"));
					jtdw.setText(temphash.get("归属集团单位"));
					sfjs.setText(temphash.get("是否结束"));
					cgbl.setText(temphash.get("是否成功办理"));
					sbyy.setText(temphash.get("办理失败原因"));
					wcsj.setText(temphash.get("完成确认时间"));
					usedtime.setText(temphash.get("耗时"));
					state.setText(temphash.get("当前状态"));
					remark.setText(temphash.get("备注"));
					Button cancel_bt = (Button) findViewById(R.id.btn_cancel);


					if(temphash.get("是否结束").equals("是"))//工单结束状态时取消按钮失效，如果是已撤销状态，则允许删除工单
					{
						cancel_bt.setVisibility(8);//已经结束的工单隐藏按钮
					}
					else
					{
						if(temphash.get("当前状态").equals("已接单"))
						{
							cancel_bt.setText("回单");
						}
					}

				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}
}
