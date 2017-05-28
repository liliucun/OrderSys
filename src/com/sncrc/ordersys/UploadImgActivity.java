package com.sncrc.ordersys;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kobjects.base64.Base64;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
//import com.karics.library.zxing.android.CaptureActivity;

public class UploadImgActivity extends Activity {
	private DBUtil dbUtil = new DBUtil();
	private AppData myapp;
	private String power,Id,serialnum;
	private ImageView img1,img2;
	private Button btn_cancel,btn_upload,btn_selfgqaddr;//btn_scanbarcode;
	private File file_go;
	private Bitmap bitmap1,bitmap2;
	private TextView msg;
	private TextView state,radius,latitude,longitude,locationdesc,fgqaddr;
	private static final String[] port = {"1", "2", "3", "4", "5", "6","7","8", "9", "10", "11", "12", "13", "14","15","16","17","18","19","20","21","22","23","24"};
	private Spinner Spinner_port;
	
	public LocationClient mLocationClient = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	    mLocationClient.registerLocationListener( mListener );    //注册监听函数
		initLocation();
		Bundle bundle = this.getIntent().getExtras();
		Id = bundle.getString("Id");
		String m=bundle.getString("Msg");
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("上传设备信息");
		myapp=(AppData)getApplication();
		power=myapp.getPower();
		setContentView(R.layout.uploadimgactivity);
		img1=(ImageView)findViewById(R.id.img1);
		img2=(ImageView)findViewById(R.id.img2);
		btn_cancel=(Button)findViewById(R.id.btn_cancel);
		btn_upload=(Button)findViewById(R.id.btn_upload);
		btn_selfgqaddr=(Button)findViewById(R.id.btn_selfgqaddr);
//		btn_scanbarcode=(Button)findViewById(R.id.btn_scanbarcode);
		msg=(TextView)findViewById(R.id.msg);
		fgqaddr=(TextView)findViewById(R.id.fgqaddr);
		
		state=(TextView)findViewById(R.id.state);
		radius=(TextView)findViewById(R.id.radius);
		latitude=(TextView)findViewById(R.id.latitude);
		longitude=(TextView)findViewById(R.id.longitude);
		locationdesc=(TextView)findViewById(R.id.location);
		
		ArrayAdapter<String> adapter;
		Spinner_port = (Spinner) findViewById(R.id.Spinner_port);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, port);
		Spinner_port.setAdapter(adapter);
		Spinner_port.setSelection(0, true);
		
		msg.setText(m);
		img1.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				OpenCamera(0x1);
			}
		});
		img2.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				OpenCamera(0x2);
			}
		});
		btn_upload.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				if(fgqaddr.getText().equals("分光器地址"))
				{
					Toast.makeText(UploadImgActivity.this, "请选择分光器地址", Toast.LENGTH_LONG).show();
					return;
				}
				UploadImg(Id,"空","","","",fgqaddr.getText().toString(),longitude.getText().toString(),latitude.getText().toString(),locationdesc.getText().toString(),serialnum,Spinner_port.getSelectedItem().toString());
//				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
//				String timestr=df.format(new Date());
//		    	String filename=timestr+"_"+Id+".jpg";
//		    	String filename1=timestr+"_"+Id+"1.jpg";
//
//				if(bitmap1==null)
//				{
//					Toast.makeText(UploadImgActivity.this, "请拍照后再上传", Toast.LENGTH_LONG).show();
//				}
//				else if(bitmap2==null)
//				{
//					byte[] photoData1 = Bitmap2Bytes(bitmap1);
//					String buffer1=new String(Base64.encode(photoData1));
//					UploadImg(Id,filename,buffer1,"","",btn_selfgqaddr.getText().toString(),longitude.getText().toString(),latitude.getText().toString(),locationdesc.getText().toString());
//				}else
//				{
//					byte[] photoData1 = Bitmap2Bytes(bitmap1);
//					String buffer1=new String(Base64.encode(photoData1));
//					byte[] photoData2 = Bitmap2Bytes(bitmap2);
//					String buffer2=new String(Base64.encode(photoData2));
//					UploadImg(Id,filename,buffer1,filename1,buffer2,btn_selfgqaddr.getText().toString(),longitude.getText().toString(),latitude.getText().toString(),locationdesc.getText().toString());
//				}
				
			}
		});
		
