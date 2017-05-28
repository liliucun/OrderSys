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
		String tstr="欢迎"+myApp.getName()+"!";
		if(!Power.equals(""))
		{
			tstr+="\n宽带角色:"+Power;
		}
		if(!Role.equals(""))
		{
			tstr+="\n中高端角色:"+Role;
		}
		title.setText(tstr);
		 GridView gridview = (GridView) findViewById(R.id.GridView);
	        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
	    if(!Power.equals(""))
	    {
	    	if(Role.equals(""))
	    	{
		        if(Power.equals("超级管理员")||Power.equals("县区管理员"))
		        {
			        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_newuser,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
			        Object[] valueObjects={"新装派单","装机工单查询","故障派单","故障工单查询","新增用户","未覆盖小区标记","修改密码","统计报表","用户退出"};
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
			        Object[] valueObjects={"新装派单","装机工单查询","故障派单","故障工单查询","未覆盖小区标记","修改密码","用户退出"};
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
	    		if(Power.equals("超级管理员")||Power.equals("县区管理员"))
		        {
			        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_newuser,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
			        Object[] valueObjects={"新装派单","装机工单查询","故障派单","故障工单查询","终端派单","终端工单查询","新增用户","未覆盖小区标记","修改密码","统计报表","用户退出"};
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
			        Object[] valueObjects={"新装派单","装机工单查询","故障派单","故障工单查询","终端派单","终端工单查询","未覆盖小区标记","修改密码","用户退出"};
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
		        if(Power.equals("超级管理员")||Power.equals("县区管理员"))
		        {
			        Object [] imageObjects={R.drawable.icon_add,R.drawable.icon_his,R.drawable.icon_addfault,R.drawable.icon_faulthis,R.drawable.icon_newuser,R.drawable.icon_map,R.drawable.icon_chgpwd,R.drawable.icon_report,R.drawable.icon_exit};
			        Object[] valueObjects={"新装派单","装机工单查询","故障派单","故障工单查询","新增用户","未覆盖小区标记","修改密码","统计报表","用户退出"};
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
			        Object[] valueObjects={"新装派单","装机工单查询","故障派单","故障工单查询","未覆盖小区标记","修改密码","用户退出"};
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
		        Object[] valueObjects={"终端派单","终端工单查询","新增用户","修改密码","统计报表","用户退出"};
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
	                  meumList, //数据源
	                  R.layout.item, //xml实现
	                  new String[]{"ItemImage","ItemText"}, //对应map的Key
	                  new int[]{R.id.ItemImage,R.id.ItemText});  //对应R的Id
	  
	                //添加Item到网格中
	                gridview.setAdapter(saItem);
	                //添加点击事件
	                gridview.setOnItemClickListener(
	                    new OnItemClickListener()
	                    {
	                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
	                        {
	                            //int index=arg2+1;//id是从0开始的，所以需要+1
	                        	HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
	                        	//显示所选Item的ItemText   
	                        	String title= (String)item.get("ItemText"); 
	                        	
	                        	if(title.equals("新装派单"))
	                        	{
									Intent addIntent=new Intent(MainActivity.this,addActivity.class);
									startActivity(addIntent);
	                        	}
	                        	else if(title.equals("装机工单查询"))
	                        	{
									Intent installlistIntent=new Intent(MainActivity.this,InstallListActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("新增用户"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,AccountManageActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("修改密码"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,ChangePwdActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("统计报表"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,reportActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("用户退出"))
	                        	{
	                        		new AlertDialog.Builder(MainActivity.this).setTitle("确认退出吗？将会清除自动登录状态")
	                        		.setIcon(android.R.drawable.ic_dialog_info)
	                        		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                         
	                        		    @Override
	                        		    public void onClick(DialogInterface dialog, int which) {
	                        		    	// 点击“确认”后的操作
	                        		    	SharedPreferences sp = MainActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
	    	                        		Editor editor = sp.edit();
	    	                        		editor.putBoolean("zddl", false);
	    	                        		editor.commit();
	    	                        		//PushManager.getInstance().stopService(getApplicationContext());
	                        		    	MainActivity.this.finish();
	                         
	                        		    }
	                        		})
	                        		.setNegativeButton("返回", new DialogInterface.OnClickListener() {
	                         
	                        		    @Override
	                        		    public void onClick(DialogInterface dialog, int which) {
	                        			// 点击“返回”后的操作,这里不设置没有任何操作
	                        		    }
	                        		}).show();
	                        	}
	                        	else if(title.equals("故障派单"))
	                        	{
				                	Intent installlistIntent=new Intent(MainActivity.this,addFaultActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("故障工单查询"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,FaultListActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("终端派单"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,AddZdActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("终端工单查询"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,ZdListActivity.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("未覆盖小区标记"))
	                        	{
	                        		Intent installlistIntent=new Intent(MainActivity.this,MarkCell.class);
									startActivity(installlistIntent);
	                        	}
	                        	else if(title.equals("推送消息"))
	                        	{
//	                        		Intent installlistIntent=new Intent(MainActivity.this,PushMsgActivity.class);
//									startActivity(installlistIntent);
	                        		Toast.makeText(getApplicationContext(), "该功能暂未开放", 0).show();
	                        	}
	                        	else if(title.equals("LOGID查询"))
	                        	{
	                        		//Bundle b=new Bundle();
	                        		//b.putString("Opt", "GetLogID");
	                        		//Intent installlistIntent=new Intent(MainActivity.this,UploadImgActivity.class);
	                        		//installlistIntent.putExtras(b);
									//startActivity(installlistIntent);
	                        	}
	                            //Toast.makeText(getApplicationContext(), "跳转中...", 0).show();
	                            //Toast用于向用户显示一些帮助/提示
	                        }
	                    }
	                );    
	                //PushManager.getInstance().initialize(this.getApplicationContext());//初始化推送SDK
	            	//CheckUpdate();//启动检查软件更新     
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
    	PushManager.getInstance().initialize(this.getApplicationContext());//初始化推送SDK
    	CheckUpdate();//启动检查软件更新     
    }
//    @Override
//    public void onBackPressed() {
//	new AlertDialog.Builder(this).setTitle("确认退出吗？")
//		.setIcon(android.R.drawable.ic_dialog_info)
//		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
// 
//		    @Override
//		    public void onClick(DialogInterface dialog, int which) {
//			// 点击“确认”后的操作
//			MainActivity.this.finish();
// 
//		    }
//		})
//		.setNegativeButton("返回", new DialogInterface.OnClickListener() {
// 
//		    @Override
//		    public void onClick(DialogInterface dialog, int which) {
//			// 点击“返回”后的操作,这里不设置没有任何操作
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
				
				 //toast=Toast.makeText(MainActivity.this,"正在检查更新...",Toast.LENGTH_LONG );
				 //toast.show();
				 UpdateManager manager = new UpdateManager(MainActivity.this);
					 //检查软件更新
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
				
				if(Power.equals("维护人员"))
				{
					t1.setText("当日接单:");
					t3.setText("当月接单:");
				}
				
				if (list!=null && list.size() > 0)
				{
					HashMap<String, String> m = new HashMap<String, String>();
					m=list.get(0);
					if(Power.equals("维护人员"))
					{
						d1.setText(m.get("当日接单"));
						d2.setText(m.get("当日装机"));
						d3.setText(m.get("当月接单"));
						d4.setText(m.get("当月装机"));
						d5.setText(m.get("未完工单"));
						d6.setText(m.get("超时工单"));
					}
					else
					{
						d1.setText(m.get("当日派单"));
						d2.setText(m.get("当日装机"));
						d3.setText(m.get("当月派单"));
						d4.setText(m.get("当月装机"));
						d5.setText(m.get("未完工单"));
						d6.setText(m.get("超时工单"));
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
