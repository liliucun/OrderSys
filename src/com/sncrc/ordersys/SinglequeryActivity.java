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
		actionBar.setTitle("��������");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.accmanager);
		
		// ��ҳ���������
		Bundle bundle = this.getIntent().getExtras();
		// ����nameֵ
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
		//������벦��绰
		userphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ�ϲ������"+userphone.getText()+"?")
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
		}
		
		cancel_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(cancel_bt.getText().equals("����"))
				{		//��ά����ԱȨ��
					new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ�ϳ�����")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���
					    	if(remark.getText().toString().equals(""))
							{
					    		Toast.makeText(SinglequeryActivity.this, "�����볷��ԭ������µ������",Toast.LENGTH_SHORT).show();
								remark.setEnabled(true);
								return;
							}else
							{
								
								AccManagerOpt(Id, myapp.getUser(), "cancel",remark.getText().toString());
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
				else if(cancel_bt.getText().equals("�޸��ɵ���Ϣ"))
				{		//��ά����ԱȨ��
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
					tv1.setText("�û��绰:");
					tv2.setText("װ����ַ:");
					tv3.setText("����:");
					tv4.setText("�û��˺�:");
					tv5.setText("ԤԼ��ע:");
					tfee.setInputType(InputType.TYPE_CLASS_NUMBER);
					tuseraccount.setInputType(InputType.TYPE_CLASS_NUMBER);
					tuserphone.setInputType(InputType.TYPE_CLASS_NUMBER);
					tuserphone.setHint("�û��绰");
					tuseraddress.setHint("װ����ַ");
					tfee.setHint("����");
					tuseraccount.setHint("�û��˺�");
					tuserremark.setHint("ԤԼ��ע");
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
					new AlertDialog.Builder(SinglequeryActivity.this).setTitle("�޸��ɵ���Ϣ")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage("���µ���Ϣ�����Ӧ�����,�ύ�󽫻Ḳ��ԭ����Ϣ��")
					.setView(view)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���
					    	if(tuserphone.getText().length()==0 || tuseraddress.getText().length()==0 || tfee.getText().length()==0)
							{
					    		Toast.makeText(SinglequeryActivity.this, "�����������",Toast.LENGTH_SHORT).show();
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
								
								AccManagerOpt(Id, myapp.getUser(), "update",tuserphone.getText().toString()+"|"+tuseraddress.getText().toString()+"|"+tfee.getText().toString()+"|"+tuseraccount.getText().toString()+"|"+tuserremark.getText().toString()+"|");
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
				else if(cancel_bt.getText().equals("�������"))
				{
						final EditText txt=new EditText(SinglequeryActivity.this);
						txt.setHint("��˱�ע");
						new AlertDialog.Builder(SinglequeryActivity.this).setTitle("������ˣ�")
						.setMessage("���������ԭ�򣬲�����3���ַ�")
						.setView(txt)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("���ͨ��", new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// �����ȷ�ϡ���Ĳ���
						    	if(txt.getText().length()<3)
								{
						    		Toast.makeText(SinglequeryActivity.this, "��������˱�ע��������3�ַ�",Toast.LENGTH_SHORT).show();
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
								}else
								{
									new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ�����ͨ��?")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
									    	Qdsh("ok",Id,txt.getText().toString(),myapp.getUser());
									    }
									})
									.setNegativeButton("����", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
										// ��������ء���Ĳ���,���ﲻ����û���κβ���
									    }
									}).show();	
									try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
						    		    field.set(dialog, true );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}			 
						    }
						})
						.setNeutralButton("��˾ܾ�", new DialogInterface.OnClickListener() {
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// �����ȷ�ϡ���Ĳ���
						    	if(txt.getText().length()<3)
								{
						    		Toast.makeText(SinglequeryActivity.this, "��������˱�ע��������3�ַ�",Toast.LENGTH_SHORT).show();
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
								}else
								{
									new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ����˾ܾ�?")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
									    	Qdsh("refuse",Id,txt.getText().toString(),myapp.getUser());
									    }
									})
									.setNegativeButton("����", new DialogInterface.OnClickListener() {
							 
									    @Override
									    public void onClick(DialogInterface dialog, int which) {
										// ��������ء���Ĳ���,���ﲻ����û���κβ���
									    }
									}).show();	
									try 
						    		{
						    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
						    		    field.setAccessible( true );
						    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
						    		    field.set(dialog, true );
						    		    dialog.dismiss();
						    		}
						    		catch (Exception e)
						    		{}
								}
						    }
						})
						.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// ��������ء���Ĳ���,���ﲻ����û���κβ���
						    	try 
					    		{
					    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
					    		    field.setAccessible( true );
					    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
					    		    field.set(dialog, true );
					    		    dialog.dismiss();
					    		}
					    		catch (Exception e)
					    		{}
						    }
						}).show();
				}else if(cancel_bt.getText().equals("�ӵ���Ϣ���"))
				{
					LinearLayout view = new LinearLayout(SinglequeryActivity.this);
					view.setOrientation(LinearLayout.VERTICAL);
					final EditText remark=new EditText(SinglequeryActivity.this);
					final EditText account=new EditText(SinglequeryActivity.this);
					account.setInputType(InputType.TYPE_CLASS_NUMBER);
					account.setText(useraccount.getText());
					view.addView(account);
					view.addView(remark);
					remark.setHint("LOID/��˱�ע");
					account.setHint("�û��˺�");
					new AlertDialog.Builder(SinglequeryActivity.this).setTitle("�ӵ���Ϣ��ˣ�")
					.setMessage("�������û��˺ź�LOID/���ԭ��")
					.setView(view)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("���ͨ��", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���
					    	if(remark.getText().length()<3 || account.getText().length()!=11)
							{
					    		Toast.makeText(SinglequeryActivity.this, "�û��˺�Ϊ11λ�ֻ�����,LOID/��˱�ע������3���ַ�",Toast.LENGTH_SHORT).show();
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
							}else
							{
								new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ�����ͨ��?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
								    	Zpsh(Id,"ok",account.getText().toString(),remark.getText().toString(),myapp.getUser());
								    }
								})
								.setNegativeButton("����", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
									// ��������ء���Ĳ���,���ﲻ����û���κβ���
								    }
								}).show();	
								try 
					    		{
					    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
					    		    field.setAccessible( true );
					    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
					    		    field.set(dialog, true );
					    		    dialog.dismiss();
					    		}
					    		catch (Exception e)
					    		{}
							}			 
					    }
					})
					.setNeutralButton("��˾ܾ�", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���
					    	if(remark.getText().length()<3 || account.getText().length()!=11)
							{
					    		Toast.makeText(SinglequeryActivity.this, "�û��˺�Ϊ11λ�ֻ�����,LOID/��˱�ע������3���ַ�",Toast.LENGTH_SHORT).show();
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
							}else
							{
								new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ����˾ܾ�?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
								    	Zpsh(Id,"refuse",account.getText().toString(),remark.getText().toString(),myapp.getUser());
								    }
								})
								.setNegativeButton("����", new DialogInterface.OnClickListener() {
						 
								    @Override
								    public void onClick(DialogInterface dialog, int which) {
									// ��������ء���Ĳ���,���ﲻ����û���κβ���
								    }
								}).show();	
								try 
					    		{
					    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
					    		    field.setAccessible( true );
					    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
					    		    field.set(dialog, true );
					    		    dialog.dismiss();
					    		}
					    		catch (Exception e)
					    		{}
							}
					    }
					})
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						// ��������ء���Ĳ���,���ﲻ����û���κβ���
					    	try 
				    		{
				    		    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
				    		    field.setAccessible( true );
				    		     //   ��mShowing������Ϊfalse����ʾ�Ի����ѹر� 
				    		    field.set(dialog, true );
				    		    dialog.dismiss();
				    		}
				    		catch (Exception e)
				    		{}
					    }
					}).show();
				}
				else
				{		//ά����ԱȨ��
					if(((Button) findViewById(R.id.btn_cancel)).getText().equals("�ص�"))
					{
						Intent intent;
				    	intent = new Intent(SinglequeryActivity.this,ReceiptJob.class);
				    	Bundle bundle = new Bundle();
						// ����name����Ϊtinyphp
						bundle.putString("Id", Id);
						bundle.putString("mode", "0");
						intent.putExtras(bundle);
						startActivityForResult(intent,0);
					}else{
						new AlertDialog.Builder(SinglequeryActivity.this).setTitle("ȷ�Ͻӵ���")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				 
						    @Override
						    public void onClick(DialogInterface dialog, int which) {
							// �����ȷ�ϡ���Ĳ���
						    	AccManagerOpt(Id, myapp.getUser(), "accept", "");
				 
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
		
		btn_downloadpic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btn_downloadpic.setVisibility(View.GONE);
				Toast toast=Toast.makeText(SinglequeryActivity.this, "���ڼ�����Ƭ", Toast.LENGTH_LONG);
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
		switch (resultCode) { //resultCodeΪ�ش��ı�ǣ�����B�лش�����RESULT_OK
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
            		b.putString("Msg", "���ѡ��ǰ�ֹ���");
            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
            		intent.putExtras(b);
            		startActivityForResult(intent,0);
				}
				super.onPostExecute(result);
			}
		}.execute( ID,  user,  type, remark);

	}
	
	//�������
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
				if (result.equals("�����ɹ�")) 
				{
					finish();
				}
				super.onPostExecute(result);
			}
		}.execute( op,  id,  remark, name);

	}
	
	//��Ƭ���
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
				if (result.equals("�����ɹ�")) 
				{
					finish();
				}
				super.onPostExecute(result);
			}
		}.execute(id, op, account,  remark, name);

	}

	// ���ض�����Ϣ
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
					country.setText(temphash.get("����"));
					useraddress.setText(temphash.get("װ����ַ"));
					sender.setText(temphash.get("�ɵ���"));
					sendertype.setText(temphash.get("����"));
					person.setText(temphash.get("װ����"));
					receiver.setText(temphash.get("�ӵ���"));
					issuccess.setText(temphash.get("�Ƿ�ɹ�װ��"));
					area.setText(temphash.get("Ƭ��"));
					cell.setText(temphash.get("С��"));
					reason.setText(temphash.get("��ԭ��")+"|"+temphash.get("ԭ��"));
					usedtime.setText(temphash.get("��ʱ"));
					userphone.setText(temphash.get("�û���ϵ��ʽ"));
					money.setText(temphash.get("���"));
					state.setText(temphash.get("��ǰ״̬"));
					inmode.setText(temphash.get("���뷽ʽ"));
					jdtime.setText(temphash.get("�ӵ�ʱ��"));
					isover.setText(temphash.get("�Ƿ����"));
					remark.setText(temphash.get("ԤԼ��ע"));
					pdtime.setText(temphash.get("�ɵ�ʱ��"));
					wctime.setText(temphash.get("���ȷ��ʱ��"));
					useraccount.setText(temphash.get("�û��˺�"));
					celladdress.setText(temphash.get("С����ַ"));
					spremark.setText(temphash.get("��Լ������ע"));
					Logid.setText(temphash.get("��Ƭ������ע"));
					newaccountaddr.setText(temphash.get("������ַ"));
					imgfilename1=temphash.get("��Ƭ·��");
					imgfilename2=temphash.get("��Ƭ2·��");
