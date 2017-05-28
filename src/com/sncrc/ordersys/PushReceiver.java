package com.sncrc.ordersys;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

import android.app.Activity;
public class PushReceiver extends BroadcastReceiver{
	private DBUtil dbUtil;
	private AppData myApp;
	@Override
	public void onReceive(Context context, Intent intent) {
		dbUtil = new DBUtil();
		myApp=(AppData)context.getApplicationContext();
		Bundle bundle = intent.getExtras();
		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
			case PushConsts.GET_MSG_DATA:
				// payload
				byte[] payload = bundle.getByteArray("payload");
				if (payload != null)
				{
					String data = new String(payload);
					Log.d("GetuiSdkDemo", "Got Payload:" + data);
					// TODO:payload
					String[] action=data.split(":");

					if(action[0].equals("openFault"))
					{
						if(!myApp.getUser().equals(""))
						{
							intent = new Intent(context.getApplicationContext(),FaultSinglequerryActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							// ��BundleЯ������
							Bundle bundle1 = new Bundle();
							// ����name����Ϊtinyphp
							bundle1.putString("Id", action[1]);
							intent.putExtras(bundle1);
							context.getApplicationContext().startActivity(intent);
						}
						else
						{
							intent = new Intent(context.getApplicationContext(),loginActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							context.getApplicationContext().startActivity(intent);
						}
					}
					else if(action[0].equals("Msg"))
					{
						String[] msg=action[1].split("\\|");
						intent = new Intent(context.getApplicationContext(),MsgViewActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
						// ��BundleЯ������
						Bundle bundle1 = new Bundle();
						// ����name����Ϊtinyphp
						bundle1.putString("Title", msg[0]);
						bundle1.putString("Text", msg[1]);
						intent.putExtras(bundle1);
						context.getApplicationContext().startActivity(intent);
					}
					else if(action[0].equals("openzd"))
					{
						if(!myApp.getUser().equals(""))
						{
							intent = new Intent(context.getApplicationContext(),ZdDetailActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							// ��BundleЯ������
							Bundle bundle1 = new Bundle();
							// ����name����Ϊtinyphp
							bundle1.putString("Id", action[1]);
							intent.putExtras(bundle1);
							context.getApplicationContext().startActivity(intent);
						}
						else
						{
							intent = new Intent(context.getApplicationContext(),loginActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							context.getApplicationContext().startActivity(intent);
						}
					}
				}
				break;
			case PushConsts.GET_CLIENTID:
				String cid = bundle.getString("clientid");
				Log.d("GetuiSdkDemo", "Got ClientID:" + cid);
				Tag[] tag=new Tag[2];
				Tag t=new Tag();
				t.setName(myApp.getPower());
				tag[0]=t;
				Tag t1=new Tag();
				t1.setName(myApp.getCounty()+myApp.getPower());
				tag[1]=t1;
				int i =PushManager.getInstance().setTag(context, tag);
				String text="";
				switch (i) {
			    case PushConsts.SETTAG_SUCCESS:
			        text = "���ñ�ǩ�ɹ�";
			        break;
			 
			    case PushConsts.SETTAG_ERROR_COUNT:
			        text = "���ñ�ǩʧ��, tag��������, ����ܳ���200��";
			        break;
			 
			    case PushConsts.SETTAG_ERROR_FREQUENCY:
			        text = "���ñ�ǩʧ��, Ƶ�ʹ���, ���μ��Ӧ����1s";
			        break;
			 
			    case PushConsts.SETTAG_ERROR_REPEAT:
			        text = "���ñ�ǩʧ��, ��ǩ�ظ�";
			        break;
			 
			    case PushConsts.SETTAG_ERROR_UNBIND:
			        text = "���ñ�ǩʧ��, ����δ��ʼ���ɹ�";
			        break;
			 
			    case PushConsts.SETTAG_ERROR_EXCEPTION:
			        text = "���ñ�ǩʧ��, δ֪�쳣";
			        break;
			 
			    case PushConsts.SETTAG_ERROR_NULL:
			        text = "���ñ�ǩʧ��, tag Ϊ��";
			        break;
			 
			    default:
			        break;
				}
				Log.d("����Tag", text);
				UploadCID(myApp.getUser(),cid);
				break;
			default:
			break;
		}
	}
	
	public void UploadCID(String User,String CID)
	{
		new AsyncTask<String,Void,String>() {
			protected String doInBackground(String... params) {
				String result;
				result=dbUtil.UploadCID(params[0],params[1]);
				return  result;
			
			}
	
			@Override
			protected void onPostExecute(String result) {
				Log.d("UploadCID","CID�ϴ����:"+result);
				super.onPostExecute(result);
			}
			
		}.execute(User,CID);

	
	}
}