package com.sncrc.ordersys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kobjects.base64.Base64;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class ImgViewerActivity extends Activity {
	private PhotoView img;
	private Button btn;
	private String bmp;
	private Bitmap bmp1;
	private AppData myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		// ���ر�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		// ����״̬��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.imgviewer);
		myapp=(AppData)getApplication();
		bmp = myapp.getBitmapData();
		myapp.setBitmapData("");
		img=(PhotoView)findViewById(R.id.img);
		btn=(Button)findViewById(R.id.btn_save);
		
		byte[] img1buffer=Base64.decode(bmp);
		bmp1=Bytes2Bitmap(img1buffer);
		img.setImageBitmap(bmp1);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ��ȡsd����Ŀ¼��ַ,������ͼƬ��Ŀ¼�ļ�������ļ��Ķ���;����
		    	String file_str = Environment.getExternalStorageDirectory().getPath();
		    	File mars_file = new File(file_str + "/OrderSys");
		    	DateFormat df = new SimpleDateFormat("yyMMddHHmmss"); 
		    	String filename=df.format(new Date()); 
		    	String path=file_str + "/OrderSys/"+filename+".jpg";
		    	// ��֤sd���Ƿ���ȷ��װ��
		    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) 
		    	{
		    		// �ȴ�����Ŀ¼������´���һ���ļ���ʱ�򣬸�Ŀ¼û�д��ڣ���ô�����ȴ�����Ŀ¼�����½��ļ�������
		    		if (!mars_file.exists()) {mars_file.mkdirs();}
		    		FileOutputStream b=null;
		    		try {  
		    			b = new FileOutputStream(path);  
		                bmp1.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�  
		                b.flush();  
	                    b.close();
	                    Toast.makeText(ImgViewerActivity.this, "��Ƭ�ѱ�����·��:"+path,Toast.LENGTH_LONG).show();
		            } catch (Exception e) 
		            {  
		            	Toast.makeText(ImgViewerActivity.this, "����ʧ��",Toast.LENGTH_LONG).show();
		                e.printStackTrace();
		            } 

		    	} else 
		    	{
		    		Toast.makeText(ImgViewerActivity.this, "δ�ҵ��洢",Toast.LENGTH_LONG).show();
		    	}
			}
		});
	}
	
	private Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}

