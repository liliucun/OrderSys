package com.sncrc.ordersys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SelectListViewActivity extends Activity {
	private DBUtil dbUtil;
	private ListView celllist;
	private String County,Area,opt;
	private List<HashMap<String,String>> SourceDateList = new ArrayList<HashMap<String,String>>();
	private List<HashMap<String,String>> filterDateList = new ArrayList<HashMap<String,String>>();
	private SimpleAdapter adapter;
	
	private int state=0;
	private String Fgq_County="",Fgq_Cell="",Fgq_Addr="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectlistview);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("С���б�");
		dbUtil = new DBUtil();
		Bundle bundle = this.getIntent().getExtras();
		County=bundle.getString("County");
		Area=bundle.getString("Area");
		opt=bundle.getString("Opt");
		celllist=(ListView)findViewById(R.id.celllist);
		//�������������ֵ�ĸı�����������
		ClearEditText mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		if(opt.equals("AddJob"))
		{
			celllist.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					HashMap<String, String> map = (HashMap<String, String>) celllist.getItemAtPosition(position);
					String cell=map.get("С��").toString();
					Bundle b=new Bundle();
					b.putString("cell", cell);
					Intent intent=new Intent();
					intent.putExtras(b);
					setResult(RESULT_OK,intent);
					finish();
				}
			});
			GetList();
		}
		else if(opt.equals("GetFgqAddr"))
		{
			actionBar.setTitle("�ֹ�����ַ��ѯ");
			actionBar.setDisplayHomeAsUpEnabled(true); 

			celllist.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					HashMap<String, String> map = (HashMap<String, String>) celllist.getItemAtPosition(position);
					String cell=map.get("С��").toString();
					if(state==0)
					{
						Fgq_County=cell;
						GetCell();
						state++;
					}
					else if(state==1)
					{
						Fgq_Cell=cell;
						GetAddr();
						state++;
					}
					else if(state==2)
					{
						Bundle b=new Bundle();
						b.putString("cell", cell);
						b.putString("fgqnum",map.get("�豸����").toString());
						Intent intent=new Intent();
						intent.putExtras(b);
						setResult(RESULT_OK,intent);
						finish();
					}
				}
			});
			GetCounty();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if(state==0)
				{
					finish();
				}
				if(state==1)
				{
					GetCounty();
					state--;
				}
				else if(state==2)
				{
					GetCell();
					state--;
				}
			default:
			return super.onOptionsItemSelected(item);
		}
	} 

	private void GetList()
	{
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {

				return dbUtil.SelectCell(params[0],params[1]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				result.remove(0);
				SourceDateList.addAll(result);
				filterDateList.addAll(result);
				adapter = new SimpleAdapter(SelectListViewActivity.this,filterDateList,R.layout.selectlistview_div,new String[] {"С��","С����ϸ��ַ"},new int[] {R.id.CellName,R.id.CellAddress});
				celllist.setAdapter(adapter);
				super.onPostExecute(result);
			}
		}.execute(County,Area);
	}
	
	private void GetCounty()
	{
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				return dbUtil.GetCounty();
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				SourceDateList.clear();
				filterDateList.clear();
				SourceDateList.addAll(result);
				filterDateList.addAll(SourceDateList);
				adapter = new SimpleAdapter(SelectListViewActivity.this,filterDateList,R.layout.selectlistview_div_logid,new String[] {"С��"},new int[] {R.id.CellName});
				celllist.setAdapter(adapter);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	private void GetCell()
	{
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				return dbUtil.GetCell(params[0]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				SourceDateList.clear();
				filterDateList.clear();
				SourceDateList.addAll(result);
				filterDateList.addAll(SourceDateList);
				adapter = new SimpleAdapter(SelectListViewActivity.this,filterDateList,R.layout.selectlistview_div_logid,new String[] {"С��"},new int[] {R.id.CellName});
				celllist.setAdapter(null);
				celllist.setAdapter(adapter);
				super.onPostExecute(result);
			}
		}.execute(Fgq_County);
	}
	
	private void GetAddr()
	{
		new AsyncTask<String, Void, List<HashMap<String, String>>>() {

			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... params) {
				return dbUtil.GetAddr(params[0],params[1]);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, String>> result) {
				SourceDateList.clear();
				filterDateList.clear();
				SourceDateList.addAll(result);
				filterDateList.addAll(SourceDateList);
				adapter = new SimpleAdapter(SelectListViewActivity.this,filterDateList,R.layout.selectlistview_div,new String[] {"С��","�豸����"},new int[] {R.id.CellName,R.id.CellAddress});
				celllist.setAdapter(null);
				celllist.setAdapter(adapter);
				super.onPostExecute(result);
			}
		}.execute(Fgq_County,Fgq_Cell);
	}
	

	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList.clear();
			filterDateList.addAll(SourceDateList);
		}else{
			filterDateList.clear();
			for(HashMap<String,String> cell : SourceDateList){
				if(opt.equals("AddJob"))
				{
					String cellname = cell.get("С��").toString();
					String celladdress=cell.get("С����ϸ��ַ").toString();
					if(cellname.indexOf(filterStr.toString()) != -1 || celladdress.indexOf(filterStr.toString()) != -1){
						filterDateList.add(cell);
					}
				}
				else if(opt.equals("GetFgqAddr"))
				{
					String cellname = cell.get("С��").toString();
					if(cellname.indexOf(filterStr.toString()) != -1){
						filterDateList.add(cell);
					}
				}
			}
		}
		adapter.notifyDataSetChanged();
	}
}