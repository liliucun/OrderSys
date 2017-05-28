package com.sncrc.ordersys;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.kobjects.base64.Base64;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SinglequeryActivity extends Activity {

	private String Id;
	private TextView country;
	private TextView issuccess;
	private TextView area;
	private TextView cell;
	private TextView person;
	private TextView receiver;
	private TextView reason;
	private TextView sender;
	private TextView sendertype;
	private TextView userphone;
	private TextView useraddress;
	private TextView money;
	private EditText remark;
	private TextView state;
	private TextView inmode;
	private TextView jdtime;
	private TextView usedtime;
	private TextView isover;
	private TextView pdtime;
	private TextView wctime;
	private TextView useraccount;
	private TextView celladdress;
	private TextView newaccountaddr;
	private TextView spremark;
	private TextView Logid;
	private TextView img1txt;
	private TextView img2txt;
	private ImageView img1;
	private ImageView img2;
	private AppData myapp;
	private String power;
	private Button cancel_bt;
	private Button btn_downloadpic;
	private String imgfilename1;
	private String imgfilename2;
	private String bmp_str1="";
	private String bmp_str2="";
//	private Button delete_bt;

	private DBUtil dbUtil = new DBUtil();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("工单详情");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.accmanager);
		
		// 新页面接收数据
		Bundle bundle = this.getIntent().getExtras();
		// 接收name值
		Id = bundle.getString("Id");
		country = (TextView) findViewById(R.id.country);
		issuccess = (TextView) findViewById(R.id.issuccess);
		area = (TextView) findViewById(R.id.area);
		cell = (TextView) findViewById(R.id.cell);
		person = (TextView) findViewById(R.id.person);
		reason = (TextView) findViewById(R.id.reason);
		sender = (TextView) findViewById(R.id.sender);
		receiver = (TextView) findViewById(R.id.receiver);
		sendertype = (TextView) findViewById(R.id.sendertype);
		userphone = (TextView) findViewById(R.id.userphone);
		useraddress = (TextView) findViewById(R.id.useraddress);
		newaccountaddr = (TextView) findViewById(R.id.newaccountaddr);
		money = (TextView) findViewById(R.id.money);
		remark = (EditText) findViewById(R.id.remark);
		state = (TextView) findViewById(R.id.state);
		jdtime = (TextView) findViewById(R.id.jdtime);
		usedtime = (TextView) findViewById(R.id.usedtime);
		isover = (TextView) findViewById(R.id.isover);
		pdtime = (TextView) findViewById(R.id.pdtime);
		wctime = (TextView) findViewById(R.id.wctime);
		celladdress = (TextView) findViewById(R.id.celladdress);
		useraccount = (TextView) findViewById(R.id.useraccount);
		spremark = (TextView) findViewById(R.id.spremark);
		Logid = (TextView) findViewById(R.id.Logid);
		inmode = (TextView) findViewById(R.id.inmode);
		cancel_bt = (Button) findViewById(R.id.btn_cancel);
		img1txt = (TextView) findViewById(R.id.img1txt);
		img2txt = (TextView) findViewById(R.id.img2txt);
		img1 = (ImageView) findViewById(R.id.img1);
		img2 = (ImageView) findViewById(R.id.img2);
		btn_downloadpic = (Button) findViewById(R.id.btn_downloadpic);
