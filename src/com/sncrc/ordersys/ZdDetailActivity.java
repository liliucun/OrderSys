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
		actionBar.setTitle("�ն˹�������");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.zddetial);
		
		// ��ҳ���������
		Bundle bundle = this.getIntent().getExtras();
		// ����nameֵ
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
		
		//������벦��绰
		userphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(ZdDetailActivity.this).setTitle("ȷ�ϲ������"+userphone.getText()+"?")
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
		
		cancel_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					if(((Button) findViewById(R.id.btn_cancel)).getText().equals("�ص�"))
					{
						LinearLayout view = new LinearLayout(ZdDetailActivity.this);
						view.setOrientation(LinearLayout.VERTICAL);
						final Spinner spi_cgbl=new Spinner(ZdDetailActivity.this);
						final Spinner spi_sbyy=new Spinner(ZdDetailActivity.this);
						ArrayAdapter<String> cgblAdapter=new ArrayAdapter<String>(ZdDetailActivity.this,android.R.layout.simple_spinner_dropdown_item,new String[] {"��","��"});
						spi_cgbl.setAdapter(cgblAdapter);
						spi_cgbl.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
						
						ArrayAdapter<String> sbyyAdapter=new ArrayAdapter<String>(ZdDetailActivity.this,android.R.layout.simple_spinner_dropdown_item,new String[] {"��","�û��������","�û�δ�յ����"});
						spi_sbyy.setAdapter(sbyyAdapter);
						spi_sbyy.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
						final TextView tv1=new TextView(ZdDetailActivity.this);
						final TextView tv2=new TextView(ZdDetailActivity.this);
						final TextView tv3=new TextView(ZdDetailActivity.this);
						final EditText et_remark=new EditText(ZdDetailActivity.this);
						et_remark.setHint("��ע");
						tv1.setText("�Ƿ�ɹ�����");
						tv2.setText("����ʧ��ԭ��");
						tv3.setText("��ע");
						view.addView(tv1);
						view.addView(spi_cgbl);
						view.addView(tv2);
						view.addView(spi_sbyy);
						view.addView(tv3);
						view.addView(et_remark);
						
						new AlertDialog.Builder(ZdDetailActivity.this).setTitle("�ն˻ص�")
						.setIcon(android.R.drawable.ic_dialog_info)
						//.setMessage("���µ���Ϣ�����Ӧ�����,�ύ�󽫻Ḳ��ԭ����Ϣ��")
						.setView(view)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// �����ȷ�ϡ���Ĳ���
						    	if(spi_cgbl.getSelectedItem().toString().equals("��") && !spi_sbyy.getSelectedItem().toString().equals("��"))
								{
						    		Toast.makeText(ZdDetailActivity.this, "�ɹ�����ʧ��ԭ����ѡ����",Toast.LENGTH_SHORT).show();
						    		try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
						    		    field.set(dialog, false );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}
						    	else if(spi_cgbl.getSelectedItem().toString().equals("��") && spi_sbyy.getSelectedItem().toString().equals("��"))
								{
						    		Toast.makeText(ZdDetailActivity.this, "����ʧ�ܣ���ѡ��ʧ��ԭ��",Toast.LENGTH_SHORT).show();
						    		try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
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
						.setNegativeButton("����", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// ��������ء���Ĳ���,���ﲻ����û���κβ���
						    }
						}).show();
					}
					else{
						new AlertDialog.Builder(ZdDetailActivity.this).setTitle("ȷ�Ͻӵ���")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// �����ȷ�ϡ���Ĳ���
						    	ZdListOpt(Id, myapp.getUser(),myapp.getName(), "accept");
				 
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

	// ���ض�����Ϣ
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
					country.setText(temphash.get("����"));
					area.setText(temphash.get("Ƭ��"));
					hdlx.setText(temphash.get("�����"));
					packageid.setText(temphash.get("Ӫ��������"));
					phonetype.setText(temphash.get("����"));
					money.setText(temphash.get("�û�����"));
					yfhf.setText(temphash.get("�·�����"));
					fhys.setText(temphash.get("��������"));
					zdxf.setText(temphash.get("�������"));
					kbys.setText(temphash.get("��������"));
					cpyq.setText(temphash.get("��ƷҪ��"));
					xjcj.setText(temphash.get("�ֽ���"));
					sender.setText(temphash.get("�ɵ���"));
					sendtime.setText(temphash.get("�ɵ�ʱ��"));
					pdremark.setText(temphash.get("�ɵ���ע"));
					receiver.setText(temphash.get("�ӵ���"));
					receivetime.setText(temphash.get("�ӵ�ʱ��"));
					userphone.setText(temphash.get("�ͻ��绰"));
					username.setText(temphash.get("�ͻ�����"));
					xj.setText(temphash.get("�Ǽ�"));
					ywq.setText(temphash.get("����ҵ����"));
					jtdw.setText(temphash.get("�������ŵ�λ"));
					sfjs.setText(temphash.get("�Ƿ����"));
					cgbl.setText(temphash.get("�Ƿ�ɹ�����"));
					sbyy.setText(temphash.get("����ʧ��ԭ��"));
					wcsj.setText(temphash.get("���ȷ��ʱ��"));
					usedtime.setText(temphash.get("��ʱ"));
					state.setText(temphash.get("��ǰ״̬"));
					remark.setText(temphash.get("��ע"));
					Button cancel_bt = (Button) findViewById(R.id.btn_cancel);


					if(temphash.get("�Ƿ����").equals("��"))//��������״̬ʱȡ����ťʧЧ��������ѳ���״̬��������ɾ������
					{
						cancel_bt.setVisibility(8);//�Ѿ������Ĺ������ذ�ť
					}
					else
					{
						if(temphash.get("��ǰ״̬").equals("�ѽӵ�"))
						{
							cancel_bt.setText("�ص�");
						}
					}

				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}
}
