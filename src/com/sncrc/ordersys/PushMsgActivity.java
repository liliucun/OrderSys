package com.sncrc.ordersys;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


public class PushMsgActivity extends Activity 
{
	private AppData myApp;
	private String Power;
	private DBUtil dbUtil;
	private static final String[] country = {"---��ѡ��---","ȫ��","������", "��ľ", "����", "����", "����","��ɽ", "���", "��֬", "����", "�Ɽ", "�彧", "����", "������" };
	private static final String[] goal={"---��ѡ��---","ȫ��","��������Ա","��������Ա","�ͻ�����","ȫ���ɵ�","ά����Ա"};
	private ArrayAdapter<String> adapter_county;
	private ArrayAdapter<String> adapter_goal;
	private Spinner spinner_county;
	private Spinner spinner_goal;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pushmsgactivity);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("������Ϣ");
		myApp=(AppData)getApplication();
		spinner_county=(Spinner)findViewById(R.id.Spinner_county);
		spinner_goal=(Spinner)findViewById(R.id.Spinner_goal);
		bindSpinner();
		
		if(!(myApp.getUser().equals("18209120621")))
		{
			spinner_county.setEnabled(false);
		}
		setSpinnerItemSelectedByValue(spinner_county,myApp.getCounty());
	}
	/**
	 * ����ֵ, ����spinnerĬ��ѡ��:
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		SpinnerAdapter apsAdapter= spinner.getAdapter(); //�õ�SpinnerAdapter����
	    int k= apsAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(apsAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// Ĭ��ѡ����
				break;
			}
		}
	}
	public void bindSpinner() {
		adapter_county = new ArrayAdapter<String>(this,R.layout.spinner, country);
		adapter_county.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_county.setAdapter(adapter_county);
		spinner_county.setSelection(0, true);
		
		adapter_goal = new ArrayAdapter<String>(this,R.layout.spinner, goal);
		adapter_goal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_goal.setAdapter(adapter_goal);
		spinner_goal.setSelection(0, true);
	}
}