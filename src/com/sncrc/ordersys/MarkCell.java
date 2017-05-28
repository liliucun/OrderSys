package com.sncrc.ordersys;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.map.BaiduMap.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MarkCell extends Activity {
	private DBUtil dbUtil = new DBUtil();
	private AppData myApp;
	private MapView mMapView = null;
	private BaiduMap mBaiduMap=null;
	private LocationClient mLocationClient = null;
	private TextView TStreet;
	private TextView TPosition;
	private TextView TLng;
	private TextView TLat;
	private EditText TRemark,TPhone;
	private Button BSubmit;
	private String pStreet="",pPosition="",pLng="",pLat="";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (AppData) getApplication();
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("δ����С�����");
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.markcell);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap=mMapView.getMap();
		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
	    mLocationClient.registerLocationListener( mListener );    //ע���������
		initLocation();
		TStreet=(TextView) findViewById(R.id.street);
//		TPosition=(TextView) findViewById(R.id.position);
//		TLng=(TextView) findViewById(R.id.longitude);
//		TLat=(TextView) findViewById(R.id.latitude);
		TPhone=(EditText) findViewById(R.id.phone);
		TRemark=(EditText) findViewById(R.id.remark);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);    
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {   
            public void onCheckedChanged(RadioGroup group, int checkedId) {   
                RadioButton radioButton = (RadioButton) findViewById(checkedId);   
                if(radioButton.getText().equals("��ͨ��ͼ"))
                {
                	mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                else
                {
                	mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
            }   
        }); 
		BSubmit=(Button) findViewById(R.id.submit);
		BSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BSubmit.setEnabled(false);

				if(!(pStreet.equals("") || pPosition.equals("") || pLng.equals("") || pLat.equals("") || TRemark.getText().toString().equals(""))){// || TRemark.getText().toString().equals("")
					AddAddress(pStreet,pPosition,pLat,pLng,TRemark.getText().toString(),TPhone.getText().toString());//address,tags,latitude,longitude,remark
				} else {
					Toast.makeText(MarkCell.this, "������Ϣ����Ϊ�գ�",Toast.LENGTH_SHORT).show();
					BSubmit.setEnabled(true);
				}
			}
		});
		
		final BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_pin);  
        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {  
  
            @Override  
            public boolean onMapPoiClick(MapPoi arg0) {  
                // TODO Auto-generated method stub  
                return false;  
            }  
  
            //�˷������ǵ����ͼ����  
            @Override  
            public void onMapClick(LatLng latLng) {  
                //��ȡ��γ��  
                double latitude = latLng.latitude;  
                double longitude = latLng.longitude;  
                System.out.println("latitude=" + latitude + ",longitude=" + longitude);  
                //�����ͼ��  
                mBaiduMap.clear();  
                // ����Maker�����  
                LatLng point = new LatLng(latitude, longitude);  
                // ����MarkerOption�������ڵ�ͼ�����Marker  
                MarkerOptions options = new MarkerOptions().position(point)  
                        .icon(bitmap)
                        .draggable(true);  
                // �ڵ�ͼ�����Marker������ʾ  
                mBaiduMap.addOverlay(options);
                GetPosInfo(latLng);
            }  
        });
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener(){
        	@Override
        	public void onMarkerDrag(Marker marker)
        	{
        		//��ק��
        	}
        	@Override
        	public void onMarkerDragEnd(Marker marker)
        	{
        		//��ק����
        		GetPosInfo(marker.getPosition());
        	}
        	@Override
        	public void onMarkerDragStart(Marker marker)
        	{
        		//��ק��ʼ
        	}
        });
	}
	
	private void GetPosInfo(LatLng latLng)
	{
		//��ȡ��γ��  
        double latitude = latLng.latitude;  
        double longitude = latLng.longitude; 
//		TLng.setText("����:"+String.valueOf(longitude));
//      TLat.setText("γ��:"+String.valueOf(latitude));
        pLng=String.valueOf(longitude);
        pLat=String.valueOf(latitude);
		GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {  
        	@Override 
            public void onGetGeoCodeResult(GeoCodeResult result) {  
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
                    //û�м��������  
                }  
                //��ȡ���������  
            }  
         
            @Override  
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {  
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
                    //û���ҵ��������  
                }  
                //��ȡ������������  
                System.out.println(result.getAddress()+result.getSematicDescription());
                TStreet.setText("�ֵ�:"+result.getAddress());
                //TPosition.setText("λ��:"+result.getSematicDescription());
                pStreet=result.getAddress();
                pPosition=result.getSematicDescription();
            } 
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
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

			Toast.makeText(MarkCell.this,statestr,Toast.LENGTH_LONG ).show();
			// ������λͼ��  
			mBaiduMap.setMyLocationEnabled(true);  
			// ���춨λ����  
			MyLocationData locData = new MyLocationData.Builder()  
			    .accuracy(location.getRadius())  
			    // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360  
			    .direction(100).latitude(location.getLatitude())  
			    .longitude(location.getLongitude()).build();  
			// ���ö�λ����  
			mBaiduMap.setMyLocationData(locData);  
			MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(location.getLatitude(),location.getLongitude()))
					.zoom(14).build();
					// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
					MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
					// �ı��ͼ״̬
					// ������λͼ��
			mBaiduMap.setMapStatus(mMapStatusUpdate);
			// ������Ҫ��λͼ��ʱ�رն�λͼ��  
			//mBaiduMap.setMyLocationEnabled(false);
			// ����markerͼ��  
			
			mLocationClient.stop();
		}
	};
	
	private void AddAddress(final String street,final String position,final String latitude,final String longitude,final String remark,final String phone) {

		new AsyncTask<String, Void, String>() {
			Toast toast;
			@Override
			protected String doInBackground(String... params) {
				LinkedHashMap map=new LinkedHashMap();
				map.put("type", "addmark");
				map.put("street", street);
				map.put("county", myApp.getCounty());
				map.put("latitude", latitude);
				map.put("longitude", longitude);
				map.put("position", position);
				map.put("account", myApp.getUser());
				map.put("remark", remark);
				map.put("phone", phone);
				
				return dbUtil.AddAddress(map);
			}
			@Override
			protected void onPostExecute(String result) {
				toast.cancel();
				List<HashMap<String, String>> list=JsonUtil.json2list(result);
				if (list==null || list.size()==0) {
					Toast.makeText(MarkCell.this, "û�����ݣ�",Toast.LENGTH_SHORT).show();
					BSubmit.setEnabled(true);
				} else 
				{
					if(String.valueOf(list.get(0).get("status")).equals("0"))
					{
						Toast.makeText(MarkCell.this, "�ύ�ɹ�",Toast.LENGTH_SHORT).show();
						finish();
					}
					else
					{
						Toast.makeText(MarkCell.this, "�ύʧ��:"+list.get(0).get("message").toString(),Toast.LENGTH_SHORT).show();
						BSubmit.setEnabled(true);
					}
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				toast=Toast.makeText(MarkCell.this, "�ύ��...",Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

		}.execute(street,position,latitude,longitude,remark);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}
}