//		btn_scanbarcode.setOnClickListener(new OnClickListener() 
//		{
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(UploadImgActivity.this,CaptureActivity.class);
//				startActivityForResult(intent, 0x01);
//			}
//		});
		
		btn_cancel.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				Intent intent;
		    	intent = new Intent(getApplication(),ReceiptJob.class);
		    	Bundle bundle = new Bundle();
				// 传递name参数为tinyphp
				bundle.putString("Id", Id);
				bundle.putString("mode", "1");
				intent.putExtras(bundle);
				startActivityForResult(intent,3);
			}
		});
		btn_selfgqaddr.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				Bundle b=new Bundle();
				b.putString("Opt", "GetFgqAddr");
				Intent intent=new Intent(UploadImgActivity.this,SelectListViewActivity.class);
				intent.putExtras(b);
				startActivityForResult(intent, 4);
			}
		});
	}
	
	private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        //option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

	
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			String statestr="";
			switch (location.getLocType())
			{
				case BDLocation.TypeGpsLocation:
					statestr="GPS定位成功";
					break;
				case BDLocation.TypeNetWorkLocation:
					statestr="网络定位成功";
					break;
				case BDLocation.TypeOffLineLocation:
					statestr="离线定位成功";
					break;
				case BDLocation.TypeServerError:
					statestr="服务端定位失败";
					break;
				case BDLocation.TypeNetWorkException:
					statestr="网络不通定位失败";
					break;
				case BDLocation.TypeCriteriaException:
					statestr="无有效定位依据";
					break;
				default:
					statestr="状态码:"+location.getLocType();
					break;
			}

			state.setText(statestr);
			radius.setText(String.valueOf(location.getRadius()));
			latitude.setText(String.valueOf(location.getLatitude()));
			longitude.setText(String.valueOf(location.getLongitude()));
			locationdesc.setText(location.getLocationDescribe());
			mLocationClient.stop();
		}
	};
	
	//拍照结束后显示图片;　　
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0x01 && resultCode == RESULT_OK) {
			if (data != null) {

				String content = data.getStringExtra("codedContent");
				serialnum=new String(content);
				//Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
				GetAddrName(content);
			}
		}
