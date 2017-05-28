package com.sncrc.ordersys;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MsgViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msgview);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("œ˚œ¢œÍ«È");
		Bundle bundle = this.getIntent().getExtras();
		String title=bundle.getString("Title");
		String text=bundle.getString("Text");
		TextView Title=(TextView)findViewById(R.id.title);
		TextView Text=(TextView)findViewById(R.id.text);
		Title.setText(title);
		Text.setText(text);
	}
}