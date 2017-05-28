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
		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
	    mLocationClient.registerLocationListener( mListener );    //ע���������
		initLocation();
		Bundle bundle = this.getIntent().getExtras();
		Id = bundle.getString("Id");
		String m=bundle.getString("Msg");
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("�ϴ��豸��Ϣ");
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
				if(fgqaddr.getText().equals("�ֹ�����ַ"))
				{
					Toast.makeText(UploadImgActivity.this, "��ѡ��ֹ�����ַ", Toast.LENGTH_LONG).show();
					return;
				}
				UploadImg(Id,"��","","","",fgqaddr.getText().toString(),longitude.getText().toString(),latitude.getText().toString(),locationdesc.getText().toString(),serialnum,Spinner_port.getSelectedItem().toString());
//				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
//				String timestr=df.format(new Date());
//		    	String filename=timestr+"_"+Id+".jpg";
//		    	String filename1=timestr+"_"+Id+"1.jpg";
//
//				if(bitmap1==null)
//				{
//					Toast.makeText(UploadImgActivity.this, "�����պ����ϴ�", Toast.LENGTH_LONG).show();
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
				// ����name����Ϊtinyphp
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
        option.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
        int span=1000;
        //option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        //option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        //option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        option.setIgnoreKillProcess(false);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
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
					statestr="GPS��λ�ɹ�";
					break;
				case BDLocation.TypeNetWorkLocation:
					statestr="���綨λ�ɹ�";
					break;
				case BDLocation.TypeOffLineLocation:
					statestr="���߶�λ�ɹ�";
					break;
				case BDLocation.TypeServerError:
					statestr="����˶�λʧ��";
					break;
				case BDLocation.TypeNetWorkException:
					statestr="���粻ͨ��λʧ��";
					break;
				case BDLocation.TypeCriteriaException:
					statestr="����Ч��λ����";
					break;
				default:
					statestr="״̬��:"+location.getLocType();
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
	
	//���ս�������ʾͼƬ;����
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
//		int width=480;//���ͼƬ�Ŀ��
//		// TODO Auto-generated method stub����
//		// �ж�������ͽ�����Ƿ���ȷ�������ȷ�Ļ�����activity����ʾ�ո������յ�ͼƬ;����
//		if(requestCode==3 && resultCode == Activity.RESULT_OK)
//		{
//			setResult(RESULT_OK);
//			finish();
//		}
//		else if ((requestCode==0x1 || requestCode==0x2) && resultCode == Activity.RESULT_OK) 
//		{
//			/* ʹ��BitmapFactory.Options���ֹOOM(Out Of Memory)�����⣻��������һ��BitmapFactory.Options����������bitmap��*/
//			BitmapFactory.Options myoptions=new BitmapFactory.Options();
//			/* ����Options����inJustDecodeBounds������Ϊtrue��������BitmapFactory�ġ���
//			 * decodeFile(String path, Options opt)���ȡͼƬ�ĸߺͿ�����
//			 * ������������������ֵΪtrue��ʹ��BitmapFactory��decodeFile()�����޷�����һ�š���
//			 * ͼƬ��bitmap���󣬽����ǰ�ͼƬ�ĸߺͿ���Ϣ��Options���󣻡���*/
//			myoptions.inJustDecodeBounds=true;
//			BitmapFactory.decodeFile(file_go.getAbsolutePath(),myoptions);
//			//��û������Ÿտ�ʼ,Ҫ��Լ�ڴ滹��Ҫ�������ԣ���������ؼ���һ��������
//			myoptions.inSampleSize=myoptions.outWidth/width;
//			//������ͼƬ�Ŀ�͸ߣ��õ�ͼƬ�ڲ����ε����ָ����С�µ�����ͼ,���ÿ�Ϊ222������
//			int height=myoptions.outHeight*width/myoptions.outWidth;
//			myoptions.outWidth=width;
//			myoptions.outHeight=height;
//			//������������ͼƬ��ʾ�ĸߺͿ���סҪ�޸ģ�Options����inJustDecodeBounds������Ϊfalse;����//��Ȼ�޷���ʾͼƬ;����
//			myoptions.inJustDecodeBounds=false;
//			//�ɹ��ˣ��������ʾͼƬ��������
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
//			Log.d("UploadImg","������Ƭʧ��");
//		}
		if(requestCode==4 && resultCode == Activity.RESULT_OK)
		{
			fgqaddr.setText(data.getExtras().getString("cell"));
			serialnum=new String(data.getExtras().getString("fgqnum"));
		}
	}
	
	@Override
    public void onBackPressed() {
		new AlertDialog.Builder(UploadImgActivity.this).setTitle("�ݲ��ϴ����ݣ�")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// �����ȷ�ϡ���Ĳ���
		    	//setResult(RESULT_OK);
				finish();			 
		    }
		})
		.setNegativeButton("����", new DialogInterface.OnClickListener() {
 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// ��������ء���Ĳ���,���ﲻ����û���κβ���
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
				if (result.equals("�ϴ�ͼƬ�ɹ�") || result.equals("�ϴ��������")) 
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
		// ��ȡsd����Ŀ¼��ַ,������ͼƬ��Ŀ¼�ļ�������ļ��Ķ���;����
    	String file_str = Environment.getExternalStorageDirectory().getPath();
    	File mars_file = new File(file_str + "/OrderSys");
//    	DateFormat df = new SimpleDateFormat("yyMMddHHmmss"); 
//    	String filename=df.format(new Date()); 
    	file_go = new File(file_str + "/OrderSys/"+Id+".jpg");
    	// ��֤sd���Ƿ���ȷ��װ��
    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) 
    	{
    		// �ȴ�����Ŀ¼������´���һ���ļ���ʱ�򣬸�Ŀ¼û�д��ڣ���ô�����ȴ�����Ŀ¼�����½��ļ�������
    		if (!mars_file.exists()) {mars_file.mkdirs();}
    		// ������ת��ϵͳ���յ�activityΪ��MediaStore.ACTION_IMAGE_CAPTURE ;����
    		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    		// ���������յĴ��ڷ�ʽΪ�ⲿ�洢�ʹ洢��·����
    		intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file_go));
    		//��ת�����ս���;����
    		startActivityForResult(intent, tag);
    	} else 
    	{
    		Toast.makeText(UploadImgActivity.this, "���Ȱ�װ��sd��",Toast.LENGTH_LONG).show();
    	}
	}
}