//		delete_bt = (Button) findViewById(R.id.btn_delete);
		//点击号码拨打电话
		userphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认拨打号码"+userphone.getText()+"?")
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
		}
		
		cancel_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(cancel_bt.getText().equals("撤销"))
				{		//非维护人员权限
					new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认撤销吗？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
					    	if(remark.getText().toString().equals(""))
							{
					    		Toast.makeText(SinglequeryActivity.this, "请输入撤销原因后重新点击撤销",Toast.LENGTH_SHORT).show();
								remark.setEnabled(true);
								return;
							}else
							{
								
								AccManagerOpt(Id, myapp.getUser(), "cancel",remark.getText().toString());
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
				else if(cancel_bt.getText().equals("修改派单信息"))
				{		//非维护人员权限
					LinearLayout view = new LinearLayout(SinglequeryActivity.this);
					view.setOrientation(LinearLayout.VERTICAL);
					final EditText tuserphone=new EditText(SinglequeryActivity.this);
					final EditText tuseraddress=new EditText(SinglequeryActivity.this);
					final EditText tfee=new EditText(SinglequeryActivity.this);
					final EditText tuseraccount=new EditText(SinglequeryActivity.this);
					final EditText tuserremark=new EditText(SinglequeryActivity.this);
					final TextView tv1=new TextView(SinglequeryActivity.this);
					final TextView tv2=new TextView(SinglequeryActivity.this);
					final TextView tv3=new TextView(SinglequeryActivity.this);
					final TextView tv4=new TextView(SinglequeryActivity.this);
					final TextView tv5=new TextView(SinglequeryActivity.this);
					tuserphone.setText(userphone.getText().toString());
					tuseraddress.setText(useraddress.getText().toString());
					tfee.setText(money.getText().toString());
					tuseraccount.setText(useraccount.getText().toString());
					tuserremark.setText(remark.getText().toString());
					tv1.setText("用户电话:");
					tv2.setText("装机地址:");
					tv3.setText("费用:");
					tv4.setText("用户账号:");
					tv5.setText("预约备注:");
					tfee.setInputType(InputType.TYPE_CLASS_NUMBER);
					tuseraccount.setInputType(InputType.TYPE_CLASS_NUMBER);
					tuserphone.setInputType(InputType.TYPE_CLASS_NUMBER);
					tuserphone.setHint("用户电话");
					tuseraddress.setHint("装机地址");
					tfee.setHint("费用");
					tuseraccount.setHint("用户账号");
					tuserremark.setHint("预约备注");
					view.addView(tv1);
					view.addView(tuserphone);
					view.addView(tv2);
					view.addView(tuseraddress);
					view.addView(tv3);
					view.addView(tfee);
					view.addView(tv4);
					view.addView(tuseraccount);
					view.addView(tv5);
					view.addView(tuserremark);
					new AlertDialog.Builder(SinglequeryActivity.this).setTitle("修改派单信息")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage("将新的信息输入对应输入框,提交后将会覆盖原有信息。")
					.setView(view)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
					    	if(tuserphone.getText().length()==0 || tuseraddress.getText().length()==0 || tfee.getText().length()==0)
							{
					    		Toast.makeText(SinglequeryActivity.this, "请输入必填项",Toast.LENGTH_SHORT).show();
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
								
								AccManagerOpt(Id, myapp.getUser(), "update",tuserphone.getText().toString()+"|"+tuseraddress.getText().toString()+"|"+tfee.getText().toString()+"|"+tuseraccount.getText().toString()+"|"+tuserremark.getText().toString()+"|");
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
				else if(cancel_bt.getText().equals("弃单审核"))
				{
						final EditText txt=new EditText(SinglequeryActivity.this);
						txt.setHint("审核备注");
						new AlertDialog.Builder(SinglequeryActivity.this).setTitle("弃单审核？")
						.setMessage("请输入审核原因，不少于3个字符")
						.setView(txt)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("审核通过", new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
						    	if(txt.getText().length()<3)
								{
						    		Toast.makeText(SinglequeryActivity.this, "请输入审核备注，不少于3字符",Toast.LENGTH_SHORT).show();
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
								}else
								{
									new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认审核通过?")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
									    	Qdsh("ok",Id,txt.getText().toString(),myapp.getUser());
									    }
									})
									.setNegativeButton("返回", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
										// 点击“返回”后的操作,这里不设置没有任何操作
									    }
									}).show();	
									try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   将mShowing变量设为false，表示对话框已关闭 
						    		    field.set(dialog, true );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}			 
						    }
						})
						.setNeutralButton("审核拒绝", new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
						    	if(txt.getText().length()<3)
								{
						    		Toast.makeText(SinglequeryActivity.this, "请输入审核备注，不少于3字符",Toast.LENGTH_SHORT).show();
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
								}else
								{
									new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认审核拒绝?")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
									    	Qdsh("refuse",Id,txt.getText().toString(),myapp.getUser());
									    }
									})
									.setNegativeButton("返回", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
										// 点击“返回”后的操作,这里不设置没有任何操作
									    }
									}).show();	
									try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   将mShowing变量设为false，表示对话框已关闭 
						    		    field.set(dialog, true );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}
						    }
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“返回”后的操作,这里不设置没有任何操作
						    	try 
					    		{
					    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
					    		    field.setAccessible( true );
					    		     //   将mShowing变量设为false，表示对话框已关闭 
					    		    field.set(dialog, true );
					    		    dialog.dismiss();
					    		}
					    		catch (Exception e)
					    		{}
						    }
						}).show();
				}else if(cancel_bt.getText().equals("接单信息审核"))
				{
					LinearLayout view = new LinearLayout(SinglequeryActivity.this);
					view.setOrientation(LinearLayout.VERTICAL);
					final EditText remark=new EditText(SinglequeryActivity.this);
					final EditText account=new EditText(SinglequeryActivity.this);
					account.setInputType(InputType.TYPE_CLASS_NUMBER);
					account.setText(useraccount.getText());
					view.addView(account);
					view.addView(remark);
					remark.setHint("LOID/审核备注");
					account.setHint("用户账号");
					new AlertDialog.Builder(SinglequeryActivity.this).setTitle("接单信息审核？")
					.setMessage("请输入用户账号和LOID/审核原因")
					.setView(view)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("审核通过", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
					    	if(remark.getText().length()<3 || account.getText().length()!=11)
							{
					    		Toast.makeText(SinglequeryActivity.this, "用户账号为11位手机号码,LOID/审核备注不少于3个字符",Toast.LENGTH_SHORT).show();
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
							}else
							{
								new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认审核通过?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
								    	Zpsh(Id,"ok",account.getText().toString(),remark.getText().toString(),myapp.getUser());
								    }
								})
								.setNegativeButton("返回", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
									// 点击“返回”后的操作,这里不设置没有任何操作
								    }
								}).show();	
								try 
					    		{
					    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
					    		    field.setAccessible( true );
					    		     //   将mShowing变量设为false，表示对话框已关闭 
					    		    field.set(dialog, true );
					    		    dialog.dismiss();
					    		}
					    		catch (Exception e)
					    		{}
							}			 
					    }
					})
					.setNeutralButton("审核拒绝", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
					    	if(remark.getText().length()<3 || account.getText().length()!=11)
							{
					    		Toast.makeText(SinglequeryActivity.this, "用户账号为11位手机号码,LOID/审核备注不少于3个字符",Toast.LENGTH_SHORT).show();
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
							}else
							{
								new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认审核拒绝?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
								    	Zpsh(Id,"refuse",account.getText().toString(),remark.getText().toString(),myapp.getUser());
								    }
								})
								.setNegativeButton("返回", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
									// 点击“返回”后的操作,这里不设置没有任何操作
								    }
								}).show();	
								try 
					    		{
					    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
					    		    field.setAccessible( true );
					    		     //   将mShowing变量设为false，表示对话框已关闭 
					    		    field.set(dialog, true );
					    		    dialog.dismiss();
					    		}
					    		catch (Exception e)
					    		{}
							}
					    }
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					    	try 
				    		{
				    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
				    		    field.setAccessible( true );
				    		     //   将mShowing变量设为false，表示对话框已关闭 
				    		    field.set(dialog, true );
				    		    dialog.dismiss();
				    		}
				    		catch (Exception e)
				    		{}
					    }
					}).show();
				}
				else
				{		//维护人员权限
					if(((Button) findViewById(R.id.btn_cancel)).getText().equals("回单"))
					{
						Intent intent;
				    	intent = new Intent(SinglequeryActivity.this,ReceiptJob.class);
				    	Bundle bundle = new Bundle();
						// 传递name参数为tinyphp
						bundle.putString("Id", Id);
						bundle.putString("mode", "0");
						intent.putExtras(bundle);
						startActivityForResult(intent,0);
					}else{
						new AlertDialog.Builder(SinglequeryActivity.this).setTitle("确认接单吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
						    	AccManagerOpt(Id, myapp.getUser(), "accept", "");
				 
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
		
		btn_downloadpic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_downloadpic.setVisibility(View.GONE);
				Toast toast=Toast.makeText(SinglequeryActivity.this, "正在加载照片", Toast.LENGTH_LONG);
				toast.show();
				DownloadImg(imgfilename1,imgfilename2,toast);
			}
		});
		
		img1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplication(),ImgViewerActivity.class);
				myapp.setBitmapData(bmp_str1);
				startActivity(intent);
			}
		});
		
		img2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplication(),ImgViewerActivity.class);
				myapp.setBitmapData(bmp_str2);
				startActivity(intent);
			}
		});

		loadInfo(Id);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		    finish();
		    break;
		default:
		    break;
		    }
	}
	
	public void AccManagerOpt(String ID, String user, String type,String remark) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.AccManagerOpt(params[0], params[1], params[2],params[3]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(SinglequeryActivity.this, result, Toast.LENGTH_LONG).show();
				if (result.equals("Success")) {
					finish();
					Bundle b=new Bundle();
            		b.putString("Id", Id);
            		b.putString("Msg", "点击选择当前分光箱");
            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
            		intent.putExtras(b);
            		startActivityForResult(intent,0);
				}
				super.onPostExecute(result);
			}
		}.execute( ID,  user,  type, remark);

	}
	
	//弃单审核
	public void Qdsh(String op, String id, String remark,String name) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.Qdsh(params[0], params[1], params[2],params[3]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(SinglequeryActivity.this, result, Toast.LENGTH_LONG).show();
				if (result.equals("审批成功")) 
				{
					finish();
				}
				super.onPostExecute(result);
			}
		}.execute( op,  id,  remark, name);

	}
	
	//照片审核
	public void Zpsh(String id,String op,String account,String remark,String name) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return dbUtil.Zpsh(params[0], params[1], params[2],params[3],params[4]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(SinglequeryActivity.this, result, Toast.LENGTH_LONG).show();
				if (result.equals("审批成功")) 
				{
					finish();
				}
				super.onPostExecute(result);
			}
		}.execute(id, op, account,  remark, name);

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
					useraddress.setText(temphash.get("装机地址"));
					sender.setText(temphash.get("派单人"));
					sendertype.setText(temphash.get("性质"));
					person.setText(temphash.get("装机人"));
					receiver.setText(temphash.get("接单人"));
					issuccess.setText(temphash.get("是否成功装机"));
					area.setText(temphash.get("片区"));
					cell.setText(temphash.get("小区"));
					reason.setText(temphash.get("总原因")+"|"+temphash.get("原因"));
					usedtime.setText(temphash.get("耗时"));
					userphone.setText(temphash.get("用户联系方式"));
					money.setText(temphash.get("金额"));
					state.setText(temphash.get("当前状态"));
					inmode.setText(temphash.get("接入方式"));
					jdtime.setText(temphash.get("接单时间"));
					isover.setText(temphash.get("是否结束"));
					remark.setText(temphash.get("预约备注"));
					pdtime.setText(temphash.get("派单时间"));
					wctime.setText(temphash.get("完成确认时间"));
					useraccount.setText(temphash.get("用户账号"));
					celladdress.setText(temphash.get("小区地址"));
					spremark.setText(temphash.get("另约审批备注"));
					Logid.setText(temphash.get("照片审批备注"));
					newaccountaddr.setText(temphash.get("开户地址"));
					imgfilename1=temphash.get("照片路径");
					imgfilename2=temphash.get("照片2路径");
