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
		actionBar.setTitle("���Ϲ�������");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.faultsinglequerryactivity);
		
		// ��ҳ���������
		Bundle bundle = this.getIntent().getExtras();
		// ����nameֵ
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
		
		//������벦��绰
		userphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("ȷ�ϲ������"+userphone.getText()+"?")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					// �����ȷ�ϡ���Ĳ���
				    	//ֱ�Ӳ������
						Uri uri = Uri.parse("tel:"+userphone.getText());   
						Intent intent = new Intent(Intent.ACTION_CALL, uri);
						startActivity(intent);
				    }
				})
				.setNeutralButton("�༭����", new DialogInterface.OnClickListener() {
		 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					// �����ȷ�ϡ���Ĳ���
				    	//ֱ�Ӳ������
						Uri uri = Uri.parse("tel:"+userphone.getText());   
						Intent intent = new Intent(Intent.ACTION_DIAL, uri);
						startActivity(intent);
				    }
				})
				.setNegativeButton("����", new DialogInterface.OnClickListener() {
		 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					// ��������ء���Ĳ���,���ﲻ����û���κβ���
				    }
				}).show();				
			}
			});
		
		if(power.equals("ά����Ա"))
		{
			cancel_bt.setText("�ӵ�");
//			delete_bt.setText("����");
		}
//		delete_bt.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(delete_bt.getText().equals("ɾ��")){	//��ά����ԱȨ��
//					new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("ȷ��ɾ����")
//					.setIcon(android.R.drawable.ic_dialog_info)
//					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// �����ȷ�ϡ���Ĳ���				    	
//					    	AccManagerOpt(Id, myapp.getUser(), "delete", "");		 
//					    }
//					})
//					.setNegativeButton("����", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// ��������ء���Ĳ���,���ﲻ����û���κβ���
//					    }
//					}).show();
//				}else{		//ά����ԱȨ��
//					new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("ȷ�ϲ�����")
//					.setIcon(android.R.drawable.ic_dialog_info)
//					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// �����ȷ�ϡ���Ĳ���
//					    	if(remark.getText().toString().equals(""))
//							{
//					    		Toast.makeText(FaultSinglequerryActivity.this, "�����벵��ԭ������µ������",Toast.LENGTH_SHORT).show();
//								remark.setEnabled(true);
//								return;
//							}else
//							{
//								
//								AccManagerOpt(Id, myapp.getUser(), "reject",remark.getText().toString());
//							}
//					    }
//					})
//					.setNegativeButton("����", new DialogInterface.OnClickListener() {
//			 
//					    @Override
//					    public void onClick(DialogInterface dialog, int which) {
//						// ��������ء���Ĳ���,���ﲻ����û���κβ���
//					    }
//					}).show();
//				}
//				
//			}
//		});
		cancel_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(cancel_bt.getText().equals("����")){		//��ά����ԱȨ��
					new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("ȷ�ϳ�����")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���								
					    	FaultManagerOpt(Id, myapp.getUser(), "cancel");		 
					    }
					})
					.setNegativeButton("����", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// ��������ء���Ĳ���,���ﲻ����û���κβ���
					    }
					}).show();
					}else{		//ά����ԱȨ��
						if(((Button) findViewById(R.id.btn_cancel)).getText().equals("�ص�"))
						{
							Intent intent;
					    	intent = new Intent(FaultSinglequerryActivity.this,ReceiptFaultJob.class);
					    	Bundle bundle = new Bundle();
							// ����name����Ϊtinyphp
							bundle.putString("Id", Id);
							intent.putExtras(bundle);
							startActivity(intent);
						}else{
						new AlertDialog.Builder(FaultSinglequerryActivity.this).setTitle("ȷ�Ͻӵ���")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// �����ȷ�ϡ���Ĳ���
						    	FaultManagerOpt(Id, myapp.getUser(), "accept");
				 
						    }
						})
						.setNegativeButton("����", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// ��������ء���Ĳ���,���ﲻ����û���κβ���
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
					useraddress.setText(temphash.get("���ϵ�ַ"));
					sender.setText(temphash.get("�ɵ���"));
					person.setText(temphash.get("�ӵ���"));
					issuccess.setText(temphash.get("״̬"));
					area.setText(temphash.get("Ƭ��"));
					cell.setText(temphash.get("С��"));
					faultreason.setText(temphash.get("����ԭ��"));
					usedtime.setText(temphash.get("��ʱ"));
					userphone.setText(temphash.get("��ϵ��ʽ"));
					pdtime.setText(temphash.get("�ɵ�ʱ��"));
					state.setText(temphash.get("��ǰ״̬"));
					wctime.setText(temphash.get("���ʱ��"));
					jdtime.setText(temphash.get("�ӵ�ʱ��"));
					isover.setText(temphash.get("�Ƿ����"));
					faulttype.setText(temphash.get("�������"));
					faultdescribe.setText(temphash.get("��������"));
					celladdress.setText(temphash.get("С����ַ"));
					inmode.setText(temphash.get("���뷽ʽ"));
					Button cancel_bt = (Button) findViewById(R.id.btn_cancel);
//					Button delete_bt = (Button) findViewById(R.id.btn_delete);

					if(power.equals("ά����Ա"))
					{
						if(temphash.get("��ǰ״̬").equals("�ѽӵ�") || temphash.get("��ǰ״̬").equals("Э������"))
						{
							cancel_bt.setText("�ص�");
						}else if(temphash.get("�Ƿ����").equals("��"))
						{
							cancel_bt.setVisibility(8);//�Ѿ������Ĺ������ذ�ť
//							delete_bt.setVisibility(8);
						}
					}else
					{
					
						if(!(temphash.get("��ǰ״̬").equals("���ύ") || temphash.get("��ǰ״̬").equals("�ѳ���")))//��ά����Ա�����·��ѽӵ����ѳ���״̬ʱ���޷������ť
						{
							cancel_bt.setVisibility(8);//�Ѿ������Ĺ������ذ�ť
//							delete_bt.setVisibility(8);
						}
					}
					if(temphash.get("�Ƿ����").equals("��"))//��������״̬ʱȡ����ťʧЧ��������ѳ���״̬��������ɾ������
					{
						cancel_bt.setVisibility(8);//�Ѿ������Ĺ������ذ�ť
//						delete_bt.setVisibility(8);
					}

				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}
}
