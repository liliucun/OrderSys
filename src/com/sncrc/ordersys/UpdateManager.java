package com.sncrc.ordersys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 *@author coolszy
 *@date 2012-4-26
 *@blog http://blog.92coding.com
 */

public class UpdateManager
{
    /* ������ */
    private static final int DOWNLOAD = 1;
    /* ���ؽ��� */
    private static final int DOWNLOAD_FINISH = 2;
    /* ���������XML��Ϣ */
    HashMap<String, String> mHashMap;
    /* ���ر���·�� */
    private String mSavePath;
    /* ��¼���������� */
    private int progress;
    /* �Ƿ�ȡ������ */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* ���½����� */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
            // ��������
            case DOWNLOAD:
                // ���ý�����λ��
                mProgress.setProgress(progress);
                break;
            case DOWNLOAD_FINISH:
                // ��װ�ļ�
                installApk();
                break;
            default:
                break;
            }
        };
    };

    public UpdateManager(Context context)
    {
        this.mContext = context;
    }

    /**
     * ����������
     */
    public void checkUpdate(String ver)
    {
    	if(ver!=null && ver!="")
    	{
    		try
    		{
    			//Log.d("version", ver);
	    		String[] version=ver.split("\\|");
	    		mHashMap=new HashMap();
	    		mHashMap.put("Version", version[0]);
	    		mHashMap.put("Url", version[1]);
	    		mHashMap.put("Name", version[2]);
	    		mHashMap.put("Detail", version[3]);
    		}catch(Exception e)
    		{
    			//toast.cancel();//ȡ��֮ǰ����ʾ
    			Toast.makeText(mContext, "��ȡ�汾��Ϣʧ��", Toast.LENGTH_LONG).show();
    			return;
    		}
	        if (isUpdate())
	        {
	            // ��ʾ��ʾ�Ի���
	        	//toast.cancel();
	            showNoticeDialog();
	        } else
	        {
	        	//toast.cancel();
	            //Toast.makeText(mContext, "��ǰΪ���°汾", Toast.LENGTH_LONG).show();
	        	Log.d("������", "��ǰΪ���°�");
	        }
    	}
    }

    /**
     * �������Ƿ��и��°汾
     * 
     * @return
     */
    private boolean isUpdate()
    {
        // ��ȡ��ǰ����汾
        int versionCode = getVersionCode(mContext);
        // �汾�ж�
        try
        {
            if (Integer.parseInt(mHashMap.get("Version")) > versionCode)
            {
                return true;
            }
        }catch(Exception e)
        {
        	System.out.println("��ȡ�汾�Ŵ���");
        	return false;
        }
        return false;
    }

/**
 * ��ȡ����汾��
 * 
 * @param context
 * @return
 */
private int getVersionCode(Context context)
{
    int versionCode = 0;
    try
    {
        // ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
        //versionCode = context.getPackageManager().getPackageInfo("com.sncrc.ordersys", 0).versionCode;
    	PackageInfo info = context.getPackageManager().getPackageInfo("com.sncrc.ordersys", PackageManager.GET_CONFIGURATIONS);
        //String versionName = info.versionName;
        versionCode = info.versionCode; 

    } catch (NameNotFoundException e)
    {
        e.printStackTrace();
    }
    return versionCode;
}

    /**
     * ��ʾ������¶Ի���
     */
    private void showNoticeDialog()
    {
        // ����Ի���
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("�������");
        builder.setMessage(mHashMap.get("Detail"));
        // ����
        builder.setPositiveButton("����", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // ��ʾ���ضԻ���
                showDownloadDialog();
            }
        });
        // �Ժ����
        builder.setNegativeButton("ȡ��", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * ��ʾ������ضԻ���
     */
    private void showDownloadDialog()
    {
        // ����������ضԻ���
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("��������");
        // �����ضԻ������ӽ�����
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // ȡ������
        builder.setNegativeButton("ȡ������", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // ����ȡ��״̬
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // �����ļ�
        downloadApk();
    }

    /**
     * ����apk�ļ�
     */
    private void downloadApk()
    {
        // �������߳��������
        new downloadApkThread().start();
    }

    /**
     * �����ļ��߳�
     * 
     * @author coolszy
     *@date 2012-4-26
     *@blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // ��ô洢����·��
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(mHashMap.get("Url"));
                    // ��������
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // ��ȡ�ļ���С
                    int length = conn.getContentLength();
                    // ����������
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // �ж��ļ�Ŀ¼�Ƿ����
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("Name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // ����
                    byte buf[] = new byte[1024];
                    // д�뵽�ļ���
                    do
                    {
                        int numread = is.read(buf);
                        count += numread;
                        // ���������λ��
                        progress = (int) (((float) count / length) * 100);
                        // ���½���
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0)
                        {
                            // �������
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // д���ļ�
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// ���ȡ����ֹͣ����.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // ȡ�����ضԻ�����ʾ
            mDownloadDialog.dismiss();
        }
    };

    /**
     * ��װAPK�ļ�
     */
    private void installApk()
    {
        File apkfile = new File(mSavePath, mHashMap.get("Name"));
        if (!apkfile.exists())
        {
            return;
        }
        // ͨ��Intent��װAPK�ļ�
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}