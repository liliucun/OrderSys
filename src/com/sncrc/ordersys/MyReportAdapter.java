package com.sncrc.ordersys;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyReportAdapter extends SimpleAdapter{

	private int MaxColNum=15;
	private TextView[] ReportCol=new TextView[MaxColNum];
	private View[] div=new View[MaxColNum];
	private int[] TextViewLayout={R.id.col1,R.id.col2,R.id.col3,R.id.col4,R.id.col5,R.id.col6,R.id.col7,R.id.col8,R.id.col9,R.id.col10,R.id.col11,R.id.col12,R.id.col13,R.id.col14,R.id.col15};
	private int[] divLayout={R.id.div1,R.id.div2,R.id.div3,R.id.div4,R.id.div5,R.id.div6,R.id.div7,R.id.div8,R.id.div9,R.id.div10,R.id.div11,R.id.div12,R.id.div13,R.id.div14,R.id.div15};
	private int dispcolnum=MaxColNum;
	
	public MyReportAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	 public View getView(int position,View contentView,ViewGroup parent){
	  
		View v = super.getView(position, contentView, parent);
		//Log.d("MyReportAdapter",String.valueOf(position));
		SetColVisible(v);
		if (position % 2 == 0) {//奇偶行背景色
			v.setBackgroundColor(Color.parseColor("#F5F5DC"));
		}else {
			v.setBackgroundColor(Color.parseColor("#F5DEB3"));
		}
		//当然在这还可以对行加上点击事件
		return v;
	  
	 }
	private void SetColVisible(View view)
	{
		for(int i=0;i<MaxColNum;i++)
		{
			ReportCol[i]=(TextView)view.findViewById(TextViewLayout[i]);
			div[i]=(View)view.findViewById(divLayout[i]);
			if(i<dispcolnum)
			{
				ReportCol[i].setVisibility(View.VISIBLE);
				div[i].setVisibility(View.VISIBLE);
			}
			else
			{
				ReportCol[i].setVisibility(View.GONE);
				div[i].setVisibility(View.GONE);
			}
		}
	}
	public void SetDisplayColNum(int num)
	{
		this.dispcolnum=num;
	}
	
}