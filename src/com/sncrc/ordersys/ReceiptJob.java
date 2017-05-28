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
	private String[] issuccess = { "��", "��" };
	private String[] reason_emp={"��"};
	private String[] reason_emp1={"��"};
	private String[] linetype = { "���ۺϲ���", "���ۺϲ���", "FTTHװ��", "ũ��װ��" };
//	private String[][] mainReason = { { "��" },// ��
//			{ "����ԭ��", "��Դԭ��", "�޷���װ","�������","�ͻ�ԭ��","����" } };// ��
//	private String[][][] reason = {
//			{ { "��" } },
//			{ { "�ͻ���ס�ؾ���������Զ", "�ͻ�����ס¥���޸���", "С������δ����" }, { "¼���ʹ����Ϻ�ʵ�ʲ���", "¼���ʹ����ϱ�������", "�豸������","�˿���������·����","�˿��������豸����" },
//			{ "�ͻ��Ұ��߲�ͨ", "�뻧���޷�����", "�ͻ��������׵�", "����ԭ��"},{"�ɵ����󣬿ͻ���ַ����","Bģʽ��Hģʽ����","�ͻ�δ��װ","�ʷѰ������"},{"��Լʱ��","�ͻ��޷���ϵ","�ͻ�ȡ����װ"},{"�о���","�������","�ظ�����","δ�Ҳ�","��·����","�������û�"} } };

	private String[][] mainReason = { { "��" },// ��
			{ "�û�ԭ��", "ǰ̨ԭ��", "����ԭ��","����ԭ��","����ԭ��" } };// ��
	private String[][][] reason = {
			{ { "��" } },
			{ { "ԭ��1:�û���ȷ��ʾ����װ", "ԭ��2:�û������ڲ���װ", "ԭ��3:�û������޷���ϵ��ܽӵ绰","ԭ��4:�û�/�ھ�/��ҵ��ͬ�������ߡ����ߡ���ǽ��Э������","ԭ��5:�ͻ���������������ʷ��ײͻ��û���װ����ַ��","ԭ��6:�û���Ϣ���޵�Դ�����û���ͬ��ͨ��POE����","ԭ��7:�û���Ը�򽻻�����һ��ΪУ԰�����" },
				{ "ԭ��8:ǰ̨ѡ���ַ��ʵ�����ز��������С������OLT��", "ԭ��9:ǰ̨ͬС����ѡ���ַ�����û�ʵ�ʰ�װ��ַδ����", "ԭ��10:�û���֪�鿪ͨ","ԭ��11:ǰ̨�ظ��ɵ����ɵ����󣨺��������ʹ���","ԭ��12:ǰ̨δ���ն��豸�������ն��豸�ɷ�����","ԭ��13:ǰ̨ҵ�����������ٰ�ҵ���ײʹ����������շ�����ȣ�","ԭ��14:ǰ̨Ӫ����Ա������ʵ�����ز���","ԭ��15:ǰ̨�ṩ�Ŀͻ���ϵ�˺���ϵ��ʽ����"},
			{ "ԭ��16:�޷��뻧���뻧�ܵ��ѱ�������Ӫ��ռ�õȣ�", "ԭ��17:�豸�˿ڻ�������Դ�����������޷�ʵʩ", "ԭ��18:װ����ַ���ʹ�ϵͳ�У���ʵ��δ���ǣ����ܰ�װ", "ԭ��19:�ƶ������޷������û�����Ϸ��Ҫ��","ԭ��20:���ܷ����û��������վ�������վ��","ԭ��21:�û���Ҫ�̶���IP��ַ","ԭ��22:�⹦�ʲ����"},
			{"ԭ��23:�ͻ���ס�ؾ���������Զ","ԭ��24:����¥��ǣ����ͻ�����ס¥���޸���","ԭ��25:����δ����","ԭ��26:������Ͷ���½���С�����߲�ͨ���޷�����","ԭ��27:�Ͼ�С������������·��ͨ���޷�����","ԭ��28:����δ�깤"},
			{"ԭ��29:��ҵ����","ԭ��30:����������","ԭ��31:�Խ����Ե�","ԭ��32:�ҵ�ʱ�䳤���¿ͻ�ȡ��װ��","ԭ��33:����"}} };	
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
		actionBar.setTitle("װ���ص�");
		myapp=(AppData)getApplication();
		// ��ҳ���������
		Bundle bundle = this.getIntent().getExtras();
		// ����nameֵ
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
				if(result.equals("��") && useraccount.equals(""))
				{
					Toast.makeText(ReceiptJob.this, "�������û��˺�", Toast.LENGTH_LONG).show();
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
					//���ɵ��˷��ͷ�������
					String[] sendnum=sender.getText().toString().split(":");
					if(issuccess.equals("��"))
					{
						sendSMS(sendnum[1],"[װ���ص�����]����Ϊ"+Id+"�Ĺ����ѳɹ�װ��������APP�鿴��ϸ��Ϣ��");
					}
					else
					{
						sendSMS(sendnum[1],"[װ���ص�����]����Ϊ"+Id+"�Ĺ�������������������APP�鿴��ϸ��Ϣ��");
					}
					finish();
				}else if(result.equals("���ϴ��ֹ�������"))
				{
					Bundle b=new Bundle();
            		b.putString("Id", Id);
            		b.putString("Msg", "���ϴ��ֹ������ݣ�");
            		Intent intent=new Intent(getApplication(),UploadImgActivity.class);
            		intent.putExtras(b);
					startActivity(intent);
				}
				super.onPostExecute(result);
			}
		}.execute(ID, issuccess, MainReason, Reason, SN, WiringMode,remark,useraccount,userid,needphoto);

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
					Idtxt.setText(temphash.get("ID"));
					address.setText(temphash.get("װ����ַ"));
					sender.setText(temphash.get("�ɵ���"));
					installedperson.setText(temphash.get("װ����"));
					account.setText(temphash.get("�û��˺�"));
				}
				super.onPostExecute(result);
			}
		}.execute(ID);

	}

	/*
	 * ����������
	 */
	private void setSpinner() {

		Spinner_issuccess = (Spinner) findViewById(R.id.Spinner_issuccess);
		Spinner_mainreason = (Spinner) findViewById(R.id.Spinner_mainreason);
		Spinner_reason = (Spinner) findViewById(R.id.Spinner_reason);
		Spinner_linetype = (Spinner) findViewById(R.id.Spinner_linetype);
		// ����������ֵ
		mainReasonAdapter=new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, reason_emp);
		Spinner_mainreason.setAdapter(mainReasonAdapter);
		Spinner_mainreason.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
		reasonAdapter=new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, reason_emp1);
		Spinner_reason.setAdapter(reasonAdapter);
		Spinner_reason.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
		linetypeAdapter = new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, linetype);
		Spinner_linetype.setAdapter(linetypeAdapter);
		Spinner_linetype.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ

		issuccessAdapter = new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item, issuccess);
		Spinner_issuccess.setAdapter(issuccessAdapter);
		if(mode.equals("0"))
		{
			Spinner_issuccess.setEnabled(true);
			Spinner_issuccess.setSelection(0, true); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�0��ֵ
		}else
		{
			Spinner_issuccess.setSelection(1); // ����Ĭ��ѡ����˴�ΪĬ��ѡ�е�1��ֵ
			Spinner_issuccess.setEnabled(false);
		}

		// ʡ�����������
		Spinner_issuccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					// ��ʾѡ��ı��ʱ�򴥷��˷�������Ҫʵ�ְ취����̬�ı�ؼ��������İ�ֵ
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// positionΪ��ǰʡ��ѡ�е�ֵ�����

						// ���ؼ���������ֵ�ı�Ϊcity[position]�е�ֵ
						mainReasonAdapter = new ArrayAdapter<String>(ReceiptJob.this,android.R.layout.simple_spinner_dropdown_item,mainReason[position]);
						// ���ö��������б��ѡ������������
						Spinner_mainreason.setAdapter(mainReasonAdapter);
						issuccessPosition = position; // ��¼��ǰʡ����ţ����������޸��ؼ�������ʱ��
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		// �ؼ���������
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
	 * ֱ�ӵ��ö��Žӿڷ�����
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public void sendSMS(String phoneNumber, String message) {
		// ��ȡ���Ź�����
		try{
			android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
			Log.d("sendSMS",phoneNumber+":"+message);
			// ��ֶ������ݣ��ֻ����ų������ƣ�
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
		}catch(Exception e)
		{
			Log.d("�ɵ�����","���Ͷ���ʧ�ܣ�");
		}
	}

}
