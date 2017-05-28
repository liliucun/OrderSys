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
		actionBar.setTitle("未覆盖小区标记");
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.markcell);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap=mMapView.getMap();
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	    mLocationClient.registerLocationListener( mListener );    //注册监听函数
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
                if(radioButton.getText().equals("普通地图"))
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
					Toast.makeText(MarkCell.this, "以上信息不能为空！",Toast.LENGTH_SHORT).show();
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
  
            //此方法就是点击地图监听  
            @Override  
            public void onMapClick(LatLng latLng) {  
                //获取经纬度  
                double latitude = latLng.latitude;  
                double longitude = latLng.longitude;  
                System.out.println("latitude=" + latitude + ",longitude=" + longitude);  
                //先清除图层  
                mBaiduMap.clear();  
                // 定义Maker坐标点  
                LatLng point = new LatLng(latitude, longitude);  
                // 构建MarkerOption，用于在地图上添加Marker  
                MarkerOptions options = new MarkerOptions().position(point)  
                        .icon(bitmap)
                        .draggable(true);  
                // 在地图上添加Marker，并显示  
                mBaiduMap.addOverlay(options);
                GetPosInfo(latLng);
            }  
        });
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener(){
        	@Override
        	public void onMarkerDrag(Marker marker)
        	{
        		//拖拽中
        	}
        	@Override
        	public void onMarkerDragEnd(Marker marker)
        	{
        		//拖拽结束
        		GetPosInfo(marker.getPosition());
        	}
        	@Override
        	public void onMarkerDragStart(Marker marker)
        	{
        		//拖拽开始
        	}
        });
	}
	
	private void GetPosInfo(LatLng latLng)
	{
		//获取经纬度  
        double latitude = latLng.latitude;  
        double longitude = latLng.longitude; 
//		TLng.setText("经度:"+String.valueOf(longitude));
//      TLat.setText("纬度:"+String.valueOf(latitude));
        pLng=String.valueOf(longitude);
        pLat=String.valueOf(latitude);
		GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {  
        	@Override 
            public void onGetGeoCodeResult(GeoCodeResult result) {  
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
                    //没有检索到结果  
                }  
                //获取地理编码结果  
            }  
         
            @Override  
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {  
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
                    //没有找到检索结果  
                }  
                //获取反向地理编码结果  
                System.out.println(result.getAddress()+result.getSematicDescription());
                TStreet.setText("街道:"+result.getAddress());
                //TPosition.setText("位置:"+result.getSematicDescription());
                pStreet=result.getAddress();
                pPosition=result.getSematicDescription();
            } 
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
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

			Toast.makeText(MarkCell.this,statestr,Toast.LENGTH_LONG ).show();
			// 开启定位图层  
			mBaiduMap.setMyLocationEnabled(true);  
			// 构造定位数据  
			MyLocationData locData = new MyLocationData.Builder()  
			    .accuracy(location.getRadius())  
			    // 此处设置开发者获取到的方向信息，顺时针0-360  
			    .direction(100).latitude(location.getLatitude())  
			    .longitude(location.getLongitude()).build();  
			// 设置定位数据  
			mBaiduMap.setMyLocationData(locData);  
			MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(location.getLatitude(),location.getLongitude()))
					.zoom(14).build();
					// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
					MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
					.newMapStatus(mMapStatus);
					// 改变地图状态
					// 开启定位图层
			mBaiduMap.setMapStatus(mMapStatusUpdate);
			// 当不需要定位图层时关闭定位图层  
			//mBaiduMap.setMyLocationEnabled(false);
			// 设置marker图标  
			
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
					Toast.makeText(MarkCell.this, "没有数据！",Toast.LENGTH_SHORT).show();
					BSubmit.setEnabled(true);
				} else 
				{
					if(String.valueOf(list.get(0).get("status")).equals("0"))
					{
						Toast.makeText(MarkCell.this, "提交成功",Toast.LENGTH_SHORT).show();
						finish();
					}
					else
					{
						Toast.makeText(MarkCell.this, "提交失败:"+list.get(0).get("message").toString(),Toast.LENGTH_SHORT).show();
						BSubmit.setEnabled(true);
					}
				}

				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				toast=Toast.makeText(MarkCell.this, "提交中...",Toast.LENGTH_SHORT);
				toast.show();
				super.onPreExecute();
			}

		}.execute(street,position,latitude,longitude,remark);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
}