//					if(!imgfilename1.equals("无"))
//					{
//						btn_downloadpic.setVisibility(View.VISIBLE);
//					}
//					else
//					{
//						btn_downloadpic.setVisibility(View.GONE);
//					}
					btn_downloadpic.setVisibility(View.GONE);
					Button cancel_bt = (Button) findViewById(R.id.btn_cancel);

					if(power.equals("维护人员"))
					{
						if(temphash.get("当前状态").equals("已接单") || temphash.get("当前状态").equals("待回单"))
						{
							cancel_bt.setText("回单");
						}else if(temphash.get("是否结束").equals("是") || temphash.get("当前状态").equals("申请弃单"))
						{
							cancel_bt.setVisibility(View.GONE);//已经结束的工单隐藏按钮
						}
						
//						if(myapp.GetNeedPhoto().toString().equals("照片审核") && (temphash.get("当前状态").equals("已接单") || temphash.get("当前状态").equals("待回单")))
//						{
//							if(temphash.get("照片审批结果").equals("拒绝"))
//							{
//								Bundle b=new Bundle();
//			            		b.putString("Id", Id);
//			            		b.putString("Msg", "你的工单审核被拒绝，请重新上传分光箱数据！");
//			            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
//			            		intent.putExtras(b);
//			            		startActivityForResult(intent,0);
//							}
//							else if(temphash.get("照片路径").equals("无"))
//							{
//								Bundle b=new Bundle();
//			            		b.putString("Id", Id);
//			            		b.putString("Msg", "点击选择或者扫描二维码获取当前分光箱位置");
//			            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
//			            		intent.putExtras(b);
//			            		startActivityForResult(intent,0);
//							}
//						}
						if(temphash.get("开户地址").equals("无") && (temphash.get("当前状态").equals("已接单") || temphash.get("当前状态").equals("待回单")))
						{
							Bundle b=new Bundle();
		            		b.putString("Id", Id);
		            		b.putString("Msg", "点击选择当前分光箱位置");
		            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
		            		intent.putExtras(b);
		            		startActivityForResult(intent,0);
						}
					}else
					{
					
						if(!(temphash.get("当前状态").equals("已提交") || temphash.get("当前状态").equals("已撤销")))//非维护人员界面下非已接单或已撤销状态时，无法点击按钮
						{
							cancel_bt.setVisibility(View.GONE);//已经结束的工单隐藏按钮
						}
						if((power.equals("超级管理员") || power.equals("县区管理员")) && temphash.get("当前状态").equals("申请弃单"))
						{
							cancel_bt.setVisibility(View.VISIBLE);
							cancel_bt.setText("弃单审核");
						}
						
						if((power.equals("超级管理员") || power.equals("县区管理员")) && temphash.get("当前状态").equals("已接单") && !temphash.get("照片路径").equals("无") && temphash.get("照片审批结果").equals("无"))
						{
							cancel_bt.setVisibility(View.VISIBLE);
							cancel_bt.setText("接单信息审核");
						}
					}
					if(temphash.get("是否结束").equals("是"))//工单结束状态时取消按钮失效，如果是已撤销状态，则允许删除工单
					{
						cancel_bt.setVisibility(View.GONE);//已经结束的工单隐藏按钮
					}
					
				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}
	
	public void DownloadImg(String fileName,String fileName1,final Toast toast) {
		new AsyncTask<String, Void, String>() {

			@Override  
		    protected void onPreExecute() 
			{
				
		    }
			
			@Override
			protected String doInBackground(String... params) {
				return dbUtil.DownloadImg(params[0], params[1]);
			}

			@Override
			protected void onPostExecute(String result) {
				if(result=="")
				{
					Toast.makeText(SinglequeryActivity.this, "加载失败", Toast.LENGTH_LONG).show();
				}
				else
				{
					toast.cancel();
					Toast.makeText(SinglequeryActivity.this,"照片加载完毕", Toast.LENGTH_LONG).show();
					String[] img=result.split("\\|");
					Log.d("DownloadImg",String.valueOf(img.length));
					if(img.length==1)
					{
						bmp_str1=img[0];
						img1txt.setVisibility(View.VISIBLE);
						img1.setVisibility(View.VISIBLE);
						byte[] img1buffer=Base64.decode(img[0]);
						Bitmap bmp1=Bytes2Bitmap(img1buffer);
						img1.setImageBitmap(bmp1);
					}
					else if(img.length==2)
					{
						bmp_str1=img[0];
						bmp_str2=img[1];
						img1txt.setVisibility(View.VISIBLE);
						img1.setVisibility(View.VISIBLE);
						byte[] img1buffer=Base64.decode(img[0]);
						Bitmap bmp1=Bytes2Bitmap(img1buffer);
						img1.setImageBitmap(bmp1);
						
						img2txt.setVisibility(View.VISIBLE);
						img2.setVisibility(View.VISIBLE);
						byte[] img2buffer=Base64.decode(img[1]);
						Bitmap bmp2=Bytes2Bitmap(img2buffer);
						img2.setImageBitmap(bmp2);
					}
				}
				super.onPostExecute(result);
			}
		}.execute(fileName,fileName1);

	}
	
	private Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


}