//		int width=480;//输出图片的宽度
//		// TODO Auto-generated method stub　　
//		// 判断请求码和结果码是否正确，如果正确的话就在activity上显示刚刚所拍照的图片;　　
//		if(requestCode==3 && resultCode == Activity.RESULT_OK)
//		{
//			setResult(RESULT_OK);
//			finish();
//		}
//		else if ((requestCode==0x1 || requestCode==0x2) && resultCode == Activity.RESULT_OK) 
//		{
//			/* 使用BitmapFactory.Options类防止OOM(Out Of Memory)的问题；　　创建一个BitmapFactory.Options类用来处理bitmap；*/
//			BitmapFactory.Options myoptions=new BitmapFactory.Options();
//			/* 设置Options对象inJustDecodeBounds的属性为true，用于在BitmapFactory的　　
//			 * decodeFile(String path, Options opt)后获取图片的高和宽；　　
//			 * 而且设置了他的属性值为true后使用BitmapFactory的decodeFile()方法无法返回一张　　
//			 * 图片的bitmap对象，仅仅是把图片的高和宽信息给Options对象；　　*/
//			myoptions.inJustDecodeBounds=true;
//			BitmapFactory.decodeFile(file_go.getAbsolutePath(),myoptions);
//			//还没完这里才刚开始,要节约内存还需要几个属性，下面是最关键的一个；　　
//			myoptions.inSampleSize=myoptions.outWidth/width;
//			//根据在图片的宽和高，得到图片在不变形的情况指定大小下的缩略图,设置宽为222；　　
//			int height=myoptions.outHeight*width/myoptions.outWidth;
//			myoptions.outWidth=width;
//			myoptions.outHeight=height;
//			//在重新设置玩图片显示的高和宽后记住要修改，Options对象inJustDecodeBounds的属性为false;　　//不然无法显示图片;　　
//			myoptions.inJustDecodeBounds=false;
//			//成功了，下面就显示图片咯；　　
//			if(requestCode==0x1)
//			{
//				bitmap1 = BitmapFactory.decodeFile(file_go.getAbsolutePath(),myoptions);
//				img1.setImageBitmap(bitmap1);
//				img2.setVisibility(View.VISIBLE);
//			}else if(requestCode==0x2)
//			{
//				bitmap2 = BitmapFactory.decodeFile(file_go.getAbsolutePath(),myoptions);
//				img2.setImageBitmap(bitmap2);
//			}
//	   
//		} else {
//			Log.d("UploadImg","拍摄照片失败");
//		}
		if(requestCode==4 && resultCode == Activity.RESULT_OK)
		{
			fgqaddr.setText(data.getExtras().getString("cell"));
			serialnum=new String(data.getExtras().getString("fgqnum"));
		}
	}
	
	@Override
    public void onBackPressed() {
		new AlertDialog.Builder(UploadImgActivity.this).setTitle("暂不上传数据？")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// 点击“确认”后的操作
		    	//setResult(RESULT_OK);
				finish();			 
		    }
		})
		.setNegativeButton("返回", new DialogInterface.OnClickListener() {
 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// 点击“返回”后的操作,这里不设置没有任何操作
		    }
		}).show();
    }
	
	public void UploadImg(String ID,String fileName, String imageBuffer,String fileName1,String imageBuffer1,String fgqaddr,String longitude,String latitude,String location,String fgqnum,String fgqport) {
		new AsyncTask<String, Void, String>() {

			@Override  
		    protected void onPreExecute() {  
				btn_upload.setEnabled(false);
				btn_cancel.setEnabled(false); 
		    }
			
			@Override
			protected String doInBackground(String... params) {
				return dbUtil.UploadImg(params[0], params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10]);
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(UploadImgActivity.this, result, Toast.LENGTH_LONG).show();
				if (result.equals("上传图片成功") || result.equals("上传数据完成")) 
				{
					finish();
				}
				else
				{
					btn_upload.setEnabled(true);
					btn_cancel.setEnabled(true);
				}
				super.onPostExecute(result);
			}
		}.execute(ID,fileName,imageBuffer,fileName1,imageBuffer1,fgqaddr,longitude,latitude,location,fgqnum,fgqport);

	}
	
	public void GetAddrName(String AddrID) {
		new AsyncTask<String, Void, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				return dbUtil.GetAddrName(params[0]);
			}

			@Override
			protected void onPostExecute(String result) {
				fgqaddr.setText(result);
				super.onPostExecute(result);
			}
		}.execute(AddrID);

	}
	
	public byte[] Bitmap2Bytes(Bitmap bm) 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	
	private void OpenCamera(int tag)
	{
		// 获取sd卡根目录地址,并创建图片父目录文件对象和文件的对象;　　
    	String file_str = Environment.getExternalStorageDirectory().getPath();
    	File mars_file = new File(file_str + "/OrderSys");
//    	DateFormat df = new SimpleDateFormat("yyMMddHHmmss"); 
//    	String filename=df.format(new Date()); 
    	file_go = new File(file_str + "/OrderSys/"+Id+".jpg");
    	// 验证sd卡是否正确安装：
    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) 
    	{
    		// 先创建父目录，如果新创建一个文件的时候，父目录没有存在，那么必须先创建父目录，再新建文件。　　
    		if (!mars_file.exists()) {mars_file.mkdirs();}
    		// 设置跳转的系统拍照的activity为：MediaStore.ACTION_IMAGE_CAPTURE ;　　
    		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    		// 并设置拍照的存在方式为外部存储和存储的路径；
    		intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file_go));
    		//跳转到拍照界面;　　
    		startActivityForResult(intent, tag);
    	} else 
    	{
    		Toast.makeText(UploadImgActivity.this, "请先安装好sd卡",Toast.LENGTH_LONG).show();
    	}
	}
}