//					if(!imgfilename1.equals("��"))
//					{
//						btn_downloadpic.setVisibility(View.VISIBLE);
//					}
//					else
//					{
//						btn_downloadpic.setVisibility(View.GONE);
//					}
					btn_downloadpic.setVisibility(View.GONE);
					Button cancel_bt = (Button) findViewById(R.id.btn_cancel);

					if(power.equals("ά����Ա"))
					{
						if(temphash.get("��ǰ״̬").equals("�ѽӵ�") || temphash.get("��ǰ״̬").equals("���ص�"))
						{
							cancel_bt.setText("�ص�");
						}else if(temphash.get("�Ƿ����").equals("��") || temphash.get("��ǰ״̬").equals("��������"))
						{
							cancel_bt.setVisibility(View.GONE);//�Ѿ������Ĺ������ذ�ť
						}
						
//						if(myapp.GetNeedPhoto().toString().equals("��Ƭ���") && (temphash.get("��ǰ״̬").equals("�ѽӵ�") || temphash.get("��ǰ״̬").equals("���ص�")))
//						{
//							if(temphash.get("��Ƭ�������").equals("�ܾ�"))
//							{
//								Bundle b=new Bundle();
//			            		b.putString("Id", Id);
//			            		b.putString("Msg", "��Ĺ�����˱��ܾ����������ϴ��ֹ������ݣ�");
//			            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
//			            		intent.putExtras(b);
//			            		startActivityForResult(intent,0);
//							}
//							else if(temphash.get("��Ƭ·��").equals("��"))
//							{
//								Bundle b=new Bundle();
//			            		b.putString("Id", Id);
//			            		b.putString("Msg", "���ѡ�����ɨ���ά���ȡ��ǰ�ֹ���λ��");
//			            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
//			            		intent.putExtras(b);
//			            		startActivityForResult(intent,0);
//							}
//						}
						if(temphash.get("������ַ").equals("��") && (temphash.get("��ǰ״̬").equals("�ѽӵ�") || temphash.get("��ǰ״̬").equals("���ص�")))
						{
							Bundle b=new Bundle();
		            		b.putString("Id", Id);
		            		b.putString("Msg", "���ѡ��ǰ�ֹ���λ��");
		            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
		            		intent.putExtras(b);
		            		startActivityForResult(intent,0);
						}
					}else
					{
					
						if(!(temphash.get("��ǰ״̬").equals("���ύ") || temphash.get("��ǰ״̬").equals("�ѳ���")))//��ά����Ա�����·��ѽӵ����ѳ���״̬ʱ���޷������ť
						{
							cancel_bt.setVisibility(View.GONE);//�Ѿ������Ĺ������ذ�ť
						}
						if((power.equals("��������Ա") || power.equals("��������Ա")) && temphash.get("��ǰ״̬").equals("��������"))
						{
							cancel_bt.setVisibility(View.VISIBLE);
							cancel_bt.setText("�������");
						}
						
						if((power.equals("��������Ա") || power.equals("��������Ա")) && temphash.get("��ǰ״̬").equals("�ѽӵ�") && !temphash.get("��Ƭ·��").equals("��") && temphash.get("��Ƭ�������").equals("��"))
						{
							cancel_bt.setVisibility(View.VISIBLE);
							cancel_bt.setText("�ӵ���Ϣ���");
						}
					}
					if(temphash.get("�Ƿ����").equals("��"))//��������״̬ʱȡ����ťʧЧ��������ѳ���״̬��������ɾ������
					{
						cancel_bt.setVisibility(View.GONE);//�Ѿ������Ĺ������ذ�ť
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
					Toast.makeText(SinglequeryActivity.this, "����ʧ��", Toast.LENGTH_LONG).show();
				}
				else
				{
					toast.cancel();
					Toast.makeText(SinglequeryActivity.this,"��Ƭ�������", Toast.LENGTH_LONG).show();
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
