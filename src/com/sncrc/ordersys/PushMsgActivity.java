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
	private static final String[] country = {"---请选择---","全部","榆阳区", "神木", "府谷", "定边", "靖边","横山", "绥德", "米脂", "佳县", "吴堡", "清涧", "子洲", "大柳塔" };
	private static final String[] goal={"---请选择---","全部","超级管理员","县区管理员","客户经理","全市派单","维护人员"};
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
		actionBar.setTitle("推送消息");
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
	 * 根据值, 设置spinner默认选中:
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
	    int k= apsAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(apsAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// 默认选中项
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