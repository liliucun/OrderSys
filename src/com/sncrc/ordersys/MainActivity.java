package com.sncrc.ordersys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.igexin.sdk.PushManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private AppData myApp;
	private String Power;
	private String Role;
	private DBUtil dbUtil;
	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView t4;
	private TextView t5;
	private TextView t6;
	
	private TextView d1;
	private TextView d2;
	private TextView d3;
	private TextView d4;
	private TextView d5;
	private TextView d6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbUtil = new DBUtil();
		myApp=(AppData)getApplication();
		Power=myApp.getPower();
		Role=myApp.getRole();
		t1=(TextView)findViewById(R.id.t1);
		t2=(TextView)findViewById(R.id.t2);
		t3=(TextView)findViewById(R.id.t3);
		t4=(TextView)findViewById(R.id.t4);
		t5=(TextView)findViewById(R.id.t5);
		t6=(TextView)findViewById(R.id.t6);
		
		d1=(TextView)findViewById(R.id.d1);
		d2=(TextView)findViewById(R.id.d2);
		d3=(TextView)findViewById(R.id.d3);
		d4=(TextView)findViewById(R.id.d4);
		d5=(TextView)findViewById(R.id.d5);
		d6=(TextView)findViewById(R.id.d6);
		
		TextView title=(TextView)findViewById(R.id.title);
		String tstr="��ӭ"+myApp.getName()+"!";
		if(!Power.equals(""))
		{
			tstr+="\n�����ɫ:"+Power;
		}
		if(!Role.equals(""))
		{
			tstr+="\n�и߶˽�ɫ:"+Role;
		}
		title.setText(tstr);
		 GridView gridview = (GridView) findViewById(R.id.GridView);
	        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
	    if(!Power.equals(""))
	    {
	    	if(Role.equals(""))
	    	{
		        if(Power.equals("��������Ա")||Power.equals("��������Ա"))
		        {
			        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_newuser,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
			        Object[] valueObjects={"��װ�ɵ�","װ��������ѯ","�����ɵ�","���Ϲ�����ѯ","�����û�","δ����С�����","�޸�����","ͳ�Ʊ���","�û��˳�"};
			        for(int i = 0;i <9;i++)
			        {
			            HashMap<String, Object> map = new HashMap<String, Object>();
			            map.put("ItemImage",imageObjects[i]);
			            map.put("ItemText", valueObjects[i]);
			            meumList.add(map);
			        }
		        }
		        else
		        {
		        	Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_exit};
			        Object[] valueObjects={"��װ�ɵ�","װ��������ѯ","�����ɵ�","���Ϲ�����ѯ","δ����С�����","�޸�����","�û��˳�"};
			        for(int i = 0;i <7;i++)
			        {
			            HashMap<String, Object> map = new HashMap<String, Object>();
			            map.put("ItemImage",imageObjects[i]);
			            map.put("ItemText", valueObjects[i]);
			            meumList.add(map);
			        }
		        }
	    	}
	    	else
	    	{
	    		if(Power.equals("��������Ա")||Power.equals("��������Ա"))
		        {
			        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_newuser,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
			        Object[] valueObjects={"��װ�ɵ�","װ��������ѯ","�����ɵ�","���Ϲ�����ѯ","�ն��ɵ�","�ն˹�����ѯ","�����û�","δ����С�����","�޸�����","ͳ�Ʊ���","�û��˳�"};
			        for(int i = 0;i <11;i++)
			        {
			            HashMap<String, Object> map = new HashMap<String, Object>();
			            map.put("ItemImage",imageObjects[i]);
			            map.put("ItemText", valueObjects[i]);
			            meumList.add(map);
			        }
		        }
		        else
		        {
		        	Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_exit};
			        Object[] valueObjects={"��װ�ɵ�","װ��������ѯ","�����ɵ�","���Ϲ�����ѯ","�ն��ɵ�","�ն˹�����ѯ","δ����С�����","�޸�����","�û��˳�"};
			        for(int i = 0;i <9;i++)
			        {
			            HashMap<String, Object> map = new HashMap<String, Object>();
			            map.put("ItemImage",imageObjects[i]);
			            map.put("ItemText", valueObjects[i]);
			            meumList.add(map);
			        }
		        }
	    	}
	    }
	    else
	    {
	    	if(Role.equals(""))
	    	{
		        if(Power.equals("��������Ա")||Power.equals("��������Ա"))
		        {
			        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_newuser,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
			        Object[] valueObjects={"��װ�ɵ�","װ��������ѯ","�����ɵ�","���Ϲ�����ѯ","�����û�","δ����С�����","�޸�����","ͳ�Ʊ���","�û��˳�"};
			        for(int i = 0;i <9;i++)
			        {
			            HashMap<String, Object> map = new HashMap<String, Object>();
			            map.put("ItemImage",imageObjects[i]);
			            map.put("ItemText", valueObjects[i]);
			            meumList.add(map);
			        }
		        }
		        else
		        {
		        	Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_exit};
			        Object[] valueObjects={"��װ�ɵ�","װ��������ѯ","�����ɵ�","���Ϲ�����ѯ","δ����С�����","�޸�����","�û��˳�"};
			        for(int i = 0;i <7;i++)
			        {
			            HashMap<String, Object> map = new HashMap<String, Object>();
			            map.put("ItemImage",imageObjects[i]);
			            map.put("ItemText", valueObjects[i]);
			            meumList.add(map);
			        }
		        }
	    	}
	    	else
	    	{
		        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_newuser,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
		        Object[] valueObjects={"�ն��ɵ�","�ն˹�����ѯ","�����û�","�޸�����","ͳ�Ʊ���","�û��˳�"};
		        for(int i = 0;i <6;i++)
		        {
		            HashMap<String, Object> map = new HashMap<String, Object>();
		            map.put("ItemImage",imageObjects[i]);
		            map.put("ItemText", valueObjects[i]);
		            meumList.add(map);
		        }
	    	}
	    }
	        SimpleAdapter saItem = new SimpleAdapter(this,
	                  meumList, //����Դ
	                  R.layout.item, //xmlʵ��
	                  new String[]{"ItemImage","ItemText"}, //��Ӧmap��Key
	                  new int[]{R.id.ItemImage,R.id.ItemText});  //��ӦR��Id
	  
	                //���Item��������
	                gridview.setAdapter(saItem);
	                //��ӵ���¼�
	                gridview.setOnItemClickListener(
	                    new OnItemClickListener()
	                    {
	                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
	                        {
	                            //int index=arg2+1;//id�Ǵ�0��ʼ�ģ�������Ҫ+1
	                        	HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
	                        	//��ʾ��ѡItem��ItemText   
	                        	String title= (String)item.get("ItemText"); 
	                        	
	                        	if(title.equals("��װ�ɵ�"))
	                        	{
									Intent addIntent=new Intent(MainActivity.this,addActivity.class);
									startActivity(addIntent);
	                        	}
	                        	else if(title.equals("װ��������ѯ"))
	                        	{
									Intent installlistIntent=new Intent(MainActivity.this,InstallListActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("�����û�"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,AccountManageActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("�޸�����"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,ChangePwdActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("ͳ�Ʊ���"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,reportActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("�û��˳�"))
	                        	{
	                        		new AlertDialog.Builder(MainActivity.this).setTitle("ȷ���˳��𣿽�������Զ���¼״̬")
	                        		.setIcon(android.R.drawable.ic_dialog_info)
	                        		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	                         
	                        		    @Override
	                        		    public void onClick(DialogInterface dialog, int which) {
	                        		    	// �����ȷ�ϡ���Ĳ���
	                        		    	SharedPreferences sp = MainActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
	    	                        		Editor editor = sp.edit();
	    	                        		editor.putBoolean("zddl", false);
	    	                        		editor.commit();
	    	                        		//PushManager.getInstance().stopService(getApplicationContext());
	                        		    	MainActivity.this.finish();
	                         
	                        		    }
	                        		})
	                        		.setNegativeButton("����", new DialogInterface.OnClickListener() {
	                         
	                        		    @Override
	                        		    public void onClick(DialogInterface dialog, int which) {
	                        			// ��������ء���Ĳ���,���ﲻ����û���κβ���
	                        		    }
	                        		}).show();
	                        	}
	                        	else if(title.equals("�����ɵ�"))
	                        	{
				                	Intent installlistIntent=new Intent(MainActivity.this,addFaultActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("���Ϲ�����ѯ"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,FaultListActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("�ն��ɵ�"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,AddZdActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("�ն˹�����ѯ"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,ZdListActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("δ����С�����"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,MarkCell.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("������Ϣ"))
	                        	{
//	                        		Intent installlistIntent=new Intent(MainActivity.this,PushMsgActivity.class);
//									startActivity(installlistIntent);
	                        		Toast.makeText(getApplicationContext(), "�ù�����δ����", 0).show();
	                        	}
	                        	else if(title.equals("LOGID��ѯ"))
	                        	{
	                        		//Bundle b=new Bundle();
	                        		//b.putString("Opt", "GetLogID");
	                        		//Intent installlistIntent=new Intent(MainActivity.this,UploadImgActivity.class);
	                        		//installlistIntent.putExtras(b);
									//startActivity(installlistIntent);
	                        	}
	                            //Toast.makeText(getApplicationContext(), "��ת��...", 0).show();
	                            //Toast�������û���ʾһЩ����/��ʾ
	                        }
	                    }
	                );    
	                //PushManager.getInstance().initialize(this.getApplicationContext());//��ʼ������SDK
	            	//CheckUpdate();//��������������     
	}
	
	@Override
	protected void onStart() {
    	super.onStart();
    	if(myApp.getUser().equals(""))
    	{
    		Intent intent = new Intent(MainActivity.this,loginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(intent);
			MainActivity.this.finish();
    	}
    	GetPersionalNum(myApp.getUser());
    	PushManager.getInstance().initialize(this.getApplicationContext());//��ʼ������SDK
    	CheckUpdate();//��������������     
    }
//    @Override
//    public void onBackPressed() {
//	new AlertDialog.Builder(this).setTitle("ȷ���˳���")
//		.setIcon(android.R.drawable.ic_dialog_info)
//		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
// 
//		    @Override
//		    public void onClick(DialogInterface dialog, int which) {
//			// �����ȷ�ϡ���Ĳ���
//			MainActivity.this.finish();
// 
//		    }
//		})
//		.setNegativeButton("����", new DialogInterface.OnClickListener() {
// 
//		    @Override
//		    public void onClick(DialogInterface dialog, int which) {
//			// ��������ء���Ĳ���,���ﲻ����û���κβ���
//		    }
//		}).show();
//	// super.onBackPressed();
//    }
	
	@Override
    public void onBackPressed() {
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    
    public void CheckUpdate()
	{
		new AsyncTask<String,Void,String>() {
			Toast toast;
			protected String doInBackground(String... params) {
				String result;
				result=dbUtil.GetVersion();
				return  result;
			
			}
	
			@Override
			protected void onPostExecute(String result) {
				
				 //toast=Toast.makeText(MainActivity.this,"���ڼ�����...",Toast.LENGTH_LONG );
				 //toast.show();
				 UpdateManager manager = new UpdateManager(MainActivity.this);
					 //����������
				//System.out.println(result);
				manager.checkUpdate(result);
				 
				super.onPostExecute(result);
			}
			
		}.execute();
	}
    
    private void GetPersionalNum(String user)
	{
		new AsyncTask<String,Void,String>() {
			protected String doInBackground(String... params) {
				String result;
				result=dbUtil.GetPersonalNum(params[0]);
				return  result;
			}
	
			@Override
			protected void onPostExecute(String result) {
				Log.d("GetPersonalNum",result);
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				list=JsonUtil.json2list(result);
				
				if(Power.equals("ά����Ա"))
				{
					t1.setText("���սӵ�:");
					t3.setText("���½ӵ�:");
				}
				
				if (list!=null && list.size() > 0)
				{
					HashMap<String, String> m = new HashMap<String, String>();
					m=list.get(0);
					if(Power.equals("ά����Ա"))
					{
						d1.setText(m.get("���սӵ�"));
						d2.setText(m.get("����װ��"));
						d3.setText(m.get("���½ӵ�"));
						d4.setText(m.get("����װ��"));
						d5.setText(m.get("δ�깤��"));
						d6.setText(m.get("��ʱ����"));
					}
					else
					{
						d1.setText(m.get("�����ɵ�"));
						d2.setText(m.get("����װ��"));
						d3.setText(m.get("�����ɵ�"));
						d4.setText(m.get("����װ��"));
						d5.setText(m.get("δ�깤��"));
						d6.setText(m.get("��ʱ����"));
					}
				}
				else
				{
					d1.setText("0");
					d2.setText("0");
					d3.setText("0");
					d4.setText("0");
					d5.setText("0");
					d6.setText("0");
				}
				super.onPostExecute(result);
			}
			
		}.execute(user);
	